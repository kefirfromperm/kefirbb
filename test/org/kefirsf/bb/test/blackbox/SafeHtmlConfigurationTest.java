package org.kefirsf.bb.test.blackbox;

import org.junit.Before;
import org.junit.Test;
import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.ConfigurationFactory;
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
        processor = BBProcessorFactory.getInstance().createFromResource(
                ConfigurationFactory.SAFE_HTML_CONFIGURATION_FILE
        );
    }

    @Test
    public void testSimple(){
        // Safe XML
        assertProcess("&lt;", "<");
        assertProcess("&gt;", ">");
        assertProcess("&apos;", "\'");
        assertProcess("&quot;", "\"");

        // Escape HTML
        assertProcess("&lt;", "&lt;");
        assertProcess("&gt;", "&gt;");
        assertProcess("&amp;", "&amp;");
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

        // Paragraph
        assertProcess("<p>test</p>", "<P onclick=\"...\">test</P>");
        assertProcess("test1<hr/>test2", "test1<hr/>test2");
        assertProcess("<div>test</div>", "<div>test</div>");
        assertProcess("<span>test</span>", "<span>test</span>");

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

    @Test
    public void testList(){
        assertProcess("<ol><li>1</li><li>2</li></ol>", "<ol><li>1</li><li>2</li></ol>");
        assertProcess("<ul><li>1</li><li>2</li></ul>", "<ul><li>1</li><li>2</li></ul>");
    }

    @Test
    public void testDefinitionList(){
        assertProcess("<dl><dt>item</dt><dd>definition</dd></dl>", "<DL><DT>item</DT><DD>definition</DD></DL>");
    }

    @Test
    public void testTable(){
        assertProcess(
                "<table><thead><tr><th>1</th><th>2</th></tr></thead><tbody><tr><td>3</td><td>4</td></tr></tbody></table>",
                "<table><thead><tr><th>1</th><th>2</th></tr></thead><tbody><tr><td>3</td><td>4</td></tr></tbody></table>"
        );
    }

    @Test
    public void testUnknown(){
        assertProcess("test", "<blink>test</blink>");
    }
}
