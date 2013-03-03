package org.kefirsf.bb.conf;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for configuration class Variable.
 *
 * @author Vitalii Samolovskikh aka Kefir
 */
public class VariableTest {

    public static final String NAME = "test";
    public static final java.util.regex.Pattern REGEX = java.util.regex.Pattern.compile("\\d+");

    @Test
    public void testConstructor1() {
        Variable variable = new Variable();
        Assert.assertEquals(null, variable.getName());
        Assert.assertEquals(null, variable.getRegex());
    }

    @Test
    public void testConstructor2() {
        Variable variable = new Variable(NAME);
        Assert.assertEquals(NAME, variable.getName());
        Assert.assertEquals(null, variable.getRegex());
    }

    @Test
    public void testConstructor3() {
        Variable variable = new Variable(NAME, REGEX);
        Assert.assertEquals(NAME, variable.getName());
        Assert.assertEquals(REGEX, variable.getRegex());
    }

    @Test
    public void testName() {
        Variable variable = new Variable();
        variable.setName(NAME);
        Assert.assertEquals(NAME, variable.getName());
    }

    @Test
    public void testRegex() {
        Variable variable = new Variable();
        variable.setRegex(REGEX);
        Assert.assertEquals(REGEX, variable.getRegex());
    }
}
