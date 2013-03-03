package org.kefirsf.bb.conf;

/**
 * Pattern text element. The text will be parse by the processor.
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public class Text extends NamedElement implements PatternElement {
    private Scope scope;
    private boolean transparent;

    public Text() {
        super();
    }

    public Text(String name, Scope scope, boolean transparent) {
        super(name);
        this.scope = scope;
        this.transparent = transparent;
    }

    /**
     * Code scope for parse text.
     *
     * @return scope
     */
    public Scope getScope() {
        return scope;
    }

    /**
     * Set scope for parse text.
     *
     * @param scope scope
     */
    public void setScope(Scope scope) {
        this.scope = scope;
    }

    /**
     * @return true if values from parsing of the text will be propagate to current scope level.
     */
    public boolean isTransparent() {
        return transparent;
    }

    /**
     * @param transparent propagate or not values to current scope level
     */
    public void setTransparent(boolean transparent) {
        this.transparent = transparent;
    }
}
