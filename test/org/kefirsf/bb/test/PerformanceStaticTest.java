package org.kefirsf.bb.test;

import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.TextProcessor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DecimalFormat;
import java.text.MessageFormat;

/**
 * @author Vitaliy Samolovskih aka Kefir
 */
public class PerformanceStaticTest {
    public static void main(String[] args) throws IOException {
        StringBuilder builder = new StringBuilder();

        Reader reader = new InputStreamReader(new FileInputStream("resource/text.txt"), "utf-8");
        try {
            char[] buf = new char[4096];
            int len;
            while (0 < (len = reader.read(buf))) {
                builder.append(buf, 0, len);
            }
        } finally {
            reader.close();
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
