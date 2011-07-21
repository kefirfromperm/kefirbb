package ru.perm.kefir.bbcode;

import java.util.*;

/**
 * bb-code scope. Required for tables, for example.
 * Scope contains code set for parsing text.
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public class WScope {

    /**
     * Name of scope
     */
    private final String name;

    /**
     * Parent scope for inherit codes
     */
    private WScope parent = null;

    /**
     * Code set for current scope without parent scope codes
     */
    private Set<AbstractCode> scopeCodes = null;

    /**
     * Mark that not parseable text must not append to result
     */
    private boolean ignoreText = false;

    /**
     * Code of scope include the parent codes
     */
    private List<AbstractCode> cachedCodes = null;

    /**
     * Mark that this scope is initialized
     */
    private boolean initialized = false;

    /**
     * Create scope
     *
     * @param name name of scope
     */
    public WScope(String name) {
        this.name = name;
    }

    /**
     * Get scope name
     *
     * @return scope name
     */
    public String getName() {
        return name;
    }

    /**
     * Set parent scope
     *
     * @param parent parent scope. All parent scope code added to scope codes.
     */
    public void setParent(WScope parent) {
        this.parent = parent;
    }

    /**
     * Add codes to scope
     *
     * @param codes code set
     */
    public void setScopeCodes(Set<AbstractCode> codes) {
        this.scopeCodes = codes;
    }

    public void init() {
        if (parent != null && !parent.isInitialized()) {
            throw new TextProcessorFactoryException("Can't init scope.");
        }
        cacheCodes();
        initialized = true;
    }

    /**
     * Return all scope codes include parent codes.
     *
     * @return list of codes in priority order.
     */
    public List<AbstractCode> getCodes() {
        if (!initialized) {
            throw new IllegalStateException("Scope is not initialized.");
        }
        return cachedCodes;
    }

    /**
     * Cache scope codes. Join scope codes with parent scope codes.
     */
    private void cacheCodes() {
        List<AbstractCode> list = new ArrayList<AbstractCode>();
        if (parent != null) {
            list.addAll(parent.getCodes());
        }
        if (scopeCodes != null) {
            list.addAll(scopeCodes);
        }
        Collections.sort(list,
                new Comparator<AbstractCode>() {
                    public int compare(AbstractCode code1, AbstractCode code2) {
                        return code2.compareTo(code1);
                    }
                }
        );
        cachedCodes = Collections.unmodifiableList(list);
    }

    /**
     * By default it is false.
     *
     * @return true if not parsiable text mustn't append to result.
     *         false if not parsiable text append to result as is.
     */
    public boolean isIgnoreText() {
        return ignoreText;
    }

    /**
     * Set flag marked that not parsiable text mustn't append to result. By default it is false.
     *
     * @param ignoreText flag value
     */
    public void setIgnoreText(boolean ignoreText) {
        this.ignoreText = ignoreText;
    }

    /**
     * @return true if scope was initialised, false otherwise
     */
    public boolean isInitialized() {
        return initialized;
    }
}
