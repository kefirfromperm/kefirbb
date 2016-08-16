package org.kefirsf.bb.test.blackbox;

import org.junit.Assert;
import org.junit.Test;
import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.ConfigurationFactory;
import org.kefirsf.bb.TextProcessor;

/**
 * A test to test rolling back timeout when in the text there are many corrupted codes.
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public class RollbackTest {
    @Test(timeout = 1000)
    public void test() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            builder.append("[b]");
        }
        String text = builder.toString();

        TextProcessor processor = BBProcessorFactory.getInstance().createFromResource(
                ConfigurationFactory.DEFAULT_CONFIGURATION_FILE
        );

        Assert.assertEquals(text, processor.process(text));
    }
}
