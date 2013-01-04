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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Constant constant = (Constant) o;

        if (ignoreCase != constant.ignoreCase) return false;
        if (!value.equals(constant.value)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = value.hashCode();
        result = 31 * result + (ignoreCase ? 1 : 0);
        return result;
    }
}
