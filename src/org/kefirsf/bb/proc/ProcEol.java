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
        return calcLength(source, index);
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("<eol");
        if(ghost) {
            b.append(" ghost=\"");
            b.append(ghost);
            b.append("\"");
        }
        b.append("/>");

        return b.toString();
    }
}
