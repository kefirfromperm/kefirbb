package org.kefirsf.bb.conf;

import junit.framework.Assert;
import org.junit.Test;

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
}
