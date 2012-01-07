package org.kefirsf.bb.test.blackbox;

import org.junit.Assert;
import org.junit.Test;
import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.TextProcessor;

/**
 * @author Vitaliy Samolovskih aka Kefir
 */
public class RollbackTest {
    @Test
    public void test() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            builder.append("[b]");
        }
        String text = builder.toString();

        TextProcessor processor = BBProcessorFactory.getInstance().createFromResource("org/kefirsf/bb/default.xml");
        long start = System.currentTimeMillis();
        Assert.assertEquals(text, processor.process(text));
        long finish = System.currentTimeMillis();
        assert finish - start < 1000;
    }
}
