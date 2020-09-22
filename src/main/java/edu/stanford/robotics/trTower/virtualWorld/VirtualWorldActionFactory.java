package edu.stanford.robotics.trTower.virtualWorld;

import edu.stanford.robotics.trTower.gui.NilAction;
import edu.stanford.robotics.trTower.gui.PickupAction;
import edu.stanford.robotics.trTower.gui.PutdownAction;

public class VirtualWorldActionFactory {
	private VirtualWorld virtualWorld;

	public VirtualWorld getVirtualWorld() {
		return this.virtualWorld;
	}

	public void setVirtualWorld(final VirtualWorld vw) {
		this.virtualWorld = vw;
	}

	public NilAction createNilAction() {
		final NilAction nilAction = new NilAction();
		getVirtualWorld().initDurativeArmAction(nilAction);
		return nilAction;
	}

	public PickupAction createPickupAction() {
		final PickupAction pickupAction = new PickupAction();
		getVirtualWorld().initDurativeArmAction(pickupAction);
		return pickupAction;
	}

	public PutdownAction createPutdownAction() {
		final PutdownAction putdownAction = new PutdownAction();
		getVirtualWorld().initDurativeArmAction(putdownAction);
		return putdownAction;
	}
}
