package ru.perm.kefir.bbcode.blackbox;

import org.junit.Test;
import org.kefirsf.bb.EscapeXmlProcessorFactory;

import static ru.perm.kefir.bbcode.Assert.assertProcess;

/**
 * Test escape XML processor
 *
 * @author Kefir
 */
public class EscapeXmlProcessorTest {
    @Test
    public void testEscape() {
        assertProcess(
                EscapeXmlProcessorFactory.getInstance().create(),
                "&amp;gt;&lt;a href=&quot;&quot;&gt;&lt;/a&gt;&apos;",
                "&gt;<a href=\"\"></a>'");
    }
}
