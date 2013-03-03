package org.kefirsf.bb;

/**
 * Base class for text processor exceptions.
 *
 * @author Vitalii Samolovskikh aka Kefir
 */
public abstract class TextProcessorException extends RuntimeException {
    protected TextProcessorException() {
    }

    protected TextProcessorException(String message) {
        super(message);
    }

    protected TextProcessorException(String message, Throwable cause) {
        super(message, cause);
    }

    protected TextProcessorException(Throwable cause) {
        super(cause);
    }
}
