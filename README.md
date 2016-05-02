KefirBB
=======
[![Build Status](https://travis-ci.org/kefirfromperm/kefirbb.svg?branch=master)](https://travis-ci.org/kefirfromperm/kefirbb) [![Download](https://api.bintray.com/packages/kefirsf/libs/org.kefirsf%3Akefirbb/images/download.svg) ](https://bintray.com/kefirsf/libs/org.kefirsf%3Akefirbb/_latestVersion) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.kefirsf/kefirbb/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.kefirsf/kefirbb) [![javadoc.io](https://javadocio-badges.herokuapp.com/org.kefirsf/kefirbb/badge.svg)](https://javadocio-badges.herokuapp.com/org.kefirsf/kefirbb) [![OpenHUB](https://openhub.net/p/kefirbb/widgets/project_thin_badge?format=gif)](https://openhub.net/p/kefirbb)

About
-----
KefirBB is a Java-library for text processing. Initially it was developed for BBCode (Bulletin Board Code) to HTML
translation. But flexible configuration allows to use it in others situations. For example XML-to-HTML translation or
for HTML filtration. Actually it's the most powerfull and flexible Java-library for BBCode parsing.

Maven dependency
----------------
    <dependency>
        <groupId>org.kefirsf</groupId>
        <artifactId>kefirbb</artifactId>
        <version>1.2</version>
    </dependency>

Usage
-----

### Convert BBCode

KefirBB fully supports converting from BBCode markup language to HTML. The syntax of BBCode is described at
[BBCode - Wikipedia](https://en.wikipedia.org/wiki/BBCode).

    TextProcessor processor = BBProcessorFactory.getInstance().create();
    assert "<b>text</b>".equals(processor.process("[b]text[/b]"));

### HTML Filtration

If you don't want to use special markup languages in your site but you have to safe your users from XSS-attacks
you can use KefirBB configuration for HTMl filtration. It prevents using of javascript, styles etc.

    TextProcessor processor = BBProcessorFactory.getInstance()
        .createFromResource(ConfigurationFactory.SAFE_HTML_CONFIGURATION_FILE);
    assert "<b>text</b>".equals(processor.process("<b onclick=\"javascript:alert('Fail!');\">test</B>"));

### Convert Textile

KefirBB fully supports Textile markup language. It's described at
[TxStyle](https://txstyle.org/)

    TextProcessor processor = BBProcessorFactory.getInstance()
        .createFromResource(ConfigurationFactory.TEXTILE_CONFIGURATION_FILE);
    assert "<p><b>text</b></p>".equals(processor.process("**text**"));

### Convert Markdown

KefirBB supports Markdown markup language partially described at
[Markdown Syntax](https://daringfireball.net/projects/markdown/syntax). It doesn't support fully blockquotes and lists.

    TextProcessor processor = BBProcessorFactory.getInstance()
        .createFromResource(ConfigurationFactory.MARKDOWN_CONFIGURATION_FILE);
    assert "<p><strong>text</strong></p>".equals(processor.process("**text**"));

### Your custom configuration

Also you can use your own configuration or customize existing. Just put your's own configuration to
`classpath:kefirbb.xml`.

Donation
------------
If you want to give me a beer just send some money to <https://www.paypal.me/kefir>
