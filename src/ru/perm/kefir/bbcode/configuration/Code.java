package ru.perm.kefir.bbcode.configuration;

import ru.perm.kefir.bbcode.*;

import java.util.List;
import java.util.Map;

/**
 * Code description.
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public final class Code {
    public static final int DEFAULT_PRIORITY = 0;

    private final String name;
    private int priority = DEFAULT_PRIORITY;
    private Pattern pattern;
    private Template template;

    /**
     * Construct code definition with random name and default priority.
     */
    public Code() {
        this.name = Util.generateRandomName();
    }

    /**
     * Construct code definition with name and default priority.
     *
     * @param name code name
     */
    public Code(String name) {
        assertNameNotNull(name);
        this.name = name;
    }

    /**
     * Create code definition.
     *
     * @param pattern  text pattern for parsing
     * @param template text generation template
     * @param name     code name
     * @param priority code priority
     */
    public Code(Pattern pattern, Template template, String name, int priority) {
        assertNameNotNull(name);
        this.name = name;
        this.priority = priority;
        this.pattern = pattern;
        this.template = template;
    }

    /**
     * Check name parameter in constructor
     *
     * @param name name of code
     */
    private void assertNameNotNull(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Code name can't be null.");
        }
    }

    /**
     * Create code from this definition
     *
     * @param configuration text processor configuration
     * @param createdScopes scopes are created already
     * @param codes         codes are created already
     * @return code object
     */
    AbstractCode create(Configuration configuration, Map<Scope, WScope> createdScopes, Map<Code, AbstractCode> codes) {
        if (pattern == null) {
            throw new IllegalStateException("Field pattern can't be null.");
        }

        if (template == null) {
            throw new IllegalStateException("Field template can't be null.");
        }

        AbstractCode code = codes.get(this);
        if (code == null) {
            List<? extends PatternElement> patternElements = getPattern().getElements();
            PatternElement first = patternElements.get(0);
            if (patternElements.size() == 1 && first instanceof Constant && !((Constant) first).isIgnoreCase()) {
                code = new ConstantCode(
                        ((Constant) first).getValue(), getTemplate().create(), getName(), getPriority()
                );
            } else {
                code = new WCode(
                        getPattern().create(configuration, createdScopes, codes),
                        getTemplate().create(),
                        getName(),
                        getPriority()
                );
            }
        }
        return code;
    }

    /**
     * Get code name
     *
     * @return name of code
     */
    public String getName() {
        return name;
    }

    /**
     * @return code priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     * @param priority code priority
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * Get pattern for text parsing
     *
     * @return pattern definition object
     */
    public Pattern getPattern() {
        return pattern;
    }

    /**
     * Set pattern for text parsing
     *
     * @param pattern pattern definition object
     */
    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    /**
     * Get template for text generation.
     *
     * @return template definition  object
     */
    public Template getTemplate() {
        return template;
    }

    /**
     * Set template for text generation
     *
     * @param template template definition  object
     */
    public void setTemplate(Template template) {
        this.template = template;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Code code = (Code) o;

        return name.equals(code.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
