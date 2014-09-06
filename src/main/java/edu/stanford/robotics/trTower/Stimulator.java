package edu.stanford.robotics.trTower;

import java.util.*;

import edu.stanford.robotics.trTower.virtualWorld.*;
import edu.stanford.robotics.trTower.modelTower.*;
import edu.stanford.robotics.trTower.perceptionTower.*;
import edu.stanford.robotics.trTower.actionTower.*;
import edu.stanford.robotics.trTower.gui.*;

/** 
 A stimulator is just an Observable object. 
 You subscribe with add...Listener and 
 step() will call stimuStep() of all subscribed listeners

 typical listeners (see TRWorld.java) are:
 1. VirtualWorld
 2. PerceptionTower
 3. ActionTower
*/
public class Stimulator {

	//--- attributes
    private VirtualWorld virtualWorld;
    public VirtualWorld getVirtualWorld() { return virtualWorld; }
    public void setVirtualWorld(VirtualWorld vw) { virtualWorld = vw; }

    private ModelTower modelTower;
    public ModelTower getModelTower() { return modelTower; }
    public void setModelTower(ModelTower m) { modelTower = m; }
    private PerceptionTower perceptionTower;
    public PerceptionTower getPerceptionTower() { return perceptionTower; }
    public void setPerceptionTower(PerceptionTower p) { perceptionTower = p; }

    private ActionTower actionTower;
    public ActionTower getActionTower() { return actionTower; }
    public void setActionTower(ActionTower a) { actionTower = a; }

    // [fix this: change this to allow other components to register themselves]
    private ActionTowerPanel actionTowerPanel;
    public ActionTowerPanel getActionTowerPanel() { return actionTowerPanel; }
    public void setActionTowerPanel(ActionTowerPanel atp) { actionTowerPanel = atp; }
    private BasicModelPanel basicModelPanel;
    public BasicModelPanel getBasicModelPanel() { return basicModelPanel; }
    public void setBasicModelPanel(BasicModelPanel m) { basicModelPanel = m; }
    private ModelPanel modelPanel;
    public ModelPanel getModelPanel() { return modelPanel; }
    public void setModelPanel(ModelPanel m) { modelPanel = m; }

    private boolean toBeReset = false;
    public boolean isToBeReset() { return toBeReset; }
    public void setToBeReset(boolean b) { 
    	toBeReset = b; 
    	resetAttributes();
    }

    private boolean toAddNewBlock = false;
    public boolean isToAddNewBlock() { return toAddNewBlock; }
    public void setToAddNewBlock(boolean b) { toAddNewBlock = b; }

    
    private List stimulatorListenerList = new ArrayList();
    
    // --- public methods
    public synchronized void addStimulatorListener(StimulatorListener l) {
	stimulatorListenerList.add(l);
    }
    public synchronized void removeStimulatorListener(StimulatorListener l) {
	stimulatorListenerList.remove(l);
    }

    public synchronized void step() {
	Iterator si = stimulatorListenerList.iterator();
	while (si.hasNext()) {
	    StimulatorListener sl = (StimulatorListener)(si.next());
	    sl.stimuStep();
	}
    }

    public void step_backup() {
  	if (isToBeReset()) {
  	    reset();
  	    setToBeReset(false);
  	}
  	if (isToAddNewBlock()) {
  	    getVirtualWorld().addNewBlock();
  	    setToAddNewBlock(false);
  	}
// code broken...
//  	getPerceptionTower().step();
//  	getBasicModelPanel().displayModel();
//  	getModelPanel().displayModel();
//  	getActionTower().step();
//  	getActionTowerPanel().displayTrace();
//  	getVirtualWorld().animStep();
    }
  	
  	protected void resetAttributes() {
  		setToAddNewBlock(false);
  	}

    protected void reset() {
    	getVirtualWorld().reset();
    	getActionTower().reset();
    	getModelTower().clearModelTower();
    	getBasicModelPanel().displayModel();
// code broken...
//    	getActionTowerPanel().displayTrace();
    }
}
