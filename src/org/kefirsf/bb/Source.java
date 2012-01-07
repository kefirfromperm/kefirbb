package org.kefirsf.bb;

/**
 * Класс источник для парсинга BB-кодов
 *
 * @author Kefir
 */
public class Source {
    private static final int BUFF_SIZE = 4096;

    /**
     * Текст ждля парсинга
     */
    private final CharSequence text;
    private final int textLength;

    /**
     * Смещение
     */
    private int offset = 0;
    private char currentChar;

    /**
     * Создает класс источник
     *
     * @param text исходный текст
     */
    public Source(CharSequence text) {
        this.text = text;
        textLength = text.length();
        updateCurrentChar();
    }

    public int find(String value) {
        if (text instanceof String) {
            return ((String) text).indexOf(value, offset);
        } else if (text instanceof StringBuilder) {
            return ((StringBuilder) text).indexOf(value, offset);
        } else if (text instanceof StringBuffer) {
            return ((StringBuffer) text).indexOf(value, offset);
        } else {
            int inCharSequence = findInCharSequence(text.subSequence(offset, textLength), value);
            if (inCharSequence >= 0) {
                return offset + inCharSequence;
            } else {
                return -1;
            }
        }
    }

    /**
     * Find value in character sequence
     *
     * @param sequence character sequence
     * @param value    searched value
     * @return index of value in sequence
     */
    private int findInCharSequence(CharSequence sequence, String value) {
        if (value.length() == 0) {
            throw new IllegalArgumentException("Argument value can't be empty.");
        }

        final int seqLength = sequence.length();
        final int valLength = value.length();

        if (seqLength < valLength) {
            return -1;
        }

        int index;
        int size;

        int nextSize = Math.max(BUFF_SIZE, valLength);
        do {
            size = nextSize;
            if (size > seqLength) {
                size = seqLength;
            }

            index = sequence.subSequence(0, size).toString().indexOf(value);
            nextSize = 2 * size;
        } while (index <= 0 && size < seqLength);

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
            currentChar = text.charAt(offset);
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
        return text.subSequence(getOffset(), end);
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
        return text.subSequence(start, end).toString();
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
