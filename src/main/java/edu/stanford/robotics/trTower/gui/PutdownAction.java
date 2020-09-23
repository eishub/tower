package edu.stanford.robotics.trTower.gui;

import edu.stanford.robotics.trTower.virtualWorld.Block;
import edu.stanford.robotics.trTower.virtualWorld.DurativeArmAction;

public class PutdownAction extends DurativeArmAction {
	private String subjectBlockId;

	String getSubjectBlockId() {
		return this.subjectBlockId;
	}

	public void setSubjectBlockId(final String s) {
		this.subjectBlockId = s;
	}

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

		if (getSubjectBlockId() == null) {
			System.out.println("PutdownAction> getSubjectBlockId() is null");
			return;
		}
		if (getTargetBlockId() == null) {
			System.out.println("PutdownAction> getTargetBlockId() is null");
			return;
		}

		final Block blockHeld = getHostRobotArm().getBlockHeld();
		if (blockHeld == null) {
			// arm holding nothing, return
			setStatusMessage("Arm not holding any block.");
			return;
		} else {
			// arm holding something
			if (!blockHeld.getId().equals(getSubjectBlockId())) {
				// not holding subject, nothing could be done, return
				setStatusMessage("Arm holding " + blockHeld.getId() + " instead of " + getSubjectBlockId() + ".");
				return;
			} else {
				// arm holding subject block

				// check target (see if it is Table)
				if (getTargetBlockId().equals(getVirtualWorldModel().getTableId())) {
					// putdown on table
					final int closestEmptyColumn = getVirtualWorldModel()
							.findClosestEmptyColumnFromXCoord(blockHeld.getCoord().getX());
					if (closestEmptyColumn == -1) {
						setStatusMessage("No space on table to put down the block.");
						return;
					}

					// set target coord
					setTargetCoord(getVirtualWorldModel().columnToXCoord(closestEmptyColumn),
							getVirtualWorldModel().getFloorY() - getBlockHeight());

				} else {
					// check target
					final Block targetBlock = (Block) getVirtualWorldModel().findVirtualObject(getTargetBlockId());

					if (targetBlock == null) {
						// target does not exist, do nothing, return
						setStatusMessage("Destination block " + getTargetBlockId() + " does not exist.");
						return;
					}

					if (getTargetBlockId().equals(getSubjectBlockId())) {
						// target == subject
						setStatusMessage("Cannot put block on itself.");
						return;
					}

					if (!getVirtualWorldModel().isBlockClear(targetBlock)) {
						// target not clear, do nothing, return
						setStatusMessage("Destination block " + getTargetBlockId() + " not clear.");
						return;
					}

					// now know that target exists and clear
					// set target coordinates (on top of target)
					setTargetCoord(targetBlock.getCoord().getX(), targetBlock.getCoord().getY() - 2 * getBlockHeight());
				}

				// motion
				if (isTargetCoordMatched() && getHostRobotArm().isClawCompletelyOpen()) {
					// drop off the subject block
					blockHeld.setBeingHeld(false);
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
