package org.kefirsf.bb.conf;

import java.util.List;

/**
 * Pattern definition. For creating pattern for text parsing.
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public class Pattern extends ElementListOwner<PatternElement> {
    /**
     * Create pattern definition with empty pattern elements list.
     */
    public Pattern() {
        super();
    }

    /**
     * Create pattern definition with pattern elements list.
     *
     * @param elements elements of pattern
     */
    public Pattern(List<? extends PatternElement> elements) {
        super(elements);
    }
}
