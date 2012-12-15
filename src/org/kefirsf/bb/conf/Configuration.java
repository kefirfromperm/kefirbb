package org.kefirsf.bb.conf;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Configuration of bbcode processor.
 * It's thread safe class.
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public class Configuration {
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private Map<String, Scope> scopes = null;
    private Template prefix = Template.EMPTY;
    private Template suffix = Template.EMPTY;
    private final Map<String, Object> params = new HashMap<String, Object>();

    /**
     * Create the configuration
     */
    public Configuration() {
    }

    public void readLock(){
        lock.readLock().lock();
    }

    public void readUnlock(){
        lock.readLock().unlock();
    }

    void assertReadLock(){
         if(!lock.isWriteLockedByCurrentThread() && lock.getReadHoldCount()<=0){
             throw new IllegalStateException("Configuration is not locked for read actions.");
         }
    }

    public void writeLock(){
        lock.writeLock().lock();
    }

    public void writeUnlock(){
        lock.writeLock().unlock();
    }

    void assertWriteLock(){
        if(!lock.isWriteLockedByCurrentThread()){
            throw new IllegalStateException("Configuration is not locked for write actions.");
        }
    }

    public Set<Scope> getScopes() {
        assertReadLock();
        return new HashSet<Scope>(scopes.values());
    }

    /**
     * Set root scope for text processor.
     *
     * @param scopes scopes
     */
    public void setScopes(Iterable<Scope> scopes) {
        assertWriteLock();
        this.scopes = new HashMap<String, Scope>();
        for (Scope scope : scopes) {
            this.scopes.put(scope.getName(), scope);
        }
    }

    public Scope getScope(String name) {
        assertReadLock();
        return scopes.get(name);
    }

    /**
     * Get root scope
     *
     * @return root scope
     */
    public Scope getRootScope() {
        assertReadLock();
        return scopes.get(Scope.ROOT);
    }

    public Template getPrefix() {
        assertReadLock();
        return prefix;
    }

    /**
     * Set prefix template. Prefix append to start of processed text.
     *
     * @param prefix template for prefix
     */
    public void setPrefix(Template prefix) {
        assertWriteLock();
        if (prefix != null) {
            this.prefix = prefix;
        } else {
            this.prefix = Template.EMPTY;
        }
    }

    public Template getSuffix() {
        assertReadLock();
        return suffix;
    }

    /**
     * Set suffix template. Suffix append to end of processed text.
     *
     * @param suffix template for suffix
     */
    public void setSuffix(Template suffix) {
        assertWriteLock();
        if (suffix != null) {
            this.suffix = suffix;
        } else {
            this.suffix = Template.EMPTY;
        }
    }

    public Map<String, Object> getParams() {
        return params;
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
        assertWriteLock();
        addParam(name, value);
    }

    /**
     * Add param with name <code>name</code> and value <code>value</code> to root context.
     *
     * @param name  name of context parameter
     * @param value value of context parameter
     */
    public void addParam(String name, Object value) {
        assertWriteLock();
        params.put(name, value);
    }

    /**
     * Add param from map to root context.
     *
     * @param params Map contained params
     */
    public void addParams(Map<String, ?> params) {
        assertWriteLock();
        this.params.putAll(params);
    }

    /**
     * Add param from properties to root context.
     *
     * @param properties Properties object
     */
    public void addParams(Properties properties) {
        assertWriteLock();
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
        assertWriteLock();
        this.params.remove(name);
    }

    /**
     * Remove all parameters from context.
     */
    public void clearParams() {
        assertWriteLock();
        this.params.clear();
    }
}
