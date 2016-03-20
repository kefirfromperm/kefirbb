package org.kefirsf.bb.proc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.kefirsf.bb.UrlCollection;

import static org.junit.Assert.*;

/**
 * Test ProcUrl for local URLs
 *
 * @author Vitalii Samolovskikh
 */
@RunWith(Parameterized.class)
public class ProcLocalUrlTest extends AbstractProcUrlTest {
    @Parameterized.Parameters(name = "{0}")
    public static String[] urlCollection() {
        String[] urls = new String[UrlCollection.LOCAL.length + UrlCollection.VALID.length];
        System.arraycopy(UrlCollection.LOCAL, 0, urls, 0, UrlCollection.LOCAL.length);
        System.arraycopy(UrlCollection.VALID, 0, urls, UrlCollection.LOCAL.length, UrlCollection.VALID.length);
        return urls;
    }

    private ProcUrl element = new ProcUrl("a", false, true, false);

    @Parameterized.Parameter
    public String url;

    @Test
    public void testParse() throws NestingException {
        Context context = prepareContext();
        Source source = context.getSource();
        assertFalse(element.parse(context, null));
        source.setOffset(PREFIX.length() - 1);
        assertFalse(element.parse(context, null));
        source.setOffset(PREFIX.length());
        assertTrue(element.parse(context, null));
        assertEquals(source.length() - SUFFIX.length(), source.getOffset());
        source.setOffset(source.length() - SUFFIX.length());
        assertFalse(element.parse(context, null));
    }

    @Test
    public void testIsNextIn() {
        Context context = prepareContext();
        Source source = context.getSource();
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
        assertEquals(url, PREFIX.length(), element.findIn(source));
        source.setOffset(PREFIX.length());
        assertEquals(url, PREFIX.length(), element.findIn(source));
        source.setOffset(source.length() - SUFFIX.length());
        assertEquals(url, -1, element.findIn(source));
    }

    @Override
    protected String getValue() {
        return url;
    }
}
