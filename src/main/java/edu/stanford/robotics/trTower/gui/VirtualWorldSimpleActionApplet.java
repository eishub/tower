package edu.stanford.robotics.trTower.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.stanford.robotics.trTower.virtualWorld.VirtualWorldActionFactory;

@SuppressWarnings("deprecation")
public class VirtualWorldSimpleActionApplet extends JApplet {
	private static final long serialVersionUID = 1L;

	public VirtualWorldSimpleActionApplet() {
		getContentPane().setLayout(new BorderLayout(5, 5));
		getContentPane().add(getTopPanel(), BorderLayout.NORTH);
		getContentPane().add(getMessageLabel(), BorderLayout.CENTER);
	}

	class NewBlockGuiAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		NewBlockGuiAction() {
			super("New Block");
		}

		@Override
		public void actionPerformed(final ActionEvent ae) {
			getVirtualWorldApplet().getVirtualWorld().addNewBlock();
		}
	}

	private NewBlockGuiAction newBlockGuiAction;

	protected NewBlockGuiAction getNewBlockGuiAction() {
		if (this.newBlockGuiAction == null) {
			this.newBlockGuiAction = new NewBlockGuiAction();
		}
		return this.newBlockGuiAction;
	}

	class PickupGuiAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		PickupGuiAction() {
			super("Pickup");
		}

		@Override
		public void actionPerformed(final ActionEvent ae) {
			getPickupAction().setTargetBlockId(getPickupTargetTextField().getText());
			getVirtualWorldApplet().getTheVirtualWorld().setDurativeArmAction(getPickupAction());
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
			super("Putdown");
		}

		@Override
		public void actionPerformed(final ActionEvent ae) {
			getPutdownAction().setSubjectBlockId(getPutdownSubjectTextField().getText());
			getPutdownAction().setTargetBlockId(getPutdownTargetTextField().getText());
			getVirtualWorldApplet().getTheVirtualWorld().setDurativeArmAction(getPutdownAction());
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
			super("Nil");
		}

		@Override
		public void actionPerformed(final ActionEvent ae) {
			getVirtualWorldApplet().getTheVirtualWorld().setDurativeArmAction(getNilAction());
		}
	}

	private NilGuiAction nilGuiAction;

	protected NilGuiAction getNilGuiAction() {
		if (this.nilGuiAction == null) {
			this.nilGuiAction = new NilGuiAction();
		}
		return this.nilGuiAction;
	}

	class OneCharTextField extends JTextField {
		private static final long serialVersionUID = 1L;

		OneCharTextField() {
			super(3);
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

	private VirtualWorldActionFactory virtualWorldActionFactory;

	protected VirtualWorldActionFactory getVirtualWorldActionFactory() {
		if (this.virtualWorldActionFactory == null) {
			this.virtualWorldActionFactory = new VirtualWorldActionFactory();
			this.virtualWorldActionFactory.setVirtualWorld(getVirtualWorldApplet().getTheVirtualWorld());
		}
		return this.virtualWorldActionFactory;
	}

	private NilAction nilAction;

	protected NilAction getNilAction() {
		if (this.nilAction == null) {
			this.nilAction = getVirtualWorldActionFactory().createNilAction();
		}
		return this.nilAction;
	}

	private PickupAction pickupAction;

	protected PickupAction getPickupAction() {
		if (this.pickupAction == null) {
			this.pickupAction = getVirtualWorldActionFactory().createPickupAction();
		}
		return this.pickupAction;
	}

	private PutdownAction putdownAction;

	protected PutdownAction getPutdownAction() {
		if (this.putdownAction == null) {
			this.putdownAction = getVirtualWorldActionFactory().createPutdownAction();
		}
		return this.putdownAction;
	}

	private VirtualWorldApplet virtualWorldApplet;

	protected VirtualWorldApplet getVirtualWorldApplet() {
		if (this.virtualWorldApplet == null) {
			this.virtualWorldApplet = new VirtualWorldApplet();
			// put in some inits here
		}
		return this.virtualWorldApplet;
	}

	private Box controlBox;

	protected Box getControlBox() {
		if (this.controlBox == null) {
			this.controlBox = Box.createVerticalBox();
			this.controlBox.add(Box.createVerticalStrut(10));
			this.controlBox.add(getNewBlockButtonBox());
			this.controlBox.add(Box.createVerticalStrut(20));
			this.controlBox.add(getPickupButtonBox());
			this.controlBox.add(Box.createVerticalStrut(10));
			this.controlBox.add(getPutdownButtonBox());
			this.controlBox.add(Box.createVerticalStrut(20));
			this.controlBox.add(getNilButtonBox());
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

	private Box pickupButtonBox;

	protected Box getPickupButtonBox() {
		if (this.pickupButtonBox == null) {
			this.pickupButtonBox = Box.createHorizontalBox();
			this.pickupButtonBox.add(getPickupButton());
			this.pickupButtonBox.add(Box.createRigidArea(new Dimension(10, 0)));
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
		}
		return this.pickupTargetTextField;
	}

	private Box putdownButtonBox;

	protected Box getPutdownButtonBox() {
		if (this.putdownButtonBox == null) {
			this.putdownButtonBox = Box.createHorizontalBox();
			this.putdownButtonBox.add(getPutdownButton());
			this.putdownButtonBox.add(Box.createRigidArea(new Dimension(10, 0)));
			this.putdownButtonBox.add(getPutdownSubjectTextField());
			this.putdownButtonBox.add(Box.createRigidArea(new Dimension(10, 0)));
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
		}
		return this.putdownSubjectTextField;
	}

	private JTextField putdownTargetTextField;

	protected JTextField getPutdownTargetTextField() {
		if (this.putdownTargetTextField == null) {
			this.putdownTargetTextField = new OneCharTextField();
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
		}
		return this.nilButton;
	}

	private JLabel messageLabel;

	protected JLabel getMessageLabel() {
		if (this.messageLabel == null) {
			this.messageLabel = new JLabel();
			this.messageLabel.setBorder(BorderFactory.createTitledBorder("Output Messenges"));
		}
		return this.messageLabel;
	}

	private JPanel topPanel;

	protected JPanel getTopPanel() {
		if (this.topPanel == null) {
			this.topPanel = new JPanel();
			this.topPanel.setLayout(new BorderLayout(5, 5));
			this.topPanel.add(getVirtualWorldApplet(), BorderLayout.WEST);
			this.topPanel.add(getControlBox(), BorderLayout.CENTER);
		}
		return this.topPanel;
	}

	@Override
	public void start() {
		getVirtualWorldApplet().start();
	}

	@Override
	public void stop() {
		getVirtualWorldApplet().stop();
	}
}
