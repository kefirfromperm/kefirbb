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
        int offset = source.getOffset();
        if (offset == 0) {
            char c = source.charAt(offset);
            if(c!='\n' && c!='\r') {
                return true;
            }
        } else {
            char p = source.charAt(offset - 1);
            char c = source.charAt(offset);
            if ((p == '\n' || p == '\r') && c!='\n' && c!='\r') {
                return true;
            }
        }
        return false;
    }

    /**
     * I strongly don't recommend to use tag sol as a terminator.
     */
    public int findIn(Source source) {
        // TODO Add an implementation.
        throw new UnsupportedOperationException("The operation findIn is unsupported for beginning of line.");
    }
}
