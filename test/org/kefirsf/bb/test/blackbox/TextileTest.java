package org.kefirsf.bb.test.blackbox;

import org.junit.Before;
import org.junit.Test;
import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.ConfigurationFactory;
import org.kefirsf.bb.TextProcessor;
import org.kefirsf.bb.test.Assert;

/**
 * Tests for the Textile configuration.
 * @author Vitalii Samolovskikh aka Kefir
 */
public class TextileTest {
    /**
     * Default configuration processor (BB2HTML processor)
     */
    private TextProcessor processor;


    private void assertProcess(String expected, String source) {
        Assert.assertProcess(processor, expected, source);
    }

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

/*
        assertProcess(
                "<p></p>",
                ""
        );
*/
    }
}
