package ru.perm.kefir.bbcode;

/**
 * Exception if TextProcessorFactory can't create the TextProcessor instance
 *
 * @author Kefir
 */
public class TextProcessorFactoryException extends RuntimeException {
    public TextProcessorFactoryException() {
        super();
    }

    public TextProcessorFactoryException(String message) {
        super(message);
    }

    public TextProcessorFactoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public TextProcessorFactoryException(Throwable cause) {
        super(cause);
    }
}
