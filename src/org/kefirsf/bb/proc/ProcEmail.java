package org.kefirsf.bb.proc;

import java.text.MessageFormat;
import java.util.regex.Matcher;

/**
 * The pattern element to parse EMAILs.
 *
 * @author kefir
 */
public class ProcEmail extends AbstractUrl {
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
     */
    @Override
    public int findIn(Source source) {
        Matcher matcher = REGEX_AUTHORITY.matcher(source.subToEnd());
        int offset = -1;
        while (matcher.find()) {
            int matcherPosition = source.getOffset() + matcher.start();
            if (parseLength(source, matcherPosition, null) > 0) {
                offset = matcherPosition;
                break;
            }
        }
        return offset;
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

        // A query like ?key1=value1&key2=value2
        length += parseQuery(source, offset + length, terminator);

        return length;
    }

    @Override
    public String toString() {
        return MessageFormat.format(
                "<email name=\"{0}\" ghost=\"{1}\"/>",
                getName(), ghost
        );
    }
}
