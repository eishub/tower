package edu.stanford.robotics.trTower.gui;

import java.util.*;
import java.awt.*;
import javax.swing.*;

import java.util.List;

import edu.stanford.robotics.trTower.modelTower.*;
import edu.stanford.robotics.trTower.common.*;

public class ModelPanel extends JPanel {

    ModelPanel() {
	setLayout(new FlowLayout());
	add(getOrderedScrollPane());
	add(getTowerScrollPane());
    }

    // --- attributes 
    private ModelTower modelTower;
    ModelTower getModelTower() { return modelTower; }
    void setModelTower(ModelTower m) { modelTower = m; }

    // --- components
    private JTextArea orderedTextArea;
    protected JTextArea getOrderedTextArea() {
	if (orderedTextArea == null) {
	    orderedTextArea = new JTextArea(8, 24);
	    orderedTextArea.setEditable(false);
	    orderedTextArea.setTabSize(1);
	}
	return orderedTextArea;
    }
    private JScrollPane orderedScrollPane;
    protected JScrollPane getOrderedScrollPane() {
	if (orderedScrollPane == null) {
	    orderedScrollPane = new JScrollPane(getOrderedTextArea());
	}
	return orderedScrollPane;
    }
    private JTextArea towerTextArea;
    protected JTextArea getTowerTextArea() {
	if (towerTextArea == null) {
	    towerTextArea = new JTextArea(8, 15);
	    towerTextArea.setEditable(false);
	    towerTextArea.setTabSize(1);
	}
	return towerTextArea;
    }
    private JScrollPane towerScrollPane;
    protected JScrollPane getTowerScrollPane() {
	if (towerScrollPane == null) {
	    towerScrollPane = new JScrollPane(getTowerTextArea());
	}
	return towerScrollPane;
    }

    // --- public method
    public void displayModel() {

	int numOfBlocks = getModelTower().getExistingBlockIds().size();

	// --- ordered
	StringBuffer sbo = new StringBuffer();
	sbo.append(" Ordered:\n");
	List orderedListList = getModelTower().getOrderedListList();
	for (int i = 1; i<=numOfBlocks; i++) {
	    List ll = ListUtilities.listLengthFilter(orderedListList, i);
	    if (ll.size() > 0) {
		Iterator lli = ll.iterator();
		while (lli.hasNext()) {
		    List l = (List)(lli.next());
		    sbo.append(" " + ListUtilities.listToString(l) + "    ");
		}
		sbo.append("\n");
	    }
	}
	String orderedString = sbo.toString();
	if (!orderedString.equals(getOrderedTextArea().getText()))
	    getOrderedTextArea().setText(orderedString);
       
	// --- tower
	StringBuffer sbt = new StringBuffer();
	sbt.append(" Tower:\n");
	List towerListList = getModelTower().getTowerListList();
	Iterator ti = towerListList.iterator();
	while (ti.hasNext()) {
	    List l = (List)(ti.next());
	    sbt.append(" " + ListUtilities.listToString(l) + "\n");
	}
	String towerString = sbt.toString();
	if (!towerString.equals(getTowerTextArea().getText()))
	    getTowerTextArea().setText(towerString);
    }
}
