package org.kefirsf.bb.test.blackbox;

import org.junit.Before;
import org.junit.Test;
import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.TextProcessor;
import org.kefirsf.bb.test.Assert;

/**
 * @author Vitalii Samolovskikh aka Kefir
 */
public class SafeHtmlConfigurationTest {
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
        processor = BBProcessorFactory.getInstance().createFromResource("org/kefirsf/bb/safehtml.xml");
    }

    @Test
    public void test(){
        assertProcess("<b>test</b>", "<b onclick=\"javascript:alert('Fail!');\">test</B>");
    }
}
