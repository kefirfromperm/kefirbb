package org.kefirsf.bb.comp;

import java.io.IOException;

/**
 * Code with constant string pattern. For basic escaping.
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public class ConstantCode extends AbstractCode {
    private final PatternConstant constant;

    /**
     * Create bb-code with constant pattern
     *
     * @param constant    pattern constant
     * @param template template
     * @param name     name of code
     * @param priority priority. If priority higher then code be checking early.
     */
    public ConstantCode(PatternConstant constant, WTemplate template, String name, int priority) {
        super(template, name, priority);

        this.constant = constant;
    }

    @Override
    public boolean process(Context context) throws IOException {
        context.getSource().incOffset(constant.getValue().length());
        template.generate(context);
        return true;
    }

    @Override
    public boolean suspicious(Source source) {
        return source.nextIs(constant);
    }

    @Override
    public int findSuspicious(Source source) {
        return source.nextConstantIndex();
    }
}
