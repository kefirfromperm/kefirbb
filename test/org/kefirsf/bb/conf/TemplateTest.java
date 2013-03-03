package org.kefirsf.bb.conf;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Tests for Template configuration.
 *
 * @author Vitalii Samolovskikh aka Kefir
 */
public class TemplateTest {
    @Test
    public void testDefaultConstructor(){
        Template template = new Template();
        Assert.assertTrue(template.getElements().isEmpty());
    }

    @Test
    public void testConstructor(){
        Constant constant = new Constant("test");
        Template template = new Template(Arrays.asList(constant));
        Assert.assertEquals(constant, template.getElements().iterator().next());
    }

    @Test
    public void testElements(){
        Template template = new Template();
        Constant constant = new Constant("test");
        template.setElements(Arrays.asList(constant));
        Assert.assertEquals(constant, template.getElements().iterator().next());
    }
}
