package org.kefirsf.bb.test.blackbox;

import org.junit.Before;
import org.junit.Test;
import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.TextProcessor;
import org.kefirsf.bb.test.Assert;

/**
 * @author Vitalii Samolovskikh aka Kefir
 */
public class SafeHtmlConfigurationTest {
    /**
     * Default configuration processor (BB2HTML processor)
     */
    private TextProcessor processor;


    private void assertProcess(String expected, String source) {
        Assert.assertProcess(processor, expected, source);
    }

    /**
     * Create defult text processor
     */
    @Before
    public void createProcessor() {
        processor = BBProcessorFactory.getInstance().createFromResource("org/kefirsf/bb/safehtml.xml");
    }

    @Test
    public void testSimple(){
        // Safe XML
        assertProcess("&lt;", "<");
        assertProcess("&gt;", ">");
        assertProcess("&apos;", "\'");
        assertProcess("&quot;", "\"");

        // Escape HTML
        assertProcess("&lt;", "\\<");
        assertProcess("&gt;", "\\>");
        assertProcess("&amp;", "\\&");
        assertProcess("\\", "\\\\");
        assertProcess("", "<!-- Comment -->");

        // Line breaks
        assertProcess("<br/>", "\r\n");
        assertProcess("<br/>", "\n\r");
        assertProcess("<br/>", "\r");
        assertProcess("<br/>", "\n");

        assertProcess("<br/><br/>", "\r\n\r\n");
        assertProcess("<br/><br/>", "\n\r\n\r");

        // Headers
        assertProcess("<h1>Test</h1>", "<H1 onclick=\"...\">Test</h1>");
        assertProcess("<h2>Test</h2>", "<h2 onclick=\"...\">Test</H2>");
        assertProcess("<h3>Test</h3>", "<H3 onclick=\"...\">Test</H3>");
        assertProcess("<h4>Test</h4>", "<H4 onclick=\"...\">Test</h4>");
        assertProcess("<h5>Test</h5>", "<H5 onclick=\"...\">Test</h5>");
        assertProcess("<h6>Test</h6>", "<H6 onclick=\"...\">Test</h6>");

        // Simple HTML
        assertProcess("<b>test</b>", "<b onclick=\"javascript:alert('Fail!');\">test</B>");
        assertProcess("<u>test</u>", "<u onclick=\"...\">test</u>");
        assertProcess("<s>test</s>", "<s onclick=\"...\">test</s>");
        assertProcess("<i>test</i>", "<i onclick=\"...\">test</i>");

        // Quotes
        assertProcess("<pre>test</pre>", "<PrE onclick=\"...\">test</pRe>");
        assertProcess("<code>test</code>", "<cOde onclick=\"...\">test</cOde>");
        assertProcess("<blockquote>test</blockquote>", "<blockquote onclick=\"...\">test</blockquote>");

        // Image and links
        assertProcess(
                "<img src=\"http://www.example.com/logo.png\"/>",
                "<IMG onclick=\"...\" src=\"http://www.example.com/logo.png\" style=\"...\"/>"
        );
        assertProcess(
                "<a href=\"http://www.example.com/\">Test</a>",
                "<A href=\"http://www.example.com/\" onclick=\"...\">Test</A>"
        );
        assertProcess(
                "<a href=\"mailto:john.smith@example.com\">John Smith</a>",
                "<A href=\"mailto:john.smith@example.com\" onclick=\"...\">John Smith</A>"
        );
    }
}
