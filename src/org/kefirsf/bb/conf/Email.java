package org.kefirsf.bb.conf;

/**
 * An email pattern element. Find email addresses in a text.
 *
 * @author kefir
 */
public class Email extends GhostableNamedElement {
    public static final String DEFAULT_NAME = "email";

    /**
     * The default constructor. It's needed to initialize it after construction.
     */
    public Email() {
        super(DEFAULT_NAME);
    }

    /**
     * Construct an email pattern element. Not ghost.
     *
     * @param name  the name which can be used in template variables. By default is "email".
     */
    public Email(String name) {
        super(name);
    }

    /**
     * Construct an email pattern element.
     *
     * @param name  the name which can be used in template variables. By default is "email".
     * @param ghost indicate that the element mustn't move source cursor.
     */
    public Email(String name, boolean ghost) {
        super(name, ghost);
    }
}
