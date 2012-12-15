package org.kefirsf.bb;

import org.kefirsf.bb.comp.*;
import org.kefirsf.bb.conf.*;

import java.util.*;

class ProcessorBuilder {
    private final Configuration conf;
    private final Map<Scope, WScope> createdScopes;
    private final Map<Code, AbstractCode> codes;

    /**
     * @param conf text processor configuration
     */
    public ProcessorBuilder(Configuration conf) {
        this.conf = conf;
        this.createdScopes = new HashMap<Scope, WScope>();
        this.codes = new HashMap<Code, AbstractCode>();
    }

    /**
     * Build an processor.
     */
    public BBProcessor build() {
        BBProcessor processor = new BBProcessor();
        processor.setScope(createScope(conf.getRootScope()));
        processor.setPrefix(createTemplate(conf.getPrefix()));
        processor.setSuffix(createTemplate(conf.getSuffix()));
        processor.setParams(conf.getParams());
        return processor;
    }

    /**
     * Create a scope
     *
     * @param scope the scope configuration
     * @return scope scope
     */
    private WScope createScope(Scope scope) {
        WScope created = createdScopes.get(scope);
        if (created == null) {
            created = new WScope(scope.getName());
            createdScopes.put(scope, created);
            created.setIgnoreText(scope.isIgnoreText());
            if (scope.getParent() != null) {
                created.setParent(getScope(scope.getParent()));
            }
            Set<AbstractCode> scopeCodes = new HashSet<AbstractCode>();
            for (Code code : scope.getCodes()) {
                scopeCodes.add(createCode(code));
            }
            created.setScopeCodes(scopeCodes);
            created.init();
        }
        return created;
    }

    /**
     * Create code from this definition
     *
     * @param defCode code definition
     * @return code object
     */
    private AbstractCode createCode(Code defCode) {
        if (defCode.getPattern() == null) {
            throw new IllegalStateException("Field pattern can't be null.");
        }

        if (defCode.getTemplate() == null) {
            throw new IllegalStateException("Field template can't be null.");
        }

        AbstractCode code = codes.get(defCode);
        if (code == null) {
            List<? extends PatternElement> patternElements = defCode.getPattern().getElements();
            PatternElement first = patternElements.get(0);
            if (patternElements.size() == 1 && first instanceof Constant && !((Constant) first).isIgnoreCase()) {
                code = new ConstantCode(
                        ((Constant) first).getValue(),
                        createTemplate(defCode.getTemplate()),
                        defCode.getName(),
                        defCode.getPriority()
                );
            } else {
                code = new WCode(
                        createPattern(defCode.getPattern()),
                        createTemplate(defCode.getTemplate()),
                        defCode.getName(),
                        defCode.getPriority()
                );
            }
        }
        return code;
    }

    /**
     * Create a template from definition
     *
     * @param template the template definition
     * @return template
     */
    private WTemplate createTemplate(Template template) {
        List<WTemplateElement> elements = new ArrayList<WTemplateElement>();
        if (template.getElements() != null) {
            for (TemplateElement element : template.getElements()) {
                if (element instanceof Constant) {
                    elements.add(new WConstant(((Constant) element).getValue()));
                } else if (element instanceof NamedValue) {
                    elements.add(new WNamedValue(((NamedValue) element).getName()));
                }
            }
        }
        return new WTemplate(elements);
    }

    /**
     * Create a pattern for text parsing
     *
     * @param pattern pattern definition
     * @return pattern pattern
     */
    private WPattern createPattern(Pattern pattern) {
        if (pattern.getElements() == null || pattern.getElements().isEmpty()) {
            throw new IllegalStateException("Pattern elements list can't be empty.");
        }

        List<WPatternElement> elements = new ArrayList<WPatternElement>();
        for (PatternElement element : pattern.getElements()) {
            if (element instanceof Variable) {
                elements.add(new WVariable(((Variable) element).getName(), ((Variable) element).getRegex()));
            } else if (element instanceof Text) {
                elements.add(create(((Text) element)));
            } else if (element instanceof Constant) {
                elements.add(createPatternConstant(((Constant) element)));
            }
        }
        return new WPattern(elements);
    }

    /**
     * Create a constant element for text parsing
     *
     * @param constant constant definition
     * @return pattern element for constant
     */
    private WPatternElement createPatternConstant(Constant constant) {
        if (!constant.isIgnoreCase()) {
            return new WConstant(constant.getValue());
        } else {
            return new WConstantIgnoreCase(constant.getValue());
        }
    }

    /**
     * @param text element definition
     * @return pattern element for text
     */
    private WPatternElement create(Text text) {
        String scopeName = text.getScope();
        if (scopeName != null) {
            return new WText(
                    text.getName(),
                    getScope(scopeName),
                    text.isTransparent()
            );
        } else {
            return new WText(text.getName(), text.isTransparent());
        }
    }

    /**
     * Find or create the scope by name and return in
     * @param name scope name
     * @return scope
     */
    private WScope getScope(String name) {
        return createScope(conf.getScope(name));
    }
}
