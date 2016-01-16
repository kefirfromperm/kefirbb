package org.kefirsf.bb.proc;

import java.util.List;

/**
 * The bbcode class
 *
 * @author Kefir
 */
public class ProcCode implements Comparable<ProcCode> {
    /**
     * template for build result char sequence
     */
    private final ProcTemplate template;
    /**
     * Pattern for parsing code
     */
    private final List<ProcPattern> patterns;
    /**
     * Priority. If priority higher then code be checking early.
     */
    private final int priority;
    /**
     * The code name.
     */
    private final String name;

    /**
     * Do show variables outside the code?
     */
    private final boolean transparent;

    /**
     * Create the bb-code with priority
     *  @param patterns  pattern to parse the source text
     * @param template template to build target text
     * @param name     name of code
     * @param priority priority. If priority higher then code be checking early.
     * @param transparent Do show variables outside the code?
     */
    public ProcCode(List<ProcPattern> patterns, ProcTemplate template, String name, int priority, boolean transparent) {
        this.template = template;
        this.priority = priority;
        this.name = name;
        this.patterns = patterns;
        this.transparent = transparent;
    }

    /**
     * Parse bb-code
     *
     * Before invocation suspicious method must be call
     *
     * @param context the bb-processing context
     * @return true - if parse source
     *         false - if can't parse code
     * @throws NestingException if nesting is too big.
     */
    public boolean process(Context context) throws NestingException {
        for(ProcPattern pattern: patterns){
            Context codeContext = new Context(context);
            if (pattern.parse(codeContext)) {
                if(transparent) {
                    codeContext.mergeWithParent();
                }
                template.generate(codeContext);
                return true;
            }
        }

        return false;
    }

    /**
     * Check if next sequence can be parsed with this code.
     * It's most called method in this project.
     *
     * @param context current context
     * @return true - if next sequence can be parsed with this code;
     *         false - only if next sequence can't be parsed with this code.
     */
    public boolean suspicious(Context context) {
        for(ProcPattern pattern:patterns){
            if(pattern.suspicious(context)){
                return true;
            }
        }
        return false;
    }

    /**
     * Compare by priorities
     */
    public int compareTo(ProcCode code) {
        return this.priority - code.priority;
    }

    public boolean startsWithConstant(){
        for(ProcPattern pattern: patterns){
            if(!pattern.startsWithConstant()){
                return false;
            }
        }
        return true;
    }

    @SuppressWarnings("RedundantIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProcCode procCode = (ProcCode) o;

        if (name != null ? !name.equals(procCode.name) : procCode.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    public boolean containsCheck() {
        for(ProcPattern pattern: patterns){
            if(pattern.hasCheck()){
                return true;
            }
        }
        return false;
    }
}
