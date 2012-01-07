package org.kefirsf.bb.conf;

import org.kefirsf.bb.comp.AbstractCode;
import org.kefirsf.bb.comp.WPatternElement;
import org.kefirsf.bb.comp.WScope;

import java.util.Map;

/**
 * @author Vitaliy Samolovskih aka Kefir
 */
public interface PatternElement {
    WPatternElement create(Configuration configuration, Map<Scope, WScope> createdScopes, Map<Code, AbstractCode> codes);
}
