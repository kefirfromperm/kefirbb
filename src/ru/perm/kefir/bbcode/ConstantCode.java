package ru.perm.kefir.bbcode;

import java.io.IOException;

/**
 * Code with constant string pattern. For basic escaping.
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public class ConstantCode extends AbstractCode {
    private final String value;
    private final char firstChar;
    private final int valueLength;

    /**
     * Create bb-code with constant pattern
     *
     * @param value    pattern value
     * @param template template
     * @param name     name of code
     * @param priority priority. If priority higher then code be checking early.
     */
    public ConstantCode(String value, WTemplate template, String name, int priority) {
        super(template, name, priority);

        this.value = value;
        this.firstChar = value.charAt(0);
        this.valueLength = value.length();
    }

    @Override
    public boolean process(Context context) throws IOException {
        context.getSource().incOffset(valueLength);
        template.generate(context);
        return true;
    }

    @Override
    public boolean suspicious(Source source) {
        return firstChar == source.current()
                && source.hasNext(valueLength)
                && value.contentEquals(source.subTo(valueLength));
    }
}
