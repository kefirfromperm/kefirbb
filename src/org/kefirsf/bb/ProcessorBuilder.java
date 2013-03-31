package org.kefirsf.bb;

import org.kefirsf.bb.conf.*;
import org.kefirsf.bb.proc.*;

import java.util.*;

class ProcessorBuilder {
    private final Configuration conf;

    private Map<Scope, ProcScope> createdScopes;
    private Map<Code, ProcCode> codes;
    private Map<Constant, PatternConstant> constants;

    /**
     * @param conf text processor configuration
     */
    ProcessorBuilder(Configuration conf) {
        this.conf = conf;
    }

    /**
     * Build an processor.
     */
    public BBProcessor build() {
        this.createdScopes = new HashMap<Scope, ProcScope>();
        this.codes = new HashMap<Code, ProcCode>();
        this.constants = new HashMap<Constant, PatternConstant>();

        BBProcessor processor = new BBProcessor();
        processor.setScope(createScope(conf.getRootScope()));
        processor.setPrefix(createTemplate(conf.getPrefix()));
        processor.setSuffix(createTemplate(conf.getSuffix()));
        processor.setParams(conf.getParams());
        processor.setConstants(new HashSet<PatternConstant>(constants.values()));
        processor.setNestingLimit(conf.getNestingLimit());
        processor.setPropagateNestingException(conf.isPropagateNestingException());
        return processor;
    }

    /**
     * Find or create the scope.
     *
     * @param scope the scope configuration
     * @return scope scope
     */
    private ProcScope createScope(Scope scope) {
        ProcScope created = createdScopes.get(scope);
        if (created == null) {
            created = new ProcScope(scope.getName());
            createdScopes.put(scope, created);
            created.setIgnoreText(scope.isIgnoreText());
            if (scope.getParent() != null) {
                created.setParent(createScope(scope.getParent()));
            }
            Set<ProcCode> scopeCodes = new HashSet<ProcCode>();
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
    private ProcCode createCode(Code defCode) {
        if (defCode.getPattern() == null) {
            throw new IllegalStateException("Field pattern can't be null.");
        }

        if (defCode.getTemplate() == null) {
            throw new IllegalStateException("Field template can't be null.");
        }

        ProcCode code = codes.get(defCode);
        if (code == null) {
            code = new ProcCode(
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
    private ProcTemplate createTemplate(Template template) {
        List<ProcTemplateElement> elements = new ArrayList<ProcTemplateElement>();
        if (template.getElements() != null) {
            for (TemplateElement element : template.getElements()) {
                if (element instanceof Constant) {
                    elements.add(new TemplateConstant(((Constant) element).getValue()));
                } else if (element instanceof NamedValue) {
                    elements.add(new ProcNamedValue(((NamedValue) element).getName()));
                }
            }
            return new ProcTemplate(elements);
        } else {
            return ProcTemplate.EMPTY;
        }
    }

    /**
     * Create a pattern for text parsing
     *
     * @param pattern pattern definition
     * @return pattern pattern
     */
    private ProcPattern createPattern(Pattern pattern) {
        if (pattern.getElements() == null || pattern.getElements().isEmpty()) {
            throw new IllegalStateException("Pattern elements list can't be empty.");
        }

        List<ProcPatternElement> elements = new ArrayList<ProcPatternElement>();
        for (PatternElement element : pattern.getElements()) {
            if (element instanceof Variable) {
                elements.add(new ProcVariable(((Variable) element).getName(), ((Variable) element).getRegex()));
            } else if (element instanceof Text) {
                elements.add(create((Text) element));
            } else if (element instanceof Constant) {
                elements.add(createPatternConstant((Constant) element));
            } else if (element instanceof Junk) {
                elements.add(new PatternJunk());
            }
        }
        return new ProcPattern(elements);
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
    private ProcPatternElement create(Text text) {
        Scope scope = text.getScope();
        if (scope != null) {
            return new ProcText(
                    text.getName(),
                    createScope(scope),
                    text.isTransparent()
            );
        } else {
            return new ProcText(text.getName(), text.isTransparent());
        }
    }
}
