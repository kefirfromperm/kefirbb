package org.kefirsf.bb.conf;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Tests for code
 *
 * @author Vitalii Samolovskikh aka Kefir
 */
public class CodeTest {
    private static final String CODE_NAME = "test code";

    @Test
    public void testConstructor1() {
        Code code = new Code();
        Assert.assertNotNull(code.getName());
        Assert.assertEquals(Code.DEFAULT_PRIORITY, code.getPriority());
        Assert.assertNull(code.getPattern());
        Assert.assertNull(code.getTemplate());
    }

    @Test
    public void testConstructor2() {
        Code code = new Code(CODE_NAME);
        Assert.assertEquals(CODE_NAME, code.getName());
        Assert.assertEquals(Code.DEFAULT_PRIORITY, code.getPriority());
        Assert.assertNull(code.getPattern());
        Assert.assertNull(code.getTemplate());
    }

    @Test
    public void testConstructor3() {
        Pattern pattern = createPattern();
        Template template = createTemplate();

        Code code = new Code(pattern, template, CODE_NAME, 1);
        Assert.assertEquals(CODE_NAME, code.getName());
        Assert.assertEquals(1, code.getPriority());
        Assert.assertEquals(pattern, code.getPattern());
        Assert.assertEquals(template, code.getTemplate());
    }

    @Test
    public void testName() {
        Code code = new Code();
        code.setName(CODE_NAME);
        Assert.assertEquals(CODE_NAME, code.getName());
    }

    @Test
    public void testPriority() {
        Code code = new Code();
        code.setPriority(3);
        Assert.assertEquals(3, code.getPriority());
    }

    @Test
    public void testPattern() {
        Code code = new Code();
        Pattern pattern = createPattern();
        code.setPattern(pattern);
        Assert.assertEquals(pattern, code.getPattern());
    }

    @Test
    public void testPatterns() {
        Code code = new Code();
        List<Pattern> patterns = new ArrayList<Pattern >();
        patterns.add(new Pattern(Arrays.asList(new Constant("1"))));
        patterns.add(new Pattern(Arrays.asList(new Constant("2"))));
        code.setPatterns(patterns);
        Assert.assertArrayEquals(patterns.toArray(), code.getPatterns().toArray());

        Pattern pattern = new Pattern(Arrays.asList(new Constant("3")));
        code.addPattern(pattern);
        Assert.assertEquals(pattern, code.getPatterns().get(2));
    }

    @Test
    public void testTemplate() {
        Code code = new Code();
        Template template = createTemplate();
        code.setTemplate(template);
        Assert.assertEquals(template, code.getTemplate());
    }

    private Template createTemplate() {
        return new Template(Arrays.asList(new Constant("test")));
    }

    private Pattern createPattern() {
        return new Pattern(Arrays.asList(new Constant("test")));
    }
}
