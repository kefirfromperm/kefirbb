package org.kefirsf.bb.test.standard;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.TextProcessor;

import static org.kefirsf.bb.test.Assert.assertProcess;

/**
 * @author Vitalii Samolovskikh
 */
@RunWith(Parameterized.class)
public abstract class AbstractStandardTest {
    @Parameterized.Parameter(0)
    public String expected;
    @Parameterized.Parameter(1)
    public String source;
    private TextProcessor processor;

    @Before
    public void init(){
        BBProcessorFactory processorFactory = BBProcessorFactory.getInstance();
        processor = processorFactory.createFromResource(getConfigurationPath());
    }

    protected abstract String getConfigurationPath();

    @Test
    public void testExpression(){
        assertProcess(processor, expected, source);
    }
}
