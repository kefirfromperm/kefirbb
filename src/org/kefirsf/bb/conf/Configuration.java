package org.kefirsf.bb.conf;

import java.util.*;

/**
 * Configuration of bbcode processor.
 * It's thread safe class.
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public class Configuration {
    private Map<String, Scope> scopes = null;
    private Template prefix = Template.EMPTY;
    private Template suffix = Template.EMPTY;
    private final Map<String, Object> params = new HashMap<String, Object>();

    /**
     * Create the configuration
     */
    public Configuration() {
    }

    public Set<Scope> getScopes() {
        return new HashSet<Scope>(scopes.values());
    }

    public Scope getScope(String name) {
        return scopes.get(name);
    }

    /**
     * Get root scope
     *
     * @return root scope
     */
    public Scope getRootScope() {
        return scopes.get(Scope.ROOT);
    }

    /**
     * Set root scope for text processor.
     *
     * @param scopes scopes
     */
    public void setScopes(Iterable<Scope> scopes) {
        this.scopes = new HashMap<String, Scope>();
        for (Scope scope : scopes) {
            this.scopes.put(scope.getName(), scope);
        }
    }

    /**
     * Set prefix template. Prefix append to start of processed text.
     *
     * @param prefix template for prefix
     */
    public void setPrefix(Template prefix) {
        if (prefix != null) {
            this.prefix = prefix;
        } else {
            this.prefix = Template.EMPTY;
        }
    }

    /**
     * Set suffix template. Suffix append to end of processed text.
     *
     * @param suffix template for suffix
     */
    public void setSuffix(Template suffix) {
        if (suffix != null) {
            this.suffix = suffix;
        } else {
            this.suffix = Template.EMPTY;
        }
    }

    /**
     * Add param with name <code>name</code> and value <code>value</code> to root context.
     * Call addParam(String, object)
     *
     * @param name  name of context parameter
     * @param value value of context parameter
     * @see #addParam(String, Object)
     */
    public void setParam(String name, Object value) {
        addParam(name, value);
    }

    /**
     * Add param with name <code>name</code> and value <code>value</code> to root context.
     *
     * @param name  name of context parameter
     * @param value value of context parameter
     */
    public void addParam(String name, Object value) {
        params.put(name, value);
    }

    /**
     * Add param from map to root context.
     *
     * @param params Map contained params
     */
    public void addParams(Map<String, ?> params) {
        this.params.putAll(params);
    }

    /**
     * Add param from properties to root context.
     *
     * @param properties Properties object
     */
    public void addParams(Properties properties) {
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            Object key = entry.getKey();
            if (key != null) {
                this.params.put(key.toString(), entry.getValue());
            }
        }
    }

    /**
     * Remove parameter with name <code>name</code> from context.
     *
     * @param name name of parameter
     */
    public void removeParam(String name) {
        this.params.remove(name);
    }

    /**
     * Remove all parameters from context.
     */
    public void clearParams() {
        this.params.clear();
    }

    public Template getPrefix() {
        return prefix;
    }

    public Template getSuffix() {
        return suffix;
    }

    public Map<String, Object> getParams() {
        return params;
    }
}
