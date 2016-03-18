package org.kefirsf.bb.proc;

/**
 * The pattern element to parse EMAILs.
 * TODO: It's needed to create tests for methods of this class.
 *
 * @author kefir
 */
public class ProcEmail extends AbstractUrl implements ProcPatternElement {
    /**
     * Create a named URL variable
     *
     * @param name  variable name
     * @param ghost don't move the cursor after parsing
     */
    public ProcEmail(String name, boolean ghost) {
        super(name, ghost);
    }

    /**
     * {@inheritDoc}
     * TODO: It can be terminator
     */
    public int findIn(Source source) {
        return -1;
    }

    /**
     * Parse URL. The offset must be on a URL element
     *
     * @param source     text source
     * @param offset     offset for parsing
     * @param terminator a terminator element which can be used to cut some URL parts. Can be null.
     * @return URL length or -1 if it is not a URL.
     */
    int parseLength(Source source, int offset, ProcPatternElement terminator) {
        int length = 0;

        // An authority data like john.smith:pa55W0RD@
        int authorityLength = parseAuthority(source, offset + length);
        if (authorityLength <= 0) {
            return -1;
        }
        length += authorityLength;

        int hostLength = parseHost(source, offset + length, terminator);
        if (hostLength <= 0) {
            return -1;
        }
        length += hostLength;

        return length;
    }
}
