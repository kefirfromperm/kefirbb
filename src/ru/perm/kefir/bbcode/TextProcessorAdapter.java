package ru.perm.kefir.bbcode;

/**
 * Text Processor adapter implement methods for String, StringBuffer, StringBuilder
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public abstract class TextProcessorAdapter implements TextProcessor {
    /**
     * Process the text
     *
     * @param source the sourcetext
     * @return the result of text processing
     */
    public String process(String source) {
        CharSequence result = process((CharSequence) source);
        if (result instanceof String) {
            return (String) result;
        } else {
            return result.toString();
        }
    }

    /**
     * Process the text
     *
     * @param source the sourcetext
     * @return the result of text processing
     */
    public StringBuilder process(StringBuilder source) {
        CharSequence result = process((CharSequence) source);
        if (result instanceof StringBuilder) {
            return (StringBuilder) result;
        } else {
            return new StringBuilder(result);
        }
    }

    /**
     * Process the text
     *
     * @param source the sourcetext
     * @return the result of text processing
     */
    public StringBuffer process(StringBuffer source) {
        CharSequence result = process((CharSequence) source);
        if (result instanceof StringBuffer) {
            return (StringBuffer) result;
        } else {
            return new StringBuffer(result);
        }
    }
}
