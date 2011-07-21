package ru.perm.kefir.bbcode;

import java.io.File;
import java.io.InputStream;

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
        return configurationFactory.create().create();
    }

    /**
     * Create the bb-processor using xml-configuration resource
     *
     * @param resourceName name of resource file
     * @return bb-code processor
     * @throws TextProcessorFactoryException when can't find or read the resource or illegal config file
     */
    public TextProcessor createFromResource(String resourceName) {
        return configurationFactory.createFromResource(resourceName).create();
    }

    /**
     * Create the bb-processor from XML InputStream
     *
     * @param stream the input stream with XML
     * @return bb-code processor
     * @throws TextProcessorFactoryException when can't build Document
     */
    public TextProcessor create(InputStream stream) {
        return configurationFactory.create(stream).create();
    }

    /**
     * Create the bb-code processor from file with XML-configuration.
     *
     * @param file file with configuration
     * @return bb-code processor
     * @throws TextProcessorFactoryException any problems
     */
    public TextProcessor create(File file) {
        return configurationFactory.create(file).create();
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
