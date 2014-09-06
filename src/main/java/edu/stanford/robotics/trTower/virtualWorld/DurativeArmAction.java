package edu.stanford.robotics.trTower.virtualWorld;

import java.awt.geom.*;

/**
 * DurativeArmAction is a general action that moves blocks around. It is
 * an abstract action, without actionStep() implementation. For a concrete, working
 * action check the PickupAction and putDownAction.
 */
public abstract class DurativeArmAction {
	// --- properites
	private RobotArm hostRobotArm;

	protected RobotArm getHostRobotArm() {
		return hostRobotArm;
	}

	void setHostRobotArm(RobotArm ra) {
		hostRobotArm = ra;
	}

	private VirtualWorldModel virtualWorldModel;

	protected VirtualWorldModel getVirtualWorldModel() {
		return virtualWorldModel;
	}

	void setVirtualWorldModel(VirtualWorldModel m) {
		virtualWorldModel = m;
	}

	private Point2D targetCoord;

	Point2D getTargetCoord() {
		return targetCoord;
	}

	void setTargetCoord(Point2D t) {
		targetCoord = t;
	}

	protected void setTargetCoord(double x, double y) {
		targetCoord = new Point2D.Double(x, y);
	}

	public abstract void actionStep();

	private String statusMessage = "";

	String getStatusMessage() {
		return statusMessage;
	}

	protected void setStatusMessage(String s) {
		statusMessage = s;
	}

	// --- helper
	double getXStep() {
		return getVirtualWorldModel().getXStep();
	}

	double getYStep() {
		return getVirtualWorldModel().getYStep();
	}

	// double getRestX() { return getHostRobotArm().getArmRestCoord().getX(); }
	// double getRestY() { return getHostRobotArm().getArmRestCoord().getY(); }
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

	boolean isXMatched(double x) {
		return getVirtualWorldModel().isXCloseEnough(getCoord().getX(), x);
	}

	boolean isYMatched(double y) {
		return getVirtualWorldModel().isYCloseEnough(getCoord().getY(), y);
	}

	boolean isCoordMatched(Point2D c) {
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
	void updateCoord(double dx, double dy) {
		// update coordidates
		getHostRobotArm().setCoord(getCoord().getX() + dx,
				getCoord().getY() + dy);
		// check if holding a block, update block too
		Block blockHeld = getHostRobotArm().getBlockHeld();
		if (blockHeld != null) {
			blockHeld.setCoord(blockHeld.getCoord().getX() + dx, blockHeld
					.getCoord().getY()
					+ dy);
		}
	}

	/** move towards x, with steps getXStep() . */
	void followX(double x) {

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

	void followY(double y) {

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

	public void actionStep() {

		if (!getVirtualWorldModel().isAvailable()) {
			setStatusMessage("External perturbation.");
			return;
		}

		setStatusMessage("");

		double x = getCoord().getX();
		double y = getCoord().getY();

		switch (state % 4) {
		case 0:
			x++;
			if (x > 200)
				state++;
			break;

		case 1:
			y++;
			if (y > 150)
				state++;
			break;

		case 2:
			x--;
			if (x < 40)
				state++;
			break;
		default:
			y--;
			if (y < 60)
				state++;
		}
		getHostRobotArm().setCoord(x, y);
	}
}
