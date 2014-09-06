package edu.stanford.robotics.trTower.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;
import java.util.List;

import edu.stanford.robotics.trTower.*;
import edu.stanford.robotics.trTower.actionTower.*;
import edu.stanford.robotics.trTower.common.*;

public class TRCallPanel extends JPanel {

    protected static final int hs = 10;
    protected static final int vs = 10;
    protected static final String listDelimiters = " ,";

    public TRCallPanel() {
	setLayout(new GridBagLayout());
	GridBagConstraints title = new GridBagConstraints();
	title.gridx = 0;
	title.gridy = 0;
	title.anchor = GridBagConstraints.WEST;
	add(getDurativeActionActionLabel(), title);
	GridBagConstraints control = new GridBagConstraints();
	control.gridx = 0;
	control.gridy = 2;
	control.fill = GridBagConstraints.BOTH;
	control.weightx = 1;
	control.weighty = 1;
	add(getControlBox(), control);
    }
    
    // --- attributes
    
    private ActionTower actionTower;
    public ActionTower getActionTower() { return actionTower; }
    public void setActionTower(ActionTower at) { actionTower = at; }

    private MessageLabel messageLabel;
    public MessageLabel getMessageLabel() { return messageLabel; }
    public void setMessageLabel(MessageLabel sl) { messageLabel = sl; }

    // --- GUI actions

    class PickupGuiAction extends AbstractAction {
	PickupGuiAction() {
	    super("pickup");
	}
	public void actionPerformed(ActionEvent ae) {
	    String uppercaseTarget = 
		getPickupTargetTextField().getText().trim().toUpperCase();
	    if (uppercaseTarget.equals("")) {
		// empty target
		getMessageLabel().setMessage(MessageLabel.INFO,
					     "Enter pickup target block ID.");
	    } else if (!getActionTower().isValidBlockId(uppercaseTarget)) {
		// invalid target
		getMessageLabel().setMessage(MessageLabel.ERROR, 
					     "Invalid Block ID: " 
					     + uppercaseTarget + ".");
	    } else {
		// valid input
		getMessageLabel().setMessage(MessageLabel.NIL, "");

		TRCallDescription d = new TRCallDescription();
		d.setTrFunction(TRCallDescription.PICKUP);
		d.setParameterList(ListUtilities.newList(uppercaseTarget));
		getActionTower().setActiveTRCallDescription(d);
	    }
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
	    super("putdown");
	}
	public void actionPerformed(ActionEvent ae) {
	    String uppercaseSubject =
		getPutdownSubjectTextField().getText().trim().toUpperCase();
	    String uppercaseTarget =
		getPutdownTargetTextField().getText().trim().toUpperCase();
	    if (uppercaseSubject.equals("")) {
		// subject empty
		getMessageLabel().setMessage(MessageLabel.INFO,
					     "Enter putdown block ID.");
	    } else if (!getActionTower().isValidBlockId(uppercaseSubject)) {
		// invalid subject
		getMessageLabel().setMessage(MessageLabel.ERROR,
					     "Invalid Block ID: "
					     + uppercaseSubject + ".");
	    } else if (uppercaseTarget.equals("")) {
		// target empty
		getMessageLabel().setMessage(MessageLabel.INFO,
					     "Enter putdown destination block ID or table ID (TA).");
	    } else if (!getActionTower().isValidTargetId(uppercaseTarget)) {
		// invalid target
		getMessageLabel().setMessage(MessageLabel.ERROR,
					     "Invalid Target ID: "
					     + uppercaseTarget + ".");
	    } else {
		// valid inputs
		getMessageLabel().setMessage(MessageLabel.NIL, "");

		TRCallDescription d = new TRCallDescription();
		d.setTrFunction(TRCallDescription.PUTDOWN);
		d.setParameterList(ListUtilities.newList(uppercaseSubject, 
							 uppercaseTarget));
		getActionTower().setActiveTRCallDescription(d);
	    }
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
	    super("nil");
	}
	public void actionPerformed(ActionEvent ae) {
	    getMessageLabel().setMessage(MessageLabel.NIL, "");

	    TRCallDescription d = new TRCallDescription();
	    d.setTrFunction(TRCallDescription.NIL);
	    getActionTower().setActiveTRCallDescription(d);
	}
    }
    private NilGuiAction nilGuiAction;
    protected NilGuiAction getNilGuiAction() {
	if (nilGuiAction == null) {
	    nilGuiAction = new NilGuiAction();
	}
	return nilGuiAction;
    }

