package edu.stanford.robotics.trTower.virtualWorld;

import java.awt.geom.Point2D;

/**
 * DurativeArmAction is a general action that moves blocks around. It is an
 * abstract action, without actionStep() implementation. For a concrete, working
 * action check the PickupAction and putDownAction.
 */
public abstract class DurativeArmAction {
	private RobotArm hostRobotArm;

	protected RobotArm getHostRobotArm() {
		return this.hostRobotArm;
	}

	void setHostRobotArm(final RobotArm ra) {
		this.hostRobotArm = ra;
	}

	private VirtualWorldModel virtualWorldModel;

	protected VirtualWorldModel getVirtualWorldModel() {
		return this.virtualWorldModel;
	}

	void setVirtualWorldModel(final VirtualWorldModel m) {
		this.virtualWorldModel = m;
	}

	private Point2D targetCoord;

	Point2D getTargetCoord() {
		return this.targetCoord;
	}

	void setTargetCoord(final Point2D t) {
		this.targetCoord = t;
	}

	protected void setTargetCoord(final double x, final double y) {
		this.targetCoord = new Point2D.Double(x, y);
	}

	public abstract void actionStep();

	private String statusMessage = "";

	String getStatusMessage() {
		return this.statusMessage;
	}

	protected void setStatusMessage(final String s) {
		this.statusMessage = s;
	}

	double getXStep() {
		return getVirtualWorldModel().getXStep();
	}

	double getYStep() {
		return getVirtualWorldModel().getYStep();
	}

	Point2D getArmRestCoord() {
		return getHostRobotArm().getArmRestCoord();
	}

	Point2D getCoord() {
		return getHostRobotArm().getCoord();
	}

	double getBlockWidth() {
		return getVirtualWorldModel().getBlockWidth();
	}

	protected double getBlockHeight() {
		return getVirtualWorldModel().getBlockHeight();
	}

	boolean isXMatched(final double x) {
		return getVirtualWorldModel().isXCloseEnough(getCoord().getX(), x);
	}

	boolean isYMatched(final double y) {
		return getVirtualWorldModel().isYCloseEnough(getCoord().getY(), y);
	}

	boolean isCoordMatched(final Point2D c) {
		return isXMatched(c.getX()) && isYMatched(c.getY());
	}

	protected boolean isTargetXMatched() {
		return isXMatched(getTargetCoord().getX());
	}

	boolean isTargetYMatched() {
		return isYMatched(getTargetCoord().getY());
	}

	protected boolean isTargetCoordMatched() {
		return isCoordMatched(getTargetCoord());
	}

	boolean isRestXMatched() {
		return isXMatched(getArmRestCoord().getX());
	}

	protected boolean isRestYMatched() {
		return isYMatched(getArmRestCoord().getY());
	}

	protected boolean isRestCoordMatched() {
		return isCoordMatched(getArmRestCoord());
	}

	/** move robotarm and block in the arm with distance dx,dy */
	void updateCoord(final double dx, final double dy) {
		// update coordinates
		getHostRobotArm().setCoord(getCoord().getX() + dx, getCoord().getY() + dy);
		// check if holding a block, update block too
		final Block blockHeld = getHostRobotArm().getBlockHeld();
		if (blockHeld != null) {
			blockHeld.setCoord(blockHeld.getCoord().getX() + dx, blockHeld.getCoord().getY() + dy);
		}
	}

	/** move towards x, with steps getXStep() */
	void followX(final double x) {
		// when moving, either completely open or close claw
		if (getHostRobotArm().getBlockHeld() == null) {
			getHostRobotArm().completelyOpenClaw();
		} else {
			getHostRobotArm().completelyCloseClaw();
		}

		if (!getVirtualWorldModel().isXCloseEnough(getCoord().getX(), x)) {

			if (getCoord().getX() > x) {
				// greater than x
				if (getCoord().getX() > x + getXStep()) {
					updateCoord(-getXStep(), 0.0);
				} else {
					// just a little bit (within xStep) greater than x
					updateCoord(-(getCoord().getX() - x), 0.0);
				}
			} else {
				// less than x
				if (getCoord().getX() < x - getXStep()) {
					updateCoord(getXStep(), 0.0);
				} else {
					// just a little bit (within xStep) less than x
					updateCoord(x - getCoord().getX(), 0.0);
				}
			}
		}
	}

	void followY(final double y) {
		// when moving, either completely open or close claw
		if (getHostRobotArm().getBlockHeld() == null) {
			getHostRobotArm().completelyOpenClaw();
		} else {
			getHostRobotArm().completelyCloseClaw();
		}

		if (!getVirtualWorldModel().isYCloseEnough(getCoord().getY(), y)) {
			if (getCoord().getY() > y) {
				// greater than y
				if (getCoord().getY() > y + getYStep()) {
					updateCoord(0.0, -getYStep());
				} else {
					// just a little bit (within yStep) greater than y
					updateCoord(0.0, -(getCoord().getY() - y));
				}
			} else {
				// less than y
				if (getCoord().getY() < y - getYStep()) {
					updateCoord(0.0, getYStep());
				} else {
					// just a little bit (within yStep) less than y
					updateCoord(0.0, y - getCoord().getY());
				}
			}
		}
	}

	protected void followTargetX() {
		followX(getTargetCoord().getX());
	}

	protected void followTargetY() {
		followY(getTargetCoord().getY());
	}

	protected void followRestX() {
		followX(getArmRestCoord().getX());
	}

	protected void followRestY() {
		followY(getArmRestCoord().getY());
	}
}

// This one is for testing.
class DemoAction extends DurativeArmAction {
	private int state = 0;

	@Override
	public void actionStep() {
		if (!getVirtualWorldModel().isAvailable()) {
			setStatusMessage("External perturbation.");
			return;
		}

		setStatusMessage("");

		double x = getCoord().getX();
		double y = getCoord().getY();

		switch (this.state % 4) {
		case 0:
			x++;
			if (x > 200) {
				this.state++;
			}
			break;
		case 1:
			y++;
			if (y > 150) {
				this.state++;
			}
			break;
		case 2:
			x--;
			if (x < 40) {
				this.state++;
			}
			break;
		default:
			y--;
			if (y < 60) {
				this.state++;
			}
		}
		getHostRobotArm().setCoord(x, y);
	}
}
