package org.kefirsf.bb.proc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.*;

/**
 * Tests for a URL processor pattern element.
 *
 * @author kefir
 */
@RunWith(Parameterized.class)
public class ProcUrlTest {

    public static final String PREFIX = "prefix(";
    public static final String SUFFIX = ")suffix";

    @Parameterized.Parameters
    public static String[] urlCollection() {
        return new String[]{
                "http://examle.com",
                "https://example.com",
                "ftp://example.com",
                "mailto:john.smith@example.com"
        };
    }

    @Parameterized.Parameter
    public String url;

    @Test
    public void testParse() throws NestingException {
        StringBuilder b = new StringBuilder();
        b.append(" ");
        b.append(url);
        b.append(" ");
        Source source = new Source(b);

        Context context = new Context();
        context.setSource(source);

        ProcUrl norm = new ProcUrl("a", false);
        ProcUrl ghost = new ProcUrl("b", true);

        assertFalse(norm.parse(context, null));
        assertFalse(ghost.parse(context, null));

        source.setOffset(1);
        assertTrue(ghost.parse(context, null));
        assertEquals(1, source.getOffset());
        assertTrue(norm.parse(context, null));
        assertEquals(source.length() - 1, source.getOffset());
    }

    @Test
    public void testParseTerminator() throws NestingException {
        Context context = prepareContext();
        Source source = context.getSource();
        PatternConstant terminator = new PatternConstant(SUFFIX, false);

        ProcUrl norm = new ProcUrl("a", false);
        ProcUrl ghost = new ProcUrl("b", true);

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
        ProcUrl element = new ProcUrl("a", false);

        assertFalse(element.isNextIn(context));
        source.setOffset(PREFIX.length() - 1);
        assertFalse(element.isNextIn(context));
        source.setOffset(PREFIX.length());
        assertTrue(element.isNextIn(context));
        source.setOffset(source.length() - SUFFIX.length());
        assertFalse(element.isNextIn(context));
    }

    @Test
    public void testFindIn() {
        Source source = prepareSource();
        ProcUrl element = new ProcUrl("a", false);

        assertEquals(PREFIX.length(), element.findIn(source));
        source.setOffset(PREFIX.length() - 1);
        assertEquals(PREFIX.length(), element.findIn(source));
        source.setOffset(source.length() - SUFFIX.length());
        assertEquals(-1, element.findIn(source));
    }

    private Context prepareContext() {
        Context context = new Context();
        context.setSource(prepareSource());
        return context;
    }

    private Source prepareSource() {
        StringBuilder b = new StringBuilder();
        b.append(PREFIX);
        b.append(url);
        b.append(SUFFIX);
        return new Source(b);
    }
}
