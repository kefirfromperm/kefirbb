package org.kefirsf.bb.test.blackbox;

import org.junit.Before;
import org.junit.Test;
import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.TextProcessor;
import org.kefirsf.bb.test.Assert;

/**
 * @author Vitaliy Samolovskih aka Kefir
 */
public class DefaultConfigurationTest {
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
        processor = BBProcessorFactory.getInstance().createFromResource("org/kefirsf/bb/default.xml");
    }

    /**
     * Test escaping special html symbols
     */
    @Test
    public void escape() {
        assertProcess(
                "&amp;gt;&lt;a href=&quot;&quot;&gt;&lt;/a&gt;&apos;",
                "&gt;<a href=\"\"></a>'"
        );
    }

    /**
     * Test default configuration
     */
    @Test
    public void defaultConfiguration() {
        // Simple text
        assertProcess("Simple text", "Simple text");

        // XML escape
        assertProcess("&amp;", "&");
        assertProcess("&apos;", "'");
        assertProcess("&lt;", "<");
        assertProcess("&gt;", ">");
        assertProcess("&quot;", "\"");

        // new string
        assertProcess("<br/>", "\r\n");
        assertProcess("<br/>", "\n\r");
        assertProcess("<br/>", "\r");
        assertProcess("<br/>", "\n");

        // quote bbcode symbols
        assertProcess("\\", "\\\\");
        assertProcess("[", "\\[");
        assertProcess("]", "\\]");

        // Simple formatting
        assertProcess("<b>test</b>", "[B]test[/b]");
        assertProcess("<u>test</u>", "[u]test[/u]");
        assertProcess("<s>test</s>", "[s]test[/s]");
        assertProcess("<i>test</i>", "[i]test[/i]");

        // pictures
        assertProcess("<img src=\"http://kefirsf.org/kefirbb/ava.jpg\"/>", "[img]http://kefirsf.org/kefirbb/ava.jpg[/img]");
        assertProcess("<img src=\"/ava.jpg\"/>", "[img]/ava.jpg[/img]");

        // links
        // http
        assertProcess("<a href=\"http://kefirsf.org/kefirbb/\">KefirBB</a>", "[url=http://kefirsf.org/kefirbb/]KefirBB[/url]");
        assertProcess("<a href=\"http://kefirsf.org/kefirbb/\">http://kefirsf.org/kefirbb/</a>", "[url]http://kefirsf.org/kefirbb/[/url]");
        assertProcess("<a href=\"http://kefirsf.org/kefirbb/\">KefirBB</a>", "[url=kefirsf.org/kefirbb/]KefirBB[/url]");
        assertProcess("<a href=\"http://kefirsf.org/kefirbb/\">kefirsf.org/kefirbb/</a>", "[url]kefirsf.org/kefirbb/[/url]");

        // https
        assertProcess("<a href=\"https://securedsite.org\">Secured</a>", "[url=https://securedsite.org]Secured[/url]");
        assertProcess("<a href=\"https://securedsite.org\">https://securedsite.org</a>", "[url]https://securedsite.org[/url]");

        //mailto
        assertProcess("<a href=\"mailto:kefir@perm.ru\">Mail to Kefir</a>", "[url=mailto:kefir@perm.ru]Mail to Kefir[/url]");
        assertProcess("<a href=\"mailto:kefir@perm.ru\">mailto:kefir@perm.ru</a>", "[url]mailto:kefir@perm.ru[/url]");

        // local links
        assertProcess("<a href=\"/test.xml\">Test</a>", "[url=/test.xml]Test[/url]");
        assertProcess("<a href=\"/test.xml\">/test.xml</a>", "[url]/test.xml[/url]");
        assertProcess("<a href=\"./test.xml\">Test</a>", "[url=./test.xml]Test[/url]");
        assertProcess("<a href=\"./test.xml\">./test.xml</a>", "[url]./test.xml[/url]");
        assertProcess("<a href=\"../test.xml\">Test</a>", "[url=../test.xml]Test[/url]");
        assertProcess("<a href=\"../test.xml\">../test.xml</a>", "[url]../test.xml[/url]");

        // XSS attempt
        assertProcess("<a href=\"http://javascript:alert(&apos;Kefir is stupid!&apos;);\">javascript:alert(&apos;Kefir is stupid!&apos;);</a>", "[url]javascript:alert('Kefir is stupid!');[/url]");

        // formatting
        assertProcess("<span style=\"color:red;\">Red text</span>", "[color=red]Red text[/color]");
        assertProcess("<span style=\"font-size:14px;\">Test</span>", "[size=14px]Test[/size]");

        // quotes
        assertProcess("<blockquote>Quoted text</blockquote>", "[quote]Quoted text[/quote]");
        assertProcess("&laquo;Test&raquo;", "[aquote]Test[/aquote]");
        assertProcess("", "[*Comment*]");

        // Special html symbols
        assertProcess("&copy;", "[symbol=copy/]");
    }

    @Test
    public void code() {
        // Simple text
        assertCodeScope("Simple text");

        // XML escape
        assertCodeScope("&amp;", "&");
        assertCodeScope("&apos;", "'");
        assertCodeScope("&lt;", "<");
        assertCodeScope("&gt;", ">");
        assertCodeScope("&quot;", "\"");

        // new string
        assertCodeScope("<br/>", "\r\n");
        assertCodeScope("<br/>", "\n\r");
        assertCodeScope("<br/>", "\r");
        assertCodeScope("<br/>", "\n");

        // quote bbcode symbols
        assertCodeScope("\\", "\\\\");
        assertCodeScope("[", "\\[");
        assertCodeScope("]", "\\]");

        // Simple formatting
        assertCodeScope("[b]test[/b]");
        assertCodeScope("[u]test[/u]");
        assertCodeScope("[s]test[/s]");
        assertCodeScope("[i]test[/i]");

        // pictures
        assertCodeScope("[img]http://kefirsf.org/kefirbb/ava.jpg[/img]");
        assertCodeScope("[img]/ava.jpg[/img]");

        // links
        // http
        assertCodeScope("[url=http://kefirsf.org/kefirbb/]KefirBB[/url]");
        assertCodeScope("[url]http://kefirsf.org/kefirbb/[/url]");
        assertCodeScope("[url=http://kefirsf.org/kefirbb/]KefirBB[/url]");
        assertCodeScope("[url]http://kefirsf.org/kefirbb/[/url]");

        // https
        assertCodeScope("[url=https://securedsite.org]Secured[/url]");
        assertCodeScope("[url]https://securedsite.org[/url]");

        //mailto
        assertCodeScope("[url=mailto:kefir@perm.ru]Mail to Kefir[/url]");
        assertCodeScope("[url]mailto:kefir@perm.ru[/url]");

        // local links
        assertCodeScope("[url=/test.xml]Test[/url]");
        assertCodeScope("[url]/test.xml[/url]");
        assertCodeScope("[url=./test.xml]Test[/url]");
        assertCodeScope("[url]./test.xml[/url]");
        assertCodeScope("[url=../test.xml]Test[/url]");
        assertCodeScope("[url]../test.xml[/url]");

        // formatting
        assertCodeScope("[color=red]Red text[/color]");
        assertCodeScope("[size=14px]Test[/size]");

        // quotes
        assertCodeScope("[quote]Quoted text[/quote]");
        assertCodeScope("&laquo;Test&raquo;", "[aquote]Test[/aquote]");
        assertCodeScope("", "[*Comment*]");

        // Special html symbols
        assertCodeScope("&copy;", "[symbol=copy/]");
    }

    private void assertCodeScope(String expected, String source) {
        assertProcess("<pre>" + expected + "</pre>", "[code]" + source + "[/code]");
    }

    private void assertCodeScope(String source) {
        assertCodeScope(source, source);
    }

    @Test
    public void table() {
        assertProcess("<table></table>", "[table][/table]");
        assertProcess("<table></table>", "[table]Test[/table]");
        assertProcess("<table></table>", "[table][*Test*][/table]");
        assertProcess("<table><tr></tr></table>", "[table][tr][/tr][/table]");
        assertProcess("<table><tr></tr></table>", "[table][tr]Test[/tr][/table]");
        assertProcess("<table><tr></tr></table>", "[table][tr][*Test*][/tr][/table]");
        assertProcess(
                "<table><tr><th>header1</th><th>header2</th></tr><tr><td>column1</td><td>column2</td></tr></table>",
                "[table][tr][th]header1[/th][th]header2[/th][/tr][tr][td]column1[/td][td]column2[/td][/tr][/table]"
        );
    }
}
