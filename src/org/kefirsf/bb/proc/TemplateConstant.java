package org.kefirsf.bb.proc;

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
