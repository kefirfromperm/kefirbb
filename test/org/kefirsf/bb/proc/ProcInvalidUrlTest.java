package org.kefirsf.bb.proc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.kefirsf.bb.UrlCollection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Tests for invalid URLs
 * @author kefir
 */
@RunWith(Parameterized.class)
public class ProcInvalidUrlTest extends AbstractProcUrlTest {

    @Parameterized.Parameters(name = "{0}")
    public static String[] urlCollection() {
        return UrlCollection.INVALID;
    }

    @Parameterized.Parameter
    public String url;

    @Test
    public void testParse() throws NestingException {
        Context context = prepareContext();
        Source source = context.getSource();

        ProcUrl proc = new ProcUrl("a", false, false, false);

        assertFalse(proc.parse(context, null));
        source.setOffset(1);
        assertFalse(proc.parse(context, null));
    }

    @Test
    public void testParseTerminator() throws NestingException {
        StringBuilder b = new StringBuilder();
        b.append(PREFIX);
        b.append(url);
        b.append("suffix");
        Source source = new Source(b);

        Context context = new Context();

        context.setSource(source);
        PatternConstant terminator = new PatternConstant("suffix", false);

        ProcUrl proc = new ProcUrl("a", false, false, false);

        assertFalse(url, proc.parse(context, terminator));

        source.setOffset(PREFIX.length());
        assertFalse(url, proc.parse(context, terminator));
    }

    @Test
    public void testIsNextIn() {
        Context context = prepareContext();
        Source source = context.getSource();
        ProcUrl element = new ProcUrl("a", false, false, false);

        assertFalse(url, element.isNextIn(context));
        source.setOffset(PREFIX.length() - 1);
        assertFalse(url, element.isNextIn(context));
        source.setOffset(PREFIX.length());
        assertFalse(url, element.isNextIn(context));
        source.setOffset(source.length() - SUFFIX.length());
        assertFalse(url, element.isNextIn(context));
    }

    @Test
    public void testFindIn() {
        Source source = prepareSource();
        ProcUrl element = new ProcUrl("a", false, false, false);

        assertEquals(url, -1, element.findIn(source));
        source.setOffset(PREFIX.length() - 1);
        assertEquals(url, -1, element.findIn(source));
        source.setOffset(source.length() - SUFFIX.length());
        assertEquals(url, -1, element.findIn(source));
    }

    @Override
    protected String getValue() {
        return url;
    }
}
