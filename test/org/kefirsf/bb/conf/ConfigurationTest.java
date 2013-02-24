package org.kefirsf.bb.conf;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Tests for {@link Configuration}.
 *
 * @author kefir
 */
public class ConfigurationTest {
    @Test
    public void testScope(){
        Configuration conf = new Configuration();

        // Fill the configuration
        Scope root = new Scope(Scope.ROOT);

        conf.setRootScope(root);

        // Read scopes
        Assert.assertEquals(root, conf.getRootScope());
    }
}
