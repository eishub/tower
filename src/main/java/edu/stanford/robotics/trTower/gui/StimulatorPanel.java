package edu.stanford.robotics.trTower.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.stanford.robotics.trTower.Stimulator;

public class StimulatorPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	protected static final int hs = 10;
	protected static final int vs = 10;

	public StimulatorPanel() {
		setLayout(new BorderLayout());
		add(getControlBox());
	}

	private Stimulator stimulator;

	public Stimulator getStimulator() {
		return this.stimulator;
	}

	public void setStimulator(final Stimulator s) {
		this.stimulator = s;
	}

	class NewBlockGuiAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		NewBlockGuiAction() {
			super("New Block");
		}

		@Override
		public void actionPerformed(final ActionEvent ae) {
			getStimulator().setToAddNewBlock(true);
		}
	}

	private NewBlockGuiAction newBlockGuiAction;

	protected NewBlockGuiAction getNewBlockGuiAction() {
		if (this.newBlockGuiAction == null) {
			this.newBlockGuiAction = new NewBlockGuiAction();
		}
		return this.newBlockGuiAction;
	}

	class ResetGuiAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		ResetGuiAction() {
			super("Reset");
		}

		@Override
		public void actionPerformed(final ActionEvent ae) {
			getStimulator().setToBeReset(true);
		}
	}

	private ResetGuiAction resetGuiAction;

	protected ResetGuiAction getResetGuiAction() {
		if (this.resetGuiAction == null) {
			this.resetGuiAction = new ResetGuiAction();
		}
		return this.resetGuiAction;
	}

	private Box controlBox;

	protected Box getControlBox() {
		if (this.controlBox == null) {
			this.controlBox = Box.createVerticalBox();
			this.controlBox.add(Box.createVerticalStrut(vs));
			this.controlBox.add(getNewBlockButtonBox());
			this.controlBox.add(Box.createVerticalStrut(vs));
			this.controlBox.add(getResetButtonBox());
			this.controlBox.add(Box.createVerticalStrut(vs));
			this.controlBox.add(Box.createVerticalGlue());
		}
		return this.controlBox;
	}

	private Box newBlockButtonBox;

	protected Box getNewBlockButtonBox() {
		if (this.newBlockButtonBox == null) {
			this.newBlockButtonBox = Box.createHorizontalBox();
			this.newBlockButtonBox.add(getNewBlockButton());
			this.newBlockButtonBox.add(Box.createHorizontalGlue());
		}
		return this.newBlockButtonBox;
	}

	private JButton newBlockButton;

	protected JButton getNewBlockButton() {
		if (this.newBlockButton == null) {
			this.newBlockButton = new JButton();
			this.newBlockButton.setAction(getNewBlockGuiAction());
		}
		return this.newBlockButton;
	}

	private Box resetButtonBox;

	protected Box getResetButtonBox() {
		if (this.resetButtonBox == null) {
			this.resetButtonBox = Box.createHorizontalBox();
			this.resetButtonBox.add(getResetButton());
			this.resetButtonBox.add(Box.createHorizontalGlue());
		}
		return this.resetButtonBox;
	}

	private JButton resetButton;

	protected JButton getResetButton() {
		if (this.resetButton == null) {
			this.resetButton = new JButton();
			this.resetButton.setAction(getResetGuiAction());
			this.resetButton.setToolTipText("Erase all blocks and cancel action");
		}
		return this.resetButton;
	}
}
