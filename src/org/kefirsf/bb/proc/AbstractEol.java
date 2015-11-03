package org.kefirsf.bb.proc;

/**
 * @author kefir
 */
public abstract class AbstractEol implements ProcPatternElement {

    protected static final char[] AN = new char[]{'\n'};
    protected static final char[] AR = new char[]{'\r'};

    protected final boolean ghost;

    public AbstractEol(boolean ghost) {
        this.ghost = ghost;
    }

    public boolean parse(Context context, ProcPatternElement terminator) throws NestingException {
        Source source = context.getSource();
        int len = match(source);
        if (len >= 0) {
            if(!ghost) {
                source.incOffset(len);
            }
            return true;
        } else {
            return false;
        }
    }

    private int match(Source source) {
        return match(source, source.getOffset());
    }

    public boolean isNextIn(Context context) {
        return match(context.getSource()) >= 0;
    }

    public int findIn(Source source) {
        int index = source.getOffset()-1;
        do {
            index++;
            int n = source.findFrom(index, AN, false);
            int r = source.findFrom(index, AR, false);
            if (n >= 0 && r >= 0) {
                index = Math.min(n, r);
            } else if (n >= 0) {
                index = n;
            } else if (r >= 0) {
                index = r;
            } else {
                index = source.length();
            }
        } while (match(source, index) < 0);

        return index;
    }

    protected abstract int match(Source source, int index);

    /**
     * @return -1 if at the index there is not end of line
     * 0 if it's end of text
     * 1 if it's single \n or \r
     * 2 if it's pair \n\r or \r\n
     */
    protected int calcLength(Source source, int index) {
        if (index >= source.length()) {
            return 0;
        } else {
            char c = source.charAt(index);
            if (c == '\n' || c == '\r') {
                if (index + 1 < source.length()) {
                    char nc = source.charAt(index + 1);
                    if (c == '\n' && nc == '\r' || c == '\r' && nc == '\n') {
                        return 2;
                    }
                }
                return 1;
            } else {
                return -1;
            }
        }
    }
}
