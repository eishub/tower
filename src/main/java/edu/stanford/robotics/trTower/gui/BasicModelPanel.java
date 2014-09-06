package edu.stanford.robotics.trTower.gui;

import java.util.*;
import java.awt.*;
import javax.swing.*;

import java.util.List;

import edu.stanford.robotics.trTower.modelTower.*;

public class BasicModelPanel extends JPanel {
    
    //    protected static final String hspace = "   ";

    BasicModelPanel() {
	setLayout(new BorderLayout());
	add(getScrollPane());
    }
    
    // --- attributes
    private ModelTower modelTower;
    ModelTower getModelTower() { return modelTower; }
    void setModelTower(ModelTower m) { modelTower = m; }

    // --- components
    private JTextArea textArea;
    protected JTextArea getTextArea() {
	if (textArea == null) {
	    textArea = new JTextArea(6, 15);
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
    public void displayModel() {
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
			System.out.println("BasicModelPanel> block is not on anything!");
			System.exit(0);
		    }
		}
	    }
	    // --- clear
	    if (getModelTower().isClear(blockId)) {
		sb.append("\t\t\t");
		sb.append("clear");
	    }
	    
	    sb.append("\n");
	}
	
	String modelString = sb.toString();
	if (!modelString.equals(getTextArea().getText()))
	    getTextArea().setText(modelString);
    }
}
