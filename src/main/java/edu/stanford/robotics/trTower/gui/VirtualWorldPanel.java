package edu.stanford.robotics.trTower.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.net.*;

import edu.stanford.robotics.trTower.*;
import edu.stanford.robotics.trTower.virtualWorld.*;
import edu.stanford.robotics.trTower.modelTower.*;

public class VirtualWorldPanel extends JPanel {

    public VirtualWorldPanel() {

	Border empty = BorderFactory.createEmptyBorder(5, 5, 5, 5);
	Border bevel = new SoftBevelBorder(SoftBevelBorder.LOWERED);
	setBorder(BorderFactory.createCompoundBorder(bevel, empty));
	setLayout(new GridBagLayout());
	GridBagConstraints title = new GridBagConstraints();
	title.gridx = 0;
	title.gridy = 0;
	title.weighty = 0.5;
	title.anchor = GridBagConstraints.NORTHWEST;
	add(getVirtualWorldActionLabel(), title);
	GridBagConstraints applet = new GridBagConstraints();
	applet.gridx = 0;
	applet.gridy = 1;
	add(getVirtualWorldRenderPanel(), applet);
	GridBagConstraints anim = new GridBagConstraints();
	anim.gridx = 0;
	anim.gridy = 2;
	anim.anchor = GridBagConstraints.SOUTH;
	anim.fill = GridBagConstraints.HORIZONTAL;
	anim.weightx = 1;
	anim.weighty = 0.5;
	add(getAnimationControlPanel(), anim);
    }

    // --- attributes
    private VirtualWorld virtualWorld;
    public VirtualWorld getVirtualWorld() { return virtualWorld; }
    public void setVirtualWorld(VirtualWorld vw) { 
	virtualWorld = vw;
	getVirtualWorldRenderPanel().setVirtualWorld(virtualWorld);
	getAnimationControlPanel().setVirtualWorld(virtualWorld);
    }
    private ModelTower modelTower;
    public ModelTower getModelTower() { return modelTower; }
    public void setModelTower(ModelTower mt) { 
	modelTower = mt; 
	getAnimationControlPanel().setModelTower(modelTower);
    }

    private Stimulator stimulator;
    public Stimulator getStimulator() { return stimulator; }
    public void setStimulator(Stimulator s) { 
	stimulator = s; 
	if (stimulator != null) {
	    stimulator.addStimulatorListener(getVirtualWorldRenderPanel());
	    stimulator.addStimulatorListener(getAnimationControlPanel());
	}
    }

    private AnimationTimer animationTimer;
    public AnimationTimer getAnimationTimer() { return animationTimer; }
    public void setAnimationTimer(AnimationTimer at) {
	animationTimer = at;
	getAnimationControlPanel().setAnimationTimer(animationTimer);
    }
	
    // --- components
    private ActionLabel virtualWorldActionLabel;
    protected ActionLabel getVirtualWorldActionLabel() {
	if (virtualWorldActionLabel == null) {
	    virtualWorldActionLabel = new ActionLabel();
	    virtualWorldActionLabel.setText("Environment");
	    virtualWorldActionLabel.setAction(new AbstractAction("Environment") {
		    public void actionPerformed(ActionEvent ae) {
		    /*
		     * Disabled W.Pasman 3may10, #706
			boolean useBuiltInHelpBrowser = 
			    (ae.getModifiers()&ActionEvent.SHIFT_MASK)==0? false : true;
			TRTower.getInstance().showHelp(useBuiltInHelpBrowser, 
						       "Environment", 
						       "help/Environment.html");			
		    */
		    }
		  
		});
	}
	return virtualWorldActionLabel;
    }

    private AnimationControlPanel animationControlPanel;
    protected AnimationControlPanel getAnimationControlPanel() {
	if (animationControlPanel == null) {
	    animationControlPanel = new AnimationControlPanel();
	}
	return animationControlPanel;
    }
    private VirtualWorldRenderPanel virtualWorldRenderPanel;
    protected VirtualWorldRenderPanel getVirtualWorldRenderPanel() {
	if (virtualWorldRenderPanel == null) {
	    virtualWorldRenderPanel = new VirtualWorldRenderPanel();
//  	    virtualWorldRenderPanel.setVirtualWorld(getVirtualWorld());
//  	    getStimulator().addStimulatorListener(virtualWorldRenderPanel);
	}
	return virtualWorldRenderPanel;
    }
}
