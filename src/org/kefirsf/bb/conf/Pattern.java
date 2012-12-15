package org.kefirsf.bb.conf;

import java.util.ArrayList;
import java.util.List;

/**
 * Pattern definition. For creating pattern for text parsing.
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public class Pattern {
    private List<? extends PatternElement> elements;

    /**
     * Create pattern definition with empty pattern elements list.
     */
    public Pattern() {
        setElements(new ArrayList<PatternElement>());
    }

    /**
     * Create pattern definition with pattern elements list.
     *
     * @param elements elements of pattern
     */
    public Pattern(List<? extends PatternElement> elements) {
        this.setElements(elements);
    }

    /**
     * Pattern elements
     */ /**
     * Get pattern elements
     *
     * @return elements list
     */
    public List<? extends PatternElement> getElements() {
        return elements;
    }

    /**
     * Set pattern elements
     *
     * @param elements elements list
     */
    public void setElements(List<? extends PatternElement> elements) {
        this.elements = elements;
    }
}
