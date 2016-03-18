package org.kefirsf.bb.proc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author kefir
 */
public abstract class AbstractUrl extends ProcNamedElement {
    static final Pattern REGEX_AUTHORITY = Pattern.compile(
            "[\\w\\.\\-~_!\\$&'\\(\\)%;:=\\+,\\*]+(:[\\w\\.\\-~_!\\$&'\\(\\)%;:=\\+,\\*]+)?@"
    );
    static final Pattern REGEX_HOST = Pattern.compile(
            "([\\da-zA-Z](\\-?\\w+)*\\.)*[\\da-zA-Z](\\-?\\w+)*\\.?"
    );
    /**
     * Don't move the cursor offset.
     */
    protected final boolean ghost;

    public AbstractUrl(String name, boolean ghost) {
        super(name);
        this.ghost = ghost;
    }

    /**
     * {@inheritDoc}
     */
    public boolean parse(Context context, ProcPatternElement terminator) throws NestingException {
        Source source = context.getSource();
        int length = parseLength(source, source.getOffset(), terminator);
        if (length >= 0) {
            context.setAttribute(getName(), source.sub(source.getOffset() + length));
            if (!ghost) {
                source.incOffset(length);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean isNextIn(Context context) {
        Source source = context.getSource();
        return parseLength(source, source.getOffset(), context.getTerminator()) >= 0;
    }

    /**
     * Parse URL. The offset must be on a URL element
     *
     * @param source     text source
     * @param offset     offset for parsing
     * @param terminator a terminator element which can be used to cut some URL parts. Can be null.
     * @return URL length or -1 if it is not a URL.
     */
    abstract int parseLength(Source source, int offset, ProcPatternElement terminator);

    int parseHost(Source source, int offset, ProcPatternElement terminator) {
        return parseRegex(source, offset, calcEnd(source, terminator), REGEX_HOST);
    }

    protected int calcEnd(Source source, ProcPatternElement terminator) {
        int end = source.length();
        if (terminator != null) {
            int ind = terminator.findIn(source);
            if (ind > 0) {
                end = ind;
            }
        }
        return end;
    }

    protected int parseRegex(Source source, int offset, int end, Pattern pattern) {
        CharSequence seq = source.subSequence(offset, end);
        Matcher matcher = pattern.matcher(seq);
        if (matcher.lookingAt()) {
            return matcher.group().length();
        } else {
            return 0;
        }
    }

    int parseAuthority(Source source, int offset) {
        return parseRegex(source, offset, source.length(), REGEX_AUTHORITY);
    }
}