    class MakeTowerGuiAction extends AbstractAction {
	MakeTowerGuiAction() {
	    super("maketower");
	}
	public void actionPerformed(ActionEvent ae) {
	    String uppercaseList = 
		getMakeTowerListTextField().getText().toUpperCase();
	    List blockList = ListUtilities.stringToList(uppercaseList, 
							listDelimiters);
	    if (blockList.isEmpty()) {
		// list empty
		getMessageLabel().setMessage(MessageLabel.INFO,
					     "Enter tower block ID(s) separated by space."); 
	    } else {
		// list not empty
		Iterator bli = blockList.iterator();
		boolean isAllBlockIdValid = true;
		while (bli.hasNext()) {
		    String uppercaseBlockId = (String)(bli.next());
		    if (!getActionTower().isValidBlockId(uppercaseBlockId)) {
			// block ID invalid
			isAllBlockIdValid = false;
			getMessageLabel().setMessage(MessageLabel.ERROR,
						     "Invalid Block ID: "
						     + uppercaseBlockId + ".");
			break;
		    }
		}
		if (isAllBlockIdValid) {
		    getMessageLabel().setMessage(MessageLabel.NIL, "");
		    
		    TRCallDescription d = new TRCallDescription();
		    d.setTrFunction(TRCallDescription.MAKETOWER);
		    d.setParameterList(blockList);
		    getActionTower().setActiveTRCallDescription(d);
		}
	    }
	}
    }
    private MakeTowerGuiAction makeTowerGuiAction;
    protected MakeTowerGuiAction getMakeTowerGuiAction() {
	if (makeTowerGuiAction == null) {
	    makeTowerGuiAction = new MakeTowerGuiAction();
	}
	return makeTowerGuiAction;
    }

    class MoveToTableGuiAction extends AbstractAction {
	MoveToTableGuiAction() {
	    super("move-to-table");
	}
	public void actionPerformed(ActionEvent ae) {
	    String uppercaseBlockId =
		getMoveToTableTextField().getText().trim().toUpperCase();
	    if (uppercaseBlockId.equals("")) {
		// empty input
		getMessageLabel().setMessage(MessageLabel.INFO,
					     "Enter move-to-table block ID.");
	    } else if (!getActionTower().isValidBlockId(uppercaseBlockId)) {
		// invalid Block ID
		getMessageLabel().setMessage(MessageLabel.ERROR,
					     "Invalid Block ID: "
					     + uppercaseBlockId + ".");
	    } else {
		// Block ID valid
		getMessageLabel().setMessage(MessageLabel.NIL, "");

		TRCallDescription d = new TRCallDescription();
		d.setTrFunction(TRCallDescription.MOVETOTABLE);
		d.setParameterList(ListUtilities.newList(uppercaseBlockId));
		getActionTower().setActiveTRCallDescription(d);
	    }
	}
    }
    private MoveToTableGuiAction moveToTableGuiAction;
    protected MoveToTableGuiAction getMoveToTableGuiAction() {
	if (moveToTableGuiAction == null) {
	    moveToTableGuiAction = new MoveToTableGuiAction();
	}
	return moveToTableGuiAction;
    }

