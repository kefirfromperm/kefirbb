package org.kefirsf.bb.proc;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Tests for ComplexTerminator.
 * @author Vitalii Samolovskikh aka Kefir
 */
public class ComplexTerminatorTest {
    private ComplexTerminator terminator;

    @Before
    public void prepare(){
        Set<ProcPatternElement> set = new HashSet<ProcPatternElement>();
        set.add(new PatternConstant("test", false));
        set.add(new PatternConstant("terminator", false));
        terminator = new ComplexTerminator(set);
    }

    @Test
    public void testIsNextIn(){
        Source source = new Source("junk terminator");
        Assert.assertFalse(terminator.isNextIn(source));

        source.setOffset(5);
        Assert.assertTrue(terminator.isNextIn(source));
    }

    @Test
    public void testFindIn(){
        Assert.assertEquals(5, terminator.findIn(new Source("junk terminator")));
        Assert.assertEquals(-1, terminator.findIn(new Source("abcdefghijklmnopqrst")));
    }
}
