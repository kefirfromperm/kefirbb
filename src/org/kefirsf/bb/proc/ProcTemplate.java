package org.kefirsf.bb.proc;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Code template
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public class ProcTemplate {
    /**
     * Empty template
     */
    public static final ProcTemplate EMPTY = new ProcTemplate(Collections.<ProcTemplateElement>emptyList());

    /**
     * Template elemnts
     */
    private final List<? extends ProcTemplateElement> elements;

    /**
     * Create neq template with elements.
     *
     * @param elements template elements.
     */
    public ProcTemplate(List<? extends ProcTemplateElement> elements) {
        this.elements = Collections.unmodifiableList(elements);
    }

    /**
     * Append to result string processed text.
     *
     * @param context current context.
     * @throws IOException if can't append.
     */
    public void generate(Context context) throws IOException {
        for (ProcTemplateElement element : elements) {
            context.getTarget().append(element.generate(context));
        }
    }
}
