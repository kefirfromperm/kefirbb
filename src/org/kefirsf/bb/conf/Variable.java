package org.kefirsf.bb.conf;

/**
 * @author Vitaliy Samolovskih aka Kefir
 */
public class Variable extends NamedElement implements PatternElement {
    private final java.util.regex.Pattern regex;

    public Variable(String name) {
        super(name);
        this.regex = null;
    }

    public Variable(String name, java.util.regex.Pattern regex) {
        super(name);
        this.regex = regex;
    }

    public java.util.regex.Pattern getRegex() {
        return regex;
    }
}
