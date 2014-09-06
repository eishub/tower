package edu.stanford.robotics.trTower;

import edu.stanford.robotics.trTower.virtualWorld.*;
import edu.stanford.robotics.trTower.modelTower.*;
import edu.stanford.robotics.trTower.perceptionTower.*;
import edu.stanford.robotics.trTower.actionTower.*;


public class TRWorld {

    public TRWorld() {
	connect();
    }
    // --- public components
    private VirtualWorld virtualWorld;
    public VirtualWorld getVirtualWorld() {
	if (virtualWorld == null) {
	    virtualWorld = new VirtualWorld();
	}
	return virtualWorld;
    }
    private ModelTower modelTower;
    public ModelTower getModelTower() {
	if (modelTower == null) {
	    modelTower = new ModelTower();
	}
	return modelTower;
    }
    private PerceptionTower perceptionTower;
    public PerceptionTower getPerceptionTower() {
	if (perceptionTower == null) {
	    perceptionTower = new PerceptionTower();
	}
	return perceptionTower;
    }
    private ActionTower actionTower;
    public ActionTower getActionTower() {
	if (actionTower == null) {
	    actionTower = new ActionTower();
	}
	return actionTower;
    }
    private Stimulator stimulator;
    public Stimulator getStimulator() {
	if (stimulator == null) {
	    stimulator = new Stimulator();
	}
	return stimulator;
    }
    private AnimationTimer animationTimer;
    public AnimationTimer getAnimationTimer() {
	if (animationTimer == null) {
	    animationTimer = new AnimationTimer();
	}
	return animationTimer;
    }

    // --- private methods
    private void connect() {
	getModelTower().setTableId(getVirtualWorld().getTableId());
	getActionTower().setVirtualWorld(getVirtualWorld());
	getActionTower().setModelTower(getModelTower());

	getPerceptionTower().setVirtualWorldSensor(getVirtualWorld().getVirtualWorldSensor());
	getPerceptionTower().setModelTower(getModelTower());
	
//  	getStimulator().setVirtualWorld(getVirtualWorld());
//  	getStimulator().setModelTower(getModelTower());
//  	getStimulator().setPerceptionTower(getPerceptionTower());
//  	getStimulator().setActionTower(getActionTower());

	getAnimationTimer().setStimultor(getStimulator());

	// [fix this] is order alright???
	getStimulator().addStimulatorListener(getVirtualWorld());
	//	getStimulator().addStimulatorListener(getModelTower());
	getStimulator().addStimulatorListener(getPerceptionTower());
	getStimulator().addStimulatorListener(getActionTower());

    }
    
    // --- public methods
}
