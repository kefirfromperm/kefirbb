package org.kefirsf.bb.test;

import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.TextProcessor;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.Random;

/**
 * It's test for profiling
 *
 * @author Vitaliy Samolovskih aka Kefir
 */
public class PerformanceRandomTest {
    private static final String[] symbols = new String[]{
            "[b]", "[/b]",
            "[i]", "[/i]",
            "[s]", "[/s]",
            "[u]", "[/u]",
            "[img]", "[/img]",
            "[url]", "[/url]",
            "[url=", "]", "[/]",
            "[", "\\[", "\\]",
            "&", "'", "\"", "\\", ";", "<", ">",
            "test", "src", "text", "123", "http://",
            "=", ",", "Превед", "Медвед", "Smells like teen spirit",
            "Nevermind", "Contra la Contra", "Hate to state",
            "Klowns", " ", "\n", "\r", "Apple", "Sun microsystems",
            "IBM", "Hello world", "Good job", "Spring", "Hibernate",
            "Grails", "KefirBB", "http://kefir-bb.sourceforge.net",
            "Sitemech", "More english words"
    };

    public static void main(String[] args) {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            builder.append(symbols[random.nextInt(symbols.length)]);
        }
        String text = builder.toString();

        TextProcessor processor = BBProcessorFactory.getInstance().createFromResource("org/kefirsf/bb/default.xml");

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
