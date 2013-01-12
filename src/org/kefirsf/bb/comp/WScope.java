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
    private Set<AbstractCode> scopeCodes = null;

    /**
     * Mark that not parseable text must not append to result
     */
    private boolean ignoreText = false;

    /**
     * Code of scope include the parent codes
     */
    private AbstractCode[] cachedCodes = null;

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
     * Парсит тект с BB-кодами
     *
     * @throws java.io.IOException if can't append chars to target
     */
    public void process(Context context) throws IOException {
        Source source = context.getSource();
/*
        int[] codeOffsets = new int[cachedCodes.length];

        for (int i = 0; i < codeOffsets.length; i++) {
            codeOffsets[i] = -1;
        }

        WPatternElement terminator = context.getTerminator();
        boolean tnn = terminator != null;
        int terminatorOffset = -1;
        if (tnn) {
            terminatorOffset = terminator.findSuspicious(source);
        }

        while (source.hasNext() && (!tnn || terminatorOffset>0)) {

            int ci = -1;
            for (int i = 0; i < codeOffsets.length; i++) {
                int offset = cachedCodes[i].findSuspicious(source);
                codeOffsets[i] = offset;
                if (offset >= 0 && (ci < 0 || codeOffsets[ci] > offset)) {
                    ci = i;
                }
            }
        }
*/

        while (context.hasNextAdjustedForTerminator()) {
            int offset = source.getOffset();
            boolean parsed = false;

            if (!context.checkBadTag(offset)) {

                boolean suspicious = false;
                for (AbstractCode code : cachedCodes) {
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
    private AbstractCode[] getCodes() {
        if (!initialized) {
            throw new IllegalStateException("Scope is not initialized.");
        }
        return cachedCodes;
    }

    /**
     * Cache scope codes. Join scope codes with parent scope codes.
     */
    private void cacheCodes() {
        Set<AbstractCode> set = new HashSet<AbstractCode>();

        if (parent != null) {
            set.addAll(Arrays.asList(parent.getCodes()));
        }

        if (scopeCodes != null) {
            set.addAll(scopeCodes);
        }

        cachedCodes = set.toArray(new AbstractCode[set.size()]);
        Arrays.sort(
                cachedCodes,
                new Comparator<AbstractCode>() {
                    public int compare(AbstractCode code1, AbstractCode code2) {
                        return code2.compareTo(code1);
                    }
                }
        );
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
