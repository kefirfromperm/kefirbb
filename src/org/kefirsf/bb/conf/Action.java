package org.kefirsf.bb.conf;

/**
 * Actions with variables in pattern element
 * @author Vitalii Samolovskikh
 */
public enum Action {
    /* By default rewrite the variable value */
    rewrite,
    /* Add current value to the context value */
    append,
    /* Check if the current variable value is equals the context's value*/
    check
}
