package org.kefirsf.bb;

import org.kefirsf.bb.conf.*;
import org.kefirsf.bb.util.Utils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.*;

/**
 * @author Vitaliy Samolovskih aka Kefir
 */
public class DomConfigurationFactory {
    /**
     * Schema location
     */
    private static final String SCHEMA_LOCATION = "http://kefir-bb.sourceforge.net/schemas";
    /**
     * Constants wich uses when parse XML-configuration
     */
    private static final String TAG_CODE = "code";
    private static final String TAG_CODE_ATTR_NAME = "name";
    private static final String TAG_CODE_ATTR_PRIORITY = "priority";
    private static final String TAG_PATTERN = "pattern";
    private static final String TAG_VAR = "var";
    private static final String TAG_VAR_ATTR_NAME = "name";
    private static final String DEFAULT_VARIABLE_NAME = "variable";
    private static final String TAG_VAR_ATTR_PARSE = "parse";
    private static final boolean DEFAULT_PARSE_VALUE = true;
    private static final String TAG_VAR_ATTR_INHERIT = "inherit";
    private static final boolean DEFAULT_INHERIT_VALUE = false;
    private static final String TAG_VAR_ATTR_REGEX = "regex";
    private static final String TAG_VAR_ATTR_TRANSPARENT = "transparent";
    private static final String TAG_TEMPLATE = "template";
    private static final String TAG_SCOPE = "scope";
    private static final String TAG_SCOPE_ATTR_NAME = "name";
    private static final String TAG_SCOPE_ATTR_PARENT = "parent";
    private static final String TAG_SCOPE_ATTR_IGNORE_TEXT = "ignoreText";
    private static final boolean DEFAULT_IGNORE_TEXT = false;
    private static final String TAG_CODEREF = "coderef";
    private static final String TAG_CODEREF_ATTR_NAME = TAG_CODE_ATTR_NAME;
    private static final String TAG_PREFIX = "prefix";
    private static final String TAG_SUFFIX = "suffix";
    private static final String TAG_PARAMS = "params";
    private static final String TAG_PARAM = "param";
    private static final String TAG_PARAM_ATTR_NAME = "name";
    private static final String TAG_PARAM_ATTR_VALUE = "value";
    private static final String TAG_CONSTANT = "constant";
    private static final String TAG_CONSTANT_ATTR_VALUE = "value";
    private static final String TAG_CONSTANT_ATTR_IGNORE_CASE = "ignoreCase";
    private static final String TAG_JUNK = "junk";

    private static final DomConfigurationFactory instance = new DomConfigurationFactory();

    private DomConfigurationFactory() {
    }

    public static DomConfigurationFactory getInstance() {
        return instance;
    }

    /**
     * Create the bb-code processor from DOM Document
     *
     * @param dc document
     * @return bb-code processor
     * @throws TextProcessorFactoryException If invalid Document
     */
    public Configuration create(Document dc) {
        // Create configuration
        Configuration configuration = new Configuration();

        parseNesting(configuration, dc);

        // Parse parameters
        configuration.setParams(parseParams(dc));

        // Parse prefix and suffix
        configuration.setPrefix(parseFix(dc, TAG_PREFIX));
        configuration.setSuffix(parseFix(dc, TAG_SUFFIX));

        // Parse codes and scope and set this to configuration
        // Parse scopes
        NodeList scopeNodeList = dc.getDocumentElement().getElementsByTagNameNS(SCHEMA_LOCATION, TAG_SCOPE);
        Map<String, Scope> scopes = parseScopes(scopeNodeList);

        boolean fillRoot = false;
        Scope root;
        if (!scopes.containsKey(Scope.ROOT)) {
            root = new Scope(Scope.ROOT);
            scopes.put(Scope.ROOT, root);
            fillRoot = true;
        } else {
            root = scopes.get(Scope.ROOT);
        }

        // Parse codes
        Map<String, Code> codes = parseCodes(dc);

        // include codes in scopes
        fillScopeCodes(scopeNodeList, scopes, codes);

        // If root scope not defined in configuration file, then root scope fills all codes
        if (fillRoot) {
            root.setCodes(new HashSet<Code>(codes.values()));
        }

        // set root scope
        configuration.setScopes(scopes.values());

        // return configuration
        return configuration;
    }

