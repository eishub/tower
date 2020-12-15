package edu.stanford.robotics.trTower.virtualWorld;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

/** Wouter: HACK made public, I need it to get percepts */
public class RobotArm extends VirtualObject {
	private double armThickness;

	double getArmThickness() {
		return this.armThickness;
	}

	void setArmThickness(final double t) {
		this.armThickness = t;
	}

	private double innerArmWidth;

	double getInnerArmWidth() {
		return this.innerArmWidth;
	}

	void setInnerArmWidth(final double w) {
		this.innerArmWidth = w;
	}

	private double innerArmDrop;

	double getInnerArmDrop() {
		return this.innerArmDrop;
	}

	void setInnerArmDrop(final double w) {
		this.innerArmDrop = w;
	}

	private double armCeilingY;

	double getArmCeilingY() {
		return this.armCeilingY;
	}

	void setArmCeilingY(final double y) {
		this.armCeilingY = y;
	}

	private Point2D armRestCoord;

	Point2D getArmRestCoord() {
		return this.armRestCoord;
	}

	void setArmRestCoord(final Point2D r) {
		this.armRestCoord = r;
	}

	void setArmRestCoord(final double x, final double y) {
		this.armRestCoord = new Point2D.Double(x, y);
	}

	double getOutterArmDrop() {
		return getInnerArmDrop() + getArmThickness();
	}

	double getArmShoulder() {
		return (getInnerArmWidth() + getArmThickness()) / 2;
	}

	private Block blockHeld;

	// Wouter HACK made public 15apr08 to get percepts.
	public Block getBlockHeld() {
		return this.blockHeld;
	}

	public void setBlockHeld(final Block b) {
		this.blockHeld = b;
	}

	private DurativeArmAction durativeArmAction;

	DurativeArmAction getDurativeArmAction() {
		return this.durativeArmAction;
	}

	void setDurativeArmAction(final DurativeArmAction a) {
		this.durativeArmAction = a;
	}

	private static final double maxClawOpenning = 3;
	private double clawOpenning = maxClawOpenning;

	public void openClaw() {
		if (this.clawOpenning < maxClawOpenning) {
			this.clawOpenning += 1;
		}
	}

	void completelyOpenClaw() {
		this.clawOpenning = maxClawOpenning;
	}

	public void closeClaw() {
		if (this.clawOpenning > 0) {
			this.clawOpenning -= 1;
		}
	}

	void completelyCloseClaw() {
		this.clawOpenning = 0;
	}

	public boolean isClawCompletelyOpen() {
		return this.clawOpenning == maxClawOpenning ? true : false;
	}

	public boolean isClawCompletelyClosed() {
		return this.clawOpenning == 0 ? true : false;
	}

	protected Shape getArmShape() {
		if (getCoord() == null) {
			System.out.println("RobotArm.getArmShape> getCoord() is null");
			System.exit(-1);
		}
		final double coordX = getCoord().getX();
		final double coordY = getCoord().getY();

		final GeneralPath armPath = new GeneralPath();
		double x;
		double y;
		x = coordX - this.clawOpenning;
		y = coordY - this.clawOpenning;
		armPath.moveTo((float) x, (float) y);
		y += getInnerArmDrop();
		armPath.lineTo((float) x, (float) y);
		x += -getArmThickness();
		armPath.lineTo((float) x, (float) y);
		y += -getOutterArmDrop();
		armPath.lineTo((float) x, (float) y);
		x += getArmShoulder() + this.clawOpenning;
		armPath.lineTo((float) x, (float) y);
		y = getArmCeilingY();
		armPath.lineTo((float) x, (float) y);
		x += getArmThickness();
		armPath.lineTo((float) x, (float) y);
		y = coordY - getArmThickness() - this.clawOpenning;
		armPath.lineTo((float) x, (float) y);
		x += getArmShoulder() + this.clawOpenning;
		armPath.lineTo((float) x, (float) y);
		y += getOutterArmDrop();
		armPath.lineTo((float) x, (float) y);
		x += -getArmThickness();
		armPath.lineTo((float) x, (float) y);
		y += -getInnerArmDrop();
		armPath.lineTo((float) x, (float) y);
		armPath.closePath();
		return armPath;
	}

	@Override
	void render(final Graphics g) {
		if (g == null) {
			System.out.println("RobotArm.render> g is null");
			System.exit(-1);
		}
		final Graphics2D g2 = (Graphics2D) g;
		// drawArm
		if (getPaint() == null) {
			System.out.println("RobotArm.render> getPaint() is null");
			System.exit(-1);
		}
		if (getArmShape() == null) {
			System.out.println("RobotArm.render> getArmShape() is null");
			System.exit(-1);
		}
		g2.setPaint(getPaint());
		// g2.setPaint(Color.white);
		g2.fill(getArmShape());
	}

	void setDurativeAction() {
	}

	void stepAction() {
	}
}
