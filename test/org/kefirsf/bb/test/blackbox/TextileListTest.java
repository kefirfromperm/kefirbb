package org.kefirsf.bb.test.blackbox;

import org.junit.Before;
import org.junit.Test;
import org.kefirsf.bb.BBProcessorFactory;
import org.kefirsf.bb.test.standard.AbstractConfigurationTest;

/**
 * Tests for testile style lists
 */
public class TextileListTest extends AbstractConfigurationTest {
    /**
     * Create Textile text processor
     */
    @Before
    public void createProcessor() {
        processor = BBProcessorFactory.getInstance().createFromResource(
                "org/kefirsf/bb/test/blackbox/config-list-textile.xml"
        );
    }

    /**
     * Simple lists
     * http://txstyle.org/doc/8/bullet-list
     * http://txstyle.org/doc/9/numbered-lists
     */
    @Test
    public void testSimpleLists(){
        assertProcess(
                "<ul><li>1</li><li>2</li><li>3</li></ul>",
                "* 1\n* 2\n* 3\n"
        );

        assertProcess(
                "<ol><li>1</li><li>2</li><li>3</li></ol>",
                "# 1\n# 2\n# 3\n"
        );
    }

    /**
     * Bullet lists
     * http://txstyle.org/doc/8/bullet-list
     */
    @Test
    public void testBulletList(){
        assertProcess(
                "<ul><li>1</li><li>2</li><li>3</li></ul>",
                "* 1\n* 2\n* 3\n"
        );
        assertProcess(
                "<ul><li>1</li><li><ul><li>2</li><li>3</li><li>4</li></ul></li><li>5</li></ul>",
                "* 1\n** 2\n** 3\n** 4\n* 5\n"
        );
        assertProcess(
                "<ul><li>1</li><li><ul><li>2</li><li>3</li></ul></li><li>4</li><li><ul><li>5</li><li>6</li></ul></li><li>7</li></ul>",
                "* 1\n** 2\n** 3\n* 4\n** 5\n** 6\n* 7\n"
        );
        assertProcess(
                "<ul><li>1</li><li><ul><li>2</li><li><ul><li>3</li></ul></li></ul></li></ul>",
                "* 1\n** 2\n*** 3\n"
        );
        assertProcess(
                "<ul><li><ul><li><ul><li>1</li></ul></li><li>2</li></ul></li><li>3</li></ul>",
                "*** 1\n** 2\n* 3\n"
        );
    }

    /**
     * Numbered lists
     * http://txstyle.org/doc/9/numbered-lists
     */
    @Test
    public void testNumberedList(){
        assertProcess(
                "<ol><li>1</li><li>2</li><li>3</li></ol>",
                "# 1\n# 2\n# 3\n"
        );
        assertProcess(
                "<ol><li>1</li><li><ol><li>2</li><li>3</li><li>4</li></ol></li><li>5</li></ol>",
                "# 1\n## 2\n## 3\n## 4\n# 5\n"
        );
        assertProcess(
                "<ol><li>1</li><li><ol><li>2</li><li>3</li></ol></li><li>4</li><li><ol><li>5</li><li>6</li></ol></li><li>7</li></ol>",
                "# 1\n## 2\n## 3\n# 4\n## 5\n## 6\n# 7\n"
        );
        assertProcess(
                "<ol><li>1</li><li><ol><li>2</li><li><ol><li>3</li></ol></li></ol></li></ol>",
                "# 1\n## 2\n### 3\n"
        );
        assertProcess(
                "<ol><li><ol><li><ol><li>1</li></ol></li><li>2</li></ol></li><li>3</li></ol>",
                "### 1\n## 2\n# 3\n"
        );
    }

    /**
     * Mixed lists
     * http://txstyle.org/doc/8/bullet-list
     * http://txstyle.org/doc/9/numbered-lists
     */
    @Test
    public void testMixedList(){
        assertProcess(
                "<ul><li>1</li><li><ol><li>2</li><li>3</li><li>4</li></ol></li><li>5</li></ul>",
                "* 1\n*# 2\n*# 3\n*# 4\n* 5\n"
        );

        assertProcess(
                "<ol><li>1</li><li><ul><li>2</li><li>3</li><li>4</li></ul></li><li>5</li></ol>",
                "# 1\n#* 2\n#* 3\n#* 4\n# 5\n"
        );
    }
}
