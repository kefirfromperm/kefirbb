package org.kefirsf.bb.conf;

import org.kefirsf.bb.comp.AbstractCode;
import org.kefirsf.bb.comp.WScope;
import org.kefirsf.bb.comp.WVariable;

import java.util.Map;

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

    public WVariable create(Configuration configuration, Map<Scope, WScope> scopes, Map<Code, AbstractCode> codes) {
        return new WVariable(name, regex);
    }
}
