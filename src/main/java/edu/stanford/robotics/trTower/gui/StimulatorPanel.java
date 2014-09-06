package edu.stanford.robotics.trTower.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import edu.stanford.robotics.trTower.*;
import edu.stanford.robotics.trTower.virtualWorld.*;

public class StimulatorPanel extends JPanel {

	protected static final int hs = 10;
	protected static final int vs = 10;

	public StimulatorPanel() {
		setLayout(new BorderLayout());
		add(getControlBox());
	}

	// --- attributes
	private Stimulator stimulator;

	public Stimulator getStimulator() {
		return stimulator;
	}

	public void setStimulator(Stimulator s) {
		stimulator = s;
	}

	// --- GUI actions

	class NewBlockGuiAction extends AbstractAction {
		NewBlockGuiAction() {
			super("New Block");
		}

		public void actionPerformed(ActionEvent ae) {
			getStimulator().setToAddNewBlock(true);
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
			getStimulator().setToBeReset(true);
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
	private Box controlBox;

	protected Box getControlBox() {
		if (controlBox == null) {
			controlBox = Box.createVerticalBox();
			controlBox.add(Box.createVerticalStrut(vs));
			controlBox.add(getNewBlockButtonBox());
			controlBox.add(Box.createVerticalStrut(vs));
			controlBox.add(getResetButtonBox());
			controlBox.add(Box.createVerticalStrut(vs));
			controlBox.add(Box.createVerticalGlue());
		}
		return controlBox;
	}

	private Box newBlockButtonBox;

	protected Box getNewBlockButtonBox() {
		if (newBlockButtonBox == null) {
			newBlockButtonBox = Box.createHorizontalBox();
			newBlockButtonBox.add(getNewBlockButton());
			newBlockButtonBox.add(Box.createHorizontalGlue());
		}
		return newBlockButtonBox;
	}

	private JButton newBlockButton;

	protected JButton getNewBlockButton() {
		if (newBlockButton == null) {
			newBlockButton = new JButton();
			newBlockButton.setAction(getNewBlockGuiAction());
		}
		return newBlockButton;
	}

	private Box resetButtonBox;

	protected Box getResetButtonBox() {
		if (resetButtonBox == null) {
			resetButtonBox = Box.createHorizontalBox();
			resetButtonBox.add(getResetButton());
			resetButtonBox.add(Box.createHorizontalGlue());
		}
		return resetButtonBox;
	}

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
