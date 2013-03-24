package org.kefirsf.bb.util;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for ArrayCharSequence
 */
public class ArrayCharSequenceTest {
    public static final char[] TEXT = "abcde".toCharArray();
    public static final int OFFSET = 1;
    public static final int LENGTH = 3;
    private CharSequence sequence;

    @Before
    public void prepare(){
        sequence = new ArrayCharSequence(TEXT, OFFSET, LENGTH);
    }

    @Test
    public void testLength(){
        Assert.assertEquals(LENGTH, sequence.length());
    }

    @Test
    public void testCharAt(){
        for(int i=0; i<sequence.length();i++){
            Assert.assertEquals(TEXT[OFFSET+i], sequence.charAt(i));
        }
    }

    @Test
    public void testSubSequence(){
        Assert.assertEquals(new ArrayCharSequence(TEXT,2,1), sequence.subSequence(1,2));
    }
}
