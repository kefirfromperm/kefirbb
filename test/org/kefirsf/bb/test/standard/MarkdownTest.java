package org.kefirsf.bb.test.standard;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.TextProcessor;

import java.util.Arrays;
import java.util.Collection;

import static org.kefirsf.bb.ConfigurationFactory.MARKDOWN_CONFIGURATION_FILE;
import static org.kefirsf.bb.test.Assert.assertProcess;

/**
 * Tests for the standard markdown configuration.
 * @author kefir
 */
@RunWith(Parameterized.class)
public class MarkdownTest {
    public static final String[][] DATA = new String[][]{
            {"<p>Simple text paragraph.</p>", "Simple text paragraph."},
            {"<p>First paragraph.</p><p>Second paragraph.</p>", "First paragraph.\n\nSecond paragraph."},
            {"<p>Paragraph with an\n unsigned line break.</p>", "Paragraph with an\n unsigned line break."},
            {"<p>Paragraph with a signed<br/>line break.</p>", "Paragraph with a signed  \nline break."},
            {"<h1>This is an H1</h1>", "This is an H1\n============="},
            {"<h2>This is an H2</h2>", "This is an H2\n-------------"},
            {"<h1>This is an H1</h1>", "# This is an H1"},
            {"<h2>This is an H2</h2>", "## This is an H2"},
            {"<h6>This is an H6</h6>", "###### This is an H6"},
            {"<h1>This is an H1</h1>", "# This is an H1 #"},
            {"<h2>This is an H2</h2>", "## This is an H2 ##"},
            {"<h3>This is an H3</h3>", "### This is an H3 ######"},
            {"<hr/>", "* * *"},
            {"<hr/>", "***"},
            {"<hr/>", "*****"},
            {"<hr/>", "- - -"},
            {"<hr/>", "---------------------------------------"},
            {"<p>\\`*_{}[]()#+-.!</p>", "\\\\`\\*\\_\\{\\}\\[\\]\\(\\)\\#\\+\\-\\.\\!"},
            {"<p>A text with <em>an em</em> block.</p>", "A text with *an em* block."},
            {"<p>A text with <em>an em</em> block.</p>", "A text with _an em_ block."},
            {"<p>A text with <strong>an strong</strong> block.</p>", "A text with **an strong** block."},
            {"<p>A text with <strong>an strong</strong> block.</p>", "A text with __an strong__ block."},
            {
                    "<p>This is <a href=\"http://example.com/\" title=\"Title\">an example</a> inline link.</p>",
                    "This is [an example](http://example.com/ \"Title\") inline link."
            },
            {
                    "<p><a href=\"http://example.net/\">This link</a> has no title attribute.</p>",
                    "[This link](http://example.net/) has no title attribute."
            },
            {
                    "<p>See my <a href=\"/about/\">About</a> page for details.</p>",
                    "See my [About](/about/) page for details."
            },
            {
                    "<p><a href=\"http://example.com/\">http://example.com/</a></p>",
                    "<http://example.com/>"
            },
            {
                    "<p><a href=\"mailto:address@example.com\">address@example.com</a></p>",
                    "<address@example.com>"
            },
            {"<p>Use the <code>printf()</code> function.</p>", "Use the `printf()` function."},
            {
                    "<p><code>There is a literal backtick (`) here.</code></p>",
                    "``There is a literal backtick (`) here.``"
            },
            {
                    "<p>A single backtick in a code span: <code>`</code></p>",
                    "A single backtick in a code span: `` ` ``\n"
            },
            {
                    "<p>A backtick-delimited string in a code span: <code>`foo`</code></p>",
                    "A backtick-delimited string in a code span: `` `foo` ``"
            },
            {
                    "<p>Please don&apos;t use any <code>&lt;blink&gt;</code> tags.</p>",
                    "Please don't use any `<blink>` tags."
            },
            {
                    "<p><code>&amp;#8212;</code> is the decimal-encoded equivalent of <code>&amp;mdash;</code>.</p>",
                    "`&#8212;` is the decimal-encoded equivalent of `&mdash;`."
            },
            {"<p>&amp;</p>", "&amp;"},
            {"<p>&copy;</p>", "&copy;"},
            {"<p>&amp;</p>", "&"},
            {"<p>AT&amp;T</p>", "AT&T"},
            {"<p>4 &lt; 5</p>", "4 < 5"},
            {"<p><img src=\"/path/to/img.jpg\" alt=\"Alt text\"/></p>", "![Alt text](/path/to/img.jpg)"},
            {
                    "<p><img src=\"/path/to/img.jpg\" alt=\"Alt text\" title=\"Optional title\"/></p>",
                    "![Alt text](/path/to/img.jpg \"Optional title\")"
            },
            {
                    "<p><img src=\"https://example.com/path/to/img.jpg\" alt=\"Alt text\" title=\"Optional title\"/></p>",
                    "![Alt text](https://example.com/path/to/img.jpg \"Optional title\")"
            },
            {
                    "<p><img src=\"https://example.com/path/to/img.jpg\" title=\"Optional title\"/></p>",
                    "!(https://example.com/path/to/img.jpg \"Optional title\")"
            },
            {
                    "<p><img src=\"https://example.com/path/to/img.jpg\"/></p>",
                    "!(https://example.com/path/to/img.jpg)"
            },
            {
                    "<pre><code>A code block\n" +
                            "second string\n" +
                            "\n" +
                            "third string.</code></pre>",
                    "    A code block\n    second string\n\n    third string."
            },
            {
                    "<pre><code>A code block\n" +
                            "second string\n" +
                            "\n" +
                            "third string.</code></pre>",
                    "\tA code block\n\tsecond string\n\n\tthird string."
            },
            {
                    "<pre><code>A code block\n" +
                            "second string\n" +
                            "\n" +
                            "third string.</code></pre><p>Normal text.</p>",
                    "    A code block\n" +
                            "    second string\n" +
                            "\n" +
                            "    third string.\n" +
                            "Normal text."
            }
/*
            ,
            {
                    "<blockquote><h1>Blockquote</h1>" +
                            "<p>First line of paragraph.\n" +
                            "Second line of paragraph.</p>" +
                            "<p>Second paragraph.\n" +
                            "Second second.</p>" +
                            "<p>Third paragraph.\n" +
                            "New line of third paragraph.</p>" +
                            "</blockquote>" +
                            "<p>The paragraph outside the blockquote.</p>",
                    "> # Blockquote\n" +
                            ">\n" +
                            "> First line of paragraph.\n" +
                            "> Second line of paragraph.\n" +
                            "> \n" +
                            "> Second paragraph.\n" +
                            "Second second.\n" +
                            "\n" +
                            "> Third paragraph.\n" +
                            "New line of third paragraph.\n" +
                            "\n" +
                            "The paragraph outside the blockquote."
            },
            {
                    "<blockquote><p>Simple blockquote.</p></blockquote>",
                    "> Simple blockquote."
            },
            {
                    "<blockquote><h1>Header blockquote.</h1></blockquote>",
                    "> Header blockquote.\n" +
                            "> ======="
            }
*/
    };
    private TextProcessor processor;

    @Parameterized.Parameters(name = "{1}")
    public static Collection<String[]> data(){
        return Arrays.asList(
                DATA
        );
    }

    private String expected;
    private String source;

    public MarkdownTest(String expected, String actual) {
        this.expected = expected;
        this.source = actual;
    }

    @Before
    public void init(){
        BBProcessorFactory processorFactory = BBProcessorFactory.getInstance();
        processor = processorFactory.createFromResource(MARKDOWN_CONFIGURATION_FILE);
    }

    @Test
    public void testExpression(){
        assertProcess(processor, expected, source);
    }
}
