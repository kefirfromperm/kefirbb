package org.kefirsf.bb.proc;

/**
 * Beginning of line
 *
 * @author kefir
 */
public class ProcBol implements ProcPatternElement {
    public boolean parse(Context context, ProcPatternElement terminator) throws NestingException {
        return isNextIn(context.getSource());
    }

    public boolean isNextIn(Source source) {
        if (source.getOffset() == 0) {
            return true;
        } else {
            char c = source.get(source.getOffset() - 1);
            if (c == '\n' || c == '\r') {
                return true;
            }
        }
        return false;
    }

    /**
     * I strongly don't recommend to use tag sol as a terminator.
     */
    public int findIn(Source source) {
        int n = source.find(new char[]{'\n'}, false);
        int r = source.find(new char[]{'\r'}, false);
        if (n >= 0 && r >= 0) {
            if (n < r) {
                if (source.getLength() > n + 1) {
                    if (source.get(n + 1) == '\r') {
                        return n + 2;
                    }
                }
                return n + 1;
            } else {
                if (source.getLength() > r + 1) {
                    if (source.get(r + 1) == '\r') {
                        return r + 2;
                    }
                }
                return r + 1;
            }
        } else if (n >= 0) {
            return n;
        } else if (r >= 0) {
            return r;
        } else {
            return -1;
        }
    }
}
