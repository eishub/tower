package edu.stanford.robotics.trTower.perceptionTower;

import edu.stanford.robotics.trTower.modelTower.ModelTower;

public interface Rule {
	void execute();

	boolean isConditionTrue();

	boolean isTargetChanged();

	String getId();

	@Override
	String toString();

	boolean evalCondition();

	boolean updateTarget();

	Object getTarget();

	void setTarget(Object t);

	ModelTower getModelTower();

	void setModelTower(ModelTower mt);

	void setRuleScheduler(RuleScheduler rs);
}
