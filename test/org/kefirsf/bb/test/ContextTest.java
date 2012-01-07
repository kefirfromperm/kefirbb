package org.kefirsf.bb.test;

import org.junit.Test;
import org.kefirsf.bb.comp.Context;
import org.kefirsf.bb.comp.WConstant;
import org.kefirsf.bb.comp.WPatternElement;

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
        WPatternElement terminator = new WConstant("test");
        ctx.setTerminator(terminator);
        assertEquals(terminator, ctx.getTerminator());
    }
}
