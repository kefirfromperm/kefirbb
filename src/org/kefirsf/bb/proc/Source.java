package org.kefirsf.bb.proc;

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
     * Текст для парсинга
     */
    private final char[] text;
    private final int textLength;

    /**
     * Смещение
     */
    private int offset = 0;
    //private boolean onConstant;

    private Set<PatternConstant> constantSet;
    private char[] constantChars;

    public boolean nextMayBeConstant() {
        return Arrays.binarySearch(constantChars, text[offset]) >= 0;
    }

    /**
     * Создает класс источник
     *
     * @param text исходный текст
     */
    public Source(CharSequence text) {
        this.text = text.toString().toCharArray();
        textLength = text.length();
    }

    public void setConstantSet(Set<PatternConstant> constantSet) {
        this.constantSet = constantSet;
        this.constantChars = getConstantChars();
    }

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
            int i;
            for (i = 0; i < length; i++) {
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

    public int find(PatternConstant constant) {
        int index = -1;

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
            if(flag){
                return i;
            }
        }

        return index;
    }

    /**
     * Возвращает следующий симвойл и увеличивает смещение
     *
     * @return символ
     */
    public char next() {
        char c = text[offset];
        incOffset();
        return c;
    }

    /**
     * Возвращает текущее смещение
     *
     * @return смещение от начала
     */
    public int getOffset() {
        return offset;
    }

    /**
     * Increament offset
     */
    public void incOffset() {
        offset++;
    }

    /**
     * Увеличивает смещение
     *
     * @param increment на сколько нужно увеличить смещение
     */
    public void incOffset(int increment) {
        offset += increment;
    }

    /**
     * Устанавливает смещение
     *
     * @param offset смещение
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * Есть ли еще что-то в строке
     *
     * @return true - если есть
     *         false если достигнут конец строки
     */
    public boolean hasNext() {
        return offset < textLength;
    }

    /**
     * Return length of sorce text
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
        return String.valueOf(text, offset, end - offset);
    }

    /**
     * Get String from offset to end
     *
     * @return string
     */
    public CharSequence subToEnd() {
        return sub(textLength);
    }

    public String toString() {
        return "org.kefirsf.bb.Source,length:" + String.valueOf(textLength);
    }
}
