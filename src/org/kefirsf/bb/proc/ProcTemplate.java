package org.kefirsf.bb.proc;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Code template
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public class ProcTemplate extends AbstractTemplate {
    /**
     * Empty template
     */
    public static final ProcTemplate EMPTY = new ProcTemplate(Collections.<ProcTemplateElement>emptyList());

    /**
     * Create neq template with elements.
     *
     * @param elements template elements.
     */
    public ProcTemplate(List<? extends ProcTemplateElement> elements) {
        super(elements);
    }

    /**
     * Append to result string processed text.
     *
     * @param context current context.
     */
    public void generate(Context context) {
        Appendable target = context.getTarget();

        for (ProcTemplateElement element : elements) {
            try {
                target.append(element.generate(context));
            } catch (IOException e) {
                // Nothing! Because StringBuilder doesn't catch IOException
            }
        }
    }
}
