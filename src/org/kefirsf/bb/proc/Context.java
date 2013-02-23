package org.kefirsf.bb.proc;

import org.kefirsf.bb.util.IntSet;

import java.util.HashMap;
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
    private ProcPatternElement terminator = null;

    /**
     * Code scope
     */
    private ProcScope scope;

    /**
     * Context attributes
     */
    private final Map<String, Object> attributes = new HashMap<String, Object>();

    /**
     * тэги с ошибками. т.е. позиции в которых тэги с ошибками
     */
    private final Map<ProcScope, IntSet> falseMemo;
    private IntSet scopeFalseMemo;

    /**
     * Nesting limit for this context.
     */
    private int nestingLimit;

    /**
     * Default constructor
     */
    public Context() {
        parent = null;
        falseMemo = new HashMap<ProcScope, IntSet>();
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
        this.nestingLimit = parent.nestingLimit-1;

        this.setScope(parent.scope);
    }


    /**
     * Add the bag tag position
     *
     * @param offset offset of bad tag in source
     */
    public void addBadTag(int offset) {
        scopeFalseMemo.add(offset);
    }

    /**
     * Check the bag tag
     *
     * @param offset offset of tag
     * @return true if at this ofsset tag is bad
     */
    public boolean checkBadTag(int offset) {
        return scopeFalseMemo.contains(offset);
    }

    /**
     * Check has chars in source before terminator or not
     *
     * @return true if chars exists
     *         false if chars canceled
     */
    public boolean hasNextAdjustedForTerminator() {
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
    public void setScope(ProcScope scope) {
        this.scope = scope;

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
    public ProcPatternElement getTerminator() {
        return terminator;
    }

    /**
     * @param terminator Text terminator,this mark stop text processing
     */
    public void setTerminator(ProcPatternElement terminator) {
        this.terminator = terminator;
    }

    public ProcScope getScope() {
        return scope;
    }

    /**
     * Set nesting limit for this context.
     *
     * @param nestingLimit nesting limit
     */
    public void setNestingLimit(int nestingLimit) {
        this.nestingLimit = nestingLimit;
    }

    /** Check nesting.
     *
     * @throws NestingException if nesting limit less than 0.
     */
    public void checkNesting() throws NestingException {
        if(nestingLimit<0){
            throw new NestingException();
        }
    }
}
