package ru.perm.kefir.bbcode;

import java.util.Collections;
import java.util.List;

/**
 * Chain of text processors wich process text serially
 *
 * @author Kefir
 */
public class TextProcessorChain extends TextProcessorAdapter {
    /**
     * List of processors
     */
    private final List<? extends TextProcessor> processors;

    public TextProcessorChain(List<? extends TextProcessor> processors) {
        this.processors = Collections.unmodifiableList(processors);
    }

    /**
     * Process the text
     *
     * @param source the sourcetext
     * @return the result of text processing
     * @see TextProcessor#process(CharSequence)
     */
    public CharSequence process(CharSequence source) {
        CharSequence target = source;
        for (TextProcessor processor : processors) {
            target = processor.process(target);
        }
        return target;
    }
}
