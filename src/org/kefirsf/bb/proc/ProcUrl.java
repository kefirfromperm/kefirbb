package org.kefirsf.bb.proc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parse an URL
 * @author kefir
 */
public class ProcUrl extends ProcNamedElement implements ProcPatternElement {
    private static final Pattern pattern = Pattern.compile(
            "((ht|f)tps?://)?(\\w+\\.)+\\w{2,}(/\\w+)*"
    );
    boolean ghost = false;

    public ProcUrl(String name) {
        super(name);
    }

    /**
     * Parse element
     *
     * @param context    context
     * @param terminator teminator to stop text process
     * @return true - subsequence is valid to this pattern
     * false - not valid
     */
    public boolean parse(Context context, ProcPatternElement terminator) throws NestingException {
        Source source = context.getSource();
        int offset = source.getOffset();

        int end;
        if (terminator != null) {
            end = terminator.findIn(context.getSource());
            if (end < 0) {
                end = context.getSource().length();
            }
        } else {
            end = context.getSource().length();
        }


        CharSequence value = source.sub(end);

        // find this regex in value
            Matcher matcher = pattern.matcher(value);
            if (matcher.lookingAt()) {
                int lend = matcher.end();
                end = offset + lend;
                value = matcher.group();
            } else {
                return false;
            }

        // Test this variable already defined and equals with this in this code scope
        Object attr = context.getLocalAttribute(getName());
        if (attr == null || attr.equals(value)) {
            if (attr == null) {
                setAttribute(context, value);
            }
            if(!ghost) {
                source.incOffset(end - offset);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check next subsequence
     *
     * @param context current context
     * @return true pattern sequence equals with next subsequence
     * false not equals
     */
    public boolean isNextIn(Context context) {
        return pattern.matcher(context.getSource().subToEnd()).lookingAt();
    }

    /**
     * Find constant
     *
     * @param source text source
     * @return constant offset
     */
    public int findIn(Source source) {
        if (pattern != null) {
            Matcher matcher = pattern.matcher(source.subToEnd());
            if (matcher.find()) {
                return source.getOffset() + matcher.start();
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }
}
