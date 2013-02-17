package org.kefirsf.bb.proc;

/**
 * @author Vitalii Samolovskikh aka Kefir
 */
public class PatternJunk implements ProcPatternElement {
    public boolean parse(Context context, ProcPatternElement terminator) {
        Source source = context.getSource();
        int offset = source.getOffset();

        int end;
        if (terminator != null) {
            end = terminator.findIn(context.getSource());
        } else {
            end = context.getSource().getLength();
        }

        if (end >= 0) {
            source.incOffset(end - offset);
            return true;
        } else {
            return false;
        }
    }

    public boolean isNextIn(Source source) {
        return false;
    }

    public int findIn(Source source) {
        return -1;
    }
}
