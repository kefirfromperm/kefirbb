package org.kefirsf.bb;

import org.kefirsf.bb.comp.AbstractCode;
import org.kefirsf.bb.comp.WScope;
import org.kefirsf.bb.conf.Code;
import org.kefirsf.bb.conf.Configuration;
import org.kefirsf.bb.conf.Scope;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory for creating BBProcessor from Stream, File, Resource with configuration or default bb-processor.
 *
 * @author Kefir
 */
public final class BBProcessorFactory implements TextProcessorFactory {
    /**
     * Instance of this class. See the Singleton pattern
     */
    private static final BBProcessorFactory instance = new BBProcessorFactory();
    private final ConfigurationFactory configurationFactory = ConfigurationFactory.getInstance();

    /**
     * Return instance of BBProcessorFactory
     *
     * @return factory instance
     */
    public static BBProcessorFactory getInstance() {
        return instance;
    }

    /**
     * Private constructor. Because this is a singleton.
     */
    private BBProcessorFactory() {
    }

    /**
     * Create the default bb-code processor.
     *
     * @return Default bb-code processor
     * @throws TextProcessorFactoryException when can't read the default code set resource
     */
    public TextProcessor create() {
        return create(configurationFactory.create());
    }

    /**
     * Create TextProcessor by programmatic configuration.
     *
     * @param conf programmatic configuration
     * @return bb-code text processor
     */
    public TextProcessor create(Configuration conf) {
        BBProcessor processor;

        Map<Scope, WScope> createdScopes = new HashMap<Scope, WScope>();
        Map<Code, AbstractCode> codes = new HashMap<Code, AbstractCode>();

        processor = new BBProcessor();
        processor.setScope(conf.getRootScope().create(conf, createdScopes, codes));
        processor.setPrefix(conf.getPrefix().create());
        processor.setSuffix(conf.getSuffix().create());
        processor.setParams(conf.getParams());
        return processor;
    }

    /**
     * Create the bb-processor using xml-configuration resource
     *
     * @param resourceName name of resource file
     * @return bb-code processor
     * @throws TextProcessorFactoryException when can't find or read the resource or illegal config file
     */
    public TextProcessor createFromResource(String resourceName) {
        return create(configurationFactory.createFromResource(resourceName));
    }

    /**
     * Create the bb-processor from XML InputStream
     *
     * @param stream the input stream with XML
     * @return bb-code processor
     * @throws TextProcessorFactoryException when can't build Document
     */
    public TextProcessor create(InputStream stream) {
        return create(configurationFactory.create(stream));
    }

    /**
     * Create the bb-code processor from file with XML-configuration.
     *
     * @param file file with configuration
     * @return bb-code processor
     * @throws TextProcessorFactoryException any problems
     */
    public TextProcessor create(File file) {
        return create(configurationFactory.create(file));
    }

    /**
     * Create the bb-code processor from file with XML-configuration.
     *
     * @param fileName name of file with configuration
     * @return bb-code processor
     * @throws TextProcessorFactoryException any problems
     */
    public TextProcessor create(String fileName) {
        return create(new File(fileName));
    }
}
