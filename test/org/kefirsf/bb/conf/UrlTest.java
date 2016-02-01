package org.kefirsf.bb.conf;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for URL configuration class.
 *
 * @author kefir
 */
public class UrlTest {

    public static final String NAME = "urlName";
    public static final String NEW_NAME = "newName";

    @Test
    public void testUrl(){
        Url url = new Url(NAME, false, false, false);
        assertEquals(NAME, url.getName());
        assertFalse(url.isGhost());
        assertFalse(url.isLocal());
        assertFalse(url.isSchemaless());

        url.setGhost(true);
        url.setLocal(true);
        url.setSchemaless(true);

        assertTrue(url.isGhost());
        assertTrue(url.isLocal());
        assertTrue(url.isSchemaless());

        url.setName(NEW_NAME);
        assertEquals(NEW_NAME, url.getName());
    }
}
