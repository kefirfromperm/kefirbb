package org.kefirsf.bb.proc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Vitaliy Samolovskih aka Kefir
 */
public class ContextTest {
    @Test
    public void testTarget() {
        Context ctx = new Context();
        Appendable target = new StringBuilder();
        ctx.setTarget(target);
        assertEquals(target, ctx.getTarget());
    }

    @Test
    public void testTerminator() {
        Context ctx = new Context();
        assertNull(ctx.getTerminator());
        ProcPatternElement terminator = new PatternConstant("test", false);
        ctx.setTerminator(terminator);
        assertEquals(terminator, ctx.getTerminator());
    }
}
