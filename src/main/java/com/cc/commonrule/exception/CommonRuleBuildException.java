package com.cc.commonrule.exception;

/**
 * @author fairjm
 *
 */
public class CommonRuleBuildException extends Exception {

    private static final long serialVersionUID = 3698301742582768724L;

    private final String errorScript;

    public CommonRuleBuildException(String script, Exception e) {
        super(e);
        this.errorScript = script;
    }

    public String getErrorScript() {
        return errorScript;
    }
}
