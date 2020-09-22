package edu.stanford.robotics.trTower.perceptionTower;

import edu.stanford.robotics.trTower.modelTower.ModelTower;

public interface RuleScheduler {
	boolean isStable();

	Rule getNextRule();

	ModelTower getModelTower();

	void setModelTower(ModelTower m);
}
