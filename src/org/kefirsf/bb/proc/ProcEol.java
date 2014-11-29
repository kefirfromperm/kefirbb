package org.kefirsf.bb.proc;

/**
 * End of line
 *
 * @author Vitalii Samolovskikh aka Kefir
 */
public class ProcEol implements ProcPatternElement {
    public boolean parse(Context context, ProcPatternElement terminator) throws NestingException {
        Source source = context.getSource();

        if(!source.hasNext()){
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
        if(source.hasNext()){
            char c = source.current();
            return c == '\n' || c == '\r';
        } else {
            return true;
        }
    }

    public int findIn(Source source) {
        int n = source.find(new char[]{'\n'}, false);
        int r = source.find(new char[]{'\r'}, false);
        if (n >= 0 && r >= 0) {
            return Math.min(n, r);
        } else if (n >= 0) {
            return n;
        } else if (r >= 0) {
            return r;
        } else {
            return source.getLength();
        }
    }
}
