package org.kefirsf.bb.test.standard;

import org.kefirsf.bb.TextProcessor;
import org.kefirsf.bb.test.Assert;

/**
 * @author Vitalii Samolovskikh aka Kefir
 */
public abstract class AbstractConfigurationTest {
    /**
     * Default configuration processor (BB2HTML processor)
     */
    protected TextProcessor processor;

    protected void assertProcess(String expected, String source) {
        Assert.assertProcess(processor, expected, source);
    }
}
