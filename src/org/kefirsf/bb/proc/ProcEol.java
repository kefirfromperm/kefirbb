package org.kefirsf.bb.proc;

/**
 * End of line
 *
 * @author Vitalii Samolovskikh aka Kefir
 */
public class ProcEol extends AbstractEol {
    private final int count;

    public ProcEol(int count) {
        this.count = count;
    }

    /**
     * @return real length of the tag or -1 if not found
     */
    @Override
    protected int match(Source source, int index) {
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
}
