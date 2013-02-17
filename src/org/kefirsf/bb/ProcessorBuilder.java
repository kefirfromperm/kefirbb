package org.kefirsf.bb;

import org.kefirsf.bb.conf.*;
import org.kefirsf.bb.proc.*;

import java.util.*;

class ProcessorBuilder {
    private final Configuration conf;

    private Map<Scope, WScope> createdScopes;
    private Map<Code, WCode> codes;
    private Map<Constant, PatternConstant> constants;

    /**
     * @param conf text processor configuration
     */
    public ProcessorBuilder(Configuration conf) {
        this.conf = conf;
    }

    /**
     * Build an processor.
     */
    public BBProcessor build() {
        this.createdScopes = new HashMap<Scope, WScope>();
        this.codes = new HashMap<Code, WCode>();
        this.constants = new HashMap<Constant, PatternConstant>();

        BBProcessor processor = new BBProcessor();
        processor.setScope(createScope(conf.getRootScope()));
        processor.setPrefix(createTemplate(conf.getPrefix()));
        processor.setSuffix(createTemplate(conf.getSuffix()));
        processor.setParams(conf.getParams());
        processor.setConstants(new HashSet<PatternConstant>(constants.values()));
        return processor;
    }

    /**
     * Find or create the scope.
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
                created.setParent(createScope(conf.getScope(scope.getParent())));
            }
            Set<WCode> scopeCodes = new HashSet<WCode>();
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
    private WCode createCode(Code defCode) {
        if (defCode.getPattern() == null) {
            throw new IllegalStateException("Field pattern can't be null.");
        }

        if (defCode.getTemplate() == null) {
            throw new IllegalStateException("Field template can't be null.");
        }

        WCode code = codes.get(defCode);
        if (code == null) {
            code = new WCode(
                    createPattern(defCode.getPattern()),
                    createTemplate(defCode.getTemplate()),
                    defCode.getName(),
                    defCode.getPriority()
            );
            codes.put(defCode, code);
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
                    elements.add(new TemplateConstant(((Constant) element).getValue()));
                } else if (element instanceof NamedValue) {
                    elements.add(new WNamedValue(((NamedValue) element).getName()));
                }
            }
            return new WTemplate(elements);
        } else {
            return WTemplate.EMPTY;
        }
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
            } else if (element instanceof Junk) {
                elements.add(new PatternJunk());
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
    private PatternConstant createPatternConstant(Constant constant) {
        if (!constants.containsKey(constant)) {
            constants.put(constant, new PatternConstant(constant.getValue(), constant.isIgnoreCase()));
        }
        return constants.get(constant);
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
                    createScope(conf.getScope(scopeName)),
                    text.isTransparent()
            );
        } else {
            return new WText(text.getName(), text.isTransparent());
        }
    }
}
