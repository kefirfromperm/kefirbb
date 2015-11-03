package org.kefirsf.bb.proc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the URL pattern element.
 *
 * @author kefir
 * @see org.kefirsf.bb.proc.ProcUrl
 */
public class ProcUrlTest {
    private static final String SIMPLE_URL = "http://www.example.com";
    private static final String PREFIX = " ";
    private static final String SUFFIX = ".";
    private static final String VAR_NAME = "url";

    @Test
    public void testParse() throws NestingException {
        Source source = new Source(SIMPLE_URL + SUFFIX);
        Context context = new Context();
        context.setSource(source);
        ProcUrl el = new ProcUrl(VAR_NAME);
        assertTrue(el.parse(context, null));
        assertEquals(SIMPLE_URL, context.getAttribute(VAR_NAME));
    }

/*
    @Test
    public void testIsNextIn() {
        ProcUrl el = new ProcUrl(VAR_NAME);
        assertTrue(el.isNextIn(new Source(SIMPLE_URL)));
    }
*/

    @Test
    public void findIn() {
        ProcUrl el = new ProcUrl(VAR_NAME);
        assertEquals(PREFIX.length(), el.findIn(new Source(PREFIX + SIMPLE_URL + SUFFIX)));
    }
}
