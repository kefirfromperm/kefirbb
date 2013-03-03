package org.kefirsf.bb.conf;

import java.util.List;

/**
 * Template definition
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public class Template extends ElementListOwner<TemplateElement> {
    /**
     * Create template with empty content
     */
    public Template() {
        super();
    }

    /**
     * Create template with elements
     *
     * @param elements template definition elements
     */
    public Template(List<? extends TemplateElement> elements) {
        super(elements);
    }
}
