package edu.stanford.robotics.trTower.gui;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.List;

import edu.stanford.robotics.trTower.modelTower.*;
import edu.stanford.robotics.trTower.common.*;
import edu.stanford.robotics.trTower.*;


public class PerceptionTowerPanel 
    extends JPanel implements StimulatorListener {

    public PerceptionTowerPanel() {
	Border empty = BorderFactory.createEmptyBorder(5, 5, 5, 5);
	Border bevel = new SoftBevelBorder(SoftBevelBorder.LOWERED);
	setBorder(BorderFactory.createCompoundBorder(bevel, empty));
	setLayout(new GridBagLayout());
	GridBagConstraints title = new GridBagConstraints();
	title.gridx = 0;
	title.gridy = 0;
	title.anchor = GridBagConstraints.WEST;
	add(getPerceptionTowerActionLabel(), title);
	GridBagConstraints primitive = new GridBagConstraints();
	primitive.gridx = 0;
	primitive.gridy = 1;
	add(getPrimitivePredicatesActionLabel(), primitive);
	GridBagConstraints scroll = new GridBagConstraints();
	scroll.gridx = 0;
	scroll.gridy = 2;
	scroll.fill = GridBagConstraints.BOTH;
	scroll.weightx = 1;
	scroll.weighty = 1;
	add(getScrollPane(), scroll);
    }

    // --- attributes
    private ModelTower modelTower;
    ModelTower getModelTower() { return modelTower; }
    void setModelTower(ModelTower m) { modelTower = m; }
    
    // --- GUI components
    private ActionLabel perceptionTowerActionLabel;
    protected ActionLabel getPerceptionTowerActionLabel() {
	if (perceptionTowerActionLabel == null) {
	    perceptionTowerActionLabel = new ActionLabel();
	    perceptionTowerActionLabel.setText("Perception Tower");
	    perceptionTowerActionLabel.setAction(new AbstractAction("PerceptionTower") {
		    public void actionPerformed(ActionEvent ae) {
			boolean useBuiltInHelpBrowser = 
			    (ae.getModifiers()&ActionEvent.SHIFT_MASK)==0? false : true;
			TRTower.getInstance().showHelp(useBuiltInHelpBrowser, 
						       "Perception Tower", 
						       "help/PerceptionTower.html");
		    }
		});
	}
	return perceptionTowerActionLabel;
    }

    private ActionLabel primitivePredicatesActionLabel;
    protected ActionLabel getPrimitivePredicatesActionLabel() {
	if (primitivePredicatesActionLabel == null) {
	    primitivePredicatesActionLabel = new ActionLabel();
	    primitivePredicatesActionLabel.setText("Primitive Predicates");
	    primitivePredicatesActionLabel.setFont(primitivePredicatesActionLabel.getFont().deriveFont(Font.ITALIC));
	    primitivePredicatesActionLabel.setAction(new AbstractAction("PrimitivePredicates") {
		    public void actionPerformed(ActionEvent ae) {
			boolean useBuiltInHelpBrowser = 
			    (ae.getModifiers()&ActionEvent.SHIFT_MASK)==0? false : true;
			TRTower.getInstance().showHelp(useBuiltInHelpBrowser, 
						       "Primitive Predicates", 
						       "help/PrimitivePredicates.html");
		    }
		});
	}
	return primitivePredicatesActionLabel;
    }

    private Color defaultForeground;
    private JTextArea textArea;
    protected JTextArea getTextArea() {
	if (textArea == null) {
	    textArea = new JTextArea(7, 10);
	    defaultForeground = textArea.getForeground();
	    textArea.setEditable(false);
	    textArea.setTabSize(1);
	}
	return textArea;
    }

    private JScrollPane scrollPane;
    protected JScrollPane getScrollPane() {
	if (scrollPane == null) {
	    scrollPane = new JScrollPane(getTextArea());
	}
	return scrollPane;
    }

    // --- public method
    public void stimuStep() {

	if (!getModelTower().isAvailable()) {
	    getTextArea().setForeground(Color.gray);
	    return;
	} else {
	    getTextArea().setForeground(defaultForeground);
	}

	if (!getModelTower().isChanged()) {
	    // model tower not changed, nothing to do
	    return;
	}

	List blockIdList = getModelTower().getExistingBlockIds();
	Iterator bi = blockIdList.iterator();
	StringBuffer sb = new StringBuffer();
	while (bi.hasNext()) {
	    String blockId = (String)(bi.next());

	    // --- holding/on
	    sb.append(" " + blockId + " \t:" + "\t\t");
	    if (getModelTower().isHolding(blockId))
		sb.append("holding");
	    else {
		sb.append("on ");
		if (getModelTower().isOn(blockId, getModelTower().getTableId()))
		    sb.append(getModelTower().getTableId());
		else {
		    Iterator bj = blockIdList.iterator();
		    boolean isOnSomething = false;
		    while (bj.hasNext()) {
			String y = (String)(bj.next());
			if (getModelTower().isOn(blockId, y)) {
			    sb.append(y);
			    isOnSomething = true;
			}
		    }
		    if (!isOnSomething) {
			System.out.println("PerceptionTowerPanel> block is not on anything!");
			System.exit(0);
		    }
		}
	    }
	    sb.append("\n");
	}
	
	String modelString = sb.toString();
	if (!modelString.equals(getTextArea().getText()))
	    getTextArea().setText(modelString);
    }

}
