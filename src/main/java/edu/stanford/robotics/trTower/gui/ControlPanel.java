package edu.stanford.robotics.trTower.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import edu.stanford.robotics.trTower.virtualWorld.*;
import edu.stanford.robotics.trTower.modelTower.*;
import edu.stanford.robotics.trTower.perceptionTower.*;
import edu.stanford.robotics.trTower.actionTower.*;

public class ControlPanel extends JPanel {

//      protected static final int hs = 10;
//      protected static final int vs = 10;

    public ControlPanel() {
	setLayout(new GridBagLayout());
	//	add(getControlBox());
	GridBagConstraints newBlock = new GridBagConstraints();
	newBlock.gridx = 0;
	newBlock.gridy = 0;
	newBlock.anchor = GridBagConstraints.WEST;
	newBlock.weightx = 0.5;
	add(getNewBlockButton(), newBlock);
	GridBagConstraints reset = new GridBagConstraints();
	reset.gridx = 1;
	reset.gridy = 0;
	reset.anchor = GridBagConstraints.EAST;
	reset.weightx = 0.5;
	reset.weighty = 1;
	add(getResetButton(), reset);
    }
    
    // --- attributes
    private VirtualWorld virtualWorld;
    public VirtualWorld getVirtualWorld() { return virtualWorld; }
    public void setVirtualWorld(VirtualWorld vw) { virtualWorld = vw; }

    private ModelTower modelTower;
    public ModelTower getModelTower() { return modelTower; }
    public void setModelTower(ModelTower m) { modelTower = m; }

    private PerceptionTower perceptionTower;
    public PerceptionTower getPerceptionTower() { return perceptionTower; }
    public void setPerceptionTower(PerceptionTower p) { perceptionTower = p; }

    private ActionTower actionTower;
    public ActionTower getActionTower() { return actionTower; }
    public void setActionTower(ActionTower a) { actionTower = a; }

//      private Stimulator stimulator;
//      public Stimulator getStimulator() { return stimulator; }
//      public void setStimulator(Stimulator s) { stimulator = s; }

    private MessageLabel messageLabel;
    public MessageLabel getMessageLabel() { return messageLabel; }
    public void setMessageLabel(MessageLabel sl) { messageLabel = sl; }

     // --- GUI actions

    class NewBlockGuiAction extends AbstractAction {
	NewBlockGuiAction() {
	    super("New Block");
	}
	public void actionPerformed(ActionEvent ae) {
	    //	    getStimulator().setToAddNewBlock(true);
	    getMessageLabel().setMessage(MessageLabel.NIL, "");
	    if (getVirtualWorld().isMaxNumOfBlocksReached()) {
		getMessageLabel().setMessage(MessageLabel.INFO,
					     "Maximum number of blocks reached.");
	    } else {
		getVirtualWorld().setToAddNewBlock(true);
	    }
	}
    }
    private NewBlockGuiAction newBlockGuiAction;
    protected NewBlockGuiAction getNewBlockGuiAction() {
	if (newBlockGuiAction == null) {
	    newBlockGuiAction = new NewBlockGuiAction();
	}
	return newBlockGuiAction;
    }

    class ResetGuiAction extends AbstractAction {
	ResetGuiAction() {
	    super("Reset");
	}
	public void actionPerformed(ActionEvent ae) {
	    getMessageLabel().setMessage(MessageLabel.INFO, 
					 "Click on \"New Block\" to create blocks.");
	    //	    getStimulator().setToBeReset(true);
	    getVirtualWorld().setToBeReset(true);
	    getActionTower().setToBeReset(true);
	    //	    getModelTower().setToBeReset(true);
	    //	    getPerceptionTowerPanel().setToBeReset(true);
	    //	    getActoinTowerPanel().setToBeReset(true);
	}
    }
    private ResetGuiAction resetGuiAction;
    protected ResetGuiAction getResetGuiAction() {
	if (resetGuiAction == null) {
	    resetGuiAction = new ResetGuiAction();
	}
	return resetGuiAction;
    }

    // --- components
//      private Box controlBox;
//      protected Box getControlBox() {
//  	if (controlBox == null) {
//  	    controlBox = Box.createVerticalBox();
//  	    controlBox.add(Box.createVerticalStrut(vs));
//  	    controlBox.add(getNewBlockButtonBox());
//  	    controlBox.add(Box.createVerticalStrut(vs));
//  	    controlBox.add(getResetButtonBox());
//  	    controlBox.add(Box.createVerticalStrut(vs));
//  	    controlBox.add(Box.createVerticalGlue());
//  	}
//  	return controlBox;
//      }
//      private Box newBlockButtonBox;
//      protected Box getNewBlockButtonBox() {
//  	if (newBlockButtonBox == null) {
//  	    newBlockButtonBox = Box.createHorizontalBox();
//  	    newBlockButtonBox.add(getNewBlockButton());
//  	    newBlockButtonBox.add(Box.createHorizontalGlue());
//  	}
//  	return newBlockButtonBox;
//      }
    private JButton newBlockButton;
    protected JButton getNewBlockButton() {
	if (newBlockButton == null) {
	    newBlockButton = new JButton();
	    newBlockButton.setAction(getNewBlockGuiAction());
	}
	return newBlockButton;
    }

//      private Box resetButtonBox;
//      protected Box getResetButtonBox() {
//  	if (resetButtonBox == null) {
//  	    resetButtonBox = Box.createHorizontalBox();
//  	    resetButtonBox.add(getResetButton());
//  	    resetButtonBox.add(Box.createHorizontalGlue());
//  	}
//  	return resetButtonBox;
//      }
    private JButton resetButton;
    protected JButton getResetButton() {
	if (resetButton == null) {
	    resetButton = new JButton();
	    resetButton.setAction(getResetGuiAction());
	    resetButton.setToolTipText("Erase all blocks and cancel action");
	}
	return resetButton;
    }

}
