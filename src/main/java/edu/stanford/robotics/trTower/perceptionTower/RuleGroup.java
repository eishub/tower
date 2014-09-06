package edu.stanford.robotics.trTower.perceptionTower;

import edu.stanford.robotics.trTower.modelTower.ModelTower;

/**
 *
 */
abstract class RuleGroup implements Rule {

	boolean hasNextInstance() {
		return false;
	}

	void nextInstance() {
	}

	// --- attributes
	private ModelTower modelTower;

	public ModelTower getModelTower() {
		return modelTower;
	}

	public void setModelTower(ModelTower mt) {
		modelTower = mt;
	}

	/*
	 * code broken... private ruleScheduler; RuleIterator getRuleScheduler() {
	 * return ruleScheduler; } void setRuleScheduler(RuleScheduler rs) {
	 * ruleScheduler = rs; }
	 */
}
