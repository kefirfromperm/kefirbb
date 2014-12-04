package org.kefirsf.bb.proc;

/**
 * A blank line
 *
 * @author kefir
 */
public class ProcBlankLine extends AbstractEol {
    /**
     * White space characters.
     */
    private static final char[] WS = {
            '\u0020', '\u00A0', '\u180E', '\u2000', '\u2001', '\u2002', '\u2003', '\u2004', '\u2005',
            '\u2006', '\u2007', '\u2008', '\u2009', '\u200A', '\u200B', '\u202F', '\u205F', '\u3000',
            '\u0009', '\uFEFF'
    };

    /**
     * @param source text source
     * @param index  index in the text
     * @return length of the blank line or -1
     */
    protected int match(Source source, int index) {
        int ind = index;

        // First line break
        int len = calcLength(source, ind);
        if (len < 0) {
            return -1;
        }
        ind += len;

        // Whitespaces
        while (ind < source.length() && whitespace(source.charAt(ind))) {
            ind++;
        }

        // Second line break can be 0
        len = calcLength(source, ind);
        if (len < 0) {
            return -1;
        }
        ind += len;

        return ind - index;
    }

    private boolean whitespace(char c) {
        for (char p : WS) {
            if(c == p){
                return true;
            }
        }
        return false;
    }
}
