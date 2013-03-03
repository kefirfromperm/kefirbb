package org.kefirsf.bb.util;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Test for exceptions utils class.
 *
 * @author Vitalii Samolovskikh aka Kefir
 */
public class ExceptionsTest {
    @Test(expected = IllegalArgumentException.class)
    public void testNullArgument(){
        Exceptions.nullArgument("test", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyArgument(){
        Exceptions.emptyArgument("test", new ArrayList<Object>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBlankArgument(){
        Exceptions.blankArgument("test", "   ");
    }
}
