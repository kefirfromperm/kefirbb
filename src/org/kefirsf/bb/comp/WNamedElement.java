package org.kefirsf.bb.comp;

/**
 * Named element are variable and text WPatternElement or WTemplateElement
 */
public abstract class WNamedElement {
    /**
     * Variable name
     */
    private final String name;

    /**
     * Create named element
     *
     * @param name name of element
     */
    protected WNamedElement(String name) {
        this.name = name;
    }

    /**
     * Get element name
     *
     * @return element name
     */
    public String getName() {
        return name;
    }

    /**
     * Add attribute with name of this element name and value <code>value</code> to <code>context</code>.
     *
     * @param context context
     * @param value   variable value
     */
    protected void setAttribute(Context context, CharSequence value) {
        context.setAttribute(name, value);
    }
}
