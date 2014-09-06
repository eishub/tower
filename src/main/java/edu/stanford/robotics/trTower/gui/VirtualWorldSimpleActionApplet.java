package edu.stanford.robotics.trTower.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import edu.stanford.robotics.trTower.virtualWorld.*;

public class VirtualWorldSimpleActionApplet extends JApplet {

	public VirtualWorldSimpleActionApplet() {
		getContentPane().setLayout(new BorderLayout(5, 5));
		getContentPane().add(getTopPanel(), BorderLayout.NORTH);
		getContentPane().add(getMessageLabel(), BorderLayout.CENTER);
	}

	// --- GUI actions

	class NewBlockGuiAction extends AbstractAction {
		NewBlockGuiAction() {
			super("New Block");
		}

		public void actionPerformed(ActionEvent ae) {
			// System.out.println("New Block");
			getVirtualWorldApplet().getVirtualWorld().addNewBlock();
		}
	}

	private NewBlockGuiAction newBlockGuiAction;

	protected NewBlockGuiAction getNewBlockGuiAction() {
		if (newBlockGuiAction == null) {
			newBlockGuiAction = new NewBlockGuiAction();
		}
		return newBlockGuiAction;
	}

	class PickupGuiAction extends AbstractAction {
		PickupGuiAction() {
			super("Pickup");
		}

		public void actionPerformed(ActionEvent ae) {
			// System.out.println("Pickup");
			getPickupAction().setTargetBlockId(
					getPickupTargetTextField().getText());
			getVirtualWorldApplet().getTheVirtualWorld().setDurativeArmAction(
					getPickupAction());
		}
	}

	private PickupGuiAction pickupGuiAction;

	protected PickupGuiAction getPickupGuiAction() {
		if (pickupGuiAction == null) {
			pickupGuiAction = new PickupGuiAction();
		}
		return pickupGuiAction;
	}

	class PutdownGuiAction extends AbstractAction {
		PutdownGuiAction() {
			super("Putdown");
		}

		public void actionPerformed(ActionEvent ae) {
			// System.out.println("Putdown");
			getPutdownAction().setSubjectBlockId(
					getPutdownSubjectTextField().getText());
			getPutdownAction().setTargetBlockId(
					getPutdownTargetTextField().getText());
			getVirtualWorldApplet().getTheVirtualWorld().setDurativeArmAction(
					getPutdownAction());
		}
	}

	private PutdownGuiAction putdownGuiAction;

	PutdownGuiAction getPutdownGuiAction() {
		if (putdownGuiAction == null) {
			putdownGuiAction = new PutdownGuiAction();
		}
		return putdownGuiAction;
	}

	class NilGuiAction extends AbstractAction {
		NilGuiAction() {
			super("Nil");
		}

		public void actionPerformed(ActionEvent ae) {
			// System.out.println("Nil");
			getVirtualWorldApplet().getTheVirtualWorld().setDurativeArmAction(
					getNilAction());
		}
	}

	private NilGuiAction nilGuiAction;

	protected NilGuiAction getNilGuiAction() {
		if (nilGuiAction == null) {
			nilGuiAction = new NilGuiAction();
		}
		return nilGuiAction;
	}

	// --- components

	class OneCharTextField extends JTextField {
		OneCharTextField() {
			super(3);
			setMargin(new Insets(3, 2, 3, 2));
		}

		public Dimension getMaximumSize() {
			return getPreferredSize();
		}

		public Dimension getMinimumSize() {
			return getPreferredSize();
		}
	}

	private VirtualWorldActionFactory virtualWorldActionFactory;

	protected VirtualWorldActionFactory getVirtualWorldActionFactory() {
		if (virtualWorldActionFactory == null) {
			virtualWorldActionFactory = new VirtualWorldActionFactory();
			virtualWorldActionFactory.setVirtualWorld(getVirtualWorldApplet()
					.getTheVirtualWorld());
		}
		return virtualWorldActionFactory;
	}

	private NilAction nilAction;

	protected NilAction getNilAction() {
		if (nilAction == null) {
			nilAction = getVirtualWorldActionFactory().createNilAction();
		}
		return nilAction;
	}

	private PickupAction pickupAction;

	protected PickupAction getPickupAction() {
		if (pickupAction == null) {
			pickupAction = getVirtualWorldActionFactory().createPickupAction();
		}
		return pickupAction;
	}

	private PutdownAction putdownAction;

	protected PutdownAction getPutdownAction() {
		if (putdownAction == null) {
			putdownAction = getVirtualWorldActionFactory()
					.createPutdownAction();
		}
		return putdownAction;
	}

	private VirtualWorldApplet virtualWorldApplet;

