KefirBB
=======
[![Build Status](https://travis-ci.org/kefirfromperm/kefirbb.svg?branch=master)](https://travis-ci.org/kefirfromperm/kefirbb) [![Download](https://api.bintray.com/packages/bintray/jcenter/org.kefirsf%3Akefirbb/images/download.svg) ](https://bintray.com/bintray/jcenter/org.kefirsf%3Akefirbb/_latestVersion) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.kefirsf/kefirbb/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.kefirsf/kefirbb) [![OpenHUB](https://openhub.net/p/kefirbb/widgets/project_thin_badge?format=gif)](https://openhub.net/p/kefirbb)

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
        <version>1.1</version>
    </dependency>

Usage
-----
    TextProcessor processor = BBProcessorFactory.getInstance().create();
    assert "<b>text</b>".equals(processor.process("[b]text[/b]"));

Donation
------------
If you want to give me a beer just send some money to <https://www.paypal.me/kefir>