    /**
     * Parse nesting element, which describes nesting behavior.
     *
     * @param configuration parser configuration
     * @param dc            DOM-document
     */
    private void parseNesting(Configuration configuration, Document dc) {
        NodeList list = dc.getElementsByTagName("nesting");
        if (list.getLength() > 0) {
            Node el = list.item(0);
            String limit = nodeAttribute(el, "limit");
            if (limit != null) {
                configuration.setNestingLimit(Integer.decode(limit));
            }
            String exception = nodeAttribute(el, "exception");
            if (exception != null) {
                configuration.setPropagateNestingException(Boolean.valueOf(exception));
            }
        }
    }

    private Map<String, Object> parseParams(Document dc) {
        Map<String, Object> params = new HashMap<String, Object>();
        NodeList paramsElements = dc.getElementsByTagName(TAG_PARAMS);
        if (paramsElements.getLength() > 0) {
            Element paramsElement = (Element) paramsElements.item(0);
            NodeList paramElements = paramsElement.getElementsByTagName(TAG_PARAM);
            for (int i = 0; i < paramElements.getLength(); i++) {
                Node paramElement = paramElements.item(i);
                String name = nodeAttribute(paramElement, TAG_PARAM_ATTR_NAME, "");
                String value = nodeAttribute(paramElement, TAG_PARAM_ATTR_VALUE, "");
                if (name != null && name.length() > 0) {
                    params.put(name, value);
                }
            }
        }
        return params;
    }

    @SuppressWarnings({"unchecked"})
    private Template parseFix(Document dc, String tagname) {
        Template fix;
        NodeList prefixElementList = dc.getElementsByTagName(tagname);
        if (prefixElementList.getLength() > 0) {
            fix = parseTemplate(prefixElementList.item(0));
        } else {
            fix = new Template();
        }
        return fix;
    }

    /**
     * Fill codes of scopes.
     *
     * @param scopeNodeList node list with scopes definitions
     * @param scopes        scopes
     * @param codes         codes
     * @throws TextProcessorFactoryException any problem
     */
    private void fillScopeCodes(
            NodeList scopeNodeList,
            Map<String, Scope> scopes,
            Map<String, Code> codes) {
        for (int i = 0; i < scopeNodeList.getLength(); i++) {
            Element scopeElement = (Element) scopeNodeList.item(i);
            Scope scope =
                    scopes.get(scopeElement.getAttribute(TAG_SCOPE_ATTR_NAME));

            // Add codes to scope
            Set<Code> scopeCodes =
                    new HashSet<Code>();

            // bind exists codes
            NodeList coderefs = scopeElement.getElementsByTagNameNS(SCHEMA_LOCATION, TAG_CODEREF);
            for (int j = 0; j < coderefs.getLength(); j++) {
                Element ref = (Element) coderefs.item(j);
                String codeName = ref.getAttribute(TAG_CODEREF_ATTR_NAME);
                Code code = codes.get(codeName);
                if (code == null) {
                    throw new TextProcessorFactoryException("Can't find code \"" + codeName + "\".");
                }
                scopeCodes.add(code);
            }

            // Add inline codes
            NodeList inlineCodes = scopeElement.getElementsByTagNameNS(SCHEMA_LOCATION, TAG_CODE);
            for (int j = 0; j < inlineCodes.getLength(); j++) {
                // Inline element code
                Element ice = (Element) inlineCodes.item(j);
                scopeCodes.add(parseCode(ice));
            }

            // Set codes to scope
            scope.setCodes(scopeCodes);
        }
    }

    /**
     * Parse scopes from XML
     *
     * @param scopeNodeList list with scopes definitions
     * @return scopes
     * @throws TextProcessorFactoryException any problems
     */
    private Map<String, Scope> parseScopes(NodeList scopeNodeList) {
        Map<String, Scope> scopes =
                new HashMap<String, Scope>();
        for (int i = 0; i < scopeNodeList.getLength(); i++) {
            Element scopeElement = (Element) scopeNodeList.item(i);
            String name = scopeElement.getAttribute(TAG_SCOPE_ATTR_NAME);
            if (name.length() == 0) {
                throw new TextProcessorFactoryException("Illegal scope name.");
            }
            Scope scope =
                    new Scope(
                            name,
                            nodeAttribute(scopeElement, TAG_SCOPE_ATTR_PARENT, null),
                            nodeAttribute(scopeElement, TAG_SCOPE_ATTR_IGNORE_TEXT, DEFAULT_IGNORE_TEXT)
                    );
            scopes.put(scope.getName(), scope);
        }
        return scopes;
    }

