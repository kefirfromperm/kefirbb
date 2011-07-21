package ru.perm.kefir.bbcode;

/**
 * Constant which ignore case
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public class WConstantIgnoreCase implements WPatternElement {
    /**
     * Constant value
     */
    private final String value;

    /**
     * Length of constant value
     */
    private final int valueLength;

    public WConstantIgnoreCase(String value) {
        this.value = value;
        this.valueLength = value.length();
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
        return source.hasNext(valueLength)
                && value.equalsIgnoreCase(source.subTo(valueLength).toString());
    }

    /**
     * Find this constant.
     *
     * @param source text source
     * @return смещение константы
     */
    public int findIn(Source source) {
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

    /**
     * @return string representation of this object.
     */
    @Override
    public String toString() {
        return "constant ic:" + value;
    }
}
