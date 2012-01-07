package org.kefirsf.bb.configuration;

import org.kefirsf.bb.AbstractCode;
import org.kefirsf.bb.WScope;
import org.kefirsf.bb.WVariable;

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
