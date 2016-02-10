package org.kefirsf.bb.test.blackbox;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.TextProcessor;
import org.kefirsf.bb.UrlCollection;

import static org.kefirsf.bb.test.Assert.assertProcess;

/**
 * Tests for URLs
 *
 * @author kefir
 */
@RunWith(Parameterized.class)
public class UrlTest {
    private final BBProcessorFactory factory = BBProcessorFactory.getInstance();

    @Parameterized.Parameters(name = "{0}")
    public static String[] urlCollection() {
        return UrlCollection.VALID;
    }

    @Parameterized.Parameter
    public String url;

    @Test
    public void testUrl() {
        TextProcessor processor = factory.createFromResource("org/kefirsf/bb/test/blackbox/config-url.xml");
        assertProcess(processor, "<a href=\"" + url + "\">" + url + "</a>", url);
    }
}
