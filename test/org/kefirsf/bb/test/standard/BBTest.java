package org.kefirsf.bb.test.standard;

import org.junit.runners.Parameterized;

import static org.kefirsf.bb.ConfigurationFactory.DEFAULT_CONFIGURATION_FILE;

/**
 * Tests for the standard configuration BBCode
 * @author kefir
 */
public class BBTest extends AbstractStandardTest {
    private static final String[][] DATA = new String[][]{
            {
                    "<a href=\"http://example.com/dashed-path/index.html\">Link name</a>",
                    "[url=http://example.com/dashed-path/index.html]Link name[/url]"
            },
            {
                    "<a href=\"http://example.com/nondashedpath/index.html\">Link name</a>",
                    "[url=http://example.com/nondashedpath/index.html]Link name[/url]"
            }
    };

    @Parameterized.Parameters(name = "{1}")
    public static String[][] data(){
        return DATA;
    }

    protected String getConfigurationPath() {
        return DEFAULT_CONFIGURATION_FILE;
    }
}
