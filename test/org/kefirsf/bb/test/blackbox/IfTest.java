package org.kefirsf.bb.test.blackbox;

import org.junit.Test;
import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.TextProcessor;

import static org.kefirsf.bb.test.Assert.assertProcess;

/**
 * @author kefir
 */
public class IfTest {
    private final BBProcessorFactory factory = BBProcessorFactory.getInstance();
    private final TextProcessor processor = factory.createFromResource(
            "org/kefirsf/bb/test/blackbox/config-if.xml"
    );

    @Test
    public void testIf(){
        assertProcess(processor, "b", "a");
        assertProcess(processor, "b(1)", "a(1)");
        assertProcess(processor, "b[2]", "a{2}");
        assertProcess(processor, "b(1)[2]", "a(1){2}");
    }
}
