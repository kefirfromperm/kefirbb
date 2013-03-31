package org.kefirsf.bb.conf;

import java.util.Collections;
import java.util.List;

/**
 * Base abstract class for pattern and template configuration classes.
 *
 * @author kefir
 */
abstract class ElementListOwner<T> {
    private List<? extends T> elements;

    /**
     * The default constructor.
     */
    protected ElementListOwner() {
        elements = Collections.<T>emptyList();
    }

    /**
     * @param elements pattern or template elements.
     */
    protected ElementListOwner(List<? extends T> elements) {
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
