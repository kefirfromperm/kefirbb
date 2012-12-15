package org.kefirsf.bb.conf;

/**
 * Define constants.
 *
 * This class if immutable so we don't need synchronize it.
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public final class Constant implements TemplateElement, PatternElement {
    private final String value;
    private final boolean ignoreCase;

    public Constant(String value) {
        this.value = value;
        ignoreCase = false;
    }

    public Constant(String value, boolean ignoreCase) {
        this.value = value;
        this.ignoreCase = ignoreCase;
    }

    public String getValue() {
        return value;
    }

    public boolean isIgnoreCase() {
        return ignoreCase;
    }
}
