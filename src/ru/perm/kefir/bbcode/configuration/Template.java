package ru.perm.kefir.bbcode.configuration;

import ru.perm.kefir.bbcode.WTemplate;
import ru.perm.kefir.bbcode.WTemplateElement;

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
        this.elements = new ArrayList<TemplateElement>();
    }

    /**
     * Create template with elements
     *
     * @param elements template definition elements
     */
    public Template(List<? extends TemplateElement> elements) {
        this.elements = elements;
    }

    /**
     * Get template elements
     *
     * @return list of template elements
     */
    public List<? extends TemplateElement> getElements() {
        return elements;
    }

    /**
     * Create template from definition
     *
     * @return template
     */
    WTemplate create() {
        List<WTemplateElement> elements = new ArrayList<WTemplateElement>();
        if (this.elements != null) {
            for (TemplateElement element : this.elements) {
                elements.add(element.create());
            }
        }
        return new WTemplate(elements);
    }
}
