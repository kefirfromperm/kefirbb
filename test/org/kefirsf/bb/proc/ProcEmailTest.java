package org.kefirsf.bb.proc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.kefirsf.bb.UrlCollection;

import static org.junit.Assert.*;

/**
 * Tests for the ProcEmail class
 *
 * @author kefir
 */
@RunWith(Parameterized.class)
public class ProcEmailTest extends AbstractProcUrlTest {
    @Parameterized.Parameters(name = "{0}")
    public static String[] emailCollection() {
        return UrlCollection.VALID_EMAIL;
    }

    @Parameterized.Parameter
    public String email;

    @Test
    public void testParse() throws NestingException {
        Source source = prepareSource();

        Context context = new Context();
        context.setSource(source);

        ProcEmail norm = new ProcEmail("a", false);
        ProcEmail ghost = new ProcEmail("b", true);

        assertFalse(norm.parse(context, null));
        assertFalse(ghost.parse(context, null));

        source.setOffset(PREFIX.length());
        assertTrue(ghost.parse(context, null));
        assertEquals(PREFIX.length(), source.getOffset());
        assertTrue(norm.parse(context, null));
        assertEquals(source.length() - SUFFIX.length(), source.getOffset());
    }

    @Test
    public void testParseTerminator() throws NestingException {
        Context context = prepareContext();
        Source source = context.getSource();
        PatternConstant terminator = new PatternConstant(SUFFIX, false);

        ProcEmail norm = new ProcEmail("a", false);
        ProcEmail ghost = new ProcEmail("b", true);

        assertFalse(norm.parse(context, terminator));
        assertFalse(ghost.parse(context, terminator));

        source.setOffset(PREFIX.length());
        assertTrue(ghost.parse(context, terminator));
        assertEquals(PREFIX.length(), source.getOffset());
        assertTrue(norm.parse(context, terminator));
        assertEquals(source.length() - SUFFIX.length(), source.getOffset());
    }

    @Test
    public void testIsNextIn() {
        Context context = prepareContext();
        Source source = context.getSource();
        ProcEmail element = new ProcEmail("a", false);

        assertFalse(element.isNextIn(context));
        source.setOffset(PREFIX.length() - 1);
        assertFalse(element.isNextIn(context));
        source.setOffset(PREFIX.length());
        assertTrue(element.isNextIn(context));
        source.setOffset(source.length() - ProcUrlTest.SUFFIX.length());
        assertFalse(element.isNextIn(context));
    }

    @Test
    public void testFindIn() {
        Source source = prepareSource();
        ProcEmail element = new ProcEmail("a", false);

        assertEquals(PREFIX.length(), element.findIn(source));
        source.setOffset(PREFIX.length() - 1);
        assertEquals(PREFIX.length(), element.findIn(source));
        source.setOffset(source.length() - SUFFIX.length());
        assertEquals(-1, element.findIn(source));
    }

    @Override
    protected String getValue() {
        return email;
    }
}
