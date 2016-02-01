package org.kefirsf.bb.test.blackbox;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.TextProcessor;

import static org.kefirsf.bb.test.Assert.assertProcess;

/**
 * Tests for schema less URLs
 *
 * @author Vitalii Samolovskikh
 */
@RunWith(Parameterized.class)
public class SchemalessUrlTest {
    private final BBProcessorFactory factory = BBProcessorFactory.getInstance();

    @Parameterized.Parameters
    public static String[] urlCollection() {
        return new String[]{
                "www.example.com", "example.com", "example.com/"
        };
    }

    @Parameterized.Parameter
    public String url;

    @Test
    public void testUrl() {
        TextProcessor processor = factory.createFromResource("org/kefirsf/bb/test/blackbox/config-url.xml");
        assertProcess(processor, "<a href=\"http://" + url + "\">" + url + "</a>", "["+url+"]");
    }
}
