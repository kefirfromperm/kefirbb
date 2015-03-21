package org.kefirsf.bb.proc;

/**
 * End of line
 *
 * @author Vitalii Samolovskikh aka Kefir
 */
public class ProcEol extends AbstractEol {
    public ProcEol(boolean ghost) {
        super(ghost);
    }

    /**
     * @return real length of the tag or -1 if not found
     */
    @Override
    protected int match(Source source, int index) {
        int len = calcLength(source, index);
        if (len < 0) {
            return -1;
        } else {
            return len;
        }
    }
}
