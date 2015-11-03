package org.kefirsf.bb.proc;

/**
 * Beginning of line
 *
 * @author kefir
 */
public class ProcBol implements ProcPatternElement {
    public boolean parse(Context context, ProcPatternElement terminator) throws NestingException {
        return isNextIn(context);
    }

    public boolean isNextIn(Context context) {
        Source source = context.getSource();
        int offset = source.getOffset();
        int length = source.length();
        if (offset < length) {
            if (offset == 0) {
                char c = source.charAt(offset);
                if (c != '\n' && c != '\r') {
                    return true;
                }
            } else {
                char p = source.charAt(offset - 1);
                char c = source.charAt(offset);
                if ((p == '\n' || p == '\r') && c != '\n' && c != '\r') {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * I strongly don't recommend to use tag bol as a terminator.
     */
    public int findIn(Source source) {
        int offset = source.getOffset();
        int length = source.length();
        if (offset < length) {
            if (offset == 0) {
                char c = source.charAt(offset);
                if (c != '\n' && c != '\r') {
                    return 0;
                }
            } else {
                char c = source.charAt(offset);
                char p = source.charAt(offset - 1);
                if (c != '\n' && c != '\r' && (p == '\n' || p == '\r')) {
                    return offset;
                }

                int index;
                int n = source.findFrom(offset, AbstractEol.AN, false);
                int r = source.findFrom(offset, AbstractEol.AR, false);
                if (n >= 0 && r >= 0) {
                    index = Math.min(n, r);
                } else if (n >= 0) {
                    index = n;
                } else if (r >= 0) {
                    index = r;
                } else {
                    return -1;
                }

                // Whitespaces
                while (index < source.length() && !lineBreak(source.charAt(index))) {
                    index++;
                }

                return index;
            }
        }
        return -1;
    }

    private boolean lineBreak(double c) {
        return (c == '\n' || c == '\r');
    }
}
