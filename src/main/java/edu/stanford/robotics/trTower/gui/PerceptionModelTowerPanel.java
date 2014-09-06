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

public class PerceptionModelTowerPanel 
    extends JPanel  implements StimulatorListener {
    
    public PerceptionModelTowerPanel() {
	Border empty = BorderFactory.createEmptyBorder(5, 5, 5, 5);
	Border bevel = new SoftBevelBorder(SoftBevelBorder.LOWERED);
	setBorder(BorderFactory.createCompoundBorder(bevel, empty));
	setLayout(new GridBagLayout());

	GridBagConstraints title = new GridBagConstraints();
	title.gridx = 0;
	title.gridy = 0;
	title.gridwidth = 4;
	title.anchor = GridBagConstraints.WEST;
	add(getTitlePanel(), title);

	// --- from PerceptionTowerPanel
	GridBagConstraints primitiveTitle = new GridBagConstraints();
	primitiveTitle.gridx = 0;
	primitiveTitle.gridy = 1;
	add(getPrimitivePredicatesActionLabel(), primitiveTitle);
	GridBagConstraints primitiveText = new GridBagConstraints();
	primitiveText.gridx = 0;
	primitiveText.gridy = 2;
	primitiveText.fill = GridBagConstraints.BOTH;
	primitiveText.weightx = 0.25;
	primitiveText.weighty = 1;
	primitiveText.insets = new Insets(0, 0, 0, 2);
	add(getPrimScrollPane(), primitiveText);

	// --- from ModelTowerPanel
	GridBagConstraints clearTitle = new GridBagConstraints();
	clearTitle.gridx = 1;
	clearTitle.gridy = 1;
	add(getClearActionLabel(), clearTitle);
	GridBagConstraints orderedTitle = new GridBagConstraints();
	orderedTitle.gridx = 2;
	orderedTitle.gridy = 1;
	add(getOrderedActionLabel(), orderedTitle);
	GridBagConstraints towerTitle = new GridBagConstraints();
	towerTitle.gridx = 3;
	towerTitle.gridy = 1;
	add(getTowerActionLabel(), towerTitle);
	GridBagConstraints clearText = new GridBagConstraints();
	clearText.gridx = 1;
	clearText.gridy = 2;
	clearText.fill = GridBagConstraints.BOTH;
	clearText.weightx = 0.25;
	clearText.weighty = 1;
	clearText.insets = new Insets(0, 2, 0, 2);
	add(getClearScrollPane(), clearText);
	GridBagConstraints orderedText = new GridBagConstraints();
	orderedText.gridx = 2;
	orderedText.gridy = 2;
	orderedText.fill = GridBagConstraints.BOTH;
	orderedText.weightx = 0.25;
	orderedText.weighty = 1;
	orderedText.insets = new Insets(0, 2, 0, 2);
	add(getOrderedScrollPane(), orderedText);
	GridBagConstraints towerText = new GridBagConstraints();
	towerText.gridx = 3;
	towerText.gridy = 2;
	towerText.fill = GridBagConstraints.BOTH;
	towerText.weightx = 0.25;
	towerText.weighty = 1;
	towerText.insets = new Insets(0, 2, 0, 0);
	add(getTowerScrollPane(), towerText);

    }
    
    // --- attributes
    private ModelTower modelTower;
    ModelTower getModelTower() { return modelTower; }
    void setModelTower(ModelTower m) { modelTower = m; }
    

    // --- methods
    public void stimuStep() {
	primStimuStep();
	modelStimuStep();
    }


    private JPanel titlePanel;
    private JPanel getTitlePanel() {
	if (titlePanel == null) {
	    titlePanel = new JPanel();
	    titlePanel.setLayout(new GridBagLayout());
	    GridBagConstraints model = new GridBagConstraints();
	    model.gridx = 0;
	    model.gridy = 0;
	    titlePanel.add(getModelTowerActionLabel(), model);
	    GridBagConstraints open = new GridBagConstraints();
	    open.gridx = 1;
	    open.gridy = 0;
	    titlePanel.add(getOpenLabel(), open);
	    GridBagConstraints perception = new GridBagConstraints();
	    perception.gridx = 2;
	    perception.gridy = 0;
	    titlePanel.add(getPerceptionTowerActionLabel(), perception);
	    GridBagConstraints close = new GridBagConstraints();
	    close.gridx = 3;
	    close.gridy = 0;
	    titlePanel.add(getCloseLabel(), close);

	}
	return titlePanel;
    }

    private JLabel openLabel;
    protected JLabel getOpenLabel() {
	if (openLabel == null) {
	    openLabel = new JLabel();
	    openLabel.setText("( computed by");
	    openLabel.setForeground(Color.black);
	    openLabel.setFont(openLabel.getFont().deriveFont(Font.PLAIN));
	    openLabel.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 0));
	}
	return openLabel;
    }
    private JLabel closeLabel;
    protected JLabel getCloseLabel() {
	if (closeLabel == null) {
	    closeLabel = new JLabel();
	    closeLabel.setText(")");
	    closeLabel.setForeground(Color.black);
	    closeLabel.setFont(closeLabel.getFont().deriveFont(Font.PLAIN));
	}
	return closeLabel;
    }
    // ==================== from PerceptionTowerPanel ====================
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

    private Color defaultPrimForeground;
    private JTextArea primTextArea;
    protected JTextArea getPrimTextArea() {
	if (primTextArea == null) {
	    primTextArea = new JTextArea(7, 8);
	    defaultPrimForeground = primTextArea.getForeground();
	    primTextArea.setEditable(false);
	    primTextArea.setTabSize(1);
	}
	return primTextArea;
    }

    private JScrollPane primScrollPane;
    protected JScrollPane getPrimScrollPane() {
	if (primScrollPane == null) {
	    primScrollPane = new JScrollPane(getPrimTextArea());
	    primScrollPane.setMinimumSize(new Dimension(140, 110));
	    primScrollPane.setPreferredSize(new Dimension(140, 110));
	}
	return primScrollPane;
    }

    // --- method
    protected void primStimuStep() {

	if (!getModelTower().isAvailable()) {
	    getPrimTextArea().setForeground(Color.gray);
	    return;
	} else {
	    getPrimTextArea().setForeground(defaultPrimForeground);
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
		//		sb.append("holding");
		sb.append("being held");
	    else {
		sb.append("on ");
		if (getModelTower().isOn(blockId, getModelTower().getTableId()))
		    sb.append("Table (" + getModelTower().getTableId() + ")");
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
	if (!modelString.equals(getPrimTextArea().getText()))
	    getPrimTextArea().setText(modelString);
    }


    // ==================== from ModelTowerPanel ====================
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
	    clearTextArea = new JTextArea(7, 7);
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
	    clearScrollPane.setMinimumSize(new Dimension(100, 110));
	    clearScrollPane.setPreferredSize(new Dimension(100, 110));
	}
	return clearScrollPane;
    }
    private Color orderedDefaultForeground;
    private JTextArea orderedTextArea;
    protected JTextArea getOrderedTextArea() {
	if (orderedTextArea == null) {
	    orderedTextArea = new JTextArea(7, 17);
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
	    orderedScrollPane.setMinimumSize(new Dimension(220, 110));
	    orderedScrollPane.setPreferredSize(new Dimension(220, 110));
	}
	return orderedScrollPane;
    }
    private Color towerDefaultForeground;
    private JTextArea towerTextArea;
    protected JTextArea getTowerTextArea() {
	if (towerTextArea == null) {
	    towerTextArea = new JTextArea(7, 12);
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
	    towerScrollPane.setMinimumSize(new Dimension(170, 110));
	    towerScrollPane.setPreferredSize(new Dimension(170, 110));
	}
	return towerScrollPane;
    }

    // --- method
    protected void modelStimuStep() {

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
