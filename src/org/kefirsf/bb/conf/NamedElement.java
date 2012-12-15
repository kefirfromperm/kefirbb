package org.kefirsf.bb.conf;

/**
 * Named element definition
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
abstract class NamedElement {
    private final String name;

    public NamedElement(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