    /**
     * Parse codes from XML
     *
     * @param dc DOM document with configuration
     * @return codes
     * @throws TextProcessorFactoryException any problem
     */
    private Map<String, Code> parseCodes(Document dc) {
        Map<String, Code> codes = new HashMap<String, Code>();
        NodeList codeNodeList = dc.getDocumentElement().getElementsByTagNameNS(SCHEMA_LOCATION, TAG_CODE);
        for (int i = 0; i < codeNodeList.getLength(); i++) {
            Code code = parseCode((Element) codeNodeList.item(i));
            codes.put(code.getName(), code);
        }
        return codes;
    }

    /**
     * Parse bb-code from DOM Node
     *
     * @param codeElement node, represent code wich
     * @return bb-code
     * @throws TextProcessorFactoryException if error format
     */
    private Code parseCode(Element codeElement) {
        // Code name
        Code code = new Code(nodeAttribute(codeElement, TAG_CODE_ATTR_NAME, Utils.generateRandomName()));

        // Code priority
        code.setPriority(nodeAttribute(codeElement, TAG_CODE_ATTR_PRIORITY, Code.DEFAULT_PRIORITY));

        // Template to building
        NodeList templateElements = codeElement.getElementsByTagNameNS(SCHEMA_LOCATION, TAG_TEMPLATE);
        if (templateElements.getLength() > 0) {
            code.setTemplate(parseTemplate(templateElements.item(0)));
        } else {
            throw new TextProcessorFactoryException("Illegal configuration. Can't find template of code.");
        }


        // Pattern to parsing
        NodeList patternElements = codeElement.getElementsByTagNameNS(SCHEMA_LOCATION, TAG_PATTERN);
        if (patternElements.getLength() > 0) {
            code.setPattern(parsePattern(patternElements.item(0)));
        } else {
            throw new TextProcessorFactoryException("Illegal configuration. Can't find pattern of code.");
        }

        // return code
        return code;
    }

    /**
     * Parse code pattern for parse text.
     *
     * @param node pattern node with pattern description
     * @return list of pattern elements
     * @throws TextProcessorFactoryException If invalid pattern format
     */
    private Pattern parsePattern(Node node) {
        List<PatternElement> elements = new LinkedList<PatternElement>();
        NodeList patternList = node.getChildNodes();
        int patternLength = patternList.getLength();
        if (patternLength <= 0) {
            throw new TextProcessorFactoryException("Invalid pattern. Pattern is empty.");
        }

        // Ignore case for all constants
        boolean ignoreCase = nodeAttribute(node, "ignoreCase", false);

        for (int k = 0; k < patternLength; k++) {
            Node el = patternList.item(k);
            if (el.getNodeType() == Node.TEXT_NODE) {
                Constant constant = new Constant(el.getNodeValue(), ignoreCase);
                elements.add(constant);
            } else if (
                    el.getNodeType() == Node.ELEMENT_NODE
                            && el.getLocalName().equals(TAG_CONSTANT)
                    ) {
                elements.add(parseConstant(el, ignoreCase));
            } else if (
                    el.getNodeType() == Node.ELEMENT_NODE
                            && el.getLocalName().equals(TAG_VAR)
                            && (k != 0 || nodeHasAttribute(el, TAG_VAR_ATTR_REGEX))
                    ) {
                elements.add(parseNamedElement(el));
            } else if (
                    el.getNodeType() == Node.ELEMENT_NODE
                            && el.getLocalName().equals(TAG_JUNK)
                            && k != 0
                    ) {
                elements.add(new Junk());
            } else {
                throw new TextProcessorFactoryException("Invalid pattern. Unknown XML element.");
            }
        }
        return new Pattern(elements);
    }

    /**
     * Parse constant pattern element
     *
     * @param el         DOM element
     * @param ignoreCase if true the constant must ignore case
     * @return constant definition
     */
    private Constant parseConstant(Node el, boolean ignoreCase) {
        return new Constant(
                nodeAttribute(el, TAG_CONSTANT_ATTR_VALUE),
                nodeAttribute(el, TAG_CONSTANT_ATTR_IGNORE_CASE, ignoreCase)
        );
    }

    private PatternElement parseNamedElement(Node el) {
        String name = nodeAttribute(el, TAG_VAR_ATTR_NAME, DEFAULT_VARIABLE_NAME);
        PatternElement namedElement;
        if (
                nodeAttribute(el, TAG_VAR_ATTR_PARSE, DEFAULT_PARSE_VALUE)
                        && !nodeHasAttribute(el, TAG_VAR_ATTR_REGEX)
                ) {
            namedElement = parseText(el, name);
        } else {
            namedElement = parseVariable(el, name);
        }
        return namedElement;
    }

