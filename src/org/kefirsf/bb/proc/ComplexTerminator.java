package org.kefirsf.bb.proc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * This is a complex terminator for situations when code cen be terminate more than one constant.
 */
public class ComplexTerminator implements ProcPatternElement {
    private final Set<ProcPatternElement> elements;

    public ComplexTerminator(Set<ProcPatternElement> elements) {
        this.elements = elements;
    }

    public ComplexTerminator(ProcPatternElement... elements) {
        this.elements = new HashSet<ProcPatternElement>(Arrays.asList(elements));
    }

    public boolean parse(Context context, ProcPatternElement terminator) throws NestingException {
        throw new UnsupportedOperationException("Method parse is not supported in ComplexTerminator.");
    }

    public boolean isNextIn(Source source) {
        for (ProcPatternElement element : elements) {
            if (element.isNextIn(source)) {
                return true;
            }
        }
        return false;
    }

    public int findIn(Source source) {
        int index = -1;
        for (ProcPatternElement element : elements) {
            int elementIndex = element.findIn(source);
            if (elementIndex>=0 && (index < 0 || elementIndex < index)) {
                index = elementIndex;
            }
        }
        return index;
    }
}
