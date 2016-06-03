package org.kefirsf.bb.test.standard;

import org.junit.runners.Parameterized;

import static org.kefirsf.bb.ConfigurationFactory.SAFE_HTML_CONFIGURATION_FILE;

/**
 * Tests for the SafeHtml configuration
 * @author Vitalii Samolovskikh
 */
public class SafeHtmlTest extends AbstractStandardTest {
    private static final String[][] DATA = new String[][]{
            {"", "<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>"}
    };

    @Parameterized.Parameters(name = "{1}")
    public static String[][] data(){
        return DATA;
    }

    protected String getConfigurationPath() {
        return SAFE_HTML_CONFIGURATION_FILE;
    }
}
