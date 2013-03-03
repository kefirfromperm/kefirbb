package org.kefirsf.bb.conf;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for configuration class Constants.
 * @author Vitalii Samolovskikh aka Kefir
 */
public class ConstantTest {
    private static final String VALUE = "test";

    @Test
    public void testConstructor1(){
        Constant constant = new Constant();
        Assert.assertEquals(null, constant.getValue());
        Assert.assertEquals(false, constant.isIgnoreCase());
    }

    @Test
    public void testConstructor2(){
        Constant constant = new Constant(VALUE);
        Assert.assertEquals(VALUE, constant.getValue());
        Assert.assertEquals(false, constant.isIgnoreCase());
    }

    @Test
    public void testConstructor3(){
        Constant constant = new Constant(VALUE, true);
        Assert.assertEquals(VALUE, constant.getValue());
        Assert.assertEquals(true, constant.isIgnoreCase());
    }

    @Test
    public void testValue(){
        Constant constant = new Constant();
        constant.setValue(VALUE);
        Assert.assertEquals(VALUE, constant.getValue());
    }

    @Test
    public void testIgnoreCase(){
        Constant constant = new Constant();
        constant.setIgnoreCase(true);
        Assert.assertTrue(constant.isIgnoreCase());
    }
}

