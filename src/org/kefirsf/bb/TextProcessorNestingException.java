package org.kefirsf.bb;

import java.text.MessageFormat;

/**
 * Throws if nesting is too large.
 *
 * @author Vitalii Samolovskikh aka Kefir
 */
public class TextProcessorNestingException extends TextProcessorException {
    public static final String MESSAGE_PATTERN = "Nesting is too big. Nesting is {0} but limit is {1}.";

    public TextProcessorNestingException(int nesting, int maxNesting){
        super(
                MessageFormat.format(
                        MESSAGE_PATTERN,
                        nesting, maxNesting
                )
        );
    }
}
