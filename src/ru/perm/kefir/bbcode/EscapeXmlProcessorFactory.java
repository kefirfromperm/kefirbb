package ru.perm.kefir.bbcode;

/**
 * The class for creating the escape xml special symbols processor. It's processor change:
 * <p/>
 * &amp; to &amp;amp;
 * &apos; to &amp;apos;
 * &lt; to &amp;lt;
 * &gt; to &amp;gt;
 * &quot; to &amp;quot;
 *
 * @author Kefir
 */
public final class EscapeXmlProcessorFactory implements TextProcessorFactory {
    /**
     * The default XML escape symbols
     */
    private static final String[][] DEFAULT_ESCAPE_XML = {
            {"&", "&amp;"},
            {"'", "&apos;"},
            {">", "&gt;"},
            {"<", "&lt;"},
            {"\"", "&quot;"}
    };

    /**
     * Instance of processor.
     */
    private static final TextProcessor processor = new EscapeProcessor(DEFAULT_ESCAPE_XML);

    /**
     * Instance of factory
     */
    private static final TextProcessorFactory instance = new EscapeXmlProcessorFactory();

    /**
     * Private constructor. Because this class is singleton.
     */
    private EscapeXmlProcessorFactory() {
    }

    /**
     * Return instance of this class.
     *
     * @return instance of escape xml processor factory
     */
    public static TextProcessorFactory getInstance() {
        return instance;
    }

    /**
     * Create the new XML escape symbols processor.
     *
     * @see ru.perm.kefir.bbcode.TextProcessorFactory#create()
     */
    public TextProcessor create() {
        return processor;
    }
}
