package edu.stanford.robotics.trTower.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import edu.stanford.robotics.trTower.*;
import edu.stanford.robotics.trTower.virtualWorld.*;


public class VirtualWorldRenderPanel 
    extends JPanel implements StimulatorListener {

    private Cursor defaultCursor;

    public VirtualWorldRenderPanel() {
	setOpaque(true);
	setBackground(Color.white);
	addMouseListener(getMouseInputHandler());
	addMouseMotionListener(getMouseInputHandler());
	setToolTipText("Click and drag to reposition blocks");
	defaultCursor = getCursor();
	setBorder(BorderFactory.createEtchedBorder(Color.white, Color.gray));
    }

    // --- MouseInputHandler
    class MouseInputHandler extends MouseInputAdapter {
	public void mousePressed(MouseEvent e) {
	    getVirtualWorld().setSelectPoint(e.getPoint());
	}
	public void mouseReleased(MouseEvent e) {
	    getVirtualWorld().setSelectPoint(null);
	}
	public void mouseDragged(MouseEvent e) {
	    getVirtualWorld().setTargetPoint(e.getPoint());
	}
	public void mouseMoved(MouseEvent e) {
	    if (getVirtualWorld().getSelectPoint() == null) {
		// just moving, not dragging
		if (getVirtualWorld().isPointOverBlock(e.getPoint())) {
		    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		} else {
		    setCursor(defaultCursor);
		}
	    }
	}
    }
    
    private MouseInputHandler mouseInputHandler;
    protected MouseInputHandler getMouseInputHandler() {
	if (mouseInputHandler == null) {
	    mouseInputHandler = new MouseInputHandler();
	}
	return mouseInputHandler;
    }

    // Wouter: HACK 16apr09, Koen wants more blocks. 
    private static int preferredWidth = 550; //270;
    private static int preferredHeight = 480; //225;
    private static Dimension preferredDimension = 
	new Dimension(preferredWidth, preferredHeight);

    // cannot be resized
    public Dimension getPreferredSize() { return preferredDimension; }
    public Dimension getMaximumSize() { return getPreferredSize(); }
    public Dimension getMinimumSize() { return getPreferredSize(); }
    
    // --- attributes
    private VirtualWorld virtualWorld;
    public VirtualWorld getVirtualWorld() { return virtualWorld; }
    public void setVirtualWorld(VirtualWorld vw) { 
	virtualWorld = vw;
	virtualWorld.setVirtualWorldWidth(preferredWidth);
	virtualWorld.setVirtualWorldHeight(preferredHeight);
    }


    // --- public methods

    // overrides paintComponent
    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	getVirtualWorld().render(g);
//  	int panelWidth = getWidth();
//  	int panelHeight = getHeight();

//  	if (panelWidth == 0 || panelHeight == 0 ) {
//  	    System.out.println("VirtualWorldRenderPanel> panelWidth = 0 or panelHeight = 0");
//  	} else {
//  	    Image offScrImage = getOffScreenImage(panelWidth, panelHeight);
//  	    Graphics offScreenImageGraphics = offScrImage.getGraphics();
	    
//  	    // Clear image background
//  	    offScreenImageGraphics.setColor(getBackground());
//  	    offScreenImageGraphics.fillRect(0, 0, panelWidth, panelHeight);
//  	    offScreenImageGraphics.setColor(getForeground());

//  	    // Draw off screen
//  	    getVirtualWorld().render(offScreenImageGraphics);
	    
//  	    // put offscreen image on screen
//  	    g.drawImage(offScrImage, 0, 0, null);
	    
//  	    // clean up
//  	    offScreenImageGraphics.dispose();
//  	}
    }

//      private Image offScreenImage;
//      protected Image getOffScreenImage(int width, int height) {
//  	if (offScreenImage == null ||
//  	    offScreenImage.getWidth(null) != width ||
//  	    offScreenImage.getHeight(null) != height) {
	    
//  	    offScreenImage = createImage(width, height);
//  	}
//  	return offScreenImage;
//      }

    public void stimuStep() {
	if (getVirtualWorld().isChanged())
	    repaint();
    }
}
