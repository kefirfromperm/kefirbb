package org.kefirsf.bb.proc;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The pattern element to parse URLs.
 *
 * @author kefir
 */
public class ProcUrl extends ProcNamedElement implements ProcPatternElement {
    static final Pattern REGEX_AUTHORITY = Pattern.compile(
            "[\\w\\.\\-~_!\\$&'\\(\\)%;:=\\+,\\*]+(:[\\w\\.\\-~_!\\$&'\\(\\)%;:=\\+,\\*]+)?@"
    );
    static final Pattern REGEX_HOST = Pattern.compile(
            "([\\da-zA-Z](\\-?\\w+)*\\.)*[\\da-zA-Z](\\-?\\w+)*\\.?"
    );
    static final Pattern REGEX_PATH = Pattern.compile(
            "(/[\\w\\(\\)\\.]+)*/?"
    );
    static final Pattern REGEX_QUERY = Pattern.compile(
            "\\?([\\w%\\-\\+]+(=[\\w%\\-\\+]+)?&)*([\\w%\\-\\+]+(=[\\w%\\-\\+]+)?)?"
    );
    static final Pattern REGEX_FRAGMENT = Pattern.compile(
            "#[\\w&-=]*"
    );
    static final Pattern REGEX_PORT = Pattern.compile(
            ":\\d{1,4}"
    );

    /**
     * Don't move the cursor offset.
     */
    private final boolean ghost;

    private final boolean local;

    private final boolean schemaless;

    /**
     * Create a named URL variable
     *
     * @param name  variable name
     * @param ghost don't move the cursor after parsing
     * @param local Parse local URLs also
     * @param schemaless Parse only schemaless URL
     */
    public ProcUrl(String name, boolean ghost, boolean local, boolean schemaless) {
        super(name);
        this.ghost = ghost;
        this.local = local;
        this.schemaless = schemaless;
    }

    /**
     * {@inheritDoc}
     */
    public boolean parse(Context context, ProcPatternElement terminator) throws NestingException {
        Source source = context.getSource();
        int length = parseLength(source, source.getOffset(), terminator);
        if (length >= 0) {
            context.setAttribute(getName(), source.sub(source.getOffset() + length));
            if (!ghost) {
                source.incOffset(length);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean isNextIn(Context context) {
        Source source = context.getSource();
        return parseLength(source, source.getOffset(), context.getTerminator()) >= 0;
    }

    /**
     * {@inheritDoc}
     */
    public int findIn(Source source) {
        int start = source.getOffset();
        int sourceLength = source.length();

        int index;
        int length = -1;
        do {
            index = sourceLength;

            // Find nearest schema
            for (Schema schema : Schema.values()) {
                int ni = source.findFrom(start, schema.getPrefix().toCharArray(), true);
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
     * Parse URL. The offset must be on a URL element
     *
     * @param source     text source
     * @param offset     offset for parsing
     * @param terminator a terminator element which can be used to cut some URL parts. Can be null.
     * @return URL length or -1 if it is not a URL.
     */
    int parseLength(Source source, int offset, ProcPatternElement terminator) {
        int length = 0;

        // A schema like http://, https://, mailto:
        Schema schema = parseSchema(source, offset);
        if (schema == null) {
            return -1;
        }
        length += schema.getLength();

        // An authority data like john.smith:pa55W0RD@
        int authorityLength = parseAuthority(source, offset + length);
        if (schema.isAuthorityMandatory() && authorityLength <= 0) {
            return -1;
        }
        length += authorityLength;

        // A host like example.com
        int hostLength = parseHost(source, offset + length, terminator);
        if (hostLength <= 0) {
            return -1;
        }
        length += hostLength;

        int portLength = parsePort(source, offset + length);
        length += portLength;

        // A path like /home/web
        int pathLength = parsePath(source, offset + length, terminator);
        length += pathLength;

        // A query like ?key1=value1&key2=value2
        int queryLength = parseQuery(source, offset + length, terminator);
        length += queryLength;

        // A fragment like #anchor
        int fragmentLength = parseFragment(source, offset + length, terminator);
        length += fragmentLength;

        return length;
    }

    int parseQuery(Source source, int offset, ProcPatternElement terminator) {
        return parseRegex(source, offset, calcEnd(source, terminator), REGEX_QUERY);
    }

    int parseFragment(Source source, int offset, ProcPatternElement terminator) {
        return parseRegex(source, offset, calcEnd(source, terminator), REGEX_FRAGMENT);
    }

    int parsePath(Source source, int offset, ProcPatternElement terminator) {
        return parseRegex(source, offset, calcEnd(source, terminator), REGEX_PATH);
    }

    int parseHost(Source source, int offset, ProcPatternElement terminator) {
        return parseRegex(source, offset, calcEnd(source, terminator), REGEX_HOST);
    }

    int parsePort(Source source, int offset) {
        return parseRegex(source, offset, source.length(), REGEX_PORT);
    }

    private int calcEnd(Source source, ProcPatternElement terminator) {
        int end = source.length();
        if (terminator != null) {
            int ind = terminator.findIn(source);
            if (ind > 0) {
                end = ind;
            }
        }
        return end;
    }

    private int parseRegex(Source source, int offset, int end, Pattern pattern) {
        CharSequence seq = source.subSequence(offset, end);
        Matcher matcher = pattern.matcher(seq);
        if (matcher.lookingAt()) {
            return matcher.group().length();
        } else {
            return 0;
        }
    }

    int parseAuthority(Source source, int offset) {
        return parseRegex(source, offset, source.length(), REGEX_AUTHORITY);
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
                "<url name=\"{0}\" ghost=\"{1}\"/>",
                getName(), ghost
        );
    }
}

