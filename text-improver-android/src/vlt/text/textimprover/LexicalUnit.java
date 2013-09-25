package vlt.text.textimprover;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class LexicalUnit {
    private Set<Rule> ruleSet = new HashSet<Rule>();
    private String prefix;
    private int occurenceChance;

    public LexicalUnit() {
	this.prefix = "";
    }

    public LexicalUnit(String prefix) {
	setPrefix(prefix);
    }

    public Rule checkAgainstRules(String word) {
	Iterator<Rule> it = ruleSet.iterator();
	Rule result = null;
	Rule item;
	while (it.hasNext()) {
	    item = it.next();
	    if (item.testRuleBelonging(word)) {
		result = item;
	    }
	}
	return result;
    }

    public String improveWord(String word) {
	Rule rule = checkAgainstRules(word);
	if (word.length() <= rule.getAllowedLength()) {
	    return word;
	}
	word = word.replaceFirst(rule.getThrowAwayRegex(),
		prefix + rule.getOutcomeLetter());
	return word;
    }

    public boolean addToRuleSet(Rule rule) {
	return ruleSet.add(rule);
    }

    public String getPrefix() {
	return prefix;
    }

    public void setPrefix(String prefix) {
	this.prefix = prefix;
    }

    public int getOccurenceChance() {
	return occurenceChance;
    }

    public void setOccurenceChance(int occurenceChance) {
	this.occurenceChance = occurenceChance;
    }
}
