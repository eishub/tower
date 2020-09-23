package edu.stanford.robotics.trTower.gui;

import java.awt.BorderLayout;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import edu.stanford.robotics.trTower.modelTower.ModelTower;

public class BasicModelPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	BasicModelPanel() {
		setLayout(new BorderLayout());
		add(getScrollPane());
	}

	private ModelTower modelTower;

	ModelTower getModelTower() {
		return this.modelTower;
	}

	void setModelTower(final ModelTower m) {
		this.modelTower = m;
	}

	private JTextArea textArea;

	protected JTextArea getTextArea() {
		if (this.textArea == null) {
			this.textArea = new JTextArea(6, 15);
			this.textArea.setEditable(false);
			this.textArea.setTabSize(1);
		}
		return this.textArea;
	}

	private JScrollPane scrollPane;

	protected JScrollPane getScrollPane() {
		if (this.scrollPane == null) {
			this.scrollPane = new JScrollPane(getTextArea());
		}
		return this.scrollPane;
	}

	public void displayModel() {
		final List<String> blockIdList = getModelTower().getExistingBlockIds();
		final Iterator<String> bi = blockIdList.iterator();
		final StringBuffer sb = new StringBuffer();
		while (bi.hasNext()) {
			final String blockId = (bi.next());

			// --- holding/on
			sb.append(" " + blockId + " \t:" + "\t\t");
			if (getModelTower().isHolding(blockId)) {
				sb.append("holding");
			} else {
				sb.append("on ");
				if (getModelTower().isOn(blockId, getModelTower().getTableId())) {
					sb.append(getModelTower().getTableId());
				} else {
					final Iterator<String> bj = blockIdList.iterator();
					boolean isOnSomething = false;
					while (bj.hasNext()) {
						final String y = (bj.next());
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

		final String modelString = sb.toString();
		if (!modelString.equals(getTextArea().getText())) {
			getTextArea().setText(modelString);
		}
	}
}
