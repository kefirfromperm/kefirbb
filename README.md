KefirBB
=======

About
-----
KefirBB is a Java-library for text processing. Initially it was developed for BB2HTML translation. But flexible
configuration allowed use it for others translations. For example XML2HTML or for HTML filtration.

Maven dependency
----------------
    <dependency>
        <groupId>org.kefirsf</groupId>
        <artifactId>kefirbb</artifactId>
        <version>1.0</version>
    </dependency>

Usage
-----
    TextProcessor processor = BBProcessorFactory.getInstance().create();
    assert "<b>text</b>".equals(processor.process("[b]text[/b]"));
