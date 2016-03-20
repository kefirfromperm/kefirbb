package org.kefirsf.bb.proc;

/**
 * @author kefir
 */
public abstract class AbstractProcTest {

    protected Context prepareContext() {
        Context context = new Context();
        context.setSource(prepareSource());
        return context;
    }

    protected Source prepareSource() {
        StringBuilder b = new StringBuilder();
        b.append(getPrefix());
        b.append(getValue());
        b.append(getSuffix());
        return new Source(b);
    }

    protected abstract String getSuffix();

    protected abstract String getPrefix();

    protected abstract String getValue();
}
