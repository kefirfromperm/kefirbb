package org.kefirsf.bb.proc;

import org.junit.Test;
import org.kefirsf.bb.UrlCollection;

import static org.junit.Assert.assertEquals;
import static org.kefirsf.bb.proc.ProcUrl.Schema.*;

/**
 * Test parse methods of ProcUrl
 *
 * @author kefir
 */
public class ProcUrlMethodTest {

    public static final String PREFIX = "prefix";
    public static final String SUFFIX = "suffix";
    private final ProcUrl url;

    public ProcUrlMethodTest() {
        url = new ProcUrl("a", false);
    }

    @Test
    public void testParseSchema(){
        assertEquals(HTTP, url.parseSchema(createSource("http://"), PREFIX.length()));
        assertEquals(HTTPS, url.parseSchema(createSource("https://"), PREFIX.length()));
        assertEquals(FTP, url.parseSchema(createSource("ftp://"), PREFIX.length()));
        assertEquals(MAILTO, url.parseSchema(createSource("mailto:"), PREFIX.length()));
        assertEquals(HTTP, url.parseSchema(createSource("HTTP://"), PREFIX.length()));
        assertEquals(HTTPS, url.parseSchema(createSource("HTTPS://"), PREFIX.length()));
        assertEquals(FTP, url.parseSchema(createSource("FTP://"), PREFIX.length()));
        assertEquals(MAILTO, url.parseSchema(createSource("MAILTO:"), PREFIX.length()));
    }

    @Test
    public void testParseAuthority(){
        String[] authorities = new String[]{
                "", "john.smith@", "john.smith:pa22w0rd@",
                "JOHN.smith@", "john.SMITH:pa22w0rd@"
        };

        for(String authority: authorities) {
            assertEquals(authority, authority.length(), url.parseAuthority(createSource(authority), PREFIX.length()));
        }
    }

    @Test
    public void testParseHost(){
        String[] hosts = new String[]{
                "", "example.com", "www.example.com", "kefirsf.org",
        };

        for(String host: hosts) {
            assertEquals(
                    host, host.length(),
                    url.parseHost(createSource(host), PREFIX.length(), new PatternConstant(SUFFIX, false))
            );
        }
    }

    @Test
    public void testParsePath(){
        String[] paths = new String[]{
                "", "/", "/home", "/home/", "/home/web"
        };

        for(String path: paths) {
            assertEquals(
                    path, path.length(),
                    url.parsePath(createSource(path), PREFIX.length(), new PatternConstant(SUFFIX, false))
            );
        }
    }

    @Test
    public void testParseQuery(){
        String[] queries = new String[]{
                "", "?", "?key=value", "?key1=value1&key2=value2"
        };

        for(String query: queries) {
            assertEquals(
                    query, query.length(),
                    url.parseQuery(createSource(query), PREFIX.length(), new PatternConstant(SUFFIX, false))
            );
        }
    }

    @Test
    public void testParseFragment(){
        String[] fragments = new String[]{
                "", "#", "#anchor"
        };

        for(String fragment: fragments) {
            assertEquals(
                    fragment, fragment.length(),
                    url.parseFragment(createSource(fragment), PREFIX.length(), new PatternConstant(SUFFIX, false))
            );
        }
    }

    @Test
    public void testParseLegth(){
        for(String url1: UrlCollection.VALID) {
            assertEquals(
                    url1, url1.length(),
                    url.parseLength(createSource(url1), PREFIX.length(), new PatternConstant(SUFFIX, false))
            );
        }
    }

    private Source createSource(String value){
        StringBuilder b = new StringBuilder();
        b.append(PREFIX);
        b.append(value);
        b.append(SUFFIX);
        return new Source(b);
    }
}
