package org.kefirsf.bb.proc;

import java.util.regex.Matcher;

/**
 * Класс переменной
 *
 * @author Kefir
 */
public class ProcVariable extends ProcNamedElement implements ProcPatternElement {
    private final java.util.regex.Pattern regex;

    /**
     * Create named variable
     *
     * @param name  variable name
     * @param regex regular expression pattern
     */
    public ProcVariable(String name, java.util.regex.Pattern regex) {
        super(name);
        this.regex = regex;
    }

    /**
     * Парсит элемент
     *
     * @param context контекст
     * @return true - если удалось распарсить константу
     *         false - если не удалось
     */
    public boolean parse(Context context, ProcPatternElement terminator) {
        Source source = context.getSource();
        int offset = source.getOffset();

        int end;
        if (terminator != null) {
            end = terminator.findIn(context.getSource());
        } else {
            end = context.getSource().getLength();
        }

        if (end < 0) {
            return false;
        }

        CharSequence value = source.sub(end);

        // If define regex, then find this regex in value
        if (regex != null) {
            Matcher matcher = regex.matcher(value);
            if (matcher.lookingAt()) {
                int lend = matcher.end();
                end = offset + lend;
                value = value.subSequence(0, lend);
            } else {
                return false;
            }
        }

        // Test this variable already defined and equals with this in this code scope 
        Object attr = context.getLocalAttribute(getName());
        if (attr == null || attr.equals(value)) {
            if (attr == null) {
                setAttribute(context, value);
            }
            source.incOffset(end - offset);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Определяет, что дальше в разбираемой строке находится нужная последовательность
     *
     * @param source source text
     * @return true если следующие символы в строке совпадают с pattern
     *         false если не совпадают или строка кончилась
     */
    public boolean isNextIn(Source source) {
        return regex != null && regex.matcher(source.subToEnd()).lookingAt();
    }

    /**
     * Find this element
     *
     * @param source text source
     * @return start offset
     */
    public int findIn(Source source) {
        if (regex != null) {
            Matcher matcher = regex.matcher(source.subToEnd());
            if (matcher.find()) {
                return matcher.start();
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return "variable:" + getName();
    }
}
