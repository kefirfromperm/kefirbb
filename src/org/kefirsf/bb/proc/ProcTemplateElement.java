package org.kefirsf.bb.proc;

/**
 * The element of template to build target text
 */
public interface ProcTemplateElement {
    /**
     * Append template element to source of context
     *
     * @param context контекст
     * @return builded text
     */
    public CharSequence generate(Context context);
}
