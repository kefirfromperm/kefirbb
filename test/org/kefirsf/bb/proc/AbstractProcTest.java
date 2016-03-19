package org.kefirsf.bb.proc;

/**
 * @author kefir
 */
public abstract class AbstractProcTest {
    public static final String PREFIX = "prefix ";
    public static final String SUFFIX = " suffix";

    protected Context prepareContext() {
        Context context = new Context();
        context.setSource(prepareSource());
        return context;
    }

    protected Source prepareSource() {
        StringBuilder b = new StringBuilder();
        b.append(PREFIX);
        b.append(getValue());
        b.append(SUFFIX);
        return new Source(b);
    }

    protected abstract String getValue();
}
