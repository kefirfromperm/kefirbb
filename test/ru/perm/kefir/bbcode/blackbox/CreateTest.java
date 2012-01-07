package ru.perm.kefir.bbcode.blackbox;

import org.junit.Test;
import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.TextProcessor;
import org.kefirsf.bb.TextProcessorFactoryException;

import java.io.File;

import static ru.perm.kefir.bbcode.Assert.assertProcess;

/**
 * @author Kefir
 */
public class CreateTest {
    @Test
    public void loadFromResource() {
        TextProcessor processor = BBProcessorFactory.getInstance().createFromResource("ru/perm/kefir/bbcode/blackbox/config-resource.xml");
        assertProcess(processor, "resource", "test");
    }

    @Test
    public void loadFromFile() {
        TextProcessor processor = BBProcessorFactory.getInstance().create(new File("resource/config-file.xml"));
        assertProcess(processor, "file", "test");
        processor = BBProcessorFactory.getInstance().create("resource/config-file.xml");
        assertProcess(processor, "file", "test");
    }

    @Test
    public void namespace() {
        TextProcessor processor = BBProcessorFactory.getInstance().createFromResource("ru/perm/kefir/bbcode/blackbox/config-namespace.xml");
        assertProcess(processor, "namespace", "test");
    }

    @Test(expected = TextProcessorFactoryException.class)
    public void invalidConfig() {
        BBProcessorFactory.getInstance().createFromResource("ru/perm/kefir/bbcode/blackbox/config-invalid.xml");
        assert false;
    }

    @Test
    public void defaultLoad() {
        TextProcessor processor = BBProcessorFactory.getInstance().create();
        assertProcess(processor, "default", "test");
    }
}
