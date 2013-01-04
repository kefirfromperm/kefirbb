package org.kefirsf.bb.comp;

/**
 * Constant element of pattern or template
 *
 * @author Kefir
 */
public class PatternConstant implements WPatternElement {
    /**
     * Constant value
     */
    private final String value;

    /**
     * Mark ignore case
     */
    private final boolean ignoreCase;

    /**
     * First char of constant. It need for better performance.
     */
    private final char firstChar;

    /**
     * Length of constant value
     */
    private final int valueLength;

    /**
     * Create constant element.
     *
     * @param value constant value
     */
    public PatternConstant(String value) {
        this.value = value;
        this.valueLength = value.length();
        this.firstChar = value.charAt(0);
        this.ignoreCase = false;
    }

    /**
     * Create constant element.
     *
     * @param value constant value
     */
    public PatternConstant(String value, boolean ignoreCase) {
        this.value = value;
        this.valueLength = value.length();
        this.firstChar = value.charAt(0);
        this.ignoreCase = ignoreCase;
    }

    /**
     * Parse constant
     *
     * @param context    current context
     * @param terminator not used
     * @return true - if next sequence in source equals to this constant value,
     *         false - other
     */
    public boolean parse(Context context, WPatternElement terminator) {
        if (isNextIn(context.getSource())) {
            context.getSource().incOffset(valueLength);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check equals next sequence in source to this constant
     *
     * @param source source text
     * @return true if next subsequence is equals
     *         false other
     */
    public boolean isNextIn(Source source) {
        if (!ignoreCase) {
            return firstChar == source.current()
                    && source.hasNext(valueLength)
                    && value.contentEquals(source.subTo(valueLength));
        } else {
            return source.hasNext(valueLength)
                    && value.equalsIgnoreCase(source.subTo(valueLength).toString());
        }
    }

    /**
     * Find this constant.
     *
     * @param source text source
     * @return смещение константы
     */
    public int findIn(Source source) {
        if (!ignoreCase) {
            return source.find(value);
        } else {
            boolean flag = false;
            int offset;
            for (offset = source.getOffset(); !flag && offset < source.getLength() - valueLength; offset++) {
                String str = source.subString(offset, offset + valueLength);
                flag = str.equalsIgnoreCase(value);
            }
            if (flag) {
                return offset;
            } else {
                return -1;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PatternConstant that = (PatternConstant) o;

        if (ignoreCase != that.ignoreCase) return false;
        if (!value.equals(that.value)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = value.hashCode();
        result = 31 * result + (ignoreCase ? 1 : 0);
        return result;
    }

    /**
     * @return string representation of this object.
     */
    @Override
    public String toString() {
        return "constant:" + value;
    }
}
