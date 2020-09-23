package edu.stanford.robotics.trTower.gui;

import edu.stanford.robotics.trTower.virtualWorld.Block;
import edu.stanford.robotics.trTower.virtualWorld.DurativeArmAction;

public class PickupAction extends DurativeArmAction {
	private String targetBlockId;

	String getTargetBlockId() {
		return this.targetBlockId;
	}

	public void setTargetBlockId(final String id) {
		this.targetBlockId = id;
	}

	@Override
	public void actionStep() {
		if (!getVirtualWorldModel().isAvailable()) {
			setStatusMessage("External perturbation.");
			return;
		}

		setStatusMessage("");

		if (getTargetBlockId() == null) {
			System.out.println("PickupAction> getTargetBlockId() is null");
			return;
		}

		if (getHostRobotArm().getBlockHeld() != null) {
			if (getHostRobotArm().getBlockHeld().getId().equals(getTargetBlockId())) {
				// holding block already, do nothing, return
				return;
			} else {
				// holding some other block, cannot perform the pickup action
				setStatusMessage("Already holding " + getHostRobotArm().getBlockHeld().getId() + ". Cannot pick up "
						+ getTargetBlockId() + ".");
				return;
			}
		} else {
			// not holding any block

			// get target block
			final Block targetBlock = (Block) (getVirtualWorldModel().findVirtualObject(getTargetBlockId()));
			if (targetBlock == null) {
				setStatusMessage("Pickup target block does not exist.");
				// do nothing, return
				return;
			}
			if (!getVirtualWorldModel().isBlockClear(targetBlock)) {
				setStatusMessage("Pickup target block " + getTargetBlockId() + " not clear.");
				return;
			} else {
				// set target coord
				setTargetCoord(targetBlock.getCoord().getX(), targetBlock.getCoord().getY() - getBlockHeight());
			}

			// motion
			if (isTargetCoordMatched() && getHostRobotArm().isClawCompletelyClosed()) {
				// x and y matched
				targetBlock.setBeingHeld(true);
				getHostRobotArm().setBlockHeld(targetBlock);
				// pick "up" block a little bit
				// [fix this] how to add this?
			} else if (isTargetCoordMatched()) {
				// close claw
				getHostRobotArm().closeClaw();
			} else if (isTargetXMatched()) {
				followTargetY();
			} else if (isRestYMatched()) {
				followTargetX();
			} else {
				// nothing is matched
				followRestY();
			}
			getVirtualWorldModel().setChanged(true);
		}
	}
}
