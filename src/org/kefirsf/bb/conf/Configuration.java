package org.kefirsf.bb.conf;

import org.kefirsf.bb.util.ExceptionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Configuration of bbcode processor.
 * It's thread safe class.
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public final class Configuration {

    private Map<String, Scope> scopes = null;
    private Template prefix = new Template();
    private Template suffix = new Template();
    private final Map<String, Object> params = new HashMap<String, Object>();

    /**
     * Create the configuration
     */
    public Configuration() {
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
     * Get scope by name.
     *
     * @param name the scope name
     * @return the scope
     */
    public Scope getScope(String name) {
        return scopes.get(name);
    }

    /**
     * Get root scope
     *
     * @return root scope
     */
    public Scope getRootScope() {
        return getScope(Scope.ROOT);
    }

    public Template getPrefix() {
        return prefix;
    }

    /**
     * Set prefix template. Prefix append to start of processed text.
     *
     * @param prefix template for prefix
     */
    public void setPrefix(Template prefix) {
        if (prefix == null) {
            throw ExceptionUtils.nullArgument("prefix");
        }
        this.prefix = prefix;
    }

    public Template getSuffix() {
        return suffix;
    }

    /**
     * Set suffix template. Suffix append to end of processed text.
     *
     * @param suffix template for suffix
     */
    public void setSuffix(Template suffix) {
        if (suffix == null) {
            throw ExceptionUtils.nullArgument("suffix");
        }

        this.suffix = suffix;
    }

    public Map<String, Object> getParams() {
        return Collections.unmodifiableMap(params);
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
}
