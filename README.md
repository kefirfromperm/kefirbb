KefirBB
=======

About
-----
KefirBB is a Java-library for text processing. Initially it was developed for BBCode or BB-code (Bulletin Board Code) to HTML translation. But flexible configuration allows to use it in others situations. For example XML-to-HTML translation or for HTML filtration. Actually it's the most powerfull and flexible Java-library for BBCode parsing.

Maven dependency
----------------
    <dependency>
        <groupId>org.kefirsf</groupId>
        <artifactId>kefirbb</artifactId>
        <version>1.1</version>
    </dependency>

Usage
-----
    TextProcessor processor = BBProcessorFactory.getInstance().create();
    assert "<b>text</b>".equals(processor.process("[b]text[/b]"));

Donation
------------
If you want to give me a beer just send some money to <https://www.paypal.me/kefir>
