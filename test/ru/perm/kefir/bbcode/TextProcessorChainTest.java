package ru.perm.kefir.bbcode;

import org.junit.Test;

import java.util.Arrays;

/**
 * Test the TextProcessorChain
 */
public class TextProcessorChainTest {
    @Test
    public void test(){
        TextProcessor chain = new TextProcessorChain(
                Arrays.asList(
                        new EscapeProcessor(new String[][]{{"1", "2"}}),
                        new EscapeProcessor(new String[][]{{"2", "3"}}),
                        new EscapeProcessor(new String[][]{{"3", "4"}})
                )
        );
        Assert.assertProcess(chain, "4", "1");
    }
}
