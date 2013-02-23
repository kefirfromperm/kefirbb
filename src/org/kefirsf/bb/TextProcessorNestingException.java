package org.kefirsf.bb;

import java.text.MessageFormat;

/**
 * Throws if nesting is too large.
 *
 * @author Vitalii Samolovskikh aka Kefir
 */
public class TextProcessorNestingException extends TextProcessorException {
    public static final String MESSAGE_PATTERN = "Nesting is too big. Nesting limit is {0}.";

    public TextProcessorNestingException(int limit) {
        super(MessageFormat.format(MESSAGE_PATTERN, limit));
    }
}
