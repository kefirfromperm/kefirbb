package org.kefirsf.bb.proc;

import org.kefirsf.bb.conf.Action;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс переменной
 *
 * @author Kefir
 */
public class ProcVariable extends ProcNamedElement implements ProcPatternElement {
    private final java.util.regex.Pattern regex;
    private final boolean ghost;
    private final Action action;

    /**
     * Create named variable
     *
     * @param name   variable name
     * @param regex  regular expression pattern
     * @param action the variable action. Rewrite, append, check
     */
    public ProcVariable(String name, Pattern regex, boolean ghost, Action action) {
        super(name);
        this.regex = regex;
        this.ghost = ghost;
        this.action = action;
    }

    /**
     * Парсит элемент
     *
     * @param context контекст
     * @return true - если удалось распарсить константу
     * false - если не удалось
     */
    public boolean parse(Context context, ProcPatternElement terminator) {
        Source source = context.getSource();
        int offset = source.getOffset();

        int end;
        if (terminator != null && !ghost) {
            end = terminator.findIn(source);
            if (end < 0) {
                if (regex == null) {
                    return false;
                } else {
                    end = source.length();
                }
            }
        } else {
            end = source.length();
        }


        CharSequence value = source.sub(end);

        // If define regex, then find this regex in value
        if (regex != null) {
            Matcher matcher = regex.matcher(value);
            if (matcher.lookingAt()) {
                int lend = matcher.end();
                end = offset + lend;
                value = matcher.group();
            } else {
                return false;
            }
        }

        // Old context value
        CharSequence old = (CharSequence) context.getAttribute(getName());

        // Test this variable already defined and equals with this in this code scope
        CharSequence attr = (CharSequence) context.getLocalAttribute(getName());
        if (attr == null || attr.equals(value)) {
            if (attr == null) {
                if (action == Action.rewrite) {
                    setAttribute(context, value);
                } else {
                    /* action == append */
                    if (old != null) {
                        setAttribute(context, new StringBuilder().append(old).append(value));
                    } else {
                        setAttribute(context, value);
                    }
                }
            }
            if (!ghost) {
                source.incOffset(end - offset);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Определяет, что дальше в разбираемой строке находится нужная последовательность
     *
     * @param context current context
     * @return true если следующие символы в строке совпадают с pattern
     * false если не совпадают или строка кончилась
     */
    public boolean isNextIn(Context context) {
        return regex == null || regex.matcher(context.getSource().subToEnd()).lookingAt();
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
                return source.getOffset() + matcher.start();
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
