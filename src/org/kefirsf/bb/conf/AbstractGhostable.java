package org.kefirsf.bb.conf;

/**
 * The abstract ghostable pattern element.
 * @author kefir
 */
public class AbstractGhostable implements PatternElement {
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
