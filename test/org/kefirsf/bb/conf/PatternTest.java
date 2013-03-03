package org.kefirsf.bb.conf;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Tests for Pattern configuration.
 *
 * @author Vitalii Samolovskikh aka Kefir
 */
public class PatternTest {
    @Test
    public void testDefaultConstructor(){
        Pattern pattern = new Pattern();
        Assert.assertTrue(pattern.getElements().isEmpty());
    }

    @Test
    public void testConstructor(){
        Constant constant = new Constant("test");
        Pattern pattern = new Pattern(Arrays.asList(constant));
        Assert.assertEquals(constant, pattern.getElements().iterator().next());
    }

    @Test
    public void testElements(){
        Pattern pattern = new Pattern();
        Constant constant = new Constant("test");
        pattern.setElements(Arrays.asList(constant));
        Assert.assertEquals(constant, pattern.getElements().iterator().next());
    }
}
