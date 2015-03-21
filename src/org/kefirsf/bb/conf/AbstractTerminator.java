package org.kefirsf.bb.conf;

/**
 * @author kefir
 */
public class AbstractTerminator implements PatternElement, TerminatingPatternElement {
    protected boolean ghost = false;

    /**
     * If it's true then processor parse it but no move the cursor.
     *
     * @return is it ghost or no
     */
    public boolean isGhost() {
        return ghost;
    }

    /**
     * @param ghost If it's true then processor parse it but no move the cursor.
     */
    public void setGhost(boolean ghost) {
        this.ghost = ghost;
    }
}
