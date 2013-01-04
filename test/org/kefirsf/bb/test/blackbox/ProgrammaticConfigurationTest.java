package org.kefirsf.bb.test.blackbox;

import org.junit.Test;
import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.conf.*;
import org.kefirsf.bb.test.Assert;

import java.util.*;

/**
 * Test the programmatic configuration
 */
public class ProgrammaticConfigurationTest {
    @Test
    public void test() {
        // Create configuration
        Configuration cfg = new Configuration();

        // Set the prefix and suffix
        cfg.setPrefix(new Template(Arrays.asList(new Constant("["))));
        cfg.setSuffix(new Template(Arrays.asList(new Constant("]"))));

        // Configure default scope
        Scope scope = new Scope(Scope.ROOT);

        // Create code and add it to scope
        Code code = new Code();
        code.setPattern(new Pattern(Arrays.asList(new Constant("val"))));
        code.setTemplate(new Template(Arrays.asList(new NamedValue("value"))));

        Set<Code> codes = new HashSet<Code>();
        codes.add(code);
        scope.setCodes(codes);

        // Set scope to configuration
        cfg.setScopes(Arrays.asList(scope));

        // Set the parameter
        Map<String, Object> params = new HashMap<String, Object>();
        params.putAll(cfg.getParams());
        params.put("value", "KefirBB");
        cfg.setParams(params);

        // Test the configuration
        Assert.assertProcess(BBProcessorFactory.getInstance().create(cfg), "[KefirBB]", "val");
    }
}
