package org.kefirsf.bb.proc;

import java.util.Collections;
import java.util.List;

/**
 * Abstract class for Template and If
 * @author kefir
 */
public abstract class AbstractTemplate {
    /**
     * Template elements
     */
    protected final List<? extends ProcTemplateElement> elements;

    public AbstractTemplate(List<? extends ProcTemplateElement> elements) {
        this.elements = Collections.unmodifiableList(elements);
    }

}
