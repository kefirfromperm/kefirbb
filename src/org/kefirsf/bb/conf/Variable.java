package org.kefirsf.bb.conf;

import java.util.regex.Pattern;

/**
 * Variable pattern element.
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public class Variable extends NamedElement implements PatternElement {
    private java.util.regex.Pattern regex;
    private boolean ghost = false;
    private Action action = Action.rewrite;

    public Variable() {
        super();
        this.regex = null;
    }

    public Variable(String name) {
        super(name);
        this.regex = null;
    }

    public Variable(String name, java.util.regex.Pattern regex) {
        super(name);
        this.regex = regex;
    }

    /**
     * Get regular expression for validate variable.
     *
     * @return Regex pattern.
     */
    public java.util.regex.Pattern getRegex() {
        return regex;
    }

    /**
     * Set a regex pattern for validate variable.
     *
     * @param regex pattern
     */
    public void setRegex(Pattern regex) {
        this.regex = regex;
    }

    /**
     * If it's true then processor parse it but no move the cursor.
     *
     * @return is it ghost or no
     */
    public boolean isGhost() {
        return ghost;
    }

    /**
     * @param ghost If it's true then processor parse it but no move the cursor.
     */
    public void setGhost(boolean ghost) {
        this.ghost = ghost;
    }

    /**
     * Get the action of the variable.
     *
     * rewrite - rewrite current value
     * append - append a string to current value
     * check - check that current value is equals the variable value
     *
     * @return action
     */
    public Action getAction() {
        return action;
    }

    /**
     * Set the action of the variable.
     *
     * rewrite - rewrite current value
     * append - append a string to current value
     * check - check that current value is equals the variable value
     *
     * @param action action
     */
    public void setAction(Action action) {
        this.action = action;
    }
}
