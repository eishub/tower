package edu.stanford.robotics.trTower.gui;

import edu.stanford.robotics.trTower.virtualWorld.DurativeArmAction;

public class NilAction extends DurativeArmAction {

	public void actionStep() {

		if (!getVirtualWorldModel().isAvailable()) {
			setStatusMessage("External perturbation.");
			return;
		}

		setStatusMessage("");

		if (isRestCoordMatched()) {
			// matched, nothing needs to be done
		} else if (isRestYMatched()) {
			followRestX();
			getVirtualWorldModel().setChanged(true);
		} else {
			followRestY();
			getVirtualWorldModel().setChanged(true);
		}
	}
}
