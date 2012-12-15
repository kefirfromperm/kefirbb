package org.kefirsf.bb;

import org.kefirsf.bb.comp.*;
import org.kefirsf.bb.conf.*;

import java.io.File;
import java.io.InputStream;
import java.util.*;

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
     * Create scope
     *
     * @param scope
     * @param configuration text processor configuration
     * @param createdScopes created scopes
     * @param codes         codes
     * @return scope
     */
    public static WScope create(Scope scope, Configuration configuration, Map<Scope, WScope> createdScopes, Map<Code, AbstractCode> codes) {
        WScope created = createdScopes.get(scope);
        if (created == null) {
            created = new WScope(scope.getName());
            createdScopes.put(scope, created);
            created.setIgnoreText(scope.isIgnoreText());
            if (scope.getParent() != null) {
                created.setParent(create(configuration.getScope(scope.getParent()), configuration, createdScopes, codes));
            }
            Set<AbstractCode> scopeCodes = new HashSet<AbstractCode>();
            for (Code code : scope.getCodes()) {
                scopeCodes.add(create(code, configuration, createdScopes, codes));
            }
            created.setScopeCodes(scopeCodes);
            created.init();
        }
        return created;
    }

    /**
     * Create code from this definition
     *
     * @param thisCode
     * @param configuration text processor configuration
     * @param createdScopes scopes are created already
     * @param codes         codes are created already
     * @return code object
     */
    public static AbstractCode create(Code thisCode, Configuration configuration, Map<Scope, WScope> createdScopes, Map<Code, AbstractCode> codes) {
        if (thisCode.getPattern() == null) {
            throw new IllegalStateException("Field pattern can't be null.");
        }

        if (thisCode.getTemplate() == null) {
            throw new IllegalStateException("Field template can't be null.");
        }

        AbstractCode code = codes.get(thisCode);
        if (code == null) {
            List<? extends PatternElement> patternElements = thisCode.getPattern().getElements();
            PatternElement first = patternElements.get(0);
            if (patternElements.size() == 1 && first instanceof Constant && !((Constant) first).isIgnoreCase()) {
                code = new ConstantCode(
                        ((Constant) first).getValue(), create(thisCode.getTemplate()), thisCode.getName(), thisCode.getPriority()
                );
            } else {
                code = new WCode(
                        create(thisCode.getPattern(), configuration, createdScopes, codes),
                        create(thisCode.getTemplate()),
                        thisCode.getName(),
                        thisCode.getPriority()
                );
            }
        }
        return code;
    }

    /**
     * Create pattern for text parsing
     *
     * @param pattern
     * @param configuration text processor configuration
     * @param createdScopes scopes was created already
     * @param codes         codes
     * @return pattern
     */
    public static WPattern create(Pattern pattern, Configuration configuration, Map<Scope, WScope> createdScopes, Map<Code, AbstractCode> codes) {
        if (pattern.getElements() == null || pattern.getElements().isEmpty()) {
            throw new IllegalStateException("Pattern elements list can't be empty.");
        }

        List<WPatternElement> elements = new ArrayList<WPatternElement>();
        for (PatternElement element : pattern.getElements()) {
            elements.add(element.create(configuration, createdScopes, codes));
        }
        return new WPattern(elements);
    }

    /**
     * Create template from definition
     *
     * @return template
     * @param template
     */
    public static WTemplate create(Template template) {
        List<WTemplateElement> elements = new ArrayList<WTemplateElement>();
        if (template.getElements() != null) {
            for (TemplateElement element : template.getElements()) {
                elements.add(element.create());
            }
        }
        return new WTemplate(elements);
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
        processor.setScope(create(conf.getRootScope(), conf, createdScopes, codes));
        processor.setPrefix(create(conf.getPrefix()));
        processor.setSuffix(create(conf.getSuffix()));
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
