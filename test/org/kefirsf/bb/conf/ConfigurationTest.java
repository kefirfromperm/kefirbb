package org.kefirsf.bb.conf;

import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Tests for {@link Configuration}.
 *
 * @author kefir
 */
public class ConfigurationTest {

    public static final String NAME = "scope";

    @Test
    public void testScope(){
        Configuration conf = new Configuration();

        // Fill the configuration
        Scope root = new Scope(Scope.ROOT);
        Scope scope = new Scope(NAME);

        Collection<Scope> scopes = new ArrayList<Scope>();
        scopes.add(root);
        scopes.add(scope);
        conf.setScopes(scopes);

        // Read scopes
        Assert.assertEquals(scope, conf.getScope(NAME));
        Assert.assertEquals(root, conf.getRootScope());
    }
}