    private Text parseText(
            Node el,
            String name
    ) {
        Text text;
        if (nodeAttribute(el, TAG_VAR_ATTR_INHERIT, DEFAULT_INHERIT_VALUE)) {
            text = new Text(
                    name, null,
                    nodeAttribute(el, TAG_VAR_ATTR_TRANSPARENT, false)
            );
        } else {
            text = new Text(
                    name,
                    nodeAttribute(el, TAG_SCOPE, Scope.ROOT),
                    nodeAttribute(el, TAG_VAR_ATTR_TRANSPARENT, false)
            );
        }
        return text;
    }

    private Variable parseVariable(Node el, String name) {
        Variable variable;
        if (nodeHasAttribute(el, TAG_VAR_ATTR_REGEX)) {
            variable = new Variable(
                    name,
                    java.util.regex.Pattern.compile(
                            nodeAttribute(el, TAG_VAR_ATTR_REGEX)
                    )
            );
        } else {
            variable = new Variable(name);
        }
        return variable;
    }

    /**
     * Parse template fo generate text.
     *
     * @param node template node
     * @return list of template elements
     */
    private Template parseTemplate(Node node) {
        List<TemplateElement> elements = new LinkedList<TemplateElement>();
        NodeList templateList = node.getChildNodes();
        for (int k = 0; k < templateList.getLength(); k++) {
            Node el = templateList.item(k);
            if (el.getNodeType() == Node.ELEMENT_NODE && el.getLocalName().equals(TAG_VAR)) {
                elements.add(new NamedValue(nodeAttribute(el, TAG_VAR_ATTR_NAME, DEFAULT_VARIABLE_NAME)));
            } else {
                elements.add(new Constant(el.getNodeValue()));
            }
        }
        return new Template(elements);
    }

    /**
     * Return node attribute value, if exists or default attibute value
     *
     * @param node          XML-node
     * @param attributeName attributeName
     * @param defaultValue  attribute default value
     * @return attribute value or default value
     */
    private boolean nodeAttribute(Node node, String attributeName, boolean defaultValue) {
        boolean value = defaultValue;
        if (node.hasAttributes()) {
            Node attribute = node.getAttributes().getNamedItem(attributeName);
            if (attribute != null) {
                value = Boolean.valueOf(attribute.getNodeValue());
            }
        }
        return value;
    }

    /**
     * Return node attribute value, if exists or default attibute value
     *
     * @param node          XML-node
     * @param attributeName attributeName
     * @param defaultValue  attribute default value
     * @return attribute value or default value
     */
    private String nodeAttribute(Node node, String attributeName, String defaultValue) {
        String value = defaultValue;
        if (node.hasAttributes()) {
            Node attribute = node.getAttributes().getNamedItem(attributeName);
            if (attribute != null) {
                value = attribute.getNodeValue();
            }
        }
        return value;
    }

    /**
     * Return node attribute value, if exists or null value
     *
     * @param node          XML-node
     * @param attributeName attributeName
     * @return attribute value or default value
     */
    private String nodeAttribute(Node node, String attributeName) {
        String value = null;
        if (node.hasAttributes()) {
            Node attribute = node.getAttributes().getNamedItem(attributeName);
            if (attribute != null) {
                value = attribute.getNodeValue();
            }
        }
        return value;
    }

    /**
     * Return node attribute value, if exists or default attibute value
     *
     * @param node          XML-node
     * @param attributeName attributeName
     * @param defaultValue  attribute default value
     * @return attribute value or default value
     */
    private int nodeAttribute(Node node, String attributeName, int defaultValue) {
        int value = defaultValue;
        if (node.hasAttributes()) {
            Node attribute = node.getAttributes().getNamedItem(attributeName);
            if (attribute != null) {
                value = Integer.decode(attribute.getNodeValue());
            }
        }
        return value;
    }

    /**
     * Check node attribute.
     *
     * @param node          XML-node
     * @param attributeName name of attribute
     * @return true if node has attribute with specified name
     *         false if has not
     */
    private boolean nodeHasAttribute(Node node, String attributeName) {
        return node.hasAttributes() && node.getAttributes().getNamedItem(attributeName) != null;
    }
}
