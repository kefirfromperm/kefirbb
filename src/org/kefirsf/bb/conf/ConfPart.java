package org.kefirsf.bb.conf;

import org.kefirsf.bb.util.ExceptionUtils;

/**
 * The base class for all configuration classes.
 *
 * @author kefir
 */
abstract class ConfPart {
    protected Configuration configuration;

    protected ConfPart() {
    }

    public void setConfiguration(Configuration configuration) {
        if (configuration == null) {
            throw ExceptionUtils.nullArgument("configuration");
        }

        if (this.configuration == null) {
            configuration.assertWriteLock();
            this.configuration = configuration;
        } else if (!this.configuration.equals(configuration)) {
            throw new IllegalStateException("The configuration already is set.");
        }
    }

    protected void assertReadLock() {
        if (configuration != null) {
            configuration.assertReadLock();
        }
    }

    protected void assertWriteLock() {
        if (configuration != null) {
            configuration.assertWriteLock();
        }
    }
}
