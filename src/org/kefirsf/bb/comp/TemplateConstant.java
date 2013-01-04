package org.kefirsf.bb.comp;

/**
 * @author kefir
 */
public class TemplateConstant implements WTemplateElement {
    private final String value;

    public TemplateConstant(String value) {
        this.value = value;
    }

    public CharSequence generate(Context context) {
        return value;
    }
}
