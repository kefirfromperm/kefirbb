package org.kefirsf.bb.proc;

/**
 * End of line
 *
 * @author Vitalii Samolovskikh aka Kefir
 */
public class ProcEol implements ProcPatternElement {
    private final int count;

    public ProcEol(int count) {
        this.count = count;
    }

    public boolean parse(Context context, ProcPatternElement terminator) throws NestingException {
        Source source = context.getSource();
        int len = match(source, source.getOffset());
        if (len >= 0) {
            source.incOffset(len);
            return true;
        } else {
            return false;
        }
    }

    public boolean isNextIn(Source source) {
        return match(source, source.getOffset()) >= 0;
    }

    public int findIn(Source source) {
        int index = source.getOffset()-1;
        do {
            index++;
            int n = source.findFrom(index, new char[]{'\n'}, false);
            int r = source.findFrom(index, new char[]{'\r'}, false);
            if (n >= 0 && r >= 0) {
                index = Math.min(n, r);
            } else if (n >= 0) {
                index = n;
            } else if (r >= 0) {
                index = r;
            } else {
                index = source.getLength();
            }
        } while (match(source, index) < 0);

        return index;
    }

    /**
     * @return real length of the tag or -1 if not found
     */
    private int match(Source source, int index) {
        int ind = index;
        for (int i = 0; i < count; i++) {
            int len = calcLength(source, ind);
            if (len < 0) {
                return -1;
            }
            ind += len;
        }

        return ind - index;
    }

    /**
     * @return -1 if at the index there is not end of line
     * 0 if it's end of text
     * 1 if it's single \n or \r
     * 2 if it's pair \n\r or \r\n
     */
    private int calcLength(Source source, int index) {
        if (index >= source.getLength()) {
            return 0;
        } else {
            char c = source.get(index);
            if (c == '\n' || c == '\r') {
                if (index + 1 < source.getLength()) {
                    char nc = source.get(index + 1);
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
