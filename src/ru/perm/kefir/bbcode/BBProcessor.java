package ru.perm.kefir.bbcode;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

/**
 * The bbcode processor. You can use the standard code set or define other.
 *
 * @author Kefir
 */
public final class BBProcessor extends TextProcessorAdapter {
    /**
     * BB-codes
     */
    private WScope scope = null;
    private WTemplate prefix = null;
    private WTemplate suffix = null;
    private Map<String, Object> params = null;

    /**
     * Create the bbcode processor
     */
    public BBProcessor() {
    }

    /**
     * Process bbcodes <br/>
     * 1. Escape the xml special symbols<br/>
     * 2. replace bbcodes to HTML-tags<br/>
     * 3. replace symbols \r\n to HTML-tag "&lt;br/&gt;"<br/>
     *
     * @param source the source string
     * @return result string
     * @see TextProcessor#process(CharSequence)
     */
    public CharSequence process(CharSequence source) {
        Context context = new Context();
        StringBuilder target = new StringBuilder();
        context.setTarget(target);
        context.setSource(new Source(source));
        context.setScope(scope);
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                context.setAttribute(entry.getKey(), entry.getValue());
            }
        }

        try {
            prefix.generate(context);
            context.parse();
            suffix.generate(context);
        } catch (IOException e) {
            // Never because StringBuilder not throw IOException
        }

        return target;
    }

    /**
     * Set the root scope of text processor.
     *
     * @param scope root code scope
     * @throws IllegalStateException if scope already setted
     */
    public void setScope(WScope scope) throws IllegalStateException {
        if (this.scope == null) {
            this.scope = scope;
        } else {
            throw new IllegalStateException("Can't change the root scope.");
        }
    }

    /**
     * Set the prefix for text processor
     *
     * @param prefix template wich uses to create prefix
     * @throws IllegalStateException If prefix already setted
     */
    public void setPrefix(WTemplate prefix) throws IllegalStateException {
        if (this.prefix == null) {
            this.prefix = prefix;
        } else {
            throw new IllegalStateException("Can't change the prefix.");
        }
    }

    /**
     * Set the suffix for text processor
     *
     * @param suffix template wich uses to create prefix
     * @throws IllegalStateException If suffix already setted
     */
    public void setSuffix(WTemplate suffix) {
        if (this.suffix == null) {
            this.suffix = suffix;
        } else {
            throw new IllegalStateException("Can't change the suffix.");
        }
    }

    /**
     * Set text processor parameters map.
     *
     * @param params parameters
     */
    public void setParams(Map<String, Object> params) {
        if (this.params == null) {
            this.params = Collections.unmodifiableMap(params);
        } else {
            throw new IllegalStateException("Can't change parameters.");
        }
    }
}
