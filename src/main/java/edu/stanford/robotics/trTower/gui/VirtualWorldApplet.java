package edu.stanford.robotics.trTower.gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

import edu.stanford.robotics.trTower.virtualWorld.*;
import edu.stanford.robotics.trTower.common.*;


public class VirtualWorldApplet 
    extends AnimatorApplet {

    public VirtualWorldApplet() {
//    	addComponentListener(getComponentHandler());

	// --- init children
	getVirtualWorld().setVirtualWorldWidth(preferredWidth);
	getVirtualWorld().setVirtualWorldHeight(preferredHeight);

	// add in some blocks
	for (int i=0; i<2; i++)
	    getVirtualWorld().addNewBlock();
    }
    
//      public void initialize() {
//  	getVirtualWorld().initialize();
//  	getVirtualWorld().setVirtualWorldWidth(getSize().getWidth());
//  	getVirtualWorld().setVirtualWorldHeight(getSize().getHeight());
	// add in blocks
//  	for (int i=0; i<4; i++)
//  	    getVirtualWorld().addNewBlock();
//      }

//      class ComponentHandler extends ComponentAdapter {
//  	void ComponentResized(ComponentEvent e) {
//  	    getVirtualWorld().setVirtualWorldWidth(getSize().getWidth());
//  	    getVirtualWorld().setVirtualWorldHeight(getSize().getHeight());
//  	}
//      }
    // properties
    private VirtualWorld virtualWorld;
    protected VirtualWorld getVirtualWorld() {
	if (virtualWorld == null) {
	    virtualWorld = new VirtualWorld();
//  	    System.out.println("VirtualWorldApplet> Width = " 
//  			       + getSize().getWidth());
//  	    System.out.println("VirtualWorldApplet> Height = " 
//  			       + getSize().getHeight());
	}
	return virtualWorld;
    }
    // promote
    public VirtualWorld getTheVirtualWorld() {
	return getVirtualWorld();
    }

//      // --- components
//      private ComponentHandler componentHandler;
//      protected ComponentHandler getComponentHandler() {
//  	if (componentHandler == null) {
//  	    componentHandler = new ComponentHandler();
//  	}
//  	return componentHandler;
//      }

    // overridden methods
    private static int preferredWidth = 330;
    private static int preferredHeight = 260;
    private static Dimension preferredDimension = new Dimension(preferredWidth, preferredHeight);
    public Dimension getPreferredSize() { return preferredDimension; }
    public Dimension getMaximumSize() { return getPreferredSize(); }
    public Dimension getMinimumSize() { return getPreferredSize(); }
    

    // methods
    public void paint(Graphics g) {
	getVirtualWorld().render(g);
    }


    protected void animStep() {
    	// code broken...
    	//	virtualWorld.animStep();
    }
}
