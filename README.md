# Common Rules
A [groovy](http://www.groovy-lang.org) script based common rule engine for java.

## What is Common Rules?  
common rules is just a simple groovy script based rule engine(maybe a wrapper is more proper :>)  
As the name `common` says, it uses map as fact input and groovy script as rule content to meet common rule engine usage.

## Usage Scenario
It fits the situation that you just want to write some dynamic rules to your DB, load and execute it in you code.<br/>
All you need to do is writing `groovy` script(or you can just use java syntax, thx for the great compatible) and use map as fact.<br/>

## How to Use?
just write simple `groovy` script as the content of rule.  

	// use map as fact
	final Map<String, Object> fact = new HashMap<>();
			fact.put("age", 18);
			fact.put("name", "cc");
			fact.put("male?", true);
			fact.put("favors", Arrays.asList("coding", "sleeping"));
	// write a rule
	final CommonRule rule1 = CommonRuleFactory
					.build("ccFactRule",
							"fact['age'] < 19 && fact['name'] == 'cc' && 'coding' in fact['favors'] && fact['male?'] ")
					.get();
	// then test
	assertTrue(CommonRuleEngine.match(rule1, fact));
