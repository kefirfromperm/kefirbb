package org.kefirsf.bb.util;

import java.io.InputStream;
import java.util.UUID;

/**
 * Utils class
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public final class Utils {
    private Utils() {
    }

    /**
     * Open the resource stream for named resource.
     * Stream must be closed by user after usage.
     *
     * @param resourceName resource name
     * @return input stream
     */
    public static InputStream openResourceStream(String resourceName) {
        InputStream stream = null;
        ClassLoader classLoader = Utils.class.getClassLoader();
        if (classLoader != null) {
            stream = classLoader.getResourceAsStream(resourceName);
        }

        if (stream == null) {
            stream = ClassLoader.getSystemResourceAsStream(resourceName);
        }
        return stream;
    }

    public static String generateRandomName() {
        return UUID.randomUUID().toString();
    }
}
