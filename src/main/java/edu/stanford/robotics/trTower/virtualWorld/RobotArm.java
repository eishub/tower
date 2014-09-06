package edu.stanford.robotics.trTower.virtualWorld;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

/** Wouter: HACK made public, I need it to get percepts */

public class RobotArm extends VirtualObject {

//      // independent parameters
//      static final double armThickness = VirtualWorld.blockWidth/8;
//      static final double innerArmWidth = VirtualWorld.blockWidth;
//      static final double innerArmDrop = VirtualWorld.blockHeight/2;
//      // derived parameters
//      static final double outterArmDrop = innerArmDrop + armThickness;
//      static final double armShoulder = 
//  	(innerArmWidth + 2*armThickness - armThickness)/2;
//      // resing location
//      static final double armRestX = VirtualWorld.armRestX;
//      static final double armRestY = VirtualWorld.armRestY;
//      static final double armCeilingY = VirtualWorld.armCeilingY;

    // properties (independent parameters)
    private double armThickness;
    double getArmThickness() { return armThickness; }
    void setArmThickness(double t) { armThickness = t; }
    private double innerArmWidth;
    double getInnerArmWidth() { return innerArmWidth; }
    void setInnerArmWidth(double w) { innerArmWidth = w; }
    private double innerArmDrop;
    double getInnerArmDrop() { return innerArmDrop; }
    void setInnerArmDrop(double w) { innerArmDrop = w; }

    private double armCeilingY;
    double getArmCeilingY() { return armCeilingY; }
    void setArmCeilingY(double y) { armCeilingY = y; }
//      private double armRestX;
//      double getArmRestX() { return armRestX; }
//      void setArmRestX(double x) { armRestX = x; }
//      private double armRestY;
//      double getArmRestY() { return armRestY; }
//      void setArmRestY(double y) { armRestY = y; }
    private Point2D armRestCoord;
    Point2D getArmRestCoord() { return armRestCoord; }
    void setArmRestCoord(Point2D r) { armRestCoord = r; }
    void setArmRestCoord(double x, double y) { armRestCoord = 
						   new Point2D.Double(x, y); }
    // properties (derived parameters)
    double getOutterArmDrop() { return getInnerArmDrop() + getArmThickness(); }
    double getArmShoulder() { 
	return (getInnerArmWidth() + getArmThickness())/2;
    }
    
    // properties
    private Block blockHeld;
    // Wouter HACK made public 15apr08 to get percepts. 
    public  Block getBlockHeld() { return blockHeld; }
    public void setBlockHeld(Block b) { blockHeld = b; }
    private DurativeArmAction durativeArmAction;
    DurativeArmAction getDurativeArmAction() { return durativeArmAction; }
    void setDurativeArmAction(DurativeArmAction a) { durativeArmAction = a; }
    private static final double maxClawOpenning = 3;
    private double clawOpenning = maxClawOpenning;
//      double getClawOpenning() { return clawOpenning; }
//      void setClawOpenning(double o) { clawOpenning = o; }
    public void openClaw() {
	if (clawOpenning < maxClawOpenning)
	    clawOpenning += 1;
    }
    void completelyOpenClaw() {
	clawOpenning = maxClawOpenning;
    }
    public void closeClaw() {
	if (clawOpenning > 0)
	    clawOpenning -= 1;
    }
    void completelyCloseClaw() {
	clawOpenning = 0;
    }
    public boolean isClawCompletelyOpen() {
	return clawOpenning==maxClawOpenning ? true : false;
    }
    public boolean isClawCompletelyClosed() { 
	return clawOpenning==0 ? true: false;
    }

    protected Shape getArmShape() {

	if (getCoord() == null) {
	    System.out.println("RobotArm.getArmShape> getCoord() is null");
	    System.exit(-1);
	}
	double coordX = getCoord().getX();
	double coordY = getCoord().getY();

	GeneralPath armPath = new GeneralPath();
	double x;
	double y;
	x = coordX - clawOpenning;
	y = coordY - clawOpenning;
	armPath.moveTo((float)x, (float)y);
	y += getInnerArmDrop();
	armPath.lineTo((float)x, (float)y);
	x += -getArmThickness();
	armPath.lineTo((float)x, (float)y);
	y += -getOutterArmDrop();
	armPath.lineTo((float)x, (float)y);
	x += getArmShoulder() + clawOpenning;
	armPath.lineTo((float)x, (float)y);
	y = getArmCeilingY();
	armPath.lineTo((float)x, (float)y);
	x += getArmThickness();
	armPath.lineTo((float)x, (float)y);
	y = coordY - getArmThickness() - clawOpenning;
	armPath.lineTo((float)x, (float)y);
	x += getArmShoulder() + clawOpenning;
	armPath.lineTo((float)x, (float)y);
	y += getOutterArmDrop();
	armPath.lineTo((float)x, (float)y);
	x += -getArmThickness();
	armPath.lineTo((float)x, (float)y);
	y += -getInnerArmDrop();
	armPath.lineTo((float)x, (float)y);
	armPath.closePath();
	return armPath;
    }
		       
    // methods
    void render(Graphics g) {

	if (g == null) {
	    System.out.println("RobotArm.render> g is null");
	    System.exit(-1);
	}
	Graphics2D g2 = (Graphics2D)g;
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
	//	g2.setPaint(Color.white);
	g2.fill(getArmShape());
    }
    void setDurativeAction() {}
    void stepAction() {}
    
}
