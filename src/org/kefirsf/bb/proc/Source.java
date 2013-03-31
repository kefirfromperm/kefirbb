package org.kefirsf.bb.proc;

import org.kefirsf.bb.util.ArrayCharSequence;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * Класс источник для парсинга BB-кодов
 *
 * @author Kefir
 */
public class Source {
    /**
     * Source text content
     */
    private final char[] text;

    /**
     * Source text length
     */
    private final int textLength;

    /**
     * Current offset
     */
    private int offset = 0;

    /**
     * Set of constants. All constants in configuration.
     */
    private Set<PatternConstant> constantSet;

    /**
     * First chars of constants of configuration. It's needed for fast check constants on current position.
     */
    private char[] constantChars;

    /**
     * Check if the current sub sequence can be constant.
     */
    public boolean nextMayBeConstant() {
        return Arrays.binarySearch(constantChars, text[offset]) >= 0;
    }

    /**
     * Create source class.
     *
     * @param text source text.
     */
    public Source(CharSequence text) {
        textLength = text.length();
        this.text = new char[textLength];

        if (text instanceof String) {
            ((String) text).getChars(0, textLength, this.text, 0);
        } else if (text instanceof StringBuilder) {
            ((StringBuilder) text).getChars(0, textLength, this.text, 0);
        } else if (text instanceof StringBuffer) {
            ((StringBuffer) text).getChars(0, textLength, this.text, 0);
        } else {
            text.toString().getChars(0, textLength, this.text, 0);
        }
    }

    /**
     * Set constant set from configuration. For fast search.
     */
    public void setConstantSet(Set<PatternConstant> constantSet) {
        this.constantSet = constantSet;
        this.constantChars = getConstantChars();
    }

    /**
     * Collect first chars of constants of configuration.
     *
     * @return array of first chars of constants.
     */
    private char[] getConstantChars() {
        Set<Character> chars = new TreeSet<Character>();
        for (PatternConstant constant : constantSet) {
            char c = constant.getValue().charAt(0);
            if (constant.isIgnoreCase()) {
                chars.add(Character.toLowerCase(c));
                chars.add(Character.toUpperCase(c));
            } else {
                chars.add(c);
            }
        }

        char[] cs = new char[chars.size()];
        int j = 0;
        for (Character c : chars) {
            cs[j] = c;
            j++;
        }
        Arrays.sort(cs);
        return cs;
    }

    /**
     * Test id next sequence the constant?
     *
     * @param constant constant pattern element
     * @return true if next sub sequence is constant.
     */
    public boolean nextIs(PatternConstant constant) {
        char[] cs = constant.getCharArray();
        int length = cs.length;

        if (length > textLength - offset) {
            return false;
        }

        if (!constant.isIgnoreCase()) {
            int i;
            for (i = 0; i < length && text[offset + i] == cs[i]; i++) {
            }
            return i == length;
        } else {
            for (int i = 0; i < length; i++) {
                char ct = text[offset + i];
                char cv = cs[i];
                if (
                        ct == cv ||
                                Character.toUpperCase(ct) == Character.toUpperCase(cv) ||
                                Character.toLowerCase(ct) == Character.toLowerCase(cv)
                        ) {
                    continue;
                }
                return false;
            }
            return true;
        }
    }

    /**
     * Find constant in source text.
     *
     * @param constant constant pattern element
     * @return index of constant of negative if don't find.
     */
    public int find(PatternConstant constant) {
        char[] cs = constant.getCharArray();
        int length = cs.length;
        boolean ignoreCase = constant.isIgnoreCase();

        for (int i = offset; i < textLength - length + 1; i++) {
            boolean flag = true;
            for (int j = 0; j < length && flag; j++) {
                char ct = text[i + j];
                char cv = cs[j];
                flag = (
                        ct == cv ||
                                (
                                        ignoreCase &&
                                                (
                                                        Character.toUpperCase(ct) == Character.toUpperCase(cv) ||
                                                                Character.toLowerCase(ct) == Character.toLowerCase(cv)
                                                )
                                )
                );
            }
            if (flag) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Return next character and increment offset.
     *
     * @return character.
     */
    public char next() {
        char c = text[offset];
        incOffset();
        return c;
    }

    /**
     * Return current offset.
     *
     * @return offset from begin.
     */
    public int getOffset() {
        return offset;
    }

    /**
     * Increment offset.
     */
    public void incOffset() {
        offset++;
    }

    /**
     * Increment offset.
     *
     * @param increment increment size.
     */
    public void incOffset(int increment) {
        offset += increment;
    }

    /**
     * Set offset.
     *
     * @param offset new offset value.
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * Есть ли еще что-то в строке?
     *
     * @return true - если есть
     *         false если достигнут конец строки
     */
    public boolean hasNext() {
        return offset < textLength;
    }

    /**
     * Return length of source text
     *
     * @return length of source text
     */
    public int getLength() {
        return textLength;
    }

    /**
     * Получает строку от текущего смещения до значения <code>end</code>
     *
     * @param end последний индекс
     * @return подстрока
     */
    public CharSequence sub(int end) {
        return new ArrayCharSequence(text, offset, end - offset);
    }

    /**
     * Get String from offset to end.
     *
     * @return char sequence
     */
    public CharSequence subToEnd() {
        return sub(textLength);
    }

    public String toString() {
        return "org.kefirsf.bb.proc.Source{length:" + String.valueOf(textLength) + "}";
    }
}