    class MoveGuiAction extends AbstractAction {
	MoveGuiAction() {
	    super("move");
	}
	public void actionPerformed(ActionEvent ae) {
	    // both subject and target are Block ID's
	    String uppercaseSubject =
		getMoveSubjectTextField().getText().trim().toUpperCase();
	    String uppercaseTarget =
		getMoveTargetTextField().getText().trim().toUpperCase();
	    if (uppercaseSubject.equals("")) {
		getMessageLabel().setMessage(MessageLabel.INFO,
					     "Enter move block ID.");
	    } else if (!getActionTower().isValidBlockId(uppercaseSubject)) {
		// subject not valid Block ID
		getMessageLabel().setMessage(MessageLabel.ERROR,
					     "Invalid Block ID: "
					     + uppercaseSubject + ".");
	    } else if (uppercaseTarget.equals("")) {
		getMessageLabel().setMessage(MessageLabel.INFO,
					     "Enter move destination block ID.");
	    } else if (!getActionTower().isValidBlockId(uppercaseTarget)) {
		// target not valid Block ID
		getMessageLabel().setMessage(MessageLabel.ERROR,
					      "Invalid Block ID: "
					      + uppercaseTarget + ".");
	    } else {
		// subject and target are valid
		getMessageLabel().setMessage(MessageLabel.NIL, "");

		TRCallDescription d = new TRCallDescription();
		d.setTrFunction(TRCallDescription.MOVE);
		d.setParameterList(ListUtilities.newList(uppercaseSubject,
							 uppercaseTarget));
		getActionTower().setActiveTRCallDescription(d);
	    }
	}
    }
    private MoveGuiAction moveGuiAction;
    protected MoveGuiAction getMoveGuiAction() {
	if (moveGuiAction == null) {
	    moveGuiAction = new MoveGuiAction();
	}
	return moveGuiAction;
    }

    class UnpileGuiAction extends AbstractAction {
	UnpileGuiAction() {
	    super("unpile");
	}
	public void actionPerformed(ActionEvent ae) {
	    String uppercaseBlockId =
		getUnpileTextField().getText().trim().toUpperCase();
	    if (uppercaseBlockId.equals("")) {
		getMessageLabel().setMessage(MessageLabel.INFO,
					    "Enter unpile block ID.");
	    } else if (!getActionTower().isValidBlockId(uppercaseBlockId)) {
		// invalid block id
		getMessageLabel().setMessage(MessageLabel.ERROR,
					     "Invalid Block ID: "
					     + uppercaseBlockId + ".");
	    } else {
		getMessageLabel().setMessage(MessageLabel.NIL, "");

		// block id valid
		TRCallDescription d = new TRCallDescription();
		d.setTrFunction(TRCallDescription.UNPILE);
		d.setParameterList(ListUtilities.newList(uppercaseBlockId));
		getActionTower().setActiveTRCallDescription(d);
	    }
	}
    }
    private UnpileGuiAction unpileGuiAction;
    protected UnpileGuiAction getUnpileGuiAction() {
	if (unpileGuiAction == null) {
	    unpileGuiAction = new UnpileGuiAction();
	}
	return unpileGuiAction;
    }
    
    // --- components

    private ActionLabel durativeActionActionLabel;
    protected ActionLabel getDurativeActionActionLabel() {
	if (durativeActionActionLabel == null) {
	    durativeActionActionLabel = new ActionLabel();
	    durativeActionActionLabel.setText("Durative Action");
	    durativeActionActionLabel.setAction(new AbstractAction("DurativeAction") {
		    public void actionPerformed(ActionEvent ae) {
			boolean useBuiltInHelpBrowser = 
			    (ae.getModifiers()&ActionEvent.SHIFT_MASK)==0? false : true;
			TRTower.getInstance().showHelp(useBuiltInHelpBrowser, 
						       "Durative Action", 
						       "help/DurativeAction.html");
		    }
		});
	}
	return durativeActionActionLabel;
    }
	  
    class OneCharTextField extends JTextField {
	OneCharTextField() {
	    super(2);
	    setMargin(new Insets(3, 2, 3, 2));
	}
	public Dimension getMaximumSize() { return getPreferredSize(); }
	public Dimension getMinimumSize() { return getPreferredSize(); }
    }

    class ListTextField extends JTextField {
	ListTextField() {
	    super(9);
	    setMargin(new Insets(3, 2, 3, 2));
	}
	public Dimension getMaximumSize() { return getPreferredSize(); }
	public Dimension getMinimumSize() { return getPreferredSize(); }
    }


