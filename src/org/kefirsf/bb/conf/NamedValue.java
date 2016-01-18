package org.kefirsf.bb.conf;

/**
 * Named template element.
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public class NamedValue extends NamedElement implements TemplateElement {
    private Function function = Function.value;

    public NamedValue() {
        super();
    }

    public NamedValue(String name) {
        super(name);
    }

    public NamedValue(String name, Function function) {
        super(name);
        this.function = function;
    }

    /**
     * This element can use different functions.
     * By default it's value. In this case will be generated a text within the value of variable.
     * Other function is length. In this case will be generated a text within the length of variable value.
     *
     * @return function
     */
    public Function getFunction() {
        return function;
    }

    /**
     * This element can use different functions.
     * By default it's value. In this case will be generated a text within the value of variable.
     * Other function is length. In this case will be generated a text within the length of variable value.
     *
     * @param function one of function
     */
    public void setFunction(Function function) {
        this.function = function;
    }
}
