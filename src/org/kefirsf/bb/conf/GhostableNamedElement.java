package org.kefirsf.bb.conf;

/**
 * @author kefir
 */
class GhostableNamedElement extends NamedElement implements PatternElement {
    private boolean ghost = DEFAULT_GHOST_VALUE;

    public GhostableNamedElement() {
        super();
    }

    public GhostableNamedElement(String name) {
        super(name);
    }

    public GhostableNamedElement(String name, boolean ghost) {
        super(name);
        this.ghost = ghost;
    }

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
