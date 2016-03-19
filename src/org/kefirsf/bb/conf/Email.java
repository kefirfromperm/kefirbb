package org.kefirsf.bb.conf;

/**
 * An email pattern element. Find email addresses in a text.
 *
 * @author kefir
 */
public class Email extends NamedElement implements PatternElement {
    public static final String DEFAULT_NAME = "email";

    /**
     * Don't move a source cursor.
     */
    private boolean ghost = false;

    /**
     * Construct an email pattern element.
     *
     * @param name  the name which can be used in template variables. By default is "email".
     * @param ghost indicate that the element mustn't move source cursor.
     */
    public Email(String name, boolean ghost) {
        super(name);
        this.ghost = ghost;
    }

    /**
     * If it's true then processor parses it but doesn't move the cursor.
     *
     * @return is it ghost or no
     */
    public boolean isGhost() {
        return ghost;
    }

    /**
     * @param ghost If it's true then processor parses it but doesn't move the cursor.
     */
    public void setGhost(boolean ghost) {
        this.ghost = ghost;
    }
}
