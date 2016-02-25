package org.kefirsf.bb;

import org.kefirsf.bb.conf.Constant;
import org.kefirsf.bb.conf.If;
import org.kefirsf.bb.conf.NamedValue;
import org.kefirsf.bb.conf.TemplateElement;
import org.kefirsf.bb.proc.IfExpression;
import org.kefirsf.bb.proc.ProcNamedValue;
import org.kefirsf.bb.proc.ProcTemplateElement;
import org.kefirsf.bb.proc.TemplateConstant;

import java.util.ArrayList;
import java.util.List;

class TemplateElementFactory {
    public TemplateElementFactory() {
    }

    List<ProcTemplateElement> createTemplateList(List<? extends TemplateElement> templateElements) {
        List<ProcTemplateElement> elements = new ArrayList<ProcTemplateElement>();
        for (TemplateElement element : templateElements) {
            elements.add(create(element));
        }
        return elements;
    }

    private ProcTemplateElement create(TemplateElement element) {
        if (element instanceof Constant) {
            return new TemplateConstant(((Constant) element).getValue());
        } else if (element instanceof NamedValue) {
            NamedValue el = (NamedValue) element;
            return new ProcNamedValue(el.getName(), el.getFunction());
        } else if (element instanceof If) {
            return createIf((If) element);
        } else {
            throw new TextProcessorFactoryException("Unknown template element " + element.getClass().getName() + ".");
        }
    }

    private ProcTemplateElement createIf(If element) {
        return new IfExpression(element.getName(), createTemplateList(element.getElements()));
    }
}