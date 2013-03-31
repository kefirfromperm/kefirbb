package org.kefirsf.bb.util;

import java.util.Arrays;

/**
 * The char sequence based on existing char array.
 */
public final class ArrayCharSequence implements CharSequence {
    private final char[] text;
    private final int offset;
    private final int length;

    public ArrayCharSequence(char[] text, int offset, int length) {
        Exceptions.nullArgument("text", text);
        Exceptions.negativeArgument("offset", offset);
        Exceptions.negativeArgument("length", length);

        if ((offset + length) > text.length) {
            throw new ArrayIndexOutOfBoundsException("The breaks are wrong.");
        }

        this.text = text;
        this.offset = offset;
        this.length = length;
    }

    public int length() {
        return length;
    }

    public char charAt(int index) {
        return text[index + offset];
    }

    public CharSequence subSequence(int start, int end) {
        return new ArrayCharSequence(text, start + offset, end - start);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public String toString() {
        return String.valueOf(text, offset, length);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArrayCharSequence)) return false;

        ArrayCharSequence that = (ArrayCharSequence) o;

        if (length != that.length) return false;
        if (offset != that.offset) return false;
        //noinspection RedundantIfStatement
        if (!Arrays.equals(text, that.text)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(text);
        result = 31 * result + offset;
        result = 31 * result + length;
        return result;
    }
}
