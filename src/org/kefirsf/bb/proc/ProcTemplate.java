package org.kefirsf.bb.proc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Code template
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public class ProcTemplate {
    private final Logger log = LoggerFactory.getLogger(BBProcessor.LOGGER_GENERATE);

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

        if(log.isTraceEnabled()){
            log.trace("Generated text: {}", target);
        }
    }
}
