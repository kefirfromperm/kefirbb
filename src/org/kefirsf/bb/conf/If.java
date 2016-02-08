package org.kefirsf.bb.conf;

import java.util.List;

/**
 * The IF expression
 *
 * @author kefir
 */
public class If extends ElementListOwner<TemplateElement> implements TemplateElement {
    private String name;

    public If() {
    }

    public If(String name) {
        this.name = name;
    }

    public If(String name, List<? extends TemplateElement> elements) {
        super(elements);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
