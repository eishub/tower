package edu.stanford.robotics.trTower.gui;

import java.awt.FlowLayout;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import edu.stanford.robotics.trTower.common.ListUtilities;
import edu.stanford.robotics.trTower.modelTower.ModelTower;

public class ModelPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	ModelPanel() {
		setLayout(new FlowLayout());
		add(getOrderedScrollPane());
		add(getTowerScrollPane());
	}

	private ModelTower modelTower;

	ModelTower getModelTower() {
		return this.modelTower;
	}

	void setModelTower(final ModelTower m) {
		this.modelTower = m;
	}

	private JTextArea orderedTextArea;

	protected JTextArea getOrderedTextArea() {
		if (this.orderedTextArea == null) {
			this.orderedTextArea = new JTextArea(8, 24);
			this.orderedTextArea.setEditable(false);
			this.orderedTextArea.setTabSize(1);
		}
		return this.orderedTextArea;
	}

	private JScrollPane orderedScrollPane;

	protected JScrollPane getOrderedScrollPane() {
		if (this.orderedScrollPane == null) {
			this.orderedScrollPane = new JScrollPane(getOrderedTextArea());
		}
		return this.orderedScrollPane;
	}

	private JTextArea towerTextArea;

	protected JTextArea getTowerTextArea() {
		if (this.towerTextArea == null) {
			this.towerTextArea = new JTextArea(8, 15);
			this.towerTextArea.setEditable(false);
			this.towerTextArea.setTabSize(1);
		}
		return this.towerTextArea;
	}

	private JScrollPane towerScrollPane;

	protected JScrollPane getTowerScrollPane() {
		if (this.towerScrollPane == null) {
			this.towerScrollPane = new JScrollPane(getTowerTextArea());
		}
		return this.towerScrollPane;
	}

	public void displayModel() {

		final int numOfBlocks = getModelTower().getExistingBlockIds().size();

		final StringBuffer sbo = new StringBuffer();
		sbo.append(" Ordered:\n");
		final List<List<String>> orderedListList = getModelTower().getOrderedListList();
		for (int i = 1; i <= numOfBlocks; i++) {
			final List<List<String>> ll = ListUtilities.listLengthFilter(orderedListList, i);
			if (ll.size() > 0) {
				final Iterator<List<String>> lli = ll.iterator();
				while (lli.hasNext()) {
					final List<String> l = lli.next();
					sbo.append(" " + ListUtilities.listToString(l) + "    ");
				}
				sbo.append("\n");
			}
		}
		final String orderedString = sbo.toString();
		if (!orderedString.equals(getOrderedTextArea().getText())) {
			getOrderedTextArea().setText(orderedString);
		}

		final StringBuffer sbt = new StringBuffer();
		sbt.append(" Tower:\n");
		final List<List<String>> towerListList = getModelTower().getTowerListList();
		final Iterator<List<String>> ti = towerListList.iterator();
		while (ti.hasNext()) {
			final List<String> l = ti.next();
			sbt.append(" " + ListUtilities.listToString(l) + "\n");
		}
		final String towerString = sbt.toString();
		if (!towerString.equals(getTowerTextArea().getText())) {
			getTowerTextArea().setText(towerString);
		}
	}
}
