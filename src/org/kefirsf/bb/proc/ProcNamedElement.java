package org.kefirsf.bb.proc;

/**
 * Named element are variable and text ProcPatternElement or ProcTemplateElement
 */
public abstract class ProcNamedElement {
    /**
     * Variable name
     */
    private final String name;

    /**
     * Create named element
     *
     * @param name name of element
     */
    protected ProcNamedElement(String name) {
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
