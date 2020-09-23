package edu.stanford.robotics.trTower.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.stanford.robotics.trTower.TRTower;
import edu.stanford.robotics.trTower.actionTower.ActionTower;
import edu.stanford.robotics.trTower.actionTower.TRCallDescription;
import edu.stanford.robotics.trTower.common.ListUtilities;

public class TRCallPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	protected static final int hs = 10;
	protected static final int vs = 10;
	protected static final String listDelimiters = " ,";

	public TRCallPanel() {
		setLayout(new GridBagLayout());
		final GridBagConstraints title = new GridBagConstraints();
		title.gridx = 0;
		title.gridy = 0;
		title.anchor = GridBagConstraints.WEST;
		add(getDurativeActionActionLabel(), title);
		final GridBagConstraints control = new GridBagConstraints();
		control.gridx = 0;
		control.gridy = 2;
		control.fill = GridBagConstraints.BOTH;
		control.weightx = 1;
		control.weighty = 1;
		add(getControlBox(), control);
	}

	private ActionTower actionTower;

	public ActionTower getActionTower() {
		return this.actionTower;
	}

	public void setActionTower(final ActionTower at) {
		this.actionTower = at;
	}

	private MessageLabel messageLabel;

	public MessageLabel getMessageLabel() {
		return this.messageLabel;
	}

	public void setMessageLabel(final MessageLabel sl) {
		this.messageLabel = sl;
	}

	class PickupGuiAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		PickupGuiAction() {
			super("pickup");
		}

		@Override
		public void actionPerformed(final ActionEvent ae) {
			final String uppercaseTarget = getPickupTargetTextField().getText().trim().toUpperCase();
			if (uppercaseTarget.equals("")) {
				// empty target
				getMessageLabel().setMessage(MessageLabel.INFO, "Enter pickup target block ID.");
			} else if (!getActionTower().isValidBlockId(uppercaseTarget)) {
				// invalid target
				getMessageLabel().setMessage(MessageLabel.ERROR, "Invalid Block ID: " + uppercaseTarget + ".");
			} else {
				// valid input
				getMessageLabel().setMessage(MessageLabel.NIL, "");

				final TRCallDescription d = new TRCallDescription();
				d.setTrFunction(TRCallDescription.PICKUP);
				d.setParameterList(ListUtilities.newList(uppercaseTarget));
				getActionTower().setActiveTRCallDescription(d);
			}
		}
	}

	private PickupGuiAction pickupGuiAction;

	protected PickupGuiAction getPickupGuiAction() {
		if (this.pickupGuiAction == null) {
			this.pickupGuiAction = new PickupGuiAction();
		}
		return this.pickupGuiAction;
	}

	class PutdownGuiAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		PutdownGuiAction() {
			super("putdown");
		}

		@Override
		public void actionPerformed(final ActionEvent ae) {
			final String uppercaseSubject = getPutdownSubjectTextField().getText().trim().toUpperCase();
			final String uppercaseTarget = getPutdownTargetTextField().getText().trim().toUpperCase();
			if (uppercaseSubject.equals("")) {
				// subject empty
				getMessageLabel().setMessage(MessageLabel.INFO, "Enter putdown block ID.");
			} else if (!getActionTower().isValidBlockId(uppercaseSubject)) {
				// invalid subject
				getMessageLabel().setMessage(MessageLabel.ERROR, "Invalid Block ID: " + uppercaseSubject + ".");
			} else if (uppercaseTarget.equals("")) {
				// target empty
				getMessageLabel().setMessage(MessageLabel.INFO, "Enter putdown destination block ID or table ID (TA).");
			} else if (!getActionTower().isValidTargetId(uppercaseTarget)) {
				// invalid target
				getMessageLabel().setMessage(MessageLabel.ERROR, "Invalid Target ID: " + uppercaseTarget + ".");
			} else {
				// valid inputs
				getMessageLabel().setMessage(MessageLabel.NIL, "");

				final TRCallDescription d = new TRCallDescription();
				d.setTrFunction(TRCallDescription.PUTDOWN);
				d.setParameterList(ListUtilities.newList(uppercaseSubject, uppercaseTarget));
				getActionTower().setActiveTRCallDescription(d);
			}
		}
	}

	private PutdownGuiAction putdownGuiAction;

	PutdownGuiAction getPutdownGuiAction() {
		if (this.putdownGuiAction == null) {
			this.putdownGuiAction = new PutdownGuiAction();
		}
		return this.putdownGuiAction;
	}

	class NilGuiAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		NilGuiAction() {
			super("nil");
		}

		@Override
		public void actionPerformed(final ActionEvent ae) {
			getMessageLabel().setMessage(MessageLabel.NIL, "");

			final TRCallDescription d = new TRCallDescription();
			d.setTrFunction(TRCallDescription.NIL);
			getActionTower().setActiveTRCallDescription(d);
		}
	}

	private NilGuiAction nilGuiAction;

	protected NilGuiAction getNilGuiAction() {
		if (this.nilGuiAction == null) {
			this.nilGuiAction = new NilGuiAction();
		}
		return this.nilGuiAction;
	}

	class MakeTowerGuiAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		MakeTowerGuiAction() {
			super("maketower");
		}

		@Override
		public void actionPerformed(final ActionEvent ae) {
			final String uppercaseList = getMakeTowerListTextField().getText().toUpperCase();
			final List<String> blockList = ListUtilities.stringToList(uppercaseList, listDelimiters);
			if (blockList.isEmpty()) {
				// list empty
				getMessageLabel().setMessage(MessageLabel.INFO, "Enter tower block ID(s) separated by space.");
			} else {
				// list not empty
				final Iterator<String> bli = blockList.iterator();
				boolean isAllBlockIdValid = true;
				while (bli.hasNext()) {
					final String uppercaseBlockId = (bli.next());
					if (!getActionTower().isValidBlockId(uppercaseBlockId)) {
						// block ID invalid
						isAllBlockIdValid = false;
						getMessageLabel().setMessage(MessageLabel.ERROR, "Invalid Block ID: " + uppercaseBlockId + ".");
						break;
					}
				}
				if (isAllBlockIdValid) {
					getMessageLabel().setMessage(MessageLabel.NIL, "");

					final TRCallDescription d = new TRCallDescription();
					d.setTrFunction(TRCallDescription.MAKETOWER);
					d.setParameterList(blockList);
					getActionTower().setActiveTRCallDescription(d);
				}
			}
		}
	}

	private MakeTowerGuiAction makeTowerGuiAction;

	protected MakeTowerGuiAction getMakeTowerGuiAction() {
		if (this.makeTowerGuiAction == null) {
			this.makeTowerGuiAction = new MakeTowerGuiAction();
		}
		return this.makeTowerGuiAction;
	}

	class MoveToTableGuiAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		MoveToTableGuiAction() {
			super("move-to-table");
		}

		@Override
		public void actionPerformed(final ActionEvent ae) {
			final String uppercaseBlockId = getMoveToTableTextField().getText().trim().toUpperCase();
			if (uppercaseBlockId.equals("")) {
				// empty input
				getMessageLabel().setMessage(MessageLabel.INFO, "Enter move-to-table block ID.");
			} else if (!getActionTower().isValidBlockId(uppercaseBlockId)) {
				// invalid Block ID
				getMessageLabel().setMessage(MessageLabel.ERROR, "Invalid Block ID: " + uppercaseBlockId + ".");
			} else {
				// Block ID valid
				getMessageLabel().setMessage(MessageLabel.NIL, "");

				final TRCallDescription d = new TRCallDescription();
				d.setTrFunction(TRCallDescription.MOVETOTABLE);
				d.setParameterList(ListUtilities.newList(uppercaseBlockId));
				getActionTower().setActiveTRCallDescription(d);
			}
		}
	}

	private MoveToTableGuiAction moveToTableGuiAction;

	protected MoveToTableGuiAction getMoveToTableGuiAction() {
		if (this.moveToTableGuiAction == null) {
			this.moveToTableGuiAction = new MoveToTableGuiAction();
		}
		return this.moveToTableGuiAction;
	}

	class MoveGuiAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		MoveGuiAction() {
			super("move");
		}

		@Override
		public void actionPerformed(final ActionEvent ae) {
			// both subject and target are Block ID's
			final String uppercaseSubject = getMoveSubjectTextField().getText().trim().toUpperCase();
			final String uppercaseTarget = getMoveTargetTextField().getText().trim().toUpperCase();
			if (uppercaseSubject.equals("")) {
				getMessageLabel().setMessage(MessageLabel.INFO, "Enter move block ID.");
			} else if (!getActionTower().isValidBlockId(uppercaseSubject)) {
				// subject not valid Block ID
				getMessageLabel().setMessage(MessageLabel.ERROR, "Invalid Block ID: " + uppercaseSubject + ".");
			} else if (uppercaseTarget.equals("")) {
				getMessageLabel().setMessage(MessageLabel.INFO, "Enter move destination block ID.");
			} else if (!getActionTower().isValidBlockId(uppercaseTarget)) {
				// target not valid Block ID
				getMessageLabel().setMessage(MessageLabel.ERROR, "Invalid Block ID: " + uppercaseTarget + ".");
			} else {
				// subject and target are valid
				getMessageLabel().setMessage(MessageLabel.NIL, "");

				final TRCallDescription d = new TRCallDescription();
				d.setTrFunction(TRCallDescription.MOVE);
				d.setParameterList(ListUtilities.newList(uppercaseSubject, uppercaseTarget));
				getActionTower().setActiveTRCallDescription(d);
			}
		}
	}

	private MoveGuiAction moveGuiAction;

	protected MoveGuiAction getMoveGuiAction() {
		if (this.moveGuiAction == null) {
			this.moveGuiAction = new MoveGuiAction();
		}
		return this.moveGuiAction;
	}

	class UnpileGuiAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		UnpileGuiAction() {
			super("unpile");
		}

		@Override
		public void actionPerformed(final ActionEvent ae) {
			final String uppercaseBlockId = getUnpileTextField().getText().trim().toUpperCase();
			if (uppercaseBlockId.equals("")) {
				getMessageLabel().setMessage(MessageLabel.INFO, "Enter unpile block ID.");
			} else if (!getActionTower().isValidBlockId(uppercaseBlockId)) {
				// invalid block id
				getMessageLabel().setMessage(MessageLabel.ERROR, "Invalid Block ID: " + uppercaseBlockId + ".");
			} else {
				getMessageLabel().setMessage(MessageLabel.NIL, "");

				// block id valid
				final TRCallDescription d = new TRCallDescription();
				d.setTrFunction(TRCallDescription.UNPILE);
				d.setParameterList(ListUtilities.newList(uppercaseBlockId));
				getActionTower().setActiveTRCallDescription(d);
			}
		}
	}

	private UnpileGuiAction unpileGuiAction;

	protected UnpileGuiAction getUnpileGuiAction() {
		if (this.unpileGuiAction == null) {
			this.unpileGuiAction = new UnpileGuiAction();
		}
		return this.unpileGuiAction;
	}

	private ActionLabel durativeActionActionLabel;

	protected ActionLabel getDurativeActionActionLabel() {
		if (this.durativeActionActionLabel == null) {
			this.durativeActionActionLabel = new ActionLabel();
			this.durativeActionActionLabel.setText("Durative Action");
			this.durativeActionActionLabel.setAction(new AbstractAction("DurativeAction") {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(final ActionEvent ae) {
					final boolean useBuiltInHelpBrowser = (ae.getModifiers() & ActionEvent.SHIFT_MASK) == 0 ? false
							: true;
					TRTower.getInstance().showHelp(useBuiltInHelpBrowser, "Durative Action",
							"help/DurativeAction.html");
				}
			});
		}
		return this.durativeActionActionLabel;
	}

	class OneCharTextField extends JTextField {
		private static final long serialVersionUID = 1L;

		OneCharTextField() {
			super(2);
			setMargin(new Insets(3, 2, 3, 2));
		}

		@Override
		public Dimension getMaximumSize() {
			return getPreferredSize();
		}

		@Override
		public Dimension getMinimumSize() {
			return getPreferredSize();
		}
	}

	class ListTextField extends JTextField {
		private static final long serialVersionUID = 1L;

		ListTextField() {
			super(9);
			setMargin(new Insets(3, 2, 3, 2));
		}

		@Override
		public Dimension getMaximumSize() {
			return getPreferredSize();
		}

		@Override
		public Dimension getMinimumSize() {
			return getPreferredSize();
		}
	}

	private Box controlBox;

	protected Box getControlBox() {
		if (this.controlBox == null) {
			this.controlBox = Box.createVerticalBox();
			this.controlBox.add(Box.createVerticalStrut(vs));
			this.controlBox.add(getMakeTowerBox());
			this.controlBox.add(Box.createVerticalStrut(vs));
			this.controlBox.add(getMoveToTableBox());
			this.controlBox.add(Box.createVerticalStrut(vs));
			this.controlBox.add(getMoveBox());
			this.controlBox.add(Box.createVerticalStrut(vs));
			this.controlBox.add(getUnpileBox());
			this.controlBox.add(Box.createVerticalStrut(vs));
			this.controlBox.add(getPutdownButtonBox());
			this.controlBox.add(Box.createVerticalStrut(vs));
			this.controlBox.add(getPickupButtonBox());
			this.controlBox.add(Box.createVerticalStrut(vs));
			this.controlBox.add(getNilButtonBox());
			this.controlBox.add(Box.createVerticalStrut(vs));
			this.controlBox.add(Box.createVerticalGlue());
		}
		return this.controlBox;
	}

	private Box pickupButtonBox;

	protected Box getPickupButtonBox() {
		if (this.pickupButtonBox == null) {
			this.pickupButtonBox = Box.createHorizontalBox();
			this.pickupButtonBox.add(getPickupButton());
			this.pickupButtonBox.add(Box.createRigidArea(new Dimension(hs, 0)));
			this.pickupButtonBox.add(getPickupTargetTextField());
			this.pickupButtonBox.add(Box.createHorizontalGlue());
		}
		return this.pickupButtonBox;
	}

	private JButton pickupButton;

	protected JButton getPickupButton() {
		if (this.pickupButton == null) {
			this.pickupButton = new JButton();
			this.pickupButton.setAction(getPickupGuiAction());
		}
		return this.pickupButton;
	}

	private JTextField pickupTargetTextField;

	protected JTextField getPickupTargetTextField() {
		if (this.pickupTargetTextField == null) {
			this.pickupTargetTextField = new OneCharTextField();
			this.pickupTargetTextField.addActionListener(new AbstractAction("TargetText") {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(final ActionEvent ae) {
					getPickupButton().doClick();
				}
			});

		}
		return this.pickupTargetTextField;
	}

	private Box putdownButtonBox;

	protected Box getPutdownButtonBox() {
		if (this.putdownButtonBox == null) {
			this.putdownButtonBox = Box.createHorizontalBox();
			this.putdownButtonBox.add(getPutdownButton());
			this.putdownButtonBox.add(Box.createRigidArea(new Dimension(hs, 0)));
			this.putdownButtonBox.add(getPutdownSubjectTextField());
			this.putdownButtonBox.add(Box.createRigidArea(new Dimension(hs, 0)));
			this.putdownButtonBox.add(getPutdownTargetTextField());
			this.putdownButtonBox.add(Box.createHorizontalGlue());
		}
		return this.putdownButtonBox;
	}

	private JButton putdownButton;

	protected JButton getPutdownButton() {
		if (this.putdownButton == null) {
			this.putdownButton = new JButton();
			this.putdownButton.setAction(getPutdownGuiAction());
		}
		return this.putdownButton;
	}

	private JTextField putdownSubjectTextField;

	protected JTextField getPutdownSubjectTextField() {
		if (this.putdownSubjectTextField == null) {
			this.putdownSubjectTextField = new OneCharTextField();
			this.putdownSubjectTextField.addActionListener(new AbstractAction("PutdownSubjectText") {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(final ActionEvent ae) {
					getPutdownButton().doClick();
				}
			});
		}
		return this.putdownSubjectTextField;
	}

	private JTextField putdownTargetTextField;

	protected JTextField getPutdownTargetTextField() {
		if (this.putdownTargetTextField == null) {
			this.putdownTargetTextField = new OneCharTextField();
			this.putdownTargetTextField.setToolTipText("Enter the ID of a block or the table (TA)");
			this.putdownTargetTextField.addActionListener(new AbstractAction("PutdownTargetText") {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(final ActionEvent ae) {
					getPutdownButton().doClick();
				}
			});
		}
		return this.putdownTargetTextField;
	}

	private Box nilButtonBox;

	protected Box getNilButtonBox() {
		if (this.nilButtonBox == null) {
			this.nilButtonBox = Box.createHorizontalBox();
			this.nilButtonBox.add(getNilButton());
			this.nilButtonBox.add(Box.createHorizontalGlue());
		}
		return this.nilButtonBox;
	}

	private JButton nilButton;

	protected JButton getNilButton() {
		if (this.nilButton == null) {
			this.nilButton = new JButton();
			this.nilButton.setAction(getNilGuiAction());
			this.nilButton.setToolTipText("Does nothing; robot arm returns to home position");
		}
		return this.nilButton;
	}

	private JButton makeTowerButton;

	protected JButton getMakeTowerButton() {
		if (this.makeTowerButton == null) {
			this.makeTowerButton = new JButton();
			this.makeTowerButton.setAction(getMakeTowerGuiAction());
		}
		return this.makeTowerButton;
	}

	private JTextField makeTowerListTextField;

	protected JTextField getMakeTowerListTextField() {
		if (this.makeTowerListTextField == null) {
			this.makeTowerListTextField = new ListTextField();
			this.makeTowerListTextField.setToolTipText("Enter a list of block ID's separated by space");
			this.makeTowerListTextField.addActionListener(new AbstractAction("MakeTowerText") {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(final ActionEvent ae) {
					getMakeTowerButton().doClick();
				}
			});
		}
		return this.makeTowerListTextField;
	}

	private Box makeTowerBox;

	protected Box getMakeTowerBox() {
		if (this.makeTowerBox == null) {
			this.makeTowerBox = Box.createHorizontalBox();
			this.makeTowerBox.add(getMakeTowerButton());
			this.makeTowerBox.add(Box.createRigidArea(new Dimension(hs, 0)));
			this.makeTowerBox.add(getMakeTowerListTextField());
			this.makeTowerBox.add(Box.createHorizontalGlue());
		}
		return this.makeTowerBox;
	}

	private JButton moveToTableButton;

	protected JButton getMoveToTableButton() {
		if (this.moveToTableButton == null) {
			this.moveToTableButton = new JButton();
			this.moveToTableButton.setAction(getMoveToTableGuiAction());
		}
		return this.moveToTableButton;
	}

	private JTextField moveToTableTextField;

	protected JTextField getMoveToTableTextField() {
		if (this.moveToTableTextField == null) {
			this.moveToTableTextField = new OneCharTextField();
			this.moveToTableTextField.addActionListener(new AbstractAction("MoveToTableText") {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(final ActionEvent ae) {
					getMoveToTableButton().doClick();
				}
			});
		}
		return this.moveToTableTextField;
	}

	private Box moveToTableBox;

	protected Box getMoveToTableBox() {
		if (this.moveToTableBox == null) {
			this.moveToTableBox = Box.createHorizontalBox();
			this.moveToTableBox.add(getMoveToTableButton());
			this.moveToTableBox.add(Box.createRigidArea(new Dimension(hs, 0)));
			this.moveToTableBox.add(getMoveToTableTextField());
			this.moveToTableBox.add(Box.createRigidArea(new Dimension(hs, 0)));
			this.moveToTableBox.add(Box.createHorizontalGlue());
		}
		return this.moveToTableBox;
	}

	private JButton moveButton;

	protected JButton getMoveButton() {
		if (this.moveButton == null) {
			this.moveButton = new JButton();
			this.moveButton.setAction(getMoveGuiAction());
		}
		return this.moveButton;
	}

	private JTextField moveSubjectTextField;

	protected JTextField getMoveSubjectTextField() {
		if (this.moveSubjectTextField == null) {
			this.moveSubjectTextField = new OneCharTextField();
			this.moveSubjectTextField.addActionListener(new AbstractAction("MoveSubjectText") {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(final ActionEvent ae) {
					getMoveButton().doClick();
				}
			});
		}
		return this.moveSubjectTextField;
	}

	private JTextField moveTargetTextField;

	protected JTextField getMoveTargetTextField() {
		if (this.moveTargetTextField == null) {
			this.moveTargetTextField = new OneCharTextField();
			this.moveTargetTextField.addActionListener(new AbstractAction("MoveTargetText") {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(final ActionEvent ae) {
					getMoveButton().doClick();
				}
			});
		}
		return this.moveTargetTextField;
	}

	private Box moveBox;

	protected Box getMoveBox() {
		if (this.moveBox == null) {
			this.moveBox = Box.createHorizontalBox();
			this.moveBox.add(getMoveButton());
			this.moveBox.add(Box.createRigidArea(new Dimension(hs, 0)));
			this.moveBox.add(getMoveSubjectTextField());
			this.moveBox.add(Box.createRigidArea(new Dimension(hs, 0)));
			this.moveBox.add(getMoveTargetTextField());
			this.moveBox.add(Box.createRigidArea(new Dimension(hs, 0)));
			this.moveBox.add(Box.createHorizontalGlue());
		}
		return this.moveBox;
	}

	private JButton unpileButton;

	protected JButton getUnpileButton() {
		if (this.unpileButton == null) {
			this.unpileButton = new JButton();
			this.unpileButton.setAction(getUnpileGuiAction());
		}
		return this.unpileButton;
	}

	private JTextField unpileTextField;

	protected JTextField getUnpileTextField() {
		if (this.unpileTextField == null) {
			this.unpileTextField = new OneCharTextField();
			this.unpileTextField.addActionListener(new AbstractAction("UnpileText") {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(final ActionEvent ae) {
					getUnpileButton().doClick();
				}
			});
		}
		return this.unpileTextField;
	}

	private Box unpileBox;

	protected Box getUnpileBox() {
		if (this.unpileBox == null) {
			this.unpileBox = Box.createHorizontalBox();
			this.unpileBox.add(getUnpileButton());
			this.unpileBox.add(Box.createRigidArea(new Dimension(hs, 0)));
			this.unpileBox.add(getUnpileTextField());
			this.unpileBox.add(Box.createRigidArea(new Dimension(hs, 0)));
			this.unpileBox.add(Box.createHorizontalGlue());
		}
		return this.unpileBox;
	}
}
