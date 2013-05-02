package org.kefirsf.bb;

import org.kefirsf.bb.conf.Configuration;
import org.kefirsf.bb.util.Exceptions;
import org.kefirsf.bb.util.Utils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Create the text processor configuration
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public class ConfigurationFactory {
    // Helper constants
    private static final String DEFAULT_CONFIGURATION = "org/kefirsf/bb/default";
    private static final String DEFAULT_USER_CONFIGURATION = "kefirbb";
    private static final String CONFIGURATION_EXTENSION = ".xml";

    // Configuration paths
    public static final String DEFAULT_USER_CONFIGURATION_FILE = DEFAULT_USER_CONFIGURATION + CONFIGURATION_EXTENSION;
    public static final String DEFAULT_CONFIGURATION_FILE = DEFAULT_CONFIGURATION + CONFIGURATION_EXTENSION;
    public static final String DEFAULT_PROPERTIES_FILE = "kefirbb.properties";
    public static final String DEFAULT_PROPERTIES_XML_FILE = "kefirbb.properties.xml";

    private final DomConfigurationFactory domConfigurationFactory = DomConfigurationFactory.getInstance();

    /**
     * Singletone class instance
     */
    private static final ConfigurationFactory instance = new ConfigurationFactory();

    /**
     * private constructor
     */
    private ConfigurationFactory() {
    }

    /**
     * Return instance of class ConfigurationFactory
     *
     * @return configuration factory
     */
    public static ConfigurationFactory getInstance() {
        return instance;
    }


    /**
     * Create the default bb-code processor.
     *
     * @return Default bb-code processor
     * @throws TextProcessorFactoryException when can't read the default code set resource
     */
    public Configuration create() {
        Configuration configuration;
        try {
            InputStream stream = null;
            try {
                // Search the user configuration
                stream = Utils.openResourceStream(DEFAULT_USER_CONFIGURATION_FILE);

                // If user configuration not found then use default
                if (stream == null) {
                    stream = Utils.openResourceStream(DEFAULT_CONFIGURATION_FILE);
                }

                if (stream != null) {
                    configuration = create(stream);
                } else {
                    throw new TextProcessorFactoryException("Can't find or open default configuration resource.");
                }
            } finally {
                if (stream != null) {
                    stream.close();
                }
            }

            Properties properties = new Properties();

            // Load properties from .property file
            InputStream propertiesStream = null;
            try {
                propertiesStream = Utils.openResourceStream(DEFAULT_PROPERTIES_FILE);
                if (propertiesStream != null) {
                    properties.load(propertiesStream);
                }
            } finally {
                if (propertiesStream != null) {
                    propertiesStream.close();
                }
            }

            // Load properties from xml file
            InputStream xmlPropertiesStream = null;
            try {
                xmlPropertiesStream = Utils.openResourceStream(DEFAULT_PROPERTIES_XML_FILE);
                if (xmlPropertiesStream != null) {
                    properties.loadFromXML(xmlPropertiesStream);
                }
            } finally {
                if (xmlPropertiesStream != null) {
                    xmlPropertiesStream.close();
                }
            }

            if (!properties.isEmpty()) {
                Map<String, Object> params = new HashMap<String, Object>();
                params.putAll(configuration.getParams());
                for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                    Object key = entry.getKey();
                    if (key != null) {
                        params.put(key.toString(), entry.getValue());
                    }
                }
                configuration.setParams(params);
            }
        } catch (IOException e) {
            throw new TextProcessorFactoryException(e);
        }

        return configuration;
    }

    /**
     * Create the bb-processor using xml-configuration resource
     *
     * @param resourceName name of resource file
     * @return bb-code processor
     * @throws TextProcessorFactoryException when can't find or read the resource or illegal config file
     */
    public Configuration createFromResource(String resourceName) {
        Exceptions.nullArgument("resourceName", resourceName);

        Configuration configuration;
        try {
            InputStream stream = null;
            try {
                stream = Utils.openResourceStream(resourceName);

                if (stream != null) {
                    configuration = create(stream);
                } else {
                    throw new TextProcessorFactoryException("Can't find or open resource \"" + resourceName + "\".");
                }
            } finally {
                if (stream != null) {
                    stream.close();
                }
            }
        } catch (IOException e) {
            throw new TextProcessorFactoryException(e);
        }

        return configuration;
    }

    /**
     * Create the bb-code processor from file with XML-configuration.
     *
     * @param fileName name of file with configuration
     * @return bb-code processor
     * @throws TextProcessorFactoryException any problems
     */
    public Configuration create(String fileName) {
        return create(new File(fileName));
    }

    /**
     * Create the bb-code processor from file with XML-configuration.
     *
     * @param file file with configuration
     * @return bb-code processor
     * @throws TextProcessorFactoryException any problems
     */
    public Configuration create(File file) {
        try {
            Configuration configuration;
            InputStream stream = new BufferedInputStream(new FileInputStream(file));
            try {
                configuration = create(stream);
            } finally {
                stream.close();
            }
            return configuration;
        } catch (IOException e) {
            throw new TextProcessorFactoryException(e);
        }
    }

    /**
     * Create the bb-processor from XML InputStream
     *
     * @param stream the input stream with XML
     * @return bb-code processor
     * @throws TextProcessorFactoryException when can't build Document
     */
    public Configuration create(InputStream stream) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            factory.setIgnoringElementContentWhitespace(true);
            factory.setNamespaceAware(true);
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.parse(stream);
            return domConfigurationFactory.create(document);
        } catch (ParserConfigurationException e) {
            throw new TextProcessorFactoryException(e);
        } catch (IOException e) {
            throw new TextProcessorFactoryException(e);
        } catch (SAXException e) {
            throw new TextProcessorFactoryException(e);
        }
    }

}
