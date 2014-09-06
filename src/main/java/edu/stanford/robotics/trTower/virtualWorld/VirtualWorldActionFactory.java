package edu.stanford.robotics.trTower.virtualWorld;

import edu.stanford.robotics.trTower.gui.NilAction;
import edu.stanford.robotics.trTower.gui.PickupAction;
import edu.stanford.robotics.trTower.gui.PutdownAction;

public class VirtualWorldActionFactory {

	// properties
	private VirtualWorld virtualWorld;

	public VirtualWorld getVirtualWorld() {
		return virtualWorld;
	}

	public void setVirtualWorld(VirtualWorld vw) {
		virtualWorld = vw;
	}

	public NilAction createNilAction() {
		NilAction nilAction = new NilAction();
		getVirtualWorld().initDurativeArmAction(nilAction);
		return nilAction;
	}

	public PickupAction createPickupAction() {
		PickupAction pickupAction = new PickupAction();
		getVirtualWorld().initDurativeArmAction(pickupAction);
		return pickupAction;
	}

	public PutdownAction createPutdownAction() {
		PutdownAction putdownAction = new PutdownAction();
		getVirtualWorld().initDurativeArmAction(putdownAction);
		return putdownAction;
	}
}
