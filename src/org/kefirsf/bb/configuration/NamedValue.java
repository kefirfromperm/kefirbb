package org.kefirsf.bb.configuration;

import org.kefirsf.bb.WNamedValue;
import org.kefirsf.bb.WTemplateElement;

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
