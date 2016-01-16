package org.kefirsf.bb;

/**
 * The interface of text processors
 *
 * @author Kefir
 */
public interface TextProcessor {
    /**
     * Process the text.
     *
     * ATTENTION!!! Do not use java.nio.CharBuffer.
     * CharBuffer has invalid realization of subSequence methods from interface java.lang.CharSequence
     * since 1.6.0_10 version of JRE. http://bugs.sun.com/view_bug.do?bug_id=6795561
     *
     * @param source the source text
     * @return the result of text processing
     */
    CharSequence process(CharSequence source);

    /**
     * Process the text
     *
     * @param source the source text
     * @return the result of text processing
     */
    String process(String source);

    /**
     * Process the text
     *
     * @param source the source text
     * @return the result of text processing
     */
    StringBuilder process(StringBuilder source);

    /**
     * Process the text
     *
     * @param source the source text
     * @return the result of text processing
     */
    StringBuffer process(StringBuffer source);
}
