package org.kefirsf.bb.conf;

import org.kefirsf.bb.AbstractCode;
import org.kefirsf.bb.WPatternElement;
import org.kefirsf.bb.WScope;

import java.util.Map;

/**
 * @author Vitaliy Samolovskih aka Kefir
 */
public interface PatternElement {
    WPatternElement create(Configuration configuration, Map<Scope, WScope> createdScopes, Map<Code, AbstractCode> codes);
}