    private Box controlBox;
    protected Box getControlBox() {
	if (controlBox == null) {
	    controlBox = Box.createVerticalBox();
	    controlBox.add(Box.createVerticalStrut(vs));
	    controlBox.add(getMakeTowerBox());
	    controlBox.add(Box.createVerticalStrut(vs));
	    controlBox.add(getMoveToTableBox());
	    controlBox.add(Box.createVerticalStrut(vs));
	    controlBox.add(getMoveBox());
	    controlBox.add(Box.createVerticalStrut(vs));
	    controlBox.add(getUnpileBox());
	    controlBox.add(Box.createVerticalStrut(vs));
	    controlBox.add(getPutdownButtonBox());
	    controlBox.add(Box.createVerticalStrut(vs));
	    controlBox.add(getPickupButtonBox());
	    controlBox.add(Box.createVerticalStrut(vs));
	    controlBox.add(getNilButtonBox());
	    controlBox.add(Box.createVerticalStrut(vs));
	    controlBox.add(Box.createVerticalGlue());
	}
	return controlBox;
    }
    
    private Box pickupButtonBox;
    protected Box getPickupButtonBox() {
	if (pickupButtonBox == null) {
	    pickupButtonBox = Box.createHorizontalBox();
	    pickupButtonBox.add(getPickupButton());
	    pickupButtonBox.add(Box.createRigidArea(new Dimension(hs, 0)));
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
	    pickupTargetTextField.addActionListener(new AbstractAction("TargetText") {
		    public void actionPerformed(ActionEvent ae) {
			getPickupButton().doClick();
		    }
		});

	}
	return pickupTargetTextField;
    }

    private Box putdownButtonBox;
    protected Box getPutdownButtonBox() {
	if (putdownButtonBox == null) {
	    putdownButtonBox = Box.createHorizontalBox();
	    putdownButtonBox.add(getPutdownButton());
	    putdownButtonBox.add(Box.createRigidArea(new Dimension(hs, 0)));
	    putdownButtonBox.add(getPutdownSubjectTextField());
	    putdownButtonBox.add(Box.createRigidArea(new Dimension(hs, 0)));
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
	    putdownSubjectTextField.addActionListener(new AbstractAction("PutdownSubjectText") {
		    public void actionPerformed(ActionEvent ae) {
			getPutdownButton().doClick();
		    }
		});
	}
	return putdownSubjectTextField;
    }
    private JTextField putdownTargetTextField;
    protected JTextField getPutdownTargetTextField() {
	if (putdownTargetTextField == null) {
	    putdownTargetTextField = new OneCharTextField();
	    putdownTargetTextField.setToolTipText("Enter the ID of a block or the table (TA)");
	    putdownTargetTextField.addActionListener(new AbstractAction("PutdownTargetText") {
		    public void actionPerformed(ActionEvent ae) {
			getPutdownButton().doClick();
		    }
		});
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
	    nilButton.setToolTipText("Does nothing; robot arm returns to home position");
	}
	return nilButton;
    }

    private JButton makeTowerButton;
    protected JButton getMakeTowerButton() {
	if (makeTowerButton == null) {
	    makeTowerButton = new JButton();
	    makeTowerButton.setAction(getMakeTowerGuiAction());
	}
	return makeTowerButton;
    }
    private JTextField makeTowerListTextField;
    protected JTextField getMakeTowerListTextField() {
	if (makeTowerListTextField == null) {
	    makeTowerListTextField = new ListTextField();
	    makeTowerListTextField.setToolTipText("Enter a list of block ID's separated by space");
	    makeTowerListTextField.addActionListener(new AbstractAction("MakeTowerText") {
		    public void actionPerformed(ActionEvent ae) {
			getMakeTowerButton().doClick();
		    }
		});
	}
	return makeTowerListTextField;
    }
    private Box makeTowerBox;
    protected Box getMakeTowerBox() {
	if (makeTowerBox == null) {
	    makeTowerBox = Box.createHorizontalBox();
	    makeTowerBox.add(getMakeTowerButton());
	    makeTowerBox.add(Box.createRigidArea(new Dimension(hs, 0)));
	    makeTowerBox.add(getMakeTowerListTextField());
	    //	    makeTowerBox.add(Box.createRigidArea(new Dimension(hs, 0)));
	    makeTowerBox.add(Box.createHorizontalGlue());
	}
	return makeTowerBox;
    }

