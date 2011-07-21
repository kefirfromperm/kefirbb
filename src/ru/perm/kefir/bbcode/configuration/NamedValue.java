package ru.perm.kefir.bbcode.configuration;

import ru.perm.kefir.bbcode.WNamedValue;
import ru.perm.kefir.bbcode.WTemplateElement;

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
