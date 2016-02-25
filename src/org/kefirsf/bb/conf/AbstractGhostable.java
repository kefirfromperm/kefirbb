package org.kefirsf.bb.conf;

/**
 * The abstract ghostable pattern element. For element which has a ghost property.
 *
 * @author kefir
 */
public class AbstractGhostable implements PatternElement {
    public static final boolean DEFAULT_GHOST_VALUE = false;
    protected boolean ghost = DEFAULT_GHOST_VALUE;

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
