package com.cc.commonrule;

import java.util.ArrayList;
import java.util.List;

import com.cc.commonrule.exception.CommonRuleBuildException;

/**
 * just test memory usage by generating lots rules
 *
 * @author fairjm
 *
 */
public class RuleMemoryTest {

    private static final String RULE_TEMPLATE = "fact['age'] > %d && fact['name'] == '%s' && fact['male?']";

    private static final int ITERATE_NUM = 10_000;

    public static void main(String[] args) throws CommonRuleBuildException {
        final List<CommonRule> rules = new ArrayList<CommonRule>();
        for (int i = 0; i < ITERATE_NUM; i++) {
            final CommonRule rule = CommonRuleFactory
                    .build("rule-" + i, String.format(RULE_TEMPLATE, i, "name-" + i, i % 2 == 0))
                    .get();
            rules.add(rule);
        }
    }
}
