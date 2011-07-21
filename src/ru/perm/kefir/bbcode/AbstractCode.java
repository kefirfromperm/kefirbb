package ru.perm.kefir.bbcode;

import java.io.IOException;

/**
 * Abstract bb-code
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public abstract class AbstractCode implements Comparable<AbstractCode> {

    /**
     * Priority. If priority higher then code be checking early.
     */
    private final int priority;

    /**
     * The code name.
     */
    private final String name;

    /**
     * template for build result char sequence
     */
    protected final WTemplate template;

    /**
     * Abstract constructor for the bb-code with priority
     *
     * @param template template
     * @param name     name of code
     * @param priority priority. If priority higher then code be checking early.
     */
    protected AbstractCode(WTemplate template, String name, int priority) {
        this.template = template;
        this.priority = priority;
        this.name = name;
    }

    /**
     * Process code: parse source and generate result string
     *
     * @param context current context
     * @return true if next sequence in source is valid code
     * @throws IOException append result to target
     */
    public abstract boolean process(Context context) throws IOException;

    /**
     * @param source source of text
     * @return true if next sequence can be this code
     */
    public abstract boolean suspicious(Source source);

    /**
     * Compare by priorities
     */
    public int compareTo(AbstractCode code) {
        return this.priority - code.priority;
    }

    /**
     * Get code name
     *
     * @return code name
     */
    public String getName() {
        return name;
    }
}
