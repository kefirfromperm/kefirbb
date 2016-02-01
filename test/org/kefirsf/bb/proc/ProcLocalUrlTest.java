package org.kefirsf.bb.proc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.kefirsf.bb.UrlCollection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test ProcUrl for local URLs
 *
 * @author Vitalii Samolovskikh
 */
@RunWith(Parameterized.class)
public class ProcLocalUrlTest {

    public static final String PREFIX = "prefix ";
    public static final String SUFFIX = " suffix";

    @Parameterized.Parameters
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
        Context context = prepareContext(url);
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
        Context context = prepareContext(url);
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
        Source source = prepareSource(url);
        assertEquals(url, PREFIX.length(), element.findIn(source));
        source.setOffset(PREFIX.length());
        assertEquals(url, PREFIX.length(), element.findIn(source));
        source.setOffset(source.length() - SUFFIX.length());
        assertEquals(url, -1, element.findIn(source));
    }

    private Context prepareContext(String url) {
        Context context = new Context();
        context.setSource(prepareSource(url));
        return context;
    }

    private Source prepareSource(String url) {
        StringBuilder b = new StringBuilder();
        b.append(PREFIX);
        b.append(url);
        b.append(SUFFIX);
        return new Source(b);
    }

}
