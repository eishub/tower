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

public class ModelTowerPanel extends JPanel implements StimulatorListener {

    public ModelTowerPanel() {
	Border empty = BorderFactory.createEmptyBorder(5, 5, 5, 5);
	Border bevel = new SoftBevelBorder(SoftBevelBorder.LOWERED);
	setBorder(BorderFactory.createCompoundBorder(bevel, empty));
	setLayout(new GridBagLayout());
	GridBagConstraints title = new GridBagConstraints();
	title.gridx = 0;
	title.gridy = 0;
	title.anchor = GridBagConstraints.WEST;
	//	title.gridwidth = 3;
	//	title.fill = GridBagConstraints.HORIZONTAL;
	add(getModelTowerActionLabel(), title);
	GridBagConstraints clearTitle = new GridBagConstraints();
	clearTitle.gridx = 0;
	clearTitle.gridy = 1;
	//	clearTitle.fill = GridBagConstraints.HORIZONTAL;
	add(getClearActionLabel(), clearTitle);
	GridBagConstraints orderedTitle = new GridBagConstraints();
	orderedTitle.gridx = 1;
	orderedTitle.gridy = 1;
	//	orderedTitle.fill = GridBagConstraints.HORIZONTAL;
	add(getOrderedActionLabel(), orderedTitle);
	GridBagConstraints towerTitle = new GridBagConstraints();
	towerTitle.gridx = 2;
	towerTitle.gridy = 1;
	//	towerTitle.fill = GridBagConstraints.HORIZONTAL;
	add(getTowerActionLabel(), towerTitle);

	//	Insets insets = new Insets(1, 1, 1, 1);
	GridBagConstraints clearText = new GridBagConstraints();
	clearText.gridx = 0;
	clearText.gridy = 2;
	clearText.fill = GridBagConstraints.BOTH;
	clearText.weightx = 0.2;
	clearText.weighty = 1;
	clearText.insets = new Insets(0, 0, 0, 2);
	add(getClearScrollPane(), clearText);
	GridBagConstraints orderedText = new GridBagConstraints();
	orderedText.gridx = 1;
	orderedText.gridy = 2;
	orderedText.fill = GridBagConstraints.BOTH;
	orderedText.weightx = 0.5;
	orderedText.weighty = 1;
	orderedText.insets = new Insets(0, 2, 0, 2);
	add(getOrderedScrollPane(), orderedText);
	GridBagConstraints towerText = new GridBagConstraints();
	towerText.gridx = 2;
	towerText.gridy = 2;
	towerText.fill = GridBagConstraints.BOTH;
	towerText.weightx = 0.3;
	towerText.weighty = 1;
	towerText.insets = new Insets(0, 2, 0, 0);
	add(getTowerScrollPane(), towerText);
    }

    // --- attributes
    private ModelTower modelTower;
    ModelTower getModelTower() { return modelTower; }
    void setModelTower(ModelTower m) { modelTower = m; }

    // --- components

    // --- GUI components
//      private JPanel titlePanel;
//      protected JPanel getTitlePanel() {
//  	if (titlePanel == null) {
//  	    titlePanel = new JPanel();
//  	    titlePanel.setLayout(new BorderLayout());
//  	    titlePanel.add(getModelTowerActionLabel(),
//  			   BorderLayout.WEST);
//  	}
//  	return titlePanel;
//      }
//      private LabelButton modelTowerLabelButton;
//      protected LabelButton getModelTowerLabelButton() {
//  	if (modelTowerLabelButton == null) {
//  	    modelTowerLabelButton = new LabelButton();
//  	    modelTowerLabelButton.setIconText("Model Tower");
//  	}
//  	return modelTowerLabelButton;
//      }
    private ActionLabel modelTowerActionLabel;
    protected ActionLabel getModelTowerActionLabel() {
	if (modelTowerActionLabel == null) {
	    modelTowerActionLabel = new ActionLabel();
	    modelTowerActionLabel.setText("Model Tower");

	    modelTowerActionLabel.setAction(new AbstractAction("ModelTower") {
		    public void actionPerformed(ActionEvent ae) {
			boolean useBuiltInHelpBrowser = 
			    (ae.getModifiers()&ActionEvent.SHIFT_MASK)==0? false : true;
			TRTower.getInstance().showHelp(useBuiltInHelpBrowser, 
						       "Model Tower", 
						       "help/ModelTower.html");
		    }
		});
	}
	return modelTowerActionLabel;
    }

