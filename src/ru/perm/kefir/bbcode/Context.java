package ru.perm.kefir.bbcode;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The bb-processing context
 *
 * @author Kefir
 */
public class Context {
    /**
     * Parent context
     */
    private final Context parent;

    /**
     * source text
     */
    private Source source;

    /**
     * Target builder
     */
    private Appendable target = null;

    /**
     * Text terminator,this mark stop text processing
     */
    private WPatternElement terminator = null;

    /**
     * Code scope
     */
    private WScope scope;

    /**
     * Codes array for performance
     */
    private AbstractCode[] codes = new AbstractCode[0];

    /**
     * Context attributes
     */
    private final Map<String, Object> attributes = new HashMap<String, Object>();

    /**
     * тэги с ошибками. т.е. позиции в которых тэги с ошибками
     */
    private final Map<WScope, IntSet> falseMemo;
    private IntSet scopeFalseMemo;

    /**
     * Default constructor
     */
    public Context() {
        parent = null;
        falseMemo = new HashMap<WScope, IntSet>();
    }

    /**
     * Constructor of child-context
     *
     * @param parent parent context
     */
    public Context(Context parent) {
        this.parent = parent;
        this.source = parent.source;
        this.target = parent.target;
        this.falseMemo = parent.falseMemo;
        this.terminator = parent.terminator;

        this.setScope(parent.scope);
    }

    /**
     * Парсит тект с BB-кодами
     *
     * @throws java.io.IOException if can't append chars to target
     */
    public void parse() throws IOException {
        while (hasNextAdjustedForTerminator()) {
            if (!process()) {
                if (scope.isIgnoreText()) {
                    source.incOffset();
                } else {
                    getTarget().append(source.next());
                }
            }
        }
    }

    /**
     * Обрабатывает BB-коды
     *
     * @return true если найден BB-код
     * @throws java.io.IOException if can't append to target
     */
    private boolean process() throws IOException {
        int offset = source.getOffset();
        if (checkBadTag(offset)) {
            return false;
        }

        boolean suspicious = false;
        boolean parsed = false;
        for (AbstractCode code : codes) {
            if (code.suspicious(source)) {
                suspicious = true;
                if (code.process(this)) {
                    parsed = true;
                    break;
                }
            }
        }

        if (suspicious && !parsed) {
            addBadTag(offset);
        }

        return parsed;
    }

    /**
     * Add the bag tag position
     *
     * @param offset offset of bad tag in source
     */
    private void addBadTag(int offset) {
        scopeFalseMemo.add(offset);
    }

    /**
     * Check the bag tag
     *
     * @param offset offset of tag
     * @return true if at this ofsset tag is bad
     */
    private boolean checkBadTag(int offset) {
        return scopeFalseMemo.contains(offset);
    }

    /**
     * Check has chars in source before terminator or not
     *
     * @return true if chars exists
     *         false if chars canceled
     */
    private boolean hasNextAdjustedForTerminator() {
        return source.hasNext() && (terminator == null || !terminator.isNextIn(source));
    }

    /**
     * Put all local attributes to parent context
     */
    public void mergeWithParent() {
        parent.attributes.putAll(this.attributes);
    }

    /**
     * Add or set context attribute
     *
     * @param name  attribute name
     * @param value attribute value
     */
    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    /**
     * Get the context attribute. If attribute not exists in current context,
     * then context search the sttribute in parent context
     *
     * @param name attribute name
     * @return attribute value
     */
    public Object getAttribute(String name) {
        Object value = getLocalAttribute(name);
        if (value == null && parent != null) {
            value = parent.getAttribute(name);
        }
        return value;
    }

    /**
     * Return attribute from this context, not parent
     *
     * @param name attribute name
     * @return attribute value
     */
    public Object getLocalAttribute(String name) {
        return attributes.get(name);
    }

    /**
     * Set list of codes in current context
     *
     * @param scope code scope
     */
    public void setScope(WScope scope) {
        this.scope = scope;

        // codes
        List<AbstractCode> codeList = scope.getCodes();
        codes = codeList.toArray(new AbstractCode[codeList.size()]);

        // Scope false memo
        scopeFalseMemo = falseMemo.get(scope);
        if (scopeFalseMemo == null) {
            scopeFalseMemo = new IntSet();
            falseMemo.put(scope, scopeFalseMemo);
        }
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Appendable getTarget() {
        return target;
    }

    public void setTarget(Appendable target) {
        this.target = target;
    }

    /**
     * get Text terminator,this mark stop text processing
     *
     * @return terminator
     */
    public WPatternElement getTerminator() {
        return terminator;
    }

    /**
     * @param terminator Text terminator,this mark stop text processing
     */
    public void setTerminator(WPatternElement terminator) {
        this.terminator = terminator;
    }
}
