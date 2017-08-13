package com.cc.commonrule;

/**
 * @author fairjm
 *
 */
public interface Rule<T> {
    /**
     * get rule name
     *
     * @param input
     * @return
     */
    String getRuleName();

    /**
     * get rule priority.From high to low
     *
     * @return
     */
    int getPriority();

    /**
     * content that presents the rule
     *
     * @return
     */
    T getContent();
}
