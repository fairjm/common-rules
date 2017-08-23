package com.cc.commonrule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cc.commonrule.exception.CommonRuleBuildException;
import com.cc.commonrule.exception.CommonRuleLookUpException;

/**
 * test 100 rules run 10000 facts
 *
 * @author fairjm
 *
 */
public class RulePerformanceTest {

    private static final String RULE_TEMPLATE = "fact['age'] > %d && fact['name'] == '%s' && fact['male?']";

    private static final int RULE_NUM = 100;

    private static final int FACT_NUM = 100_00;

    public static void main(String[] args)
            throws CommonRuleBuildException, CommonRuleLookUpException {
        final List<CommonRule> rules = new ArrayList<CommonRule>();
        for (int i = 0; i < RULE_NUM; i++) {
            final CommonRule rule = CommonRuleFactory
                    .build("rule-" + i, String.format(RULE_TEMPLATE, i, "name-" + i, i % 2 == 0))
                    .get();
            rules.add(rule);
        }
        final CommonRuleEngine engine = new CommonRuleEngine();
        engine.setRules(rules);

        // generate request
        final List<Map<String, Object>> facts = new ArrayList<>(FACT_NUM);
        for (int i = 0; i < FACT_NUM; i++) {
            final Map<String, Object> fact = new HashMap<>();
            fact.put("age", i + 1);
            fact.put("name", "name-" + i);
            fact.put("male?", i % 2 == 0);
            facts.add(fact);
        }

        final long initStart = System.currentTimeMillis();
        for (final Map<String, Object> fact : facts) {
            engine.lookUp(fact);
        }
        System.out.println("init start elapsed:" + (System.currentTimeMillis() - initStart));

        // simple warm up
        for (int i = 0; i < 10; i++) {
            for (final Map<String, Object> fact : facts) {
                engine.lookUp(fact);
            }
        }

        long max = 0;
        long min = Long.MAX_VALUE;
        long all = 0;
        final List<Long> records = new ArrayList<>(10);

        for (int i = 0; i < 10; i++) {
            final long start = System.currentTimeMillis();
            for (final Map<String, Object> fact : facts) {
                engine.lookUp(fact);
            }
            final long time = System.currentTimeMillis() - start;
            if (time > max) {
                max = time;
            }
            if (time < min) {
                min = time;
            }
            records.add(time);
            all += time;
        }
        System.out.println("max time:" + max);
        System.out.println("min time:" + min);
        System.out.println("avg:" + (all / 10.));
        System.out.println("records:" + records);
    }
}
