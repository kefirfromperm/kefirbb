package ru.perm.kefir.bbcode.configuration;

import ru.perm.kefir.bbcode.Util;

/**
 * Named element definition
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public class NamedElement {
    protected String name;

    /**
     * Create named element with random name
     */
    public NamedElement() {
        name = Util.generateRandomName();
    }

    public NamedElement(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
