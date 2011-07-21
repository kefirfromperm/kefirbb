package ru.perm.kefir.bbcode;

/**
 * Constant element of pattern or template
 *
 * @author Kefir
 */
public class WConstant implements WPatternElement, WTemplateElement {
    /**
     * Constant value
     */
    private final String value;

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
    public WConstant(String value) {
        this.value = value;
        this.valueLength = value.length();
        this.firstChar = value.charAt(0);
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
     * Return constant value
     *
     * @param context context. Not used.
     */
    public CharSequence generate(Context context) {
        return value;
    }

    /**
     * Check equals next sequence in source to this constant
     *
     * @param source source text
     * @return true if next subsequence is equals
     *         false other
     */
    public boolean isNextIn(Source source) {
        return firstChar == source.current()
                && source.hasNext(valueLength)
                && value.contentEquals(source.subTo(valueLength));
    }

    /**
     * Find this constant.
     *
     * @param source text source
     * @return смещение константы
     */
    public int findIn(Source source) {
        return source.find(value);
    }

    /**
     * @return string representation of this object.
     */
    @Override
    public String toString() {
        return "constant:" + value;
    }
}
