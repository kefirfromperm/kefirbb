package ru.perm.kefir.bbcode;

import org.junit.Before;
import org.junit.Test;
import org.kefirsf.bb.IntSet;

import java.util.Random;

/**
 * @author Vitaliy Samolovskih aka Kefir
 */
public class IntSetTest {
    private IntSet set;

    @Before
    public void init() {
        set = new IntSet();
    }

    @Test
    public void test() {
        Assert.assertFalse(set.contains(0));
        set.add(0);
        Assert.assertTrue(set.contains(0));
        Assert.assertFalse(set.contains(1));
        set.add(257);
        Assert.assertFalse(set.contains(1));
        Assert.assertTrue(set.contains(257));
        set.add(769);
        Assert.assertFalse(set.contains(1));
        Assert.assertTrue(set.contains(257));
        Assert.assertTrue(set.contains(769));
        set.add(513);
        Assert.assertFalse(set.contains(1));
        Assert.assertTrue(set.contains(257));
        Assert.assertTrue(set.contains(769));
        Assert.assertTrue(set.contains(513));
        set.add(257);
        Assert.assertFalse(set.contains(1));
        Assert.assertTrue(set.contains(257));
        Assert.assertTrue(set.contains(769));
        Assert.assertTrue(set.contains(513));
        set.add(255);
        Assert.assertTrue(set.contains(255));
        Assert.assertFalse(set.contains(511));
    }

    @Test
    public void testRandom() {
        Random random = new Random();
        for (int i = 0; i < 100000; i++) {
            int value = random.nextInt();
            set.add(value);
            Assert.assertTrue(set.contains(value));
        }
    }

    @Test
    public void testRandom1() {
        int[] vals = new int[10000];
        Random random = new Random();
        for (int i = 0; i < vals.length; i++) {
            vals[i] = random.nextInt();
            set.add(vals[i]);
        }

        for (int val : vals) {
            Assert.assertTrue(set.contains(val));
        }
    }
}
