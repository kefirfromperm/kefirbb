package ru.perm.kefir.bbcode;

import java.util.regex.Matcher;

/**
 * Класс переменной
 *
 * @author Kefir
 */
public class WVariable extends WNamedElement implements WPatternElement {
    private final java.util.regex.Pattern regex;

    /**
     * Создает именованную переменную
     *
     * @param name название переменной
     */
    public WVariable(String name) {
        super(name);
        regex = null;
    }

    /**
     * Create named variable
     *
     * @param name  variable name
     * @param regex regular expression pattern
     */
    public WVariable(String name, java.util.regex.Pattern regex) {
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
    public boolean parse(Context context, WPatternElement terminator) {
        int end;
        if (terminator != null) {
            end = terminator.findIn(context.getSource());
        } else {
            end = context.getSource().getLength();
        }

        if (end < 0) {
            return false;
        }

        Source source = context.getSource();
        CharSequence value = source.sub(end);

        // If define regex, then find this regex in value
        if (regex != null) {
            Matcher matcher = regex.matcher(value);
            if (matcher.lookingAt()) {
                int lend = matcher.end();
                end = source.getOffset() + lend;
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
            source.setOffset(end);
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
