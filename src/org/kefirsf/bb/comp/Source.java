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
    private char currentChar;

    private class ConstEntry{
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

    public void findAllConstants(Set<PatternConstant> constants){
        int index = -1;
        for(int i = 0;i<textLength; i++){
            for(PatternConstant constant:constants){
                String value = constant.getValue();
                int length = value.length();
                if(length<=textLength-i){
                    String str = String.valueOf(text, i, length);
                    if(constant.isIgnoreCase()?value.equalsIgnoreCase(str):value.equals(str)){
                        ConstEntry entry;
                        if(index<0 || constantPositions.get(index).getIndex()!=i){
                            entry = new ConstEntry(i);
                            constantPositions.add(entry);
                            index = constantPositions.size()-1;
                        } else {
                            entry = constantPositions.get(index);
                        }

                        entry.getSet().add(constant);
                    }
                }
            }
        }
    }

    /**
     * Find string in source starts with offset.
     *
     * @param value the string fo find
     * @return index of substring, -1 if not found.
     */
    public int find(String value) {
        int index = -1;

        int length = value.length();
        char[] chars = value.toCharArray();

        for (int i = offset; i < text.length - length + 1 && index < 0; i++) {
            int j;
            for (j = 0; j < length && chars[j] == text[i + j]; j++) {
            }
            if (j == length) {
                index = i;
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
        updateCurrentChar();
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
        updateCurrentChar();
    }

    /**
     * Устанавливает смещение
     *
     * @param offset смещение
     */
    public void setOffset(int offset) {
        this.offset = offset;
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

    public String subString(int start, int end) {
        return Arrays.toString(Arrays.copyOfRange(text, start, end));
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
