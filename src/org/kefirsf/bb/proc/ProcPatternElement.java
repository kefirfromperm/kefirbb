package org.kefirsf.bb.proc;

/**
 * Pattern element for parse part of bbcode
 *
 * @author Kefir
 */
public interface ProcPatternElement {
    /**
     * Parse element
     *
     * @param context    context
     * @param terminator terminator to stop text process
     * @return true - subsequence is valid to this pattern
     *         false - not valid
     * @throws NestingException if nesting of tags is bigger than maximum nesting value
     */
    boolean parse(Context context, ProcPatternElement terminator) throws NestingException;

    /**
     * Check next subsequence
     *
     * @param context current context
     * @return true pattern sequence equals with next subsequence
     *         false not equals
     */
    boolean isNextIn(Context context);

    /**
     * Find constant
     *
     * @param source text source
     * @return constant offset
     */
    int findIn(Source source);
}
