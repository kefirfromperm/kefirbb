package org.kefirsf.bb.conf;

/**
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

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public boolean isTransparent() {
        return transparent;
    }

    public void setTransparent(boolean transparent) {
        this.transparent = transparent;
    }
}
