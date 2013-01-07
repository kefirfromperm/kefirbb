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
    private int constantPositionOffset = 0;
    private char currentChar;

    private class ConstEntry {
        private final int index;
        Set<PatternConstant> set = new HashSet<PatternConstant>();

        private ConstEntry(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        public Set<PatternConstant> getSet() {
            return set;
        }
    }

    private List<ConstEntry> constantPositions = new ArrayList<ConstEntry>();

    /**
     * Создает класс источник
     *
     * @param text исходный текст
     */
    public Source(CharSequence text) {
        this.text = text.toString().toCharArray();
        textLength = text.length();
        updateCurrentChar();
    }

    public void findAllConstants(Set<PatternConstant> constants) {
        int index = -1;
        for (int i = 0; i < textLength; i++) {
            for (PatternConstant constant : constants) {
                String value = constant.getValue();
                int length = value.length();
                if (length <= textLength - i) {
                    String str = String.valueOf(text, i, length);
                    if (constant.isIgnoreCase() ? value.equalsIgnoreCase(str) : value.equals(str)) {
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

    public boolean nextIs(PatternConstant constant) {
        if (!constantPositions.isEmpty()) {
            ConstEntry entry = constantPositions.get(constantPositionOffset);
            return entry.getIndex() == offset && entry.getSet().contains(constant);
        } else {
            return false;
        }
    }

    public int find(PatternConstant constant) {
        int index = -1;
        if (!constantPositions.isEmpty() && offset <= constantPositions.get(constantPositionOffset).getIndex()) {
            for (int i = constantPositionOffset; i < constantPositions.size() && index < 0; i++) {
                ConstEntry entry = constantPositions.get(i);
                if (entry.getSet().contains(constant)) {
                    index = entry.getIndex();
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
        char c = current();
        offset++;
        incConstantPositionOffset();
        updateCurrentChar();
        return c;
    }

    public char current() {
        return currentChar;
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
        incConstantPositionOffset();
        updateCurrentChar();
    }

    private void incConstantPositionOffset() {
        while (
                constantPositionOffset < constantPositions.size() - 1 &&
                        constantPositions.get(constantPositionOffset).getIndex() < offset
                ) {
            constantPositionOffset++;
        }
    }

    private void updateCurrentChar() {
        if (offset < textLength) {
            currentChar = text[offset];
        }
    }

    /**
     * Увеличивает смещение
     *
     * @param increment на сколько нужно увеличить смещение
     */
    public void incOffset(int increment) {
        offset += increment;
        incConstantPositionOffset();
        updateCurrentChar();
    }

    /**
     * Устанавливает смещение
     *
     * @param offset смещение
     */
    public void setOffset(int offset) {
        this.offset = offset;
        constantPositionOffset = 0;
        incConstantPositionOffset();
        updateCurrentChar();
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
     * Есть ли еще count символов в строке
     *
     * @param count количчество символов которое должно остаться в строке
     * @return true - если есть
     *         false если достигнут конец строки
     */
    public boolean hasNext(int count) {
        return (textLength - offset) >= count;
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
        return String.valueOf(Arrays.copyOfRange(text, getOffset(), end));
    }

    /**
     * Get String from offset to offset+valueLength
     *
     * @param count length of extracted string
     * @return string
     */
    public CharSequence subTo(int count) {
        return sub(getOffset() + count);
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
        return "ru.perm.kefir.bbcode.Source,length:" + String.valueOf(textLength);
    }
}
