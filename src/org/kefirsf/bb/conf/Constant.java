package org.kefirsf.bb.conf;

import org.kefirsf.bb.comp.*;

import java.util.Map;

/**
 * @author Vitaliy Samolovskih aka Kefir
 */
public class Constant implements TemplateElement, PatternElement {
    private final String value;
    private boolean ignoreCase = false;

    public Constant(String value) {
        this.value = value;
    }

    public WConstant create() {
        return new WConstant(value);
    }

    public WPatternElement create(Configuration configuration, Map<Scope, WScope> scopes, Map<Code, AbstractCode> codes) {
        if (!ignoreCase) {
            return new WConstant(value);
        } else {
            return new WConstantIgnoreCase(value);
        }
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
