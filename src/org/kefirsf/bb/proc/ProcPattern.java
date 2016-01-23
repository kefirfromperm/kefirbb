package org.kefirsf.bb.proc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * Represents the pattern
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public class ProcPattern {
    private final Logger log = LoggerFactory.getLogger(BBProcessor.LOGGER_PARSE);

    /**
     * Pattern elements
     */
    private final List<? extends ProcPatternElement> elements;

    // Performance optimization
    private final ProcPatternElement firstElement;
    private final int patternSize;

    /**
     * Construct pattern.
     *
     * @param elements pattern elements
     */
    public ProcPattern(List<? extends ProcPatternElement> elements) {
        this.elements = Collections.unmodifiableList(elements);
        this.patternSize = elements.size();

        // Performance optimization
        if (!this.elements.isEmpty()) {
            firstElement = this.elements.get(0);
        } else {
            throw new IllegalArgumentException("Parameter \"elements\" can't be empty.");
        }
    }

    /**
     * Указывает на то что следующая последовательность вполне может оказаться данным тэгом
     *
     * @param context current context
     * @return true - если следующие несколько символов совпадают с первой константой в коде
     *          false - означает, что это точно не тот код
     */
    public boolean suspicious(Context context) {
        return firstElement.isNextIn(context);
    }

    /**
     * Parse context with this pattern
     *
     * @param context current context
     * @return true if next subsequence is valid to this pattern,
     *          false others
     * @throws NestingException if nesting is too big.
     */
    public boolean parse(Context context) throws NestingException {
        boolean flag = true;
        Source source = context.getSource();
        int offset = source.getOffset();

        // Log start parsing
        if (log.isTraceEnabled()) {
            log.trace("[{}] Begin {}", offset, this);
        }

        int i;
        for (i = 0; i < patternSize && flag; i++) {
            ProcPatternElement current = elements.get(i);
            ProcPatternElement next;
            if (i < patternSize - 1) {
                next = elements.get(i + 1);
            } else {
                next = context.getTerminator();
            }
            flag = current.parse(context, next);
        }

        if (!flag) {
            if (log.isTraceEnabled()) {
                log.trace("[{}] Rollback {} on {} element", source.getOffset(), this, i);
            }

            source.setOffset(offset);
        } else {
            if (log.isTraceEnabled()) {
                log.trace("[{}] Complete {}", source.getOffset(), this);
            }
        }

        return flag;
    }

    public boolean startsWithConstant() {
        return
                firstElement instanceof PatternConstant ||
                        firstElement instanceof ProcEol;
    }

    public boolean hasCheck() {
        for (ProcPatternElement element : elements) {
            if (element instanceof Check) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();

        b.append("<pattern>");

        for(ProcPatternElement element:elements){
            b.append(element);
        }

        b.append("</pattern>");

        return b.toString();
    }
}
