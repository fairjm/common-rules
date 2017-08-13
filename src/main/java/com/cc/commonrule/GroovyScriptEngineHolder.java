package com.cc.commonrule;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngineManager;

/**
 * groovy script engine holder <br/>
 * just for inner use.
 *
 * @author fairjm
 *
 */
final class GroovyScriptEngineHolder {

    private static class InnerHolder {
        /**
         * use {@link Compilable} to generate {@link CompiledScript}
         */
        public static final Compilable INSTANCE;
        static {
            final ScriptEngineManager factory = new ScriptEngineManager();
            INSTANCE = (Compilable) factory.getEngineByName("groovy");
        }

        private InnerHolder() {
        }
    }

    private GroovyScriptEngineHolder() {
    }

    public static Compilable getGroovyEngine() {
        return InnerHolder.INSTANCE;
    }

}
