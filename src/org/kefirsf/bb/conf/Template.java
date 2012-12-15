package org.kefirsf.bb.conf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Template definition
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public class Template {
    /**
     * Empty template
     */
    @SuppressWarnings({"unchecked"})
    public static final Template EMPTY = new Template(Collections.EMPTY_LIST);

    private List<? extends TemplateElement> elements;

    /**
     * Create template with empty content
     */
    public Template() {
        this.setElements(new ArrayList<TemplateElement>());
    }

    /**
     * Create template with elements
     *
     * @param elements template definition elements
     */
    public Template(List<? extends TemplateElement> elements) {
        this.setElements(elements);
    }

    /**
     * Get template elements
     *
     * @return list of template elements
     */
    public List<? extends TemplateElement> getElements() {
        return elements;
    }

    public void setElements(List<? extends TemplateElement> elements) {
        this.elements = elements;
    }
}