    private ActionLabel clearActionLabel;
    protected ActionLabel getClearActionLabel() {
	if (clearActionLabel == null) {
	    clearActionLabel = new ActionLabel();
	    clearActionLabel.setText("Clear");
	    clearActionLabel.setFont(clearActionLabel.getFont().deriveFont(Font.ITALIC));
	    clearActionLabel.setAction(new  AbstractAction("Clear") {
		    public void actionPerformed(ActionEvent ae) {
			boolean useBuiltInHelpBrowser = 
			    (ae.getModifiers()&ActionEvent.SHIFT_MASK)==0? false : true;
			TRTower.getInstance().showHelp(useBuiltInHelpBrowser, 
						       "Clear", 
						       "help/Clear.html");
		    }
		});
	}
	return clearActionLabel;
    }

    private ActionLabel orderedActionLabel;
    protected ActionLabel getOrderedActionLabel() {
	if (orderedActionLabel == null) {
	    orderedActionLabel = new ActionLabel();
	    orderedActionLabel.setText("Ordered");
	    orderedActionLabel.setFont(orderedActionLabel.getFont().deriveFont(Font.ITALIC));
	    orderedActionLabel.setAction(new AbstractAction("Ordered") {
		    public void actionPerformed(ActionEvent ae) {
			boolean useBuiltInHelpBrowser = 
			    (ae.getModifiers()&ActionEvent.SHIFT_MASK)==0? false : true;
			TRTower.getInstance().showHelp(useBuiltInHelpBrowser, 
						       "Ordered", 
						       "help/Ordered.html");
		    }
		});
	}
	return orderedActionLabel;
    }

    private ActionLabel towerActionLabel;
    protected ActionLabel getTowerActionLabel() {
	if (towerActionLabel == null) {
	    towerActionLabel = new ActionLabel();
	    towerActionLabel.setText("Tower");
	    towerActionLabel.setFont(towerActionLabel.getFont().deriveFont(Font.ITALIC));
	    towerActionLabel.setAction(new AbstractAction("Tower") {
		    public void actionPerformed(ActionEvent ae) {
			boolean useBuiltInHelpBrowser = 
			    (ae.getModifiers()&ActionEvent.SHIFT_MASK)==0? false : true;
			TRTower.getInstance().showHelp(useBuiltInHelpBrowser, 
						       "Tower", 
						       "help/Tower.html");
		    }
		});
    
	}
	return towerActionLabel;
    }

    // --- GUI components from ModelPanel
    private Color clearDefaultForeground;
    private JTextArea clearTextArea;
    protected JTextArea getClearTextArea() {
	if (clearTextArea == null) {
	    clearTextArea = new JTextArea(7, 10);
	    clearDefaultForeground = clearTextArea.getForeground();
	    clearTextArea.setEditable(false);
	    clearTextArea.setTabSize(1);
	}
	return clearTextArea;
    }
    private JScrollPane clearScrollPane;
    protected JScrollPane getClearScrollPane() {
	if (clearScrollPane == null) {
	    clearScrollPane = new JScrollPane(getClearTextArea());
	}
	return clearScrollPane;
    }
    private Color orderedDefaultForeground;
    private JTextArea orderedTextArea;
    protected JTextArea getOrderedTextArea() {
	if (orderedTextArea == null) {
	    orderedTextArea = new JTextArea(7, 20);
	    orderedDefaultForeground = orderedTextArea.getForeground();
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
    private Color towerDefaultForeground;
    private JTextArea towerTextArea;
    protected JTextArea getTowerTextArea() {
	if (towerTextArea == null) {
	    towerTextArea = new JTextArea(7, 15);
	    towerDefaultForeground = towerTextArea.getForeground();
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
    public void stimuStep() {

	if (!getModelTower().isAvailable()) {
	    getClearTextArea().setForeground(Color.gray);
	    getOrderedTextArea().setForeground(Color.gray);
	    getTowerTextArea().setForeground(Color.gray);
	    return;
	} else {
	    getClearTextArea().setForeground(clearDefaultForeground);
	    getOrderedTextArea().setForeground(orderedDefaultForeground);
	    getTowerTextArea().setForeground(towerDefaultForeground);
	}

	if (!getModelTower().isChanged()) {
	    // model tower not changed, nothing to do
	    return;
	}

	// --- clear 
	List blockIdList = getModelTower().getExistingBlockIds();
	Iterator bi = blockIdList.iterator();
	StringBuffer sbc = new StringBuffer();
	while (bi.hasNext()) {
	    String blockId = (String)(bi.next());
	    sbc.append(" " + blockId + " \t:" + "\t\t");
	    if (getModelTower().isClear(blockId)) {
		//		sbc.append("\t\t\t");
		sbc.append("clear");
	    }
	    sbc.append("\n");
	}
	String clearString = sbc.toString();
	if (!clearString.equals(getClearTextArea().getText()))
	    getClearTextArea().setText(clearString);

	int numOfBlocks = getModelTower().getExistingBlockIds().size();
	// --- ordered
	StringBuffer sbo = new StringBuffer();
	//	sbo.append(" Ordered:\n");
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
	//	sbt.append(" Tower:\n");
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
