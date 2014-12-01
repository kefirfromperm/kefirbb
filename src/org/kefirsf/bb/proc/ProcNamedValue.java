package org.kefirsf.bb.proc;

import org.kefirsf.bb.conf.Function;

/**
 * Named value to build target text
 */
public class ProcNamedValue extends ProcNamedElement implements ProcTemplateElement {
    private Function function = Function.value;

    public ProcNamedValue(String name, Function function) {
        super(name);
        this.function = function;
    }

    /**
     * Добавляет элемент в новую строку
     *
     * @param context контекст
     */
    public CharSequence generate(Context context) {
        Object attribute = context.getAttribute(getName());
        if (attribute == null) {
            return "null";
        } else {
            CharSequence seq;
            if (attribute instanceof CharSequence) {
                seq = (CharSequence) attribute;
            } else {
                seq = attribute.toString();
            }

            if (function == Function.value) {
                return seq;
            } else {
                return String.valueOf(seq.length());
            }
        }
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }
}
