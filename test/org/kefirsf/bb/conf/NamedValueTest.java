package org.kefirsf.bb.conf;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Vitalii Samolovskikh aka Kefir
 */
public class NamedValueTest {

    private static final String NAME = "test";

    @Test
    public void testConstructor1(){
        NamedValue namedValue = new NamedValue();
        Assert.assertNull(namedValue.getName());
    }

    @Test
    public void testConstructor2(){
        NamedValue namedValue = new NamedValue(NAME);
        Assert.assertEquals(NAME, namedValue.getName());
    }

    @Test
    public void testName(){
        NamedValue namedValue = new NamedValue();
        namedValue.setName(NAME);
        Assert.assertEquals(NAME, namedValue.getName());
    }
}
