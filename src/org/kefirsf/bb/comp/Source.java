package org.kefirsf.bb.comp;

import java.util.*;

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
    private boolean onConstant;

    private class ConstEntry {
        private final int index;
        List<PatternConstant> set = new ArrayList<PatternConstant>();

        private ConstEntry(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        public List<PatternConstant> getSet() {
            return set;
        }
    }

    private int[] constantIndexes;
    private PatternConstant[][] constants;

    private int constantIndexOffset;

    /**
     * Создает класс источник
     *
     * @param text исходный текст
     */
    public Source(CharSequence text) {
        this.text = text.toString().toCharArray();
        textLength = text.length();
    }

    public void findAllConstants(Set<PatternConstant> constants) {
        Set<Character> chars = new TreeSet<Character>();
        for (PatternConstant constant : constants) {
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

        ArrayList<ConstEntry> constantPositions = new ArrayList<ConstEntry>();

        int index = -1;
        for (int i = 0; i < textLength; i++) {
            char c = text[i];
            int k = Arrays.binarySearch(cs, c);
            if (k >= 0) {
                for (PatternConstant constant : constants) {
                    String value = constant.getValue();
                    int length = value.length();
                    if (length <= textLength - i) {
                        boolean flag;

                        if (constant.isIgnoreCase()) {
                            String str = String.valueOf(text, i, length);
                            flag = value.equalsIgnoreCase(str);
                        } else {
                            char[] valChars = value.toCharArray();
                            int l;
                            for (l = 0; l < length && text[i + l] == valChars[l]; l++) {
                            }
                            flag = l == length;
                        }

                        if (flag) {
                            ConstEntry entry;
                            if (index < 0 || constantPositions.get(index).getIndex() != i) {
                                entry = new ConstEntry(i);
                                constantPositions.add(entry);
                                index = constantPositions.size() - 1;
                            } else {
                                entry = constantPositions.get(index);
                            }

                            entry.getSet().add(constant);
                        }
                    }
                }
            }
        }

        constantIndexes = new int[constantPositions.size()];
        this.constants = new PatternConstant[constantPositions.size()][];

        int i = 0;
        for (ConstEntry entry : constantPositions) {
            constantIndexes[i] = entry.getIndex();
            this.constants[i] = new PatternConstant[entry.getSet().size()];
            int k = 0;
            for (PatternConstant constant : entry.getSet()) {
                this.constants[i][k] = constant;
                k++;
            }
            i++;
        }

        constantIndexOffset = 0;
        recalculateOnConstant();
    }

    public boolean nextIs(PatternConstant constant) {
        if (onConstant) {
            for (int i = 0; i < constants[constantIndexOffset].length; i++) {
                if (constant == constants[constantIndexOffset][i]) {
                    return true;
                }
            }
        }

        return false;
    }

    private void recalculateOnConstant() {
        onConstant = constantIndexOffset < constantIndexes.length &&
                constantIndexes[constantIndexOffset] == offset;
    }

    public int find(PatternConstant constant) {
        int index = -1;

        for (int i = constantIndexOffset; i < constants.length && index < 0; i++) {
            for (int j = 0; j < constants[i].length && index < 0; j++) {
                if (constant == constants[i][j]) {
                    index = constantIndexes[i];
                }
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
        incConstantIndexOffset();
    }

    private void incConstantIndexOffset() {
        while (constantIndexOffset < constantIndexes.length && constantIndexes[constantIndexOffset] < offset) {
            constantIndexOffset++;
        }
        recalculateOnConstant();
    }

    /**
     * Увеличивает смещение
     *
     * @param increment на сколько нужно увеличить смещение
     */
    public void incOffset(int increment) {
        offset += increment;
        incConstantIndexOffset();
    }

    /**
     * Устанавливает смещение
     *
     * @param offset смещение
     */
    public void setOffset(int offset) {
        this.offset = offset;

        recalculateConstantIndexOffset();
    }

    private void recalculateConstantIndexOffset() {
        constantIndexOffset = Arrays.binarySearch(constantIndexes, offset);
        if (constantIndexOffset < 0) {
            constantIndexOffset = - constantIndexOffset - 1;
        }
        recalculateOnConstant();
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
