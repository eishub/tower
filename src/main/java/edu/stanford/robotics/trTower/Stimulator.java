package edu.stanford.robotics.trTower;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.stanford.robotics.trTower.actionTower.ActionTower;
import edu.stanford.robotics.trTower.gui.ActionTowerPanel;
import edu.stanford.robotics.trTower.gui.BasicModelPanel;
import edu.stanford.robotics.trTower.gui.ModelPanel;
import edu.stanford.robotics.trTower.modelTower.ModelTower;
import edu.stanford.robotics.trTower.perceptionTower.PerceptionTower;
import edu.stanford.robotics.trTower.virtualWorld.VirtualWorld;

/**
 * A stimulator is just an Observable object. You subscribe with add...Listener
 * and step() will call stimuStep() of all subscribed listeners
 *
 * typical listeners (see TRWorld.java) are: 1. VirtualWorld 2. PerceptionTower
 * 3. ActionTower
 */
public class Stimulator {
	private VirtualWorld virtualWorld;

	public VirtualWorld getVirtualWorld() {
		return this.virtualWorld;
	}

	public void setVirtualWorld(final VirtualWorld vw) {
		this.virtualWorld = vw;
	}

	private ModelTower modelTower;

	public ModelTower getModelTower() {
		return this.modelTower;
	}

	public void setModelTower(final ModelTower m) {
		this.modelTower = m;
	}

	private PerceptionTower perceptionTower;

	public PerceptionTower getPerceptionTower() {
		return this.perceptionTower;
	}

	public void setPerceptionTower(final PerceptionTower p) {
		this.perceptionTower = p;
	}

	private ActionTower actionTower;

	public ActionTower getActionTower() {
		return this.actionTower;
	}

	public void setActionTower(final ActionTower a) {
		this.actionTower = a;
	}

	// [fix this: change this to allow other components to register themselves]
	private ActionTowerPanel actionTowerPanel;

	public ActionTowerPanel getActionTowerPanel() {
		return this.actionTowerPanel;
	}

	public void setActionTowerPanel(final ActionTowerPanel atp) {
		this.actionTowerPanel = atp;
	}

	private BasicModelPanel basicModelPanel;

	public BasicModelPanel getBasicModelPanel() {
		return this.basicModelPanel;
	}

	public void setBasicModelPanel(final BasicModelPanel m) {
		this.basicModelPanel = m;
	}

	private ModelPanel modelPanel;

	public ModelPanel getModelPanel() {
		return this.modelPanel;
	}

	public void setModelPanel(final ModelPanel m) {
		this.modelPanel = m;
	}

	private boolean toBeReset = false;

	public boolean isToBeReset() {
		return this.toBeReset;
	}

	public void setToBeReset(final boolean b) {
		this.toBeReset = b;
		resetAttributes();
	}

	private boolean toAddNewBlock = false;

	public boolean isToAddNewBlock() {
		return this.toAddNewBlock;
	}

	public void setToAddNewBlock(final boolean b) {
		this.toAddNewBlock = b;
	}

	private final List<StimulatorListener> stimulatorListenerList = new LinkedList<>();

	public synchronized void addStimulatorListener(final StimulatorListener l) {
		this.stimulatorListenerList.add(l);
	}

	public synchronized void removeStimulatorListener(final StimulatorListener l) {
		this.stimulatorListenerList.remove(l);
	}

	public synchronized void step() {
		final Iterator<StimulatorListener> si = this.stimulatorListenerList.iterator();
		while (si.hasNext()) {
			final StimulatorListener sl = (si.next());
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
	}

	protected void resetAttributes() {
		setToAddNewBlock(false);
	}

	protected void reset() {
		getVirtualWorld().reset();
		getActionTower().reset();
		getModelTower().clearModelTower();
		getBasicModelPanel().displayModel();
	}
}
