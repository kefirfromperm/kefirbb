package org.kefirsf.bb.conf;

import org.kefirsf.bb.util.Exceptions;
import org.kefirsf.bb.util.Utils;

/**
 * Code description.
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public class Code {
    public static final int DEFAULT_PRIORITY = 0;

    private String name;
    private int priority = DEFAULT_PRIORITY;
    private Pattern pattern;
    private Template template;

    /**
     * Construct code definition with random name and default priority.
     */
    public Code() {
        this.name = Utils.generateRandomName();
    }

    /**
     * Construct code definition with name and default priority.
     *
     * @param name code name
     */
    public Code(String name) {
        Exceptions.nullArgument("name", name);
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
        Exceptions.nullArgument("name", name);
        this.name = name;
        this.priority = priority;
        this.pattern = pattern;
        this.template = template;
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
     * Set code name.
     *
     * @param name code name
     */
    public void setName(String name) {
        Exceptions.nullArgument("name", name);
        this.name = name;
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
