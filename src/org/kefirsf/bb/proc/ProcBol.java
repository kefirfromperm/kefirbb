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
        return source.hasNext() && onBol(source);
    }

    /**
     * I strongly don't recommend to use tag bol as a terminator.
     */
    public int findIn(Source source) {
        if (source.hasNext()) {
            // maybe we are on the beginning of line already?
            if (onBol(source)) {
                return source.getOffset();
            }

            // If no find next line break
            int index = findLineBreak(source);

            if (index < 0) {
                return -1;
            }

            // Skip line break 1 or 2 characters
            index = skipLineBreak(source, index);

            // If index is not on the end of text return it otherwise it's not a beginning of line.
            if (index < source.length()) {
                return index;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    /**
     * Check we are on the beginning of line
     *
     * @param source the text source
     * @return true if we are on the beginning of line
     */
    private boolean onBol(Source source) {
        int offset = source.getOffset();
        if (offset == 0) {
            return true;
        } else {
            char c = source.charAt(offset);
            char p = source.charAt(offset - 1);
            return c != '\n' && c != '\r' && (p == '\n' || p == '\r');
        }
    }

    /**
     * Find first input of line break
     *
     * @param source the text source
     */
    private int findLineBreak(Source source) {
        int index;
        int n = source.find(AbstractEol.AN, false);
        int r = source.find(AbstractEol.AR, false);
        if (n >= 0 && r >= 0) {
            index = Math.min(n, r);
        } else if (n >= 0) {
            index = n;
        } else if (r >= 0) {
            index = r;
        } else {
            index = -1;
        }
        return index;
    }

    /**
     * Skip line break characters. '\r\n' for example
     *
     * @param source text source
     * @param offset current index
     * @return new index
     */
    private int skipLineBreak(Source source, final int offset) {
        char p;
        char c;
        int index = offset;
        p = source.charAt(index);
        index++;
        if (index < source.length()) {
            c = source.charAt(index);
            if ((c == '\n' || c == '\r') && c != p) {
                index++;
            }
        }
        return index;
    }

    @Override
    public String toString() {
        return "<bol/>";
    }
}
