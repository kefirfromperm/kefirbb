package org.kefirsf.bb.test.blackbox;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.TextProcessor;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Tests for HTML styles collections
 * @author kefir
 */
@RunWith(Parameterized.class)
public class CssStylesTest {
    @Parameterized.Parameters(name="{0}")
    public static Collection<String> data() {
        return Arrays.asList(
                "color: red;",
                "color: #00ff00;",
                "color: rgb(0,0,255);",
                "color: rgba(255, 0, 0, 0.3);",
                "color: hsl(120, 100%, 50%);",
                "color: hsla(120, 100%, 50%, 0.3);",
                "color: white;",
                "color: black;",
                "color: Plum;"
        );
    }

    private String style;

    public CssStylesTest(String style) {
        this.style = style;
    }

    @Test
    public void testStyle(){
        TextProcessor processor = BBProcessorFactory.getInstance().createFromResource(
                "org/kefirsf/bb/test/blackbox/config-style.xml"
        );

        // Ignore whitespaces
        String expected = style.replaceAll("\\s", "");
        String actual = processor.process(style).replaceAll("\\s", "");
        assertEquals(expected, actual);
    }
}
