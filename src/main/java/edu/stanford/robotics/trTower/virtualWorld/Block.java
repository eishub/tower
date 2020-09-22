package edu.stanford.robotics.trTower.virtualWorld;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;

/** Wouter: HACK made public, I need it to get percepts */
public class Block extends VirtualObject {
	private double blockWidth;

	double getBlockWidth() {
		return this.blockWidth;
	}

	void setBlockWidth(final double w) {
		this.blockWidth = w;
	}

	private double blockHeight;

	double getBlockHeight() {
		return this.blockHeight;
	}

	void setBlockHeight(final double w) {
		this.blockHeight = w;
	}

	private Paint labelPaint;

	Paint getLabelPaint() {
		return this.labelPaint;
	}

	void setLabelPaint(final Paint l) {
		this.labelPaint = l;
	}

	private boolean beingHeld = false;

	boolean isBeingHeld() {
		return this.beingHeld;
	}

	public void setBeingHeld(final boolean h) {
		this.beingHeld = h;
	}

	private RobotArm holdingRobotArm;

	RobotArm getHoldingRobotArm() {
		return this.holdingRobotArm;
	}

	void setHoldingRobotArm(final RobotArm ra) {
		this.holdingRobotArm = ra;
	}

	// methods
	@Override
	void render(final Graphics g) {
		final Graphics2D g2 = (Graphics2D) g;
		final Composite defaultComposite = g2.getComposite();
		if (!isCoordLegal()) {
			// coord not legal, semi-transparent
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
		}
		final double x = getCoord().getX();
		final double y = getCoord().getY();
		// draw filled square
		// System.out.println("Block>render");
		g2.setPaint(getPaint());

		g2.fill(new Rectangle2D.Double(x, y - getBlockHeight(), getBlockWidth(), getBlockHeight()));
		// draw label
		g2.setFont(new Font(null, Font.BOLD, 16));
		g2.setPaint(getLabelPaint());
		final Rectangle2D stringBounds = g2.getFont().getStringBounds(getId(), g2.getFontRenderContext());
		g2.drawString(getId(), (float) ((x + (this.blockWidth - stringBounds.getWidth()) / 2)),
				(float) ((y - (this.blockHeight - stringBounds.getHeight()) / 2)) - 2);
		// restore default composite before returning.
		g2.setComposite(defaultComposite);
	}
}
