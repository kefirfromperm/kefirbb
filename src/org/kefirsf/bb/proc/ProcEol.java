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

        int offset = source.getOffset();
        boolean flag = true;
        for (int i = 0; i < count && flag; i++) {
            flag = parseOne(source);
        }
        if (!flag) {
            source.setOffset(offset);
        }
        return flag;
    }

    private boolean parseOne(Source source) {
        if (!source.hasNext()) {
            return true;
        }
        char c = source.current();
        if (c == '\n') {
            source.incOffset();
            if (source.hasNext() && source.current() == '\r') {
                source.incOffset();
            }
            return true;
        } else if (c == '\r') {
            source.incOffset();
            if (source.hasNext() && source.current() == '\n') {
                source.incOffset();
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean isNextIn(Source source) {
        int offset = source.getOffset();
        boolean flag = true;
        for (int i = 0; i < count && flag; i++) {
            flag = parseOne(source);
        }
        source.setOffset(offset);
        return flag;
    }

    public int findIn(Source source) {
        int offset = source.getOffset();

        int index;
        do {
            int n = source.find(new char[]{'\n'}, false);
            int r = source.find(new char[]{'\r'}, false);
            if (n >= 0 && r >= 0) {
                index = Math.min(n, r);
            } else if (n >= 0) {
                index = n;
            } else if (r >= 0) {
                index = r;
            } else {
                index = source.getLength();
            }
            source.setOffset(index);
        } while (!isNextIn(source));

        source.setOffset(offset);

        return index;
    }
}
