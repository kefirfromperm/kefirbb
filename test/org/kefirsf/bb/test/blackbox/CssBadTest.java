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
public class CssBadTest {
    @Parameterized.Parameters(name="{0}")
    public static Collection<String> data() {
        return Arrays.asList(new String[] {
                "background: url(http://example.com/image.gif);",
                "",
                "",
                "",
                "",
                ""
        });
    }

    private String style;

    public CssBadTest(String style) {
        this.style = style;
    }

    @Test
    public void testStyle(){
        TextProcessor processor = BBProcessorFactory.getInstance().createFromResource(
                "org/kefirsf/bb/test/blackbox/config-style.xml"
        );
        assertEquals("", processor.process(style));
    }
}
