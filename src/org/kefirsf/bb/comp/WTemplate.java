package org.kefirsf.bb.comp;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Code template
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public class WTemplate {
    /**
     * Empty template
     */
    @SuppressWarnings({"unchecked"})
    public static final WTemplate EMPTY = new WTemplate(Collections.EMPTY_LIST);

    /**
     * Template elemnts
     */
    private final List<? extends WTemplateElement> elements;

    /**
     * Create neq template with elements.
     *
     * @param elements template elements.
     */
    public WTemplate(List<? extends WTemplateElement> elements) {
        this.elements = Collections.unmodifiableList(elements);
    }

    /**
     * Append to result string processed text.
     *
     * @param context current context.
     * @throws IOException if can't append.
     */
    public void generate(Context context) throws IOException {
        for (WTemplateElement element : elements) {
            context.getTarget().append(element.generate(context));
        }
    }
}
