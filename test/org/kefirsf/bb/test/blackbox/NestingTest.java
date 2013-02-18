package org.kefirsf.bb.test.blackbox;

import org.junit.Test;
import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.TextProcessor;
import org.kefirsf.bb.conf.*;
import org.kefirsf.bb.test.Assert;

import java.util.Arrays;

/**
 * Test nesting limit. It's check for {@link StackOverflowError}.
 *
 * @author Vitalii Samolovskikh aka Kefir
 */
public class NestingTest {
    public static final int SIZE = 982;

    @Test
    public void testStackOverflow() {
        Configuration conf = new Configuration();
        Scope scope = new Scope("ROOT");
        conf.setScopes(Arrays.asList(scope));
        Code code = new Code();
        code.setPattern(new Pattern(Arrays.asList(
                new Constant("["), new Text("val", "ROOT", false), new Constant("]")
        )));
        code.setTemplate(new Template());
        scope.addCode(code);
        TextProcessor processor = BBProcessorFactory.getInstance().create(conf);

        StringBuilder b = new StringBuilder();
        for (int i = 0; i < SIZE; i++) {
            b.append("[");
        }
        b.append("test");
        for (int i = 0; i < SIZE; i++) {
            b.append("]");
        }

        Assert.assertProcess(processor, "", b);
    }
}