    private JButton moveToTableButton;
    protected JButton getMoveToTableButton() {
	if (moveToTableButton == null) {
	    moveToTableButton = new JButton();
	    moveToTableButton.setAction(getMoveToTableGuiAction());
	}
	return moveToTableButton;
    }
    private JTextField moveToTableTextField;
    protected JTextField getMoveToTableTextField() {
	if (moveToTableTextField == null) {
	    moveToTableTextField = new OneCharTextField();
	    moveToTableTextField.addActionListener(new AbstractAction("MoveToTableText") {
		    public void actionPerformed(ActionEvent ae) {
			getMoveToTableButton().doClick();
		    }
		});
	}
	return moveToTableTextField;
    }
    private Box moveToTableBox;
    protected Box getMoveToTableBox() {
	if (moveToTableBox == null) {
	    moveToTableBox = Box.createHorizontalBox();
	    moveToTableBox.add(getMoveToTableButton());
	    moveToTableBox.add(Box.createRigidArea(new Dimension(hs, 0)));
	    moveToTableBox.add(getMoveToTableTextField());
	    moveToTableBox.add(Box.createRigidArea(new Dimension(hs, 0)));
	    moveToTableBox.add(Box.createHorizontalGlue());
	}
	return moveToTableBox;
    }

    private JButton moveButton;
    protected JButton getMoveButton() {
	if (moveButton == null) {
	    moveButton = new JButton();
	    moveButton.setAction(getMoveGuiAction());
	}
	return moveButton;
    }
    private JTextField moveSubjectTextField;
    protected JTextField getMoveSubjectTextField() {
	if (moveSubjectTextField == null) {
	    moveSubjectTextField = new OneCharTextField();
	    moveSubjectTextField.addActionListener(new AbstractAction("MoveSubjectText") {
		    public void actionPerformed(ActionEvent ae) {
			getMoveButton().doClick();
		    }
		});
	}
	return moveSubjectTextField;
    }
    private JTextField moveTargetTextField;
    protected JTextField getMoveTargetTextField() {
	if (moveTargetTextField == null) {
	    moveTargetTextField = new OneCharTextField();
	    moveTargetTextField.addActionListener(new AbstractAction("MoveTargetText") {
		    public void actionPerformed(ActionEvent ae) {
			getMoveButton().doClick();
		    }
		});
	}
	return moveTargetTextField;
    }
    private Box moveBox;
    protected Box getMoveBox() {
	if (moveBox == null) {
	    moveBox = Box.createHorizontalBox();
	    moveBox.add(getMoveButton());
	    moveBox.add(Box.createRigidArea(new Dimension(hs, 0)));
	    moveBox.add(getMoveSubjectTextField());
	    moveBox.add(Box.createRigidArea(new Dimension(hs, 0)));
	    moveBox.add(getMoveTargetTextField());
	    moveBox.add(Box.createRigidArea(new Dimension(hs, 0)));
	    moveBox.add(Box.createHorizontalGlue());
	}
	return moveBox;
    }

    private JButton unpileButton;
    protected JButton getUnpileButton() {
	if (unpileButton == null) {
	    unpileButton = new JButton();
	    unpileButton.setAction(getUnpileGuiAction());
	}
	return unpileButton;
    }
    private JTextField unpileTextField;
    protected JTextField getUnpileTextField() {
	if (unpileTextField == null) {
	    unpileTextField = new OneCharTextField();
	    unpileTextField.addActionListener(new AbstractAction("UnpileText") {
		    public void actionPerformed(ActionEvent ae) {
			getUnpileButton().doClick();
		    }
		});
	}
	return unpileTextField;
    }
    private Box unpileBox;
    protected Box getUnpileBox() {
	if (unpileBox == null) {
	    unpileBox = Box.createHorizontalBox();
	    unpileBox.add(getUnpileButton());
	    unpileBox.add(Box.createRigidArea(new Dimension(hs, 0)));
	    unpileBox.add(getUnpileTextField());
	    unpileBox.add(Box.createRigidArea(new Dimension(hs, 0)));
	    unpileBox.add(Box.createHorizontalGlue());
	}
	return unpileBox;
    }

}
