package org.kefirsf.bb.comp;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Vitaliy Samolovskih aka Kefir
 */
public class SourceTest {
    // Any string for tests
    private static final String EXAMPLE = "abcdefghijklmnopqrstuvwxyz";
    private static final int EXAMPLE_LENGTH = EXAMPLE.length();

    private static final String LEFT = "abc";
    private static final String CENTER = "defh";
    private static final String RIGHT = "ijklm";
    private static final StringBuilder STR_FOR_SUB;

    static {
        STR_FOR_SUB = new StringBuilder();
        STR_FOR_SUB.append(LEFT);
        STR_FOR_SUB.append(CENTER);
        STR_FOR_SUB.append(RIGHT);
    }

    @Test
    public void testFind() {
        assertEquals(new Source("aaaabbbbcccc").find("bc"), 7);
    }

    @Test
    public void testNext() {
        Source source = new Source(EXAMPLE);
        for (int i = 0; i < EXAMPLE_LENGTH; i++) {
            assertEquals(EXAMPLE.charAt(i), source.next());
        }
    }

    @Test
    public void testGetOffset() {
        Source source = new Source(EXAMPLE);
        for (int i = 0; i < source.getLength(); ) {
            assertEquals(source.getOffset(), i);
            i++;
            source.incOffset();
        }
    }

    @Test
    public void testIncOffset() {
        Source source = new Source(EXAMPLE);
        int inc = 3;
        for (int i = 0; i < source.getLength(); ) {
            assertEquals(source.getOffset(), i);
            i += 3;
            source.incOffset(inc);
        }
    }

    @Test
    public void testSetOffset() {
        Source source = new Source(EXAMPLE);
        source.setOffset(EXAMPLE_LENGTH);
        assertEquals(source.getOffset(), EXAMPLE_LENGTH);
    }

    @Test
    public void testHasNext() {
        Source source = new Source(EXAMPLE);
        for (int i = 0; i < EXAMPLE_LENGTH; i++) {
            assertTrue(source.hasNext());
            source.incOffset();
        }
        assertFalse(source.hasNext());
    }

    @Test
    public void testHasNextCount() {
        Source source = new Source(EXAMPLE);
        int has = 5;
        for (int i = 0; i < EXAMPLE_LENGTH; i++) {
            if (EXAMPLE_LENGTH - i >= has) {
                assertTrue(source.hasNext(has));
            } else {
                assertFalse(source.hasNext(has));
            }
            source.incOffset();
        }
    }

    @Test
    public void testGetLength() {
        assertEquals(new Source(EXAMPLE).getLength(), EXAMPLE_LENGTH);
    }

    @Test
    public void testSub() {
        Source source = new Source(STR_FOR_SUB);
        source.incOffset(LEFT.length());
        assertEquals(source.sub(LEFT.length() + CENTER.length()), CENTER);
    }

    @Test
    public void testSubTo() {
        Source source = new Source(STR_FOR_SUB);
        source.incOffset(LEFT.length());
        assertEquals(source.subTo(CENTER.length()), CENTER);
    }

    @Test
    public void testSubToEnd() {
        Source source = new Source(STR_FOR_SUB);
        source.incOffset(LEFT.length());
        assertEquals(source.subToEnd(), CENTER + RIGHT);
    }
}
