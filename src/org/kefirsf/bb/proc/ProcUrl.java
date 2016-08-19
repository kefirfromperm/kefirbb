package org.kefirsf.bb.proc;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * The pattern element to parse URLs.
 *
 * @author kefir
 */
public class ProcUrl extends AbstractUrl {
    private static final Pattern REGEX_PORT = Pattern.compile(
            ":\\d{1,4}"
    );
    private static final Pattern REGEX_PATH = Pattern.compile(
            "(/([\\w\\(\\)\\.\\-]|(%\\p{XDigit}{2}))+)*/?"
    );
    private static final Pattern REGEX_FRAGMENT = Pattern.compile(
            "#([\\w&\\-=]|(%\\p{XDigit}{2}))*"
    );
    private static final Pattern REGEX_LOCAL_PREFIX = Pattern.compile("\\.{0,2}/");

    private static final String[] LOCAL_PREFIXES = {"/", "./", "../"};

    private final boolean local;

    private final boolean schemaless;

    /**
     * Create a named URL variable
     *
     * @param name       variable name
     * @param ghost      don't move the cursor after parsing
     * @param local      Parse local URLs also
     * @param schemaless Parse only schemaless URL
     */
    public ProcUrl(String name, boolean ghost, boolean local, boolean schemaless) {
        super(name, ghost);
        this.local = local;
        this.schemaless = schemaless;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int findIn(Source source) {
        if (schemaless) {
            return -1;
        }

        int start = source.getOffset();
        int sourceLength = source.length();

        int index;
        int length = -1;
        do {
            index = sourceLength;

            // Prepare URL's prefixes.
            List<String> prefixes = preparePrefixes();

            // Find nearest prefix
            for (String prefix : prefixes) {
                int ni = source.findFrom(start, prefix.toCharArray(), true);
                if (ni > 0 && ni < index) {
                    index = ni;
                }
            }

            // Try to parse it
            if (index < sourceLength) {
                length = parseLength(source, index, null);
                if (length < 0) {
                    start = index + 1;
                }
            }
        } while (length < 0 && index < sourceLength);

        if (length >= 0) {
            return index;
        } else {
            return -1;
        }
    }

    /**
     * Prepare URL's prefixes.
     *
     * @return list of schema prefixes and local prefixes if local URL are allowed.
     */
    private List<String> preparePrefixes() {
        // Prepare prefixes for all schemas
        List<String> prefixes = new ArrayList<String>(Schema.values().length + (local ? 3 : 0));
        for (Schema schema : Schema.values()) {
            prefixes.add(schema.getPrefix());
        }

        // For local URls prefixes are "./", "../", "/"
        if (local) {
            Collections.addAll(prefixes, LOCAL_PREFIXES);
        }
        return prefixes;
    }

    /**
     * Parse URL. The offset must be on a URL element
     *
     * @param source     text source
     * @param offset     offset for parsing
     * @param terminator a terminator element which can be used to cut some URL parts. Can be null.
     * @return URL length or -1 if it is not a URL.
     */
    @Override
    int parseLength(Source source, int offset, ProcPatternElement terminator) {
        int length = 0;

        // A schema like http://, https://, mailto:
        Schema schema = parseSchema(source, offset);
        if (schema != null && !schemaless) {
            length += schema.getLength();
        } else if ((schema == null && !local && !schemaless) || (schema != null)) {
            return -1;
        }

        // An authority data like john.smith:pa55W0RD@
        if (schema != null) {
            int authorityLength = parseAuthority(source, offset + length);
            if (schema.isAuthorityMandatory() && authorityLength <= 0) {
                return -1;
            }
            length += authorityLength;
        }

        // A host like example.com
        if (schema != null || schemaless) {
            int hostLength = parseHost(source, offset + length, terminator);
            if (hostLength <= 0) {
                return -1;
            }
            length += hostLength;
        }

        // Parse port
        if (schema != null || schemaless) {
            int portLength = parsePort(source, offset + length);
            length += portLength;
        }

        // For local URLs it is possible to use "./", "../", "/"
        if (schema == null && local) {
            int prefixLength = parseRegex(source, offset, calcEnd(source, terminator), REGEX_LOCAL_PREFIX);
            if (prefixLength <= 0) {
                return -1;
            }
            length += prefixLength - 1;
        }

        // A path like /home/web
        int pathLength = parsePath(source, offset + length, terminator);
        if (local && schema == null && pathLength <= 0) {
            return -1;
        }
        length += pathLength;

        // A query like ?key1=value1&key2=value2
        length += parseQuery(source, offset + length, terminator);

        // A fragment like #anchor
        length += parseFragment(source, offset + length, terminator);

        return length;
    }

    int parseFragment(Source source, int offset, ProcPatternElement terminator) {
        return parseRegex(source, offset, calcEnd(source, terminator), REGEX_FRAGMENT);
    }

    int parsePath(Source source, int offset, ProcPatternElement terminator) {
        return parseRegex(source, offset, calcEnd(source, terminator), REGEX_PATH);
    }

    private int parsePort(Source source, int offset) {
        return parseRegex(source, offset, source.length(), REGEX_PORT);
    }

    Schema parseSchema(Source source, int offset) {
        for (Schema schema : Schema.values()) {
            String str = source.subSequence(offset, Math.min(offset + schema.getLength(), source.length())).toString();
            if (schema.getPrefix().equalsIgnoreCase(str)) {
                return schema;
            }
        }
        return null;
    }

    enum Schema {
        HTTP("http://"),
        HTTPS("https://"),
        FTP("ftp://"),
        MAILTO("mailto:", true);

        private final String prefix;
        private final boolean authorityMandatory;


        Schema(String prefix) {
            this.prefix = prefix;
            authorityMandatory = false;
        }

        Schema(String prefix, boolean authorityMandatory) {
            this.prefix = prefix;
            this.authorityMandatory = authorityMandatory;
        }

        public String getPrefix() {
            return prefix;
        }

        public boolean isAuthorityMandatory() {
            return authorityMandatory;
        }

        public int getLength() {
            return prefix.length();
        }
    }

    @Override
    public String toString() {
        return MessageFormat.format(
                "<url name=\"{0}\" ghost=\"{1}\" local=\"{2}\" schemaless=\"{3}\"/>",
                getName(), ghost, local, schemaless
        );
    }
}

