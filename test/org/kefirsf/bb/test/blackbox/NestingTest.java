package org.kefirsf.bb.test.blackbox;

import org.junit.Test;
import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.TextProcessor;
import org.kefirsf.bb.TextProcessorNestingException;
import org.kefirsf.bb.conf.*;
import org.kefirsf.bb.test.Assert;

import java.util.Arrays;

/**
 * Test nesting limit. It's check for {@link StackOverflowError}.
 *
 * @author Vitalii Samolovskikh aka Kefir
 */
public class NestingTest {
    public static final int MAX_NESTING = 500;
    public static final int STACK_OVERFLOW_NESTING = 2000;

    public TextProcessor createProcessor(){
        return BBProcessorFactory.getInstance().create(createConfiguration());
    }

    private Configuration createConfiguration() {
        Configuration conf = new Configuration();
        Scope scope = new Scope("ROOT");
        conf.setScopes(Arrays.asList(scope));
        Code code = new Code();
        code.setPattern(new Pattern(Arrays.asList(
                new Constant("["), new Text("val", "ROOT", false), new Constant("]")
        )));
        code.setTemplate(new Template(Arrays.asList(new NamedValue("val"))));
        scope.addCode(code);
        return conf;
    }

    @Test
    public void testMaxNesting() {
        Assert.assertProcess(createProcessor(), "test", prepare(MAX_NESTING));
    }

    @Test
    public void testNestingOverflow() {
        Assert.assertProcess(createProcessor(), "", prepare(MAX_NESTING+1));
    }

    @Test
    public void testStackOverflow() {
        Assert.assertProcess(createProcessor(), "", prepare(STACK_OVERFLOW_NESTING));
    }

    @Test(expected = TextProcessorNestingException.class)
    public void testException(){
        Configuration configuration = createConfiguration();
        TextProcessor processor = BBProcessorFactory.getInstance().create(configuration);
        processor.process(prepare(MAX_NESTING+1));
    }

    private StringBuilder prepare(int nesting) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < nesting; i++) {
            b.append("[");
        }
        b.append("test");
        for (int i = 0; i < nesting; i++) {
            b.append("]");
        }
        return b;
    }
}
