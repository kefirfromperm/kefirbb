package org.kefirsf.bb.test.standard;

import org.junit.Before;
import org.junit.Test;
import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.ConfigurationFactory;

/**
 * Tests for the Textile configuration.
 * @author Vitalii Samolovskikh aka Kefir
 */
public class TextileTest extends AbstractConfigurationTest {
    /**
     * Create Textile text processor
     */
    @Before
    public void createProcessor() {
        processor = BBProcessorFactory.getInstance().createFromResource(
                ConfigurationFactory.TEXTILE_CONFIGURATION_FILE
        );
    }

    /**
     * Paragraphs
     * http://txstyle.org/doc/2/paragraphs
     */
    @Test
    public void testParagraphs(){
        assertProcess(
                "<p>A paragraph.</p><p>And a paragraph with<br/>a line break.</p>",
                "A paragraph.\n\nAnd a paragraph with\na line break."
        );

        assertProcess(
                "<p>A paragraph.</p><p>And a paragraph with<br/>a line break.</p>",
                "p. A paragraph.\n\np. And a paragraph with\na line break."
        );

        assertProcess(
                "<p>This is a normal paragraph.<br/>With a continuation line.</p>But no paragraph tags here.",
                "This is a normal paragraph.\nWith a continuation line.\n\n But no paragraph tags here."
        );

        assertProcess(
                "<p style=\"text-align:left;\">Aligned left paragraph (default).</p>",
                "p<. Aligned left paragraph (default)."
        );

        assertProcess(
                "<p style=\"text-align:right;\">Aligned right paragraph.</p>",
                "p>. Aligned right paragraph."
        );

        assertProcess(
                "<p style=\"text-align:center;\">Centered paragraph.</p>",
                "p=. Centered paragraph."
        );

        assertProcess(
                "<p style=\"text-align:justify;\">Justified paragraph.</p>",
                "p<>. Justified paragraph."
        );

        assertProcess(
                "<p style=\"padding-left:1em;\">Left indent 1em.</p>",
                "p(. Left indent 1em."
        );

        assertProcess(
                "<p style=\"padding-left:2em;\">Left indent 2em.</p>",
                "p((. Left indent 2em."
        );

        assertProcess(
                "<p style=\"padding-left:3em;\">Left indent 3em.</p>",
                "p(((. Left indent 3em."
        );

        assertProcess(
                "<p style=\"padding-left:7em;\">Left indent 7em.</p>",
                "p(((((((. Left indent 7em."
        );

        assertProcess(
                "<p style=\"padding-right:7em;\">Right indent 7em.</p>",
                "p))))))). Right indent 7em."
        );
    }

    /**
     * Headers
     * http://txstyle.org/doc/7/headings
     */
    @Test
    public void testHeaders(){
        assertProcess("<h1>Header</h1>", "h1. Header");
        assertProcess("<h1>Header</h1>", "h1. Header\n");
        assertProcess("<h1>Header</h1>", "h1. Header\n\n");

        assertProcess("<h2>Header</h2>", "h2. Header");
        assertProcess("<h2>Header</h2>", "h2. Header\n");
        assertProcess("<h2>Header</h2>", "h2. Header\n\n");

        assertProcess("<h3>Header</h3>", "h3. Header");
        assertProcess("<h3>Header</h3>", "h3. Header\n");
        assertProcess("<h3>Header</h3>", "h3. Header\n\n");

        assertProcess("<h4>Header</h4>", "h4. Header");
        assertProcess("<h4>Header</h4>", "h4. Header\n");
        assertProcess("<h4>Header</h4>", "h4. Header\n\n");

        assertProcess("<h5>Header</h5>", "h5. Header");
        assertProcess("<h5>Header</h5>", "h5. Header\n");
        assertProcess("<h5>Header</h5>", "h5. Header\n\n");

        assertProcess("<h6>Header</h6>", "h6. Header");
        assertProcess("<h6>Header</h6>", "h6. Header\n");
        assertProcess("<h6>Header</h6>", "h6. Header\n\n");

        assertProcess("<h3 style=\"text-align:left;\">Header</h3>", "h3<. Header");
        assertProcess("<h3 style=\"text-align:right;\">Header</h3>", "h3>. Header");
        assertProcess("<h3 style=\"text-align:center;\">Header</h3>", "h3=. Header");
        assertProcess("<h3 style=\"text-align:justify;\">Header</h3>", "h3<>. Header");

        assertProcess(
                "<h5>Header</h5><h6>Header</h6><p>Paragraph.</p>",
                "h5. Header\n\nh6. Header\n\nParagraph."
        );

    }

