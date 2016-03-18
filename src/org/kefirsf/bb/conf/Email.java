package org.kefirsf.bb.conf;

/**
 * @author kefir
 */
public class Email extends NamedElement implements PatternElement {
    public static final String DEFAULT_NAME = "email";

    private boolean ghost = false;

    public Email(String name, boolean ghost) {
        super(name);
        this.ghost = ghost;
    }

    public boolean isGhost() {
        return ghost;
    }

    public void setGhost(boolean ghost) {
        this.ghost = ghost;
    }
}
