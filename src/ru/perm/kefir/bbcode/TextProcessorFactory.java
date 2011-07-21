package ru.perm.kefir.bbcode;

/**
 * The TextProcessor factory interface
 *
 * @author Kefir
 */
public interface TextProcessorFactory {
    /**
     * Create the TextProcessor instance
     *
     * @return instance of TextProcessor interface
     * @throws TextProcessorFactoryException when factory can't create the TextProcessor instance
     */
    public TextProcessor create();
}
