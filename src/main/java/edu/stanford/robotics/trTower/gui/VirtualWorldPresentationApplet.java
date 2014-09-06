package edu.stanford.robotics.trTower.gui;

import java.awt.*;
//import java.awt.event.*;
import javax.swing.*;

import edu.stanford.robotics.trTower.*;
import edu.stanford.robotics.trTower.virtualWorld.*;
//import edu.stanford.robotics.trTower.common.*;

public class VirtualWorldPresentationApplet extends AnimatorApplet {

    private static int preferredWidth = 270;
    private static int preferredHeight = 225;
    private static Dimension preferredDimension = 
	new Dimension(preferredWidth, preferredHeight);
    
    // --- attributes
    private VirtualWorld virtualWorld;
    public VirtualWorld getVirtualWorld() { return virtualWorld; }
    public void setVirtualWorld(VirtualWorld vw) { 
	virtualWorld = vw;
	virtualWorld.setVirtualWorldWidth(preferredWidth);
	virtualWorld.setVirtualWorldHeight(preferredHeight);
    }
    private Stimulator stimulator;
    public Stimulator getStimulator() { return stimulator; }
    public void setStimulator(Stimulator s) { stimulator = s; }
    // --- public methods

    // cannot be resized
    public Dimension getPreferredSize() { return preferredDimension; }
    public Dimension getMaximumSize() { return getPreferredSize(); }
    public Dimension getMinimumSize() { return getPreferredSize(); }

    // [fix this] check for getVirtualWorld() != null?
    public void paint(Graphics g) {
	getVirtualWorld().render(g);
    }
    protected void animStep() {
	//	getVirtualWorld().animStep();
	getStimulator().step();
    }
}
