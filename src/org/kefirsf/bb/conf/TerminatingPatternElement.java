package org.kefirsf.bb.conf;

/**
 * Interface of the classes for element which can be terminator.
 *
 * @author kefir
 */
public interface TerminatingPatternElement {
    /**
     * If it's true then processor parse it but no move the cursor.
     *
     * @return is it ghost or no
     */
    boolean isGhost();

    /**
     * @param ghost If it's true then processor parse it but no move the cursor.
     */
    void setGhost(boolean ghost);
}
