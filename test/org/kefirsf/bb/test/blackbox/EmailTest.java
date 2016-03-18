package org.kefirsf.bb.test.blackbox;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.TextProcessor;

import static org.kefirsf.bb.test.Assert.assertProcess;

/**
 * Tests for email
 *
 * @author kefir
 */
@RunWith(Parameterized.class)
public class EmailTest {
    private final BBProcessorFactory factory = BBProcessorFactory.getInstance();

    @Parameterized.Parameters
    public static String[] data(){
        return new String[]{
                "john.smith@example.com"
        };
    }

    private String email;

    public EmailTest(String email) {
        this.email = email;
    }

    @Test
    public void testEmail(){
        TextProcessor processor = factory.createFromResource("org/kefirsf/bb/test/blackbox/config-email.xml");
        assertProcess(processor, "<a href=\"mailto:" + email + "\">" + email + "</a>", email);
    }
}
