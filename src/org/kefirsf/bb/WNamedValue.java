package org.kefirsf.bb;

/**
 * Named value to build target text
 */
public class WNamedValue extends WNamedElement implements WTemplateElement {
    public WNamedValue(String name) {
        super(name);
    }

    /**
     * Добавляет элемент в новую строку
     *
     * @param context контекст
     */
    public CharSequence generate(Context context) {
        Object attribute = context.getAttribute(getName());
        if (attribute == null) {
            return "null";
        } else if (attribute instanceof CharSequence) {
            return (CharSequence) attribute;
        } else {
            return attribute.toString();
        }
    }
}
