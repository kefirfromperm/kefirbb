package org.kefirsf.bb.conf;

import org.kefirsf.bb.util.Exceptions;
import org.kefirsf.bb.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Code description.
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public class Code {
    public static final int DEFAULT_PRIORITY = 0;

    private String name;
    private int priority = DEFAULT_PRIORITY;

    /**
     * A code contains collection of patterns for parsing. One or more.
     * A code tries to parse by all patterns from first to last.
     */
    private List<Pattern> patterns;

    /**
     * A template for text generating.
     */
    private Template template;

    private boolean transparent = true;

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
        addPattern(pattern);
        this.template = template;
    }

    /**
     * Get a code name.
     *
     * @return name of code
     */
    public String getName() {
        return name;
    }

    /**
     * Set a code name.
     *
     * @param name code name
     */
    public void setName(String name) {
        Exceptions.nullArgument("name", name);
        this.name = name;
    }

    /**
     * Get the code priority. By default it is 0. Greater has bigger priority.
     *
     * @return code priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Set the code priority. By default it is 0. Greater has bigger priority.
     *
     * @param priority code priority
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * A code contains collection of patterns for parsing. One or more.
     * A code tries to parse by all patterns from first to last.
     *
     * @return the list of patterns of the code
     */
    public List<Pattern> getPatterns() {
        return patterns;
    }

    /**
     * Set the patterns list. A code contains collection of patterns for parsing. One or more.
     * A code tries to parse by all patterns from first to last.
     *
     * @param patterns the list of patterns
     */
    public void setPatterns(List<Pattern> patterns) {
        this.patterns = patterns;
    }

    /**
     * Add a pattern to the list of patterns.
     *
     * @param pattern a pattern
     */
    public void addPattern(Pattern pattern) {
        if (patterns == null) {
            patterns = new ArrayList<Pattern>();
        }

        patterns.add(pattern);
    }

    /**
     * Check if the code has patterns.
     *
     * @return true if the code has one or more patterns, false otherwise
     */
    public boolean hasPatterns() {
        return patterns != null && !patterns.isEmpty();
    }

    /**
     * Get pattern for text parsing
     *
     * @return pattern definition object
     */
    @Deprecated
    public Pattern getPattern() {
        if (patterns != null && !patterns.isEmpty()) {
            return patterns.get(0);
        } else {
            return null;
        }
    }

    /**
     * Set pattern for text parsing
     *
     * @param pattern pattern definition object
     */
    @Deprecated
    public void setPattern(Pattern pattern) {
        patterns = new ArrayList<Pattern>(1);
        patterns.add(pattern);
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

    /**
     * If the code is transparent, then all variables which were set on the code processing
     * will be accessible from other codes from on the same context, the same indentation level.
     *
     * @return true if the code is transparent, false otherwise
     */
    public boolean isTransparent() {
        return transparent;
    }

    /**
     * If the code is transparent, then all variables which were set on the code processing
     * will be accessible from other codes from on the same context, the same indentation level.
     *
     * @param transparent true for transparent
     */
    public void setTransparent(boolean transparent) {
        this.transparent = transparent;
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
