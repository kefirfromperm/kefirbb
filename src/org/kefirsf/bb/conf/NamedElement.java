package org.kefirsf.bb.conf;

/**
 * Named element definition
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
abstract class NamedElement {
    private String name;

    protected NamedElement() {
    }

    protected NamedElement(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
