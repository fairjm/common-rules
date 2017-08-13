package com.cc.commonrule;

import java.util.Optional;

import javax.script.Compilable;
import javax.script.CompiledScript;

import com.cc.commonrule.exception.CommonRuleBuildException;

/**
 * @author fairjm
 *
 */
public final class CommonRuleFactory {

    private CommonRuleFactory() {
    }

    public static Optional<CommonRule> build(String name, String scriptString) throws CommonRuleBuildException {
        return build(name, scriptString, Integer.MIN_VALUE);
    }

    public static Optional<CommonRule> build(String name, String scriptString, int priority)
            throws CommonRuleBuildException {
        final Compilable engine = GroovyScriptEngineHolder.getGroovyEngine();
        try {
            final CompiledScript script = engine.compile(scriptString);
            return Optional.of(new CommonRule(name, script, priority));
        } catch (final Exception e) {
            throw new CommonRuleBuildException(scriptString, e);
        }
    }
}
