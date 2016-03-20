package org.kefirsf.bb.proc;

/**
 * @author kefir
 */
public abstract class AbstractProcUrlTest extends AbstractProcTest {
    public static final String PREFIX = "prefix ";
    public static final String SUFFIX = " suffix";

    @Override
    protected String getSuffix() {
        return SUFFIX;
    }

    @Override
    protected String getPrefix() {
        return PREFIX;
    }
}
