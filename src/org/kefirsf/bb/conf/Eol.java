package org.kefirsf.bb.conf;

/**
 * End of line
 *
 * @author Vitalii Samolovskikh aka Kefir
 */
public class Eol implements PatternElement {
    public static final int DEFAULT_COUNT = 1;

    private int count = DEFAULT_COUNT;

    public Eol() {
    }

    public Eol(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
