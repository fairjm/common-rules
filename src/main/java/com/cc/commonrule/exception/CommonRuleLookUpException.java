package com.cc.commonrule.exception;

import com.cc.commonrule.Rule;

/**
 * @author fairjm
 *
 */
public class CommonRuleLookUpException extends Exception {

    private static final long serialVersionUID = -2635935154889324097L;

    private final Rule errorRule;

    public CommonRuleLookUpException(Rule rule, Exception e) {
        super(e);
        this.errorRule = rule;
    }

    public Rule getErrorRule() {
        return errorRule;
    }

}
