package org.kefirsf.bb.proc;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

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
    public void testFind(){
        Source source = new Source(STR_FOR_SUB);

        Set<PatternConstant> constants = new HashSet<PatternConstant>();

        PatternConstant left = new PatternConstant(LEFT, false);
        PatternConstant center = new PatternConstant(CENTER, false);
        PatternConstant right = new PatternConstant(RIGHT, false);

        constants.add(left);
        constants.add(center);
        constants.add(right);

        source.setConstantSet(constants);

        assertEquals(0, source.find(left));
        assertEquals(LEFT.length(), source.find(center));
        assertEquals(LEFT.length()+CENTER.length(), source.find(right));
    }

    @Test
    public void testNextIs(){
        Source source = new Source(STR_FOR_SUB);

        Set<PatternConstant> constants = new HashSet<PatternConstant>();

        PatternConstant left = new PatternConstant(LEFT, false);
        PatternConstant center = new PatternConstant(CENTER, false);
        PatternConstant right = new PatternConstant(RIGHT, false);

        constants.add(left);
        constants.add(center);
        constants.add(right);

        source.setConstantSet(constants);

        assertTrue(source.nextIs(left));

        source.setOffset(LEFT.length());
        assertTrue(source.nextIs(center));

        source.setOffset(LEFT.length()+CENTER.length());
        assertTrue(source.nextIs(right));
    }

    @Test
    public void testNext() {
        Source source = new Source(EXAMPLE);
        source.setConstantSet(new HashSet<PatternConstant>());
        for (int i = 0; i < EXAMPLE_LENGTH; i++) {
            assertEquals(EXAMPLE.charAt(i), source.next());
        }
    }

    @Test
    public void testGetOffset() {
        Source source = new Source(EXAMPLE);
        source.setConstantSet(new HashSet<PatternConstant>());
        for (int i = 0; i < source.length(); ) {
            assertEquals(source.getOffset(), i);
            i++;
            source.incOffset();
        }
    }

    @Test
    public void testIncOffset() {
        Source source = new Source(EXAMPLE);
        source.setConstantSet(new HashSet<PatternConstant>());
        int inc = 3;
        for (int i = 0; i < source.length(); ) {
            assertEquals(source.getOffset(), i);
            i += 3;
            source.incOffset(inc);
        }
    }

    @Test
    public void testHasNext() {
        Source source = new Source(EXAMPLE);
        source.setConstantSet(new HashSet<PatternConstant>());
        for (int i = 0; i < EXAMPLE_LENGTH; i++) {
            assertTrue(source.hasNext());
            source.incOffset();
        }
        assertFalse(source.hasNext());
    }

    @Test
    public void testGetLength() {
        assertEquals(new Source(EXAMPLE).length(), EXAMPLE_LENGTH);
    }

    @Test
    public void testSub() {
        Source source = new Source(STR_FOR_SUB);
        source.setConstantSet(new HashSet<PatternConstant>());
        source.incOffset(LEFT.length());
        assertEquals(CENTER, source.sub(LEFT.length() + CENTER.length()).toString());
    }

    @Test
    public void testSubToEnd() {
        Source source = new Source(STR_FOR_SUB);
        source.setConstantSet(new HashSet<PatternConstant>());
        source.incOffset(LEFT.length());
        assertEquals(CENTER + RIGHT, source.subToEnd().toString());
    }
}
