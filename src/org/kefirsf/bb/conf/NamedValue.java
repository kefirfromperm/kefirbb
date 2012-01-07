package org.kefirsf.bb.conf;

import org.kefirsf.bb.comp.WNamedValue;
import org.kefirsf.bb.comp.WTemplateElement;

/**
 * @author Vitaliy Samolovskih aka Kefir
 */
public class NamedValue extends NamedElement implements TemplateElement {

    public NamedValue(String name) {
        super(name);
    }

    public WTemplateElement create() {
        return new WNamedValue(name);
    }
}
