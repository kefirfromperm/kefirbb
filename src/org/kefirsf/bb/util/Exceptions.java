package org.kefirsf.bb.util;

import java.text.MessageFormat;
import java.util.Collection;

/**
 * Exceptions utils.
 *
 * @author kefir
 */
public final class Exceptions {

    private static final String NULL_ARGUMENT = "Argument {0} can't be null.";
    private static final String EMPTY_ARGUMENT = "Argument {0} can't be empty.";
    private static final String BLANK_ARGUMENT = "Argument {0} can't be blank.";
    private static final String NEGATIVE_ARGUMENT = "Argument {0} can''t be negative.";

    /**
     * Prevent to create an instance.
     */
    private Exceptions() {
    }

    public static void nullArgument(String name, Object value) throws IllegalArgumentException {
        if (value == null) {
            throw new IllegalArgumentException(MessageFormat.format(NULL_ARGUMENT, name));
        }
    }

    public static <E> void emptyArgument(String name, Collection<E> value) throws IllegalArgumentException {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException(MessageFormat.format(EMPTY_ARGUMENT, name));
        }
    }

    public static void blankArgument(String name, CharSequence value) throws IllegalArgumentException {
        if (value == null || value.toString().trim().length() == 0) {
            throw new IllegalArgumentException(MessageFormat.format(BLANK_ARGUMENT, name));
        }
    }

    public static void negativeArgument(String name, int offset) {
        if(offset<0){
            throw new IllegalArgumentException(MessageFormat.format(NEGATIVE_ARGUMENT, name));
        }
    }
}