    /**
     * http://redcloth.org/hobix.com/textile/
     */
    @Test
    public void testWriting(){
        assertProcess(
                "<p>I spoke.<br/>And none replied.</p>",
                "I spoke.\nAnd none replied."
        );

        assertProcess(
                "<p>&ldquo;Observe!&rdquo;</p>",
                "\"Observe!\""
        );

        assertProcess(
                "<p>Observe &mdash; very nice!</p>",
                "Observe -- very nice!"
        );

        assertProcess(
                "<p>Observe &ndash; tiny and brief.</p>",
                "Observe - tiny and brief."
        );

        assertProcess(
                "<p>Observe&hellip;</p>",
                "Observe..."
        );

        assertProcess(
                "<p>Observe: 2 &times; 2.</p>",
                "Observe: 2 x 2."
        );

        assertProcess(
                "<p>one&trade;, two&reg;, three&copy;.</p>",
                "one(TM), two(R), three(C)."
        );
    }

    @Test
    public void testPhrase(){
        assertProcess(
                "<p>I <em>believe</em> every word.</p>",
                "I _believe_ every word."
        );
        assertProcess(
                "<p>And then? She <strong>fell</strong>!</p>",
                "And then? She *fell*!"
        );
        assertProcess(
                "<p>I <i>know</i>.<br/>I <b>really</b> <i>know</i>.</p>",
                "I __know__.\nI **really** __know__."
        );
        assertProcess(
                "<p><cite>Cat&apos;s Cradle</cite> by Vonnegut</p>",
                "??Cat's Cradle?? by Vonnegut"
        );
        assertProcess(
                "<p>Convert with <code>r.to_html</code></p>",
                "Convert with @r.to_html@"
        );
        assertProcess(
                "<p>I&apos;m <del>sure</del> not sure.</p>",
                "I'm -sure- not sure."
        );
        assertProcess(
                "<p>You are a <ins>pleasant</ins> child.</p>",
                "You are a +pleasant+ child."
        );
        assertProcess(
                "<p>a <sup>2</sup> + b <sup>2</sup> = c <sup>2</sup></p>",
                "a ^2^ + b ^2^ = c ^2^"
        );
        assertProcess(
                "<p>log <sub>2</sub> x</p>",
                "log ~2~ x"
        );
    }

    @Test
    public void testBlocks(){
        assertProcess(
                "<p>A single paragraph.</p><p>Followed by another.</p>",
                "A single paragraph.\n\nFollowed by another."
        );

        assertProcess(
                "<p>A single paragraph.<br/>With a line break.</p><p>Followed by another.</p>",
                "A single paragraph.\nWith a line break.\n\nFollowed by another."
        );

        assertProcess(
                "<p>A paragraph.</p>",
                "A paragraph.\n\n"
        );

        assertProcess(
                "<p>A paragraph.</p>",
                "A paragraph.\n"
        );

        assertProcess(
                "<p>An old text</p><blockquote><p>A block quotation.</p></blockquote><p>Any old text</p>",
                "An old text\n\nbq. A block quotation.\n\nAny old text"
        );

        // Foot note
        assertProcess(
                "<p>This is covered elsewhere<sup><a href=\"#fn1\">1</a></sup>.</p>",
                "This is covered elsewhere[1]."
        );
        assertProcess(
                "<p id=\"fn1\"><sup>1</sup> Down here, in fact.</p>",
                "fn1. Down here, in fact."
        );
        assertProcess(
                "<p id=\"fn1\"><sup>1</sup> Down here, in fact.</p>",
                "fn1. Down here, in fact.\n"
        );
        assertProcess(
                "<p id=\"fn1\"><sup>1</sup> Down here, in fact.</p>",
                "fn1. Down here, in fact.\n\n"
        );
    }
}
