package com.cc.commonrule;

import javax.script.CompiledScript;

/**
 * just a {@link CompiledScript} holder
 *
 * @author fairjm
 *
 */
public class CommonRule implements Rule<CompiledScript> {

    private final String name;

    private int priority = Integer.MIN_VALUE;

    private final CompiledScript script;

    CommonRule(String name, CompiledScript script) {
        this.name = name;
        this.script = script;
    }

    CommonRule(String name, CompiledScript script, int priority) {
        this.name = name;
        this.script = script;
        this.priority = priority;
    }

    @Override
    public String getRuleName() {
        return name;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public CompiledScript getContent() {
        return script;
    }

}
