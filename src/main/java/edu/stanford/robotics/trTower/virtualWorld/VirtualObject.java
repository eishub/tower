package edu.stanford.robotics.trTower.virtualWorld;

import java.awt.Graphics;
import java.awt.Paint;
import java.awt.geom.Point2D;

public abstract class VirtualObject {
	private String id;

	public String getId() {
		return this.id;
	}

	void setId(final String s) {
		this.id = s;
	}

	private Paint paint;

	Paint getPaint() {
		return this.paint;
	}

	void setPaint(final Paint p) {
		this.paint = p;
	}

	private Point2D coord;

	public Point2D getCoord() {
		return this.coord;
	}

	void setCoord(final Point2D p) {
		this.coord = p;
	}

	void setCoord(final double x, final double y) {
		this.coord = new Point2D.Double(x, y);
	}

	private boolean coordLegal = true;

	boolean isCoordLegal() {
		return this.coordLegal;
	}

	void setCoordLegal(final boolean l) {
		this.coordLegal = l;
	}

	abstract void render(Graphics g);
}
