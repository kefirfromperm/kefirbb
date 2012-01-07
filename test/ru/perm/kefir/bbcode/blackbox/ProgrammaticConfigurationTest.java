package ru.perm.kefir.bbcode.blackbox;

import org.junit.Test;
import org.kefirsf.bb.configuration.*;
import ru.perm.kefir.bbcode.Assert;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Test the programmatic configuration
 */
public class ProgrammaticConfigurationTest {
    @Test
    public void test() {
        // Create configuration
        Configuration cfg = new Configuration();
        cfg.lock();
        try {
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
            cfg.addParam("value", "KefirBB");
        } finally {
            cfg.unlock();
        }
        // Test the configuration
        Assert.assertProcess(cfg.create(), "[KefirBB]", "val");
    }
}
