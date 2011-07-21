package ru.perm.kefir.bbcode.configuration;

import ru.perm.kefir.bbcode.AbstractCode;
import ru.perm.kefir.bbcode.WPatternElement;
import ru.perm.kefir.bbcode.WScope;

import java.util.Map;

/**
 * @author Vitaliy Samolovskih aka Kefir
 */
public interface PatternElement {
    WPatternElement create(Configuration configuration, Map<Scope, WScope> createdScopes, Map<Code, AbstractCode> codes);
}
