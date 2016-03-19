package org.kefirsf.bb.test.blackbox;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.TextProcessor;
import org.kefirsf.bb.UrlCollection;

import static org.kefirsf.bb.test.Assert.assertProcess;

/**
 * Tests for invalid emails
 * @author kefir
 */
@RunWith(Parameterized.class)
public class InvalidEmailTest {
    private final BBProcessorFactory factory = BBProcessorFactory.getInstance();

    @Parameterized.Parameters
    public static String[] data(){
        return UrlCollection.INVALID_EMAIL;
    }

    private String email;

    public InvalidEmailTest(String email) {
        this.email = email;
    }

    @Test
    public void testEmail(){
        TextProcessor processor = factory.createFromResource("org/kefirsf/bb/test/blackbox/config-email.xml");
        assertProcess(processor, email, email);
    }
}
