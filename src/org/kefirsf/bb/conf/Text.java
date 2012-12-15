package org.kefirsf.bb.conf;

/**
 * @author Vitaliy Samolovskih aka Kefir
 */
public final class Text extends NamedElement implements PatternElement {
    private final String scope;
    private final boolean transparent;

    public Text(String name, String scope, boolean transparent) {
        super(name);
        this.scope = scope;
        this.transparent = transparent;
    }

    public String getScope() {
        return scope;
    }

    public boolean isTransparent() {
        return transparent;
    }
}
