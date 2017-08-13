package com.cc.commonrule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * @author fairjm
 *
 */
public class RuleBaseTest {

    @Test
    public void baseTest() throws Exception {
        final Map<String, Object> fact = new HashMap<>();
        fact.put("age", 18);
        fact.put("name", "cc");
        fact.put("male?", true);
        fact.put("favors", Arrays.asList("coding", "sleeping"));

        final CommonRule rule1 = CommonRuleFactory
                .build("ccFact",
                        "fact['age'] < 19 && fact['name'] == 'cc' && 'coding' in fact['favors'] && fact['male?'] ")
                .get();
        final CommonRule rule2 = CommonRuleFactory.build("notCcFact", "fact['age'] > 18 || fact['male?'] == false")
                .get();
        final CommonRule rule3 = CommonRuleFactory.build("wrongSpelling", "fact['aga'] > 18").get();
        final CommonRule rule4 = CommonRuleFactory.build("wrongSpelling2", "!fact['mal?']").get();
        final CommonRule rule5 = CommonRuleFactory.build("wrongSpelling2", "fact['mal?'] == true").get();
        assertTrue(CommonRuleEngine.match(rule1, fact));
        assertFalse(CommonRuleEngine.match(rule2, fact));
        assertFalse(CommonRuleEngine.match(rule3, fact));
        // oops...cause null in groovy return false add ! will return true.
        // so don't use ! in the script for null condition
        assertTrue(CommonRuleEngine.match(rule4, fact));
        assertFalse(CommonRuleEngine.match(rule5, fact));
    }

    @Test
    public void priorityTest() throws Exception {
        final Map<String, Object> fact = new HashMap<>();
        fact.put("age", 18);
        final CommonRule rule = CommonRuleFactory.build("ageLessThan20", "fact['age'] < 20").get();
        final CommonRule rule2 = CommonRuleFactory.build("ageLessThan19", "fact['age'] < 19", 1).get();
        final CommonRule rule3 = CommonRuleFactory.build("ageLessThan18", "fact['age'] < 18", 2).get();
        final CommonRuleEngine engine = new CommonRuleEngine();
        engine.setRules(Arrays.asList(rule, rule2, rule3));
        assertEquals(engine.lookUp(fact).get().getRuleName(), "ageLessThan19");
    }
}