	protected VirtualWorldApplet getVirtualWorldApplet() {
		if (virtualWorldApplet == null) {
			virtualWorldApplet = new VirtualWorldApplet();
			// put in some inits here
		}
		return virtualWorldApplet;
	}

	private Box controlBox;

	protected Box getControlBox() {
		if (controlBox == null) {
			controlBox = Box.createVerticalBox();
			// controlBox.setBorder(BorderFactory.createEmptyBorder(2, 2, 2,
			// 2));
			controlBox.add(Box.createVerticalStrut(10));
			controlBox.add(getNewBlockButtonBox());
			controlBox.add(Box.createVerticalStrut(20));
			controlBox.add(getPickupButtonBox());
			controlBox.add(Box.createVerticalStrut(10));
			controlBox.add(getPutdownButtonBox());
			controlBox.add(Box.createVerticalStrut(20));
			controlBox.add(getNilButtonBox());
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

	private Box pickupButtonBox;

	protected Box getPickupButtonBox() {
		if (pickupButtonBox == null) {
			pickupButtonBox = Box.createHorizontalBox();
			pickupButtonBox.add(getPickupButton());
			pickupButtonBox.add(Box.createRigidArea(new Dimension(10, 0)));
			pickupButtonBox.add(getPickupTargetTextField());
			pickupButtonBox.add(Box.createHorizontalGlue());
		}
		return pickupButtonBox;
	}

	private JButton pickupButton;

	protected JButton getPickupButton() {
		if (pickupButton == null) {
			pickupButton = new JButton();
			pickupButton.setAction(getPickupGuiAction());
		}
		return pickupButton;
	}

	private JTextField pickupTargetTextField;

	protected JTextField getPickupTargetTextField() {
		if (pickupTargetTextField == null) {
			pickupTargetTextField = new OneCharTextField();
		}
		return pickupTargetTextField;
	}

	private Box putdownButtonBox;

	protected Box getPutdownButtonBox() {
		if (putdownButtonBox == null) {
			putdownButtonBox = Box.createHorizontalBox();
			putdownButtonBox.add(getPutdownButton());
			putdownButtonBox.add(Box.createRigidArea(new Dimension(10, 0)));
			putdownButtonBox.add(getPutdownSubjectTextField());
			putdownButtonBox.add(Box.createRigidArea(new Dimension(10, 0)));
			putdownButtonBox.add(getPutdownTargetTextField());
			putdownButtonBox.add(Box.createHorizontalGlue());
		}
		return putdownButtonBox;
	}

	private JButton putdownButton;

	protected JButton getPutdownButton() {
		if (putdownButton == null) {
			putdownButton = new JButton();
			putdownButton.setAction(getPutdownGuiAction());
		}
		return putdownButton;
	}

	private JTextField putdownSubjectTextField;

	protected JTextField getPutdownSubjectTextField() {
		if (putdownSubjectTextField == null) {
			putdownSubjectTextField = new OneCharTextField();
		}
		return putdownSubjectTextField;
	}

	private JTextField putdownTargetTextField;

	protected JTextField getPutdownTargetTextField() {
		if (putdownTargetTextField == null) {
			putdownTargetTextField = new OneCharTextField();
		}
		return putdownTargetTextField;
	}

	private Box nilButtonBox;

	protected Box getNilButtonBox() {
		if (nilButtonBox == null) {
			nilButtonBox = Box.createHorizontalBox();
			nilButtonBox.add(getNilButton());
			nilButtonBox.add(Box.createHorizontalGlue());
		}
		return nilButtonBox;
	}

	private JButton nilButton;

	protected JButton getNilButton() {
		if (nilButton == null) {
			nilButton = new JButton();
			nilButton.setAction(getNilGuiAction());
		}
		return nilButton;
	}

	private JLabel messageLabel;

	protected JLabel getMessageLabel() {
		if (messageLabel == null) {
			messageLabel = new JLabel();
			messageLabel.setBorder(BorderFactory
					.createTitledBorder("Output Messenges"));
		}
		return messageLabel;
	}

	private JPanel topPanel;

	protected JPanel getTopPanel() {
		if (topPanel == null) {
			topPanel = new JPanel();
			topPanel.setLayout(new BorderLayout(5, 5));
			topPanel.add(getVirtualWorldApplet(), BorderLayout.WEST);
			topPanel.add(getControlBox(), BorderLayout.CENTER);
		}
		return topPanel;
	}

	// // public methods
	// public void initialize() {
	// getVirtualWorldApplet().initialize();
	// }

	public void start() {
		getVirtualWorldApplet().start();
	}

	public void stop() {
		getVirtualWorldApplet().stop();
	}
}
