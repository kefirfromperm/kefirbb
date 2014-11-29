package org.kefirsf.bb.proc;

/**
 * Start of text
 * @author kefir
 */
public class ProcSot implements ProcPatternElement {
    public boolean parse(Context context, ProcPatternElement terminator) throws NestingException {
        return isNextIn(context.getSource());
    }

    public boolean isNextIn(Source source) {
        return source.getOffset()==0;
    }

    /**
     * This method doesn't make sense. Tag sot can't be a terminator.
     */
    public int findIn(Source source) {
        if(source.getOffset() == 0) {
            return 0;
        } else {
            return -1;
        }
    }
}
