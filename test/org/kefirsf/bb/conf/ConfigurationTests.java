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
public class ConfigurationTests {

    public static final String NAME = "scope";

    @Test
    public void testReadLock(){
        Configuration conf = new Configuration();
        conf.readLock();
        conf.assertReadLock();
        conf.readUnlock();
    }

    @Test
    public void testWriteLock(){
        Configuration conf = new Configuration();
        conf.writeLock();
        conf.assertReadLock();
        conf.assertWriteLock();
        conf.writeUnlock();
    }


    public void testScope(){
        Configuration conf = new Configuration();

        // Fill the configuration
        conf.writeLock();

        Scope root = new Scope(Scope.ROOT);
        Scope scope = new Scope(NAME);

        Collection<Scope> scopes = new ArrayList<Scope>();
        scopes.add(root);
        scopes.add(scope);
        conf.setScopes(scopes);

        conf.writeUnlock();

        // Read scopes
        conf.readLock();

        Assert.assertEquals(scope, conf.getScope(NAME));
        Assert.assertEquals(root, conf.getRootScope());

        conf.readUnlock();
    }
}
