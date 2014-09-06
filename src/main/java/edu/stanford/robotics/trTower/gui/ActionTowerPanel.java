package edu.stanford.robotics.trTower.gui;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import edu.stanford.robotics.trTower.actionTower.*;
import edu.stanford.robotics.trTower.common.*;
import edu.stanford.robotics.trTower.*;

public class ActionTowerPanel extends JPanel implements StimulatorListener {

    public ActionTowerPanel() {
	Border empty = BorderFactory.createEmptyBorder(2, 2, 2, 2);
	Border bevel = new SoftBevelBorder(SoftBevelBorder.LOWERED);
	setBorder(BorderFactory.createCompoundBorder(bevel, empty));
	setLayout(new GridBagLayout());
	GridBagConstraints title = new GridBagConstraints();
	title.gridx = 0;
	title.gridy = 0;
	title.anchor = GridBagConstraints.WEST;
	add(getActionTowerActionLabel(), title);
	GridBagConstraints trace = new GridBagConstraints();
	trace.gridx = 0;
	trace.gridy = 1;
	add(getTRCallTraceActionLabel(), trace);
	GridBagConstraints scroll = new GridBagConstraints();
	scroll.gridx = 0;
	scroll.gridy = 2;
	scroll.fill = GridBagConstraints.BOTH;
	scroll.weightx = 1;
	scroll.weighty = 1;
	add(getScrollPane(), scroll);
    }
    // --- attributes
    private ActionTower actionTower;
    public ActionTower getActionTower() { return actionTower; }
    public void setActionTower(ActionTower a) { actionTower = a; }

    // --- components
    private ActionLabel actionTowerActionLabel;
    protected ActionLabel getActionTowerActionLabel() {
	if (actionTowerActionLabel == null) {
	    actionTowerActionLabel = new ActionLabel();
	    actionTowerActionLabel.setText("Action Tower");
	    actionTowerActionLabel.setAction(new AbstractAction("ActionTower") {
		    public void actionPerformed(ActionEvent ae) {
			boolean useBuiltInHelpBrowser = 
			    (ae.getModifiers()&ActionEvent.SHIFT_MASK)==0? false : true;
			TRTower.getInstance().showHelp(useBuiltInHelpBrowser, 
						       "Action Tower", 
						       "help/ActionTower.html");
		    }
		});
	}
	return actionTowerActionLabel;
    }

    private ActionLabel trCallTraceActionLabel;
    protected ActionLabel getTRCallTraceActionLabel() {
	if (trCallTraceActionLabel == null) {
	    trCallTraceActionLabel = new ActionLabel();
	    trCallTraceActionLabel.setText("TR Action Trace");
	    trCallTraceActionLabel.setFont(trCallTraceActionLabel.getFont().deriveFont(Font.ITALIC));
	    trCallTraceActionLabel.setAction(new AbstractAction("TRActionTrace") {
		    public void actionPerformed(ActionEvent ae) {
			boolean useBuiltInHelpBrowser = 
			    (ae.getModifiers()&ActionEvent.SHIFT_MASK)==0? false : true;
			TRTower.getInstance().showHelp(useBuiltInHelpBrowser, 
						       "TR Action Trace", 
						       "help/TRActionTrace.html");
		    }
		});
	}
	return trCallTraceActionLabel;
    }

    private Color defaultForeground;
    private JTextArea textArea;
    protected JTextArea getTextArea() {
	if (textArea == null) {
	    textArea = new JTextArea(15, 15);
	    defaultForeground = textArea.getForeground();
	    textArea.setEditable(false);
	}
	return textArea;
    }

    private JScrollPane scrollPane;
    protected JScrollPane getScrollPane() {
	if (scrollPane == null) {
	    scrollPane = new JScrollPane(getTextArea());
	    scrollPane.setPreferredSize(new Dimension(200, 220));
	}
	return scrollPane;
    }

    // --- public method
    public void stimuStep() {

	
	if (!getActionTower().isAvailable()) {
	    // if not available, just keep old data, gut gray out.
	    getTextArea().setForeground(Color.gray);
	    return;
	} else {
	    getTextArea().setForeground(defaultForeground);
	}

	if (!getActionTower().isChanged()) {
	    return;
	}
	StringBuffer sb = new StringBuffer();
	Iterator i = getActionTower().getTRCallDescriptionList().iterator();
	while (i.hasNext()) {
	    sb.append(" ");
	    TRCallDescription tr = (TRCallDescription)(i.next());
	    int trFunction = tr.getTrFunction();
	    switch(trFunction) {
	    case TRCallDescription.MAKETOWER:
		sb.append("maketower");
		break;
	    case TRCallDescription.MOVETOTABLE:
		sb.append("move-to-table");
		break;
	    case TRCallDescription.MOVE:
		sb.append("move");
		break;
	    case TRCallDescription.UNPILE:
		sb.append("unpile");
		break;
	    case TRCallDescription.PICKUP:
		sb.append("pickup");
		break;
	    case TRCallDescription.PUTDOWN:
		sb.append("putdown");
		break;
	    case TRCallDescription.NIL:
		sb.append("nil");
		break;
	    }
	    

  	    if (trFunction != TRCallDescription.NIL)
		sb.append(ListUtilities.listToString(tr.getParameterList()));

//  	    if (trFunction != TRCallDescription.NIL)
//  		sb.append("(");
	    
//  	    // append first parameter
//  	    Iterator pi = tr.getParameterList().iterator();
//  	    if (pi.hasNext()) {
//  		String param = (String)(pi.next());
//  		sb.append(param);
//  	    }

//  	    // append the rest of the parameters
//  	    while (pi.hasNext()) {
//  		String params = (String)(pi.next());
//  		sb.append(", " + params);
//  	    }
//  	    if (trFunction != TRCallDescription.NIL)
//  		sb.append(")");

	    sb.append("\n");

	}
	String trString = sb.toString();
	if (!trString.equals(getTextArea().getText()))
	    getTextArea().setText(trString);
    }
}
