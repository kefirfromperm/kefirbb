package org.kefirsf.bb.proc;

/**
 * @author Vitalii Samolovskikh aka Kefir
 */
public class ProcEot implements ProcPatternElement {
    public boolean parse(Context context, ProcPatternElement terminator) throws NestingException {
        return isNextIn(context.getSource());
    }

    public boolean isNextIn(Source source) {
        return !source.hasNext();
    }

    public int findIn(Source source) {
        return source.getLength();
    }
}
