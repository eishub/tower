package edu.stanford.robotics.trTower.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.stanford.robotics.trTower.actionTower.ActionTower;
import edu.stanford.robotics.trTower.modelTower.ModelTower;
import edu.stanford.robotics.trTower.perceptionTower.PerceptionTower;
import edu.stanford.robotics.trTower.virtualWorld.VirtualWorld;

public class ControlPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public ControlPanel() {
		setLayout(new GridBagLayout());
		final GridBagConstraints newBlock = new GridBagConstraints();
		newBlock.gridx = 0;
		newBlock.gridy = 0;
		newBlock.anchor = GridBagConstraints.WEST;
		newBlock.weightx = 0.5;
		add(getNewBlockButton(), newBlock);
		final GridBagConstraints reset = new GridBagConstraints();
		reset.gridx = 1;
		reset.gridy = 0;
		reset.anchor = GridBagConstraints.EAST;
		reset.weightx = 0.5;
		reset.weighty = 1;
		add(getResetButton(), reset);
	}

	private VirtualWorld virtualWorld;

	public VirtualWorld getVirtualWorld() {
		return this.virtualWorld;
	}

	public void setVirtualWorld(final VirtualWorld vw) {
		this.virtualWorld = vw;
	}

	private ModelTower modelTower;

	public ModelTower getModelTower() {
		return this.modelTower;
	}

	public void setModelTower(final ModelTower m) {
		this.modelTower = m;
	}

	private PerceptionTower perceptionTower;

	public PerceptionTower getPerceptionTower() {
		return this.perceptionTower;
	}

	public void setPerceptionTower(final PerceptionTower p) {
		this.perceptionTower = p;
	}

	private ActionTower actionTower;

	public ActionTower getActionTower() {
		return this.actionTower;
	}

	public void setActionTower(final ActionTower a) {
		this.actionTower = a;
	}

	private MessageLabel messageLabel;

	public MessageLabel getMessageLabel() {
		return this.messageLabel;
	}

	public void setMessageLabel(final MessageLabel sl) {
		this.messageLabel = sl;
	}

	class NewBlockGuiAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		NewBlockGuiAction() {
			super("New Block");
		}

		@Override
		public void actionPerformed(final ActionEvent ae) {
			getMessageLabel().setMessage(MessageLabel.NIL, "");
			if (getVirtualWorld().isMaxNumOfBlocksReached()) {
				getMessageLabel().setMessage(MessageLabel.INFO, "Maximum number of blocks reached.");
			} else {
				getVirtualWorld().setToAddNewBlock(true);
			}
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
			getMessageLabel().setMessage(MessageLabel.INFO, "Click on \"New Block\" to create blocks.");
			getVirtualWorld().setToBeReset(true);
			getActionTower().setToBeReset(true);
		}
	}

	private ResetGuiAction resetGuiAction;

	protected ResetGuiAction getResetGuiAction() {
		if (this.resetGuiAction == null) {
			this.resetGuiAction = new ResetGuiAction();
		}
		return this.resetGuiAction;
	}

	private JButton newBlockButton;

	protected JButton getNewBlockButton() {
		if (this.newBlockButton == null) {
			this.newBlockButton = new JButton();
			this.newBlockButton.setAction(getNewBlockGuiAction());
		}
		return this.newBlockButton;
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
