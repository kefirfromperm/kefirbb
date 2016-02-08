package org.kefirsf.bb.proc;

import java.util.List;

/**
 * @author kefir
 */
public class IfExpression extends AbstractTemplate implements ProcTemplateElement {
    /**
     * The name of checked variable
     */
    private final String name;

    public IfExpression(String name, List<? extends ProcTemplateElement> elements) {
        super(elements);
        this.name = name;
    }

    public CharSequence generate(Context context) {
        if (context.getLocalAttribute(name) != null) {
            StringBuilder b = new StringBuilder();
            for (ProcTemplateElement element : elements) {
                b.append(element.generate(context));
            }
            return b;
        } else {
            return "";
        }
    }
}
