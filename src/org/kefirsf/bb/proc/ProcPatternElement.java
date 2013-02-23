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
     * @param terminator teminator to stop text process
     * @return true - subsequence is valid to this pattern
     *         false - not valid
     */
    public boolean parse(Context context, ProcPatternElement terminator) throws NestingException;

    /**
     * Check next subsequence
     *
     * @param source source text
     * @return true pattern sequence equals with next subsequence
     *         false not equals
     */
    public boolean isNextIn(Source source);

    /**
     * Find constant
     *
     * @param source text source
     * @return constant offset
     */
    public int findIn(Source source);
}
