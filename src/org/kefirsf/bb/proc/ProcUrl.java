package org.kefirsf.bb.proc;

/**
 * The pattern element to parse URLs.
 *
 * @author kefir
 */
public class ProcUrl extends ProcNamedElement implements ProcPatternElement {
    /**
     * Don't move the cursor offset.
     */
    private boolean ghost = false;

    /**
     * Create a named URL variable
     *
     * @param name   variable name
     * @param ghost don't move the cursor after parsing
     */
    public ProcUrl(String name, boolean ghost) {
        super(name);
        this.ghost = ghost;
    }

    /**
     * {@inheritDoc}
     */
    public boolean parse(Context context, ProcPatternElement terminator) throws NestingException {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isNextIn(Context context) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public int findIn(Source source) {
        return 0;
    }
}

