package org.kefirsf.bb.proc;

/**
 * The element of template to build target text
 */
public interface ProcTemplateElement {
    /**
     * Append template element to source of context
     *
     * @param context контекст
     * @return built text
     */
    CharSequence generate(Context context);
}
