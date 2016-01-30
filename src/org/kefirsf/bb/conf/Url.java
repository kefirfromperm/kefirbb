package org.kefirsf.bb.conf;

/**
 * The URL pattern element.
 *
 * @author kefir
 */
public class Url extends NamedElement implements PatternElement {
    private boolean ghost = false;

    /**
     * Create an URL pattern element.
     *
     * @param name variable name
     * @param ghost is it ghost?
     */
    public Url(String name, boolean ghost) {
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
