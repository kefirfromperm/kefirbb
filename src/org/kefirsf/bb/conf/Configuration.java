package org.kefirsf.bb.conf;

import org.kefirsf.bb.util.Exceptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Configuration of bbcode processor.
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public class Configuration {
    /**
     * Default nesting limit for processor. How many codes can be nested.
     */
    public static final int DEFAULT_NESTING_LIMIT = 500;

    /**
     * By default the processor don't throw an exception if nesting limit exceeded.
     * But the user can override this behavior.
     */
    public static final boolean DEFAULT_PROPAGATE_NESTING_EXCEPTION = false;

    /**
     * Configuration root scope. Normally this scope has name ROOT.
     */
    private Scope rootScope = null;

    /**
     * Prefix will be set before processed text.
     */
    private Template prefix = new Template();

    /**
     * Suffix will be set before processed text.
     */
    private Template suffix = new Template();

    /**
     * Predefined parameters.
     */
    private Map<String, Object> params = new HashMap<String, Object>();

    /**
     * Nesting limit. How many codes can be nested.
     */
    private int nestingLimit = DEFAULT_NESTING_LIMIT;

    /**
     * By default the processor don't throw an exception if nesting limit exceeded.
     * But the user can override this behavior if change this property.
     */
    private boolean propagateNestingException = DEFAULT_PROPAGATE_NESTING_EXCEPTION;

    /**
     * Create the configuration
     */
    public Configuration() {
    }

    /**
     * Get root scope
     *
     * @return root scope
     */
    public Scope getRootScope() {
        return rootScope;
    }

    /**
     * Set root scope for configuration.
     *
     * @param rootScope scope
     */
    public void setRootScope(Scope rootScope) {
        this.rootScope = rootScope;
    }

    /**
     * Get prefix.
     *
     * @return template for prefix
     */
    public Template getPrefix() {
        return prefix;
    }

    /**
     * Set prefix template. Prefix append to start of processed text.
     *
     * @param prefix template for prefix
     */
    public void setPrefix(Template prefix) {
        Exceptions.nullArgument("prefix", prefix);
        this.prefix = prefix;
    }

    /**
     * Get suffix.
     *
     * @return template for suffix
     */
    public Template getSuffix() {
        return suffix;
    }

    /**
     * Set suffix template. Suffix append to end of processed text.
     *
     * @param suffix template for suffix
     */
    public void setSuffix(Template suffix) {
        Exceptions.nullArgument("suffix", suffix);

        this.suffix = suffix;
    }

    /**
     * Get predefined parameters. They can be used in var tags.
     *
     * @return map of parameters
     */
    public Map<String, Object> getParams() {
        return params;
    }

    /**
     * Set parameters to configuration.
     *
     * @param params the map with parameters. Key is a parameter name.
     */
    public void setParams(Map<String, Object> params) {
        this.params = Collections.unmodifiableMap(params);
    }

    /**
     * Get nesting limit. How many codes can be nested.
     *
     * @return nesting limit
     */
    public int getNestingLimit() {
        return nestingLimit;
    }

    /**
     * Set nesting limit. How many codes can be nested.
     *
     * @param nestingLimit nesting limit
     */
    public void setNestingLimit(int nestingLimit) {
        this.nestingLimit = nestingLimit;
    }

    /**
     * Get nesting exceeded behavior.
     *
     * @return true if the processor will throw an exception when nesting limit is exceeded.
     *         false if the processor will not throw an exception and return blank text.
     */
    public boolean isPropagateNestingException() {
        return propagateNestingException;
    }

    /**
     * Define nesting exceeded behavior.
     *
     * @param propagateNestingException throw or not exception
     */
    public void setPropagateNestingException(boolean propagateNestingException) {
        this.propagateNestingException = propagateNestingException;
    }
}
