package org.kefirsf.bb.conf;

import junit.framework.Assert;
import org.junit.Test;
import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.TextProcessorFactoryException;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Tests for configuration class Scope.
 *
 * @author Vitalii Samolovskikh aka Kefir
 */
public class ScopeTest {
    private static final String SCOPE_NAME = "scope";

    @Test
    public void testConstructor1() {
        Scope scope = new Scope();
        Assert.assertNotNull(scope.getName());
        Assert.assertNull(scope.getParent());
        Assert.assertEquals(Scope.DEFAULT_IGNORE_TEXT, scope.isIgnoreText());
        Assert.assertNotNull(scope.getCodes());
        Assert.assertTrue(scope.getCodes().isEmpty());
    }

    @Test
    public void testConstructor2() {
        Scope scope = new Scope(SCOPE_NAME);
        Assert.assertEquals(SCOPE_NAME, scope.getName());
        Assert.assertNull(scope.getParent());
        Assert.assertEquals(Scope.DEFAULT_IGNORE_TEXT, scope.isIgnoreText());
        Assert.assertNotNull(scope.getCodes());
        Assert.assertTrue(scope.getCodes().isEmpty());
    }

    @Test
    public void testConstructor3() {
        Scope scope = new Scope(SCOPE_NAME, true);
        Assert.assertEquals(SCOPE_NAME, scope.getName());
        Assert.assertNull(scope.getParent());
        Assert.assertEquals(true, scope.isIgnoreText());
        Assert.assertNotNull(scope.getCodes());
        Assert.assertTrue(scope.getCodes().isEmpty());
    }

    @Test
    public void testConstructor4() {
        Scope parent = new Scope("parent");

        Scope scope = new Scope(SCOPE_NAME, parent, true);
        Assert.assertEquals(SCOPE_NAME, scope.getName());
        Assert.assertEquals(parent, scope.getParent());
        Assert.assertEquals(true, scope.isIgnoreText());
        Assert.assertNotNull(scope.getCodes());
        Assert.assertTrue(scope.getCodes().isEmpty());
    }

    @Test
    public void testName() {
        Scope scope = new Scope();
        scope.setName(SCOPE_NAME);
        Assert.assertEquals(SCOPE_NAME, scope.getName());
    }

    @Test
    public void testParent() {
        Scope scope = new Scope();
        Scope parent = new Scope();
        scope.setParent(parent);
        Assert.assertEquals(parent, scope.getParent());
    }

    @Test
    public void testIgnoreText() {
        Scope scope = new Scope();
        scope.setIgnoreText(true);
        Assert.assertEquals(true, scope.isIgnoreText());
    }

    @Test
    public void testCodes() {
        Scope scope = new Scope();

        Code code = new Code();
        code.setPattern(new Pattern(Arrays.asList(
                new Constant("["), new Text("val", scope, false), new Constant("]")
        )));
        code.setTemplate(new Template(Arrays.asList(new NamedValue("val"))));

        scope.setCodes(new HashSet<Code>(Arrays.asList(code)));

        Assert.assertEquals(1, scope.getCodes().size());
        Assert.assertTrue(scope.getCodes().contains(code));

        code = new Code();
        code.setPattern(new Pattern(Arrays.asList(
                new Constant("*"), new Text("val", scope, false), new Constant("*")
        )));
        code.setTemplate(new Template(Arrays.asList(new NamedValue("val"))));
        scope.addCode(code);

        Assert.assertEquals(2, scope.getCodes().size());
        Assert.assertTrue(scope.getCodes().contains(code));
    }

    @Test(expected = TextProcessorFactoryException.class)
    public void testCycle(){
        Scope root = new Scope(Scope.ROOT);
        Scope loop = new Scope();
        root.setParent(loop);
        loop.setParent(root);

        Configuration conf = new Configuration();
        conf.setRootScope(root);

        BBProcessorFactory.getInstance().create(conf);
    }
}
