package org.kefirsf.bb.conf;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
        Url url = new Url(NAME, false);
        assertEquals(NAME, url.getName());
        assertEquals(false, url.isGhost());

        url.setGhost(true);
        assertEquals(true, url.isGhost());

        url.setName(NEW_NAME);
        assertEquals(NEW_NAME, url.getName());
    }
}
