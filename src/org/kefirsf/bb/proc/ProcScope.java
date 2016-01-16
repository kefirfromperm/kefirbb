package org.kefirsf.bb.proc;

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
public class ProcScope {

    /**
     * Name of scope
     */
    private final String name;

    /**
     * Parent scope for inherit codes
     */
    private ProcScope parent = null;

    /**
     * Code set for current scope without parent scope codes
     */
    private Set<ProcCode> scopeCodes = null;

    /**
     * If it is true then only codes of this scope are permitted.
     * When the parser meets a text or non scope code the parser stops parsing.
     */
    private boolean strong = false;

    /**
     * Mark that not parseable text must not append to result
     */
    private boolean ignoreText = false;

    /**
     * Code of scope include the parent codes
     */
    private ProcCode[] cachedCodes = null;

    /**
     * Mark that this scope is initialized
     */
    private boolean initializationStarted = false;
    private boolean initialized = false;

    /**
     * This scope has codes with pattern which starts from non constant pattern elements.
     */
    private boolean hasCrazyCode = false;

    /**
     * This scope has codes with variable action check.
     */
    private boolean hasCheck = false;

    /**
     * The minimal count vocdes in the text.
     */
    private int min = -1;

    /**
     * Maximum codes to parse
     */
    private int max = -1;

    /**
     * Create scope
     *
     * @param name name of scope
     */
    public ProcScope(String name) {
        this.name = name;
    }

    /**
     * Парсит тект с BB-кодами
     *
     * @param context the parsing context
     * @return true if parsing is success. False otherwise, If count of codes in text is not enough, for example.
     * @throws NestingException if nesting is too big.
     */
    public boolean process(Context context) throws NestingException {
        Source source = context.getSource();

        int count = 0;
        while (source.hasNext() && (strong || context.hasNextAdjustedForTerminator()) && (max < 0 || count < max)) {
            int offset = source.getOffset();
            boolean parsed = false;

            if ((source.nextMayBeConstant() || hasCrazyCode) && !context.checkBadTag(offset)) {
                boolean suspicious = false;

                for (ProcCode code : cachedCodes) {
                    if (code.suspicious(context)) {
                        suspicious = true;
                        if (code.process(context)) {
                            parsed = true;
                            break;
                        }
                    }
                }

                if (suspicious && !parsed && !hasCheck) {
                    context.addBadTag(offset);
                }
            }

            if (!parsed) {
                if (strong) {
                    // If scope is strong and has not a code from scope then stop the scope processing
                    break;
                } else if (ignoreText) {
                    source.incOffset();
                } else {
                    try {
                        context.getTarget().append(source.next());
                    } catch (IOException e) {
                        // Nothing! Because StringBuilder doesn't catch IOException
                    }
                }
            } else {
                count++;
            }
        }

        return min < 0 || count >= min;
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
    public void setParent(ProcScope parent) {
        this.parent = parent;
    }

    /**
     * Add codes to scope
     *
     * @param codes code set
     */
    public void setScopeCodes(Set<ProcCode> codes) {
        this.scopeCodes = codes;
    }

    public void init() {
        if (initializationStarted && !initialized) {
            throw new TextProcessorFactoryException("Can't init scope.");
        } else {
            initializationStarted = true;
        }

        if (parent != null && !parent.isInitialized()) {
            parent.init();
        }
        cacheCodes();
        initialized = true;
    }

    /**
     * Return all scope codes include parent codes.
     *
     * @return list of codes in priority order.
     */
    private ProcCode[] getCodes() {
        if (!initialized) {
            throw new IllegalStateException("Scope is not initialized.");
        }
        return cachedCodes;
    }

    /**
     * Cache scope codes. Join scope codes with parent scope codes.
     */
    private void cacheCodes() {
        Set<ProcCode> set = new HashSet<ProcCode>();

        if (parent != null) {
            set.addAll(Arrays.asList(parent.getCodes()));
        }

        if (scopeCodes != null) {
            set.addAll(scopeCodes);
        }

        cachedCodes = set.toArray(new ProcCode[set.size()]);
        Arrays.sort(
                cachedCodes,
                new Comparator<ProcCode>() {
                    public int compare(ProcCode code1, ProcCode code2) {
                        return code2.compareTo(code1);
                    }
                }
        );

        for (ProcCode code : cachedCodes) {
            hasCrazyCode = hasCrazyCode || !code.startsWithConstant();
            hasCheck = hasCheck || code.containsCheck();
        }
    }

    public boolean isStrong() {
        return strong;
    }

    public void setStrong(boolean strong) {
        this.strong = strong;
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

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
