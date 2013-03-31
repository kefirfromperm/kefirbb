package org.kefirsf.bb.conf;

/**
 * Define constants.
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public class Constant implements TemplateElement, PatternElement {
    /**
     * Constant value. String for search.
     */
    private String value;

    /**
     * If true, then uses search ignore case
     */
    private boolean ignoreCase;

    /**
     * Default constructor.
     */
    public Constant() {
    }

    /**
     * Create constant with value. Ignore case is false by default.
     *
     * @param value constant value.
     */
    public Constant(String value) {
        this.value = value;
        ignoreCase = false;
    }

    /**
     * Create constant.
     *
     * @param value      constant value
     * @param ignoreCase ignore case or no
     */
    public Constant(String value, boolean ignoreCase) {
        this.value = value;
        this.ignoreCase = ignoreCase;
    }

    /**
     * @return constant value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value constant value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Ignore case or no. If is true then processor will be ignore text case.
     *
     * @return true if ignore case, false otherwise.
     */
    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    /**
     * Set ignore case behavior.
     *
     * @param ignoreCase true if ignore case, false otherwise.
     */
    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Constant constant = (Constant) o;

        if (ignoreCase != constant.ignoreCase) return false;
        //noinspection RedundantIfStatement
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
