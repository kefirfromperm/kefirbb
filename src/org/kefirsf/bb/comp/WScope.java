package org.kefirsf.bb.comp;

import org.kefirsf.bb.TextProcessorFactoryException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

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
    private Set<WCode> scopeCodes = null;

    /**
     * Mark that not parseable text must not append to result
     */
    private boolean ignoreText = false;

    /**
     * Code of scope include the parent codes
     */
    private WCode[] cachedCodes = null;

    /**
     * Mark that this scope is initialized
     */
    private boolean initialized = false;

    /**
     * This scope has codes with pattern which starts from non constant pattern elements.
     */
    private boolean hasCrazyCode = false;

    /**
     * Create scope
     *
     * @param name name of scope
     */
    public WScope(String name) {
        this.name = name;
    }

    /**
     * Парсит тект с BB-кодами
     *
     * @throws java.io.IOException if can't append chars to target
     */
    public void process(Context context) throws IOException {
        Source source = context.getSource();

        while (context.hasNextAdjustedForTerminator()) {
            int offset = source.getOffset();
            boolean parsed = false;

            if (!context.checkBadTag(offset) && (hasCrazyCode || source.nextCanBeConstant())) {

                boolean suspicious = false;
                for (WCode code : cachedCodes) {
                    if (code.suspicious(source)) {
                        suspicious = true;
                        if (code.process(context)) {
                            parsed = true;
                            break;
                        }
                    }
                }

                if (suspicious && !parsed) {
                    context.addBadTag(offset);
                }
            }

            if (!parsed) {
                if (ignoreText) {
                    source.incOffset();
                } else {
                    context.getTarget().append(source.next());
                }
            }
        }
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
    public void setScopeCodes(Set<WCode> codes) {
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
    private WCode[] getCodes() {
        if (!initialized) {
            throw new IllegalStateException("Scope is not initialized.");
        }
        return cachedCodes;
    }

    /**
     * Cache scope codes. Join scope codes with parent scope codes.
     */
    private void cacheCodes() {
        Set<WCode> set = new HashSet<WCode>();

        if (parent != null) {
            set.addAll(Arrays.asList(parent.getCodes()));
        }

        if (scopeCodes != null) {
            set.addAll(scopeCodes);
        }

        cachedCodes = set.toArray(new WCode[set.size()]);
        Arrays.sort(
                cachedCodes,
                new Comparator<WCode>() {
                    public int compare(WCode code1, WCode code2) {
                        return code2.compareTo(code1);
                    }
                }
        );

        for(WCode code:cachedCodes){
            hasCrazyCode = hasCrazyCode || !code.startsWithConstant();
        }
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
