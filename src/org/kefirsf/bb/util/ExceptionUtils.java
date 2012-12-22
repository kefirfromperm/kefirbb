package org.kefirsf.bb.util;

import java.text.MessageFormat;

/**
 * @author kefir
 */
public final class ExceptionUtils {

    private static final String NULL_ARGUMENT = "Argument {0} can't be null.";

    /**
     * Prevent to create an instance.
     */
    private ExceptionUtils() {
    }

    public static IllegalArgumentException nullArgument(String name){
        return new IllegalArgumentException(MessageFormat.format(NULL_ARGUMENT, name));
    }
}
