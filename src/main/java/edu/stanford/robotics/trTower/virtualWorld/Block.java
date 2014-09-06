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
    
//      static final double blockWidth = VirtualWorld.blockWidth;
//      static final double blockHeight = VirtualWorld.blockHeight;
    
    //    Block() {
//  	setBlockWidth(100);
//  	setBlockHeight(100);
//  	setLabelPaint(Color.yellow);
    //	setBeingHeld(false);
    //    }

    // properties
    private double blockWidth;
    double getBlockWidth() { return blockWidth; }
    void setBlockWidth(double w) { blockWidth = w; }
    private double blockHeight;
    double getBlockHeight() { return blockHeight; }
    void setBlockHeight(double w) { blockHeight = w; }
    private Paint labelPaint;
    Paint getLabelPaint() { return labelPaint; }
    void setLabelPaint(Paint l) { labelPaint = l; }

    private boolean beingHeld = false;
    boolean isBeingHeld() { return beingHeld; }
    public void setBeingHeld(boolean h) { beingHeld = h; }

    private RobotArm holdingRobotArm;
    RobotArm getHoldingRobotArm() { return holdingRobotArm; }
    void setHoldingRobotArm(RobotArm ra) { holdingRobotArm = ra; }
	
    // methods
    void render(Graphics g) {
	Graphics2D g2 = (Graphics2D)g;
	Composite defaultComposite = g2.getComposite();
	if (!isCoordLegal()) {
	    // coord not legal, semi-transparent
	    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
	} 
	double x = getCoord().getX();
	double y = getCoord().getY();
	// draw filled square
	//	System.out.println("Block>render");
	g2.setPaint(getPaint());

	g2.fill(new Rectangle2D.Double(x, y-getBlockHeight(), 
				       getBlockWidth(), getBlockHeight()));
	// draw label
	g2.setFont(new Font(null, Font.BOLD, 16));
	g2.setPaint(getLabelPaint());
	Rectangle2D stringBounds = 
	    g2.getFont().getStringBounds(getId(), g2.getFontRenderContext());
	g2.drawString(getId(), 
		      (float)((x+(blockWidth-stringBounds.getWidth())/2)), 
		      (float)((y-(blockHeight-stringBounds.getHeight())/2))-2);
	// restore default composite before returning.
  	g2.setComposite(defaultComposite);
    }

}
