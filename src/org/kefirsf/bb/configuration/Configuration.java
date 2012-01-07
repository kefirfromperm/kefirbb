package org.kefirsf.bb.configuration;

import org.kefirsf.bb.*;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Configuration of bbcode processor.
 * It's thread safe class.
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public class Configuration implements TextProcessorFactory {
    private Map<String, Scope> scopes = null;
    private Template prefix = Template.EMPTY;
    private Template suffix = Template.EMPTY;
    private final Map<String, Object> params = new HashMap<String, Object>();

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * Create the configuration
     */
    public Configuration() {
    }

    /**
     * Create text processor.
     *
     * @return text processor
     * @throws IllegalStateException if scope not setted
     */
    public TextProcessor create() throws IllegalStateException {
        BBProcessor processor;
        lock.readLock().lock();
        try {
            if (scopes == null || scopes.isEmpty() || !scopes.containsKey(Scope.ROOT)) {
                throw new IllegalStateException("Can't find root scope.");
            }

            Map<Scope, WScope> createdScopes = new HashMap<Scope, WScope>();
            Map<Code, AbstractCode> codes = new HashMap<Code, AbstractCode>();

            processor = new BBProcessor();
            processor.setScope(scopes.get(Scope.ROOT).create(this, createdScopes, codes));
            processor.setPrefix(prefix.create());
            processor.setSuffix(suffix.create());
            processor.setParams(params);
        } finally {
            lock.readLock().unlock();
        }
        return processor;
    }

    /**
     * Lock configuration for change
     */
    public void lock() {
        lock.writeLock().lock();
    }

    /**
     * unlock configuration
     */
    public void unlock() {
        lock.writeLock().unlock();
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
        assertLock();

        this.scopes = new HashMap<String, Scope>();
        for (Scope scope : scopes) {
            this.scopes.put(scope.getName(), scope);
        }
    }

    /**
     * Test if configuration was locked for changes.
     *
     * @throws IllegalStateException if configuration not locked.
     */
    private void assertLock() throws IllegalStateException {
        if (!lock.isWriteLockedByCurrentThread()) {
            throw new IllegalStateException("Configuration must be locked for change.");
        }
    }

    /**
     * Set prefix template. Prefix append to start of processed text.
     *
     * @param prefix template for prefix
     */
    public void setPrefix(Template prefix) {
        assertLock();
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
        assertLock();
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
        assertLock();
        params.put(name, value);
    }

    /**
     * Add param from map to root context.
     *
     * @param params Map contained params
     */
    public void addParams(Map<String, ?> params) {
        assertLock();
        this.params.putAll(params);
    }

    /**
     * Add param from properties to root context.
     *
     * @param properties Properties object
     */
    public void addParams(Properties properties) {
        assertLock();
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
        assertLock();
        this.params.remove(name);
    }

    /**
     * Remove all parameters from context.
     */
    public void clearParams() {
        assertLock();
        this.params.clear();
    }
}
