package org.kefirsf.bb.conf;

import org.kefirsf.bb.util.ExceptionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Configuration of bbcode processor.
 * It's thread safe class.
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public final class Configuration {
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final ThreadLocal<Boolean> readLocked = new ThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
            return Boolean.FALSE;
        }
    };

    private Map<String, Scope> scopes = null;
    private Template prefix = new Template();
    private Template suffix = new Template();
    private final Map<String, Object> params = new HashMap<String, Object>();

    /**
     * Create the configuration
     */
    public Configuration() {
        writeLock();
        try {
            prefix.setConfiguration(this);
            suffix.setConfiguration(this);
        } finally {
            writeUnlock();
        }
    }

    /**
     * Lock the configuration for read actions.
     */
    public void readLock() {
        if (isReadLocked()) {
            throw new IllegalStateException("The configuration already is locked for read actions.");
        }

        lock.readLock().lock();
        readLocked.set(Boolean.TRUE);
    }

    /**
     * Unlock the configuration for read actions.
     */
    public void readUnlock() {
        if (!readLocked.get()) {
            throw new IllegalStateException("The configuration is not locked for read actions.");
        }

        readLocked.set(Boolean.FALSE);
        lock.readLock().unlock();
    }

    /**
     * Check configuration lock state.
     *
     * @throws IllegalStateException if the configuration is not locking for read or write actions.
     */
    void assertReadLock() {
        if (!isReadLocked()) {
            throw new IllegalStateException("The configuration is not locked for read or write actions.");
        }
    }

    private boolean isReadLocked() {
        return (lock.isWriteLockedByCurrentThread() || readLocked.get());
    }

    /**
     * Lock the configuration for write actions.
     */
    public void writeLock() {
        if (lock.isWriteLockedByCurrentThread()) {
            throw new IllegalStateException("The configuration already is locked for write actions.");
        }

        lock.writeLock().lock();
    }

    /**
     * Unlock the configuration for write actions.
     */
    public void writeUnlock() {
        assertWriteLock();
        lock.writeLock().unlock();
    }

    /**
     * Check configuration lock state.
     *
     * @throws IllegalStateException if the configuration is not locking for write actions.
     */
    void assertWriteLock() {
        if (!lock.isWriteLockedByCurrentThread()) {
            throw new IllegalStateException("Configuration is not locked for write actions.");
        }
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
            scope.setConfiguration(this);
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
        assertReadLock();
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
        if (prefix == null) {
            throw ExceptionUtils.nullArgument("prefix");
        }

        prefix.setConfiguration(this);
        this.prefix = prefix;
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
        if (suffix == null) {
            throw ExceptionUtils.nullArgument("suffix");
        }

        suffix.setConfiguration(this);
        this.suffix = suffix;
    }

    public Map<String, Object> getParams() {
        assertReadLock();
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
