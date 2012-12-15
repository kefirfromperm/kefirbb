package org.kefirsf.bb.conf;

/**
 * @author Vitaliy Samolovskih aka Kefir
 */
public class Constant implements TemplateElement, PatternElement {
    private final String value;
    private boolean ignoreCase = false;

    public Constant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }
}
