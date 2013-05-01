package org.kefirsf.bb.test;

import org.kefirsf.bb.EscapeXmlProcessorFactory;
import org.kefirsf.bb.TextProcessor;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.Random;

/**
 * Test performance of EscapeProcessor
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public class PerformanceEscapeProcessorTest {
    private static final String[] STRINGS = {
            ">",
            "<",
            "'",
            "\"",
            "&",
            "a",
            "s",
            "d",
            "f",
            "g",
            "h",
            "http://kefirsf.org/kefirbb/"
    };

    public static void main(String[] args) {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 1000000; i++) {
            builder.append(STRINGS[random.nextInt(STRINGS.length)]);
        }
        String text = builder.toString();

        TextProcessor processor = EscapeXmlProcessorFactory.getInstance().create();


        long start = System.currentTimeMillis();
        String result = processor.process(text);
        long finish = System.currentTimeMillis();

        DecimalFormat format = new DecimalFormat("# ##0");
        System.out.println(
                MessageFormat.format("Text length: {0} chars.", format.format(text.length()))
        );

        System.out.println(
                MessageFormat.format("Time: {0} milliseconds.", format.format(finish - start))
        );

        System.out.println(MessageFormat.format("Result: {0}", result.substring(0, 256)));

    }
}
