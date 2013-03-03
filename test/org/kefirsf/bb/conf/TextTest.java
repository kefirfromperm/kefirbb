package org.kefirsf.bb.conf;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for test configuration.
 *
 * @author Vitalii Samolovskikh aka Kefir
 */
public class TextTest {

    public static final String NAME = "name";
    public static final Scope SCOPE = new Scope("scope");

    @Test
    public void testConstructor1() {
        Text text = new Text();
        Assert.assertEquals(null, text.getName());
        Assert.assertEquals(null, text.getScope());
        Assert.assertEquals(false, text.isTransparent());
    }

    @Test
    public void testConstructor2() {
        Text text = new Text(NAME, SCOPE, true);
        Assert.assertEquals(NAME, text.getName());
        Assert.assertEquals(SCOPE, text.getScope());
        Assert.assertEquals(true, text.isTransparent());
    }

    @Test
    public void testName() {
        Text text = new Text();
        text.setName(NAME);
        Assert.assertEquals(NAME, text.getName());
    }

    @Test
    public void testScope() {
        Text text = new Text();
        text.setScope(SCOPE);
        Assert.assertEquals(SCOPE, text.getScope());
    }

    @Test
    public void testTransparent() {
        Text text = new Text();
        text.setTransparent(true);
        Assert.assertEquals(true, text.isTransparent());
    }
}
