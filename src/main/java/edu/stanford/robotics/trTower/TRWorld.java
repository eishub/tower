package edu.stanford.robotics.trTower;

import edu.stanford.robotics.trTower.actionTower.ActionTower;
import edu.stanford.robotics.trTower.modelTower.ModelTower;
import edu.stanford.robotics.trTower.perceptionTower.PerceptionTower;
import edu.stanford.robotics.trTower.virtualWorld.VirtualWorld;

public class TRWorld {
	public TRWorld() {
		connect();
	}

	private VirtualWorld virtualWorld;

	public VirtualWorld getVirtualWorld() {
		if (this.virtualWorld == null) {
			this.virtualWorld = new VirtualWorld();
		}
		return this.virtualWorld;
	}

	private ModelTower modelTower;

	public ModelTower getModelTower() {
		if (this.modelTower == null) {
			this.modelTower = new ModelTower();
		}
		return this.modelTower;
	}

	private PerceptionTower perceptionTower;

	public PerceptionTower getPerceptionTower() {
		if (this.perceptionTower == null) {
			this.perceptionTower = new PerceptionTower();
		}
		return this.perceptionTower;
	}

	private ActionTower actionTower;

	public ActionTower getActionTower() {
		if (this.actionTower == null) {
			this.actionTower = new ActionTower();
		}
		return this.actionTower;
	}

	private Stimulator stimulator;

	public Stimulator getStimulator() {
		if (this.stimulator == null) {
			this.stimulator = new Stimulator();
		}
		return this.stimulator;
	}

	private AnimationTimer animationTimer;

	public AnimationTimer getAnimationTimer() {
		if (this.animationTimer == null) {
			this.animationTimer = new AnimationTimer();
		}
		return this.animationTimer;
	}

	private void connect() {
		getModelTower().setTableId(getVirtualWorld().getTableId());
		getActionTower().setVirtualWorld(getVirtualWorld());
		getActionTower().setModelTower(getModelTower());

		getPerceptionTower().setVirtualWorldSensor(getVirtualWorld().getVirtualWorldSensor());
		getPerceptionTower().setModelTower(getModelTower());

		getAnimationTimer().setStimultor(getStimulator());

		// [fix this] is order alright???
		getStimulator().addStimulatorListener(getVirtualWorld());
		getStimulator().addStimulatorListener(getPerceptionTower());
		getStimulator().addStimulatorListener(getActionTower());

	}
}
