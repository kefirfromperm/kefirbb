package ru.perm.kefir.bbcode.blackbox;

import org.junit.Test;
import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.TextProcessor;
import org.kefirsf.bb.TextProcessorFactoryException;

import static ru.perm.kefir.bbcode.Assert.assertProcess;

/**
 * TEst scopes dependencies
 */
public class ScopeTest {
    @Test
    public void testScopes() {
        assertProcess(
                BBProcessorFactory.getInstance().createFromResource("ru/perm/kefir/bbcode/blackbox/config-scope.xml"),
                "[:(",
                "[("
        );
    }

    @Test
    public void testInherit() {
        TextProcessor processor = BBProcessorFactory.getInstance().createFromResource("ru/perm/kefir/bbcode/blackbox/config-inherit.xml");
        assertProcess(processor, "^(*(oi)*)^", "*[*[oi]*]*");
        assertProcess(processor, "[^(oi)^]", "[*[oi]*]");
    }

    @Test
    public void testOrder() {
        TextProcessor processor = BBProcessorFactory.getInstance().createFromResource("ru/perm/kefir/bbcode/blackbox/config-scope-order.xml");
        assertProcess(processor, "!test!", "*test*");
        assertProcess(processor, "#test#", "@test@");
    }

    @Test(expected = TextProcessorFactoryException.class)
    public void testCycle() {
        BBProcessorFactory.getInstance().createFromResource("ru/perm/kefir/bbcode/blackbox/config-scope-cycle.xml");
    }
}
