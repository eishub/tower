package edu.stanford.robotics.trTower.perceptionTower;

import edu.stanford.robotics.trTower.modelTower.ModelTower;

abstract class RuleGroup implements Rule {
	boolean hasNextInstance() {
		return false;
	}

	void nextInstance() {
	}

	private ModelTower modelTower;

	@Override
	public ModelTower getModelTower() {
		return this.modelTower;
	}

	@Override
	public void setModelTower(final ModelTower mt) {
		this.modelTower = mt;
	}
}
