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

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }
}
