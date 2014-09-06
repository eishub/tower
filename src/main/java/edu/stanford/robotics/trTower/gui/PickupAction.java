package edu.stanford.robotics.trTower.gui;

import edu.stanford.robotics.trTower.virtualWorld.Block;
import edu.stanford.robotics.trTower.virtualWorld.DurativeArmAction;

public class PickupAction extends DurativeArmAction {

	// --- properties
	private String targetBlockId;

	String getTargetBlockId() {
		return targetBlockId;
	}

	public void setTargetBlockId(String id) {
		targetBlockId = id;
	}

	public void actionStep() {

		if (!getVirtualWorldModel().isAvailable()) {
			setStatusMessage("External perturbation.");
			return;
		}

		setStatusMessage("");

		if (getTargetBlockId() == null) {
			System.out.println("PickupAction> getTargetBlockId() is null");
			return;
			// System.exit(-1);
		}

		if (getHostRobotArm().getBlockHeld() != null) {
			if (getHostRobotArm().getBlockHeld().getId().equals(
					getTargetBlockId())) {
				// holding block already, do nothing, return
				return;
			} else {
				// holding some other block, cannot perform the pickup action
				// System.out.println("PickupAction> Already holding "
				// + getHostRobotArm().getBlockHeld().getId()
				// + ". Cannot pick up "
				// + getTargetBlockId() + ".");
				setStatusMessage("Already holding "
						+ getHostRobotArm().getBlockHeld().getId()
						+ ". Cannot pick up " + getTargetBlockId() + ".");
				// simply do nothing, and return
				return;
			}
		} else {
			// not holding any block

			// get target block
			Block targetBlock = (Block) (getVirtualWorldModel()
					.findVirtualObject(getTargetBlockId()));
			if (targetBlock == null) {
				// System.out.println("PickupAction> Target Block does not exist.");
				setStatusMessage("Pickup target block does not exist.");
				// do nothing, return
				return;
			}
			if (!getVirtualWorldModel().isBlockClear(targetBlock)) {
				// target not clear, do nothing, return
				// System.out.println("PickupAction> Target block "
				// + getTargetBlockId()
				// + " not clear.");
				setStatusMessage("Pickup target block " + getTargetBlockId()
						+ " not clear.");
				return;
			} else {
				// set target coord
				setTargetCoord(targetBlock.getCoord().getX(), targetBlock
						.getCoord().getY()
						- getBlockHeight());
			}

			// motion
			if (isTargetCoordMatched()
					&& getHostRobotArm().isClawCompletelyClosed()) {
				// x and y matched
				targetBlock.setBeingHeld(true);
				// targetBlock.setHoldingRobotArm(getHostRobotArm());
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
