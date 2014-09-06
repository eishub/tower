package edu.stanford.robotics.trTower.gui;

import edu.stanford.robotics.trTower.virtualWorld.Block;
import edu.stanford.robotics.trTower.virtualWorld.DurativeArmAction;

public class PutdownAction extends DurativeArmAction {

	// --- propertes
	private String subjectBlockId;

	String getSubjectBlockId() {
		return subjectBlockId;
	}

	public void setSubjectBlockId(String s) {
		subjectBlockId = s;
	}

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

		if (getSubjectBlockId() == null) {
			System.out.println("PutdownAction> getSubjectBlockId() is null");
			return;
			// System.exit(-1);
		}
		if (getTargetBlockId() == null) {
			System.out.println("PutdownAction> getTargetBlockId() is null");
			return;
			// System.exit(-1);
		}

		Block blockHeld = getHostRobotArm().getBlockHeld();
		if (blockHeld == null) {
			// arm holding nothing, return
			// System.out.println("PutdownAction> Arm holding no block.");
			setStatusMessage("Arm not holding any block.");
			return;
		} else {
			// arm holding something
			if (!blockHeld.getId().equals(getSubjectBlockId())) {
				// not holding subject, nothing could be done, return
				// System.out.println("PutdownAction> Arm holding "
				// + blockHeld.getId()
				// + " instead of "
				// + getSubjectBlockId() + ".");
				setStatusMessage("Arm holding " + blockHeld.getId()
						+ " instead of " + getSubjectBlockId() + ".");
				return;
			} else {
				// arm holding subject block

				// check target (see if it is Table)
				if (getTargetBlockId().equals(
						getVirtualWorldModel().getTableId())) {
					// putdown on table
					int closestEmptyColumn = getVirtualWorldModel()
							.findClosestEmptyColumnFromXCoord(
									blockHeld.getCoord().getX());
					if (closestEmptyColumn == -1) {
						// System.out.println("PutdownAction> No space on table to put down the block.");
						setStatusMessage("No space on table to put down the block.");
						return;
					}

					// set target coord
					setTargetCoord(getVirtualWorldModel().columnToXCoord(
							closestEmptyColumn), getVirtualWorldModel()
							.getFloorY()
							- getBlockHeight());

				} else {
					// check target
					Block targetBlock = (Block) getVirtualWorldModel()
							.findVirtualObject(getTargetBlockId());

					if (targetBlock == null) {
						// target does not exist, do nothing, return
						// System.out.println("PutdownAction> Target block "
						// + getTargetBlockId()
						// + " does not exist.");
						setStatusMessage("Destination block "
								+ getTargetBlockId() + " does not exist.");
						return;
					}

					if (getTargetBlockId().equals(getSubjectBlockId())) {
						// target == subject
						setStatusMessage("Cannot put block on itself.");
						return;
					}

					if (!getVirtualWorldModel().isBlockClear(targetBlock)) {
						// target not clear, do nothing, return
						// System.out.println("PutdownActin> Target block "
						// + getTargetBlockId()
						// + " not clear.");
						setStatusMessage("Destination block "
								+ getTargetBlockId() + " not clear.");

						return;
					}

					// now know that target exists and clear
					// set target coordinates (on top of target)
					setTargetCoord(targetBlock.getCoord().getX(), targetBlock
							.getCoord().getY()
							- 2 * getBlockHeight());
				}

				// motion
				if (isTargetCoordMatched()
						&& getHostRobotArm().isClawCompletelyOpen()) {
					// drop off the subject block
					blockHeld.setBeingHeld(false);
					// blockHeld.setHoldingRobotArm(null);
					getHostRobotArm().setBlockHeld(null);
				}
				if (isTargetCoordMatched()) {
					// open up claw
					getHostRobotArm().openClaw();
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
}
