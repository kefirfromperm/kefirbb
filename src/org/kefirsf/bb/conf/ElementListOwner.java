package org.kefirsf.bb.conf;

import java.util.Collections;
import java.util.List;

/**
 * @author kefir
 */
class ElementListOwner<T> {
    protected List<? extends T> elements;

    public ElementListOwner() {
        elements = Collections.<T>emptyList();
    }

    public ElementListOwner(List<? extends T> elements) {
        this.elements = Collections.unmodifiableList(elements);
    }

    /**
      * Get pattern elements
      *
      * @return elements list
      */
     public List<? extends T> getElements() {
         return elements;
     }

    /**
      * Set pattern elements
      *
      * @param elements elements list
      */
     public void setElements(List<? extends T> elements) {
         this.elements = Collections.unmodifiableList(elements);
     }
}
