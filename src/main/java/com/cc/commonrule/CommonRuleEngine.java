package com.cc.commonrule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.script.Bindings;
import javax.script.SimpleBindings;

import com.cc.commonrule.exception.CommonRuleLookUpException;
import com.cc.commonrule.exception.CommonRuleMatchException;

/**
 * @author fairjm
 *
 */
public class CommonRuleEngine {

    private static final String GROOVY_SCRIPT_FACT_MAP_NAME = "fact";

    private volatile List<CommonRule> sRules;

    /**
     * check if the fact matches the rule
     * 
     * @param rule
     * @param fact
     * @return
     * @throws CommonRuleMatchException
     */
    public static boolean match(CommonRule rule, Map<String, Object> fact) throws CommonRuleMatchException {
        final Bindings b = new SimpleBindings();
        b.put(GROOVY_SCRIPT_FACT_MAP_NAME, Collections.unmodifiableMap(fact));
        try {
            return (boolean) rule.getContent().eval(b);
        } catch (final Exception e) {
            throw new CommonRuleMatchException(e);
        }
    }

    /**
     * get the rule that matches the input fact
     *
     * @param fact
     * @return
     * @throws CommonRuleLookUpException
     */
    public Optional<Rule> lookUp(Map<String, Object> fact) throws CommonRuleLookUpException {
        Rule current = null;
        final Bindings b = new SimpleBindings();
        final List<CommonRule> currentRules = sRules;
        b.put(GROOVY_SCRIPT_FACT_MAP_NAME, Collections.unmodifiableMap(fact));
        try {
            for (final CommonRule rule : currentRules) {
                current = rule;
                if (Boolean.TRUE.equals(rule.getContent().eval(b))) {
                    return Optional.of(current);
                }
            }
        } catch (final Exception e) {
            throw new CommonRuleLookUpException(current, e);
        }
        return Optional.empty();
    }

    public List<CommonRule> getRules() {
        return sRules;
    }

    public void setRules(Collection<CommonRule> rules) {
        final List<CommonRule> list = new ArrayList<>(rules);
        Collections.sort(list, Comparator.comparingInt(CommonRule::getPriority).reversed());
        sRules = Collections.unmodifiableList(list);
    }
}
