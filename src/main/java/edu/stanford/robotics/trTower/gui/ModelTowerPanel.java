package edu.stanford.robotics.trTower.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.SoftBevelBorder;

import edu.stanford.robotics.trTower.StimulatorListener;
import edu.stanford.robotics.trTower.TRTower;
import edu.stanford.robotics.trTower.common.ListUtilities;
import edu.stanford.robotics.trTower.modelTower.ModelTower;

public class ModelTowerPanel extends JPanel implements StimulatorListener {
	private static final long serialVersionUID = 1L;

	public ModelTowerPanel() {
		final Border empty = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		final Border bevel = new SoftBevelBorder(BevelBorder.LOWERED);
		setBorder(BorderFactory.createCompoundBorder(bevel, empty));
		setLayout(new GridBagLayout());
		final GridBagConstraints title = new GridBagConstraints();
		title.gridx = 0;
		title.gridy = 0;
		title.anchor = GridBagConstraints.WEST;
		add(getModelTowerActionLabel(), title);
		final GridBagConstraints clearTitle = new GridBagConstraints();
		clearTitle.gridx = 0;
		clearTitle.gridy = 1;
		add(getClearActionLabel(), clearTitle);
		final GridBagConstraints orderedTitle = new GridBagConstraints();
		orderedTitle.gridx = 1;
		orderedTitle.gridy = 1;
		add(getOrderedActionLabel(), orderedTitle);
		final GridBagConstraints towerTitle = new GridBagConstraints();
		towerTitle.gridx = 2;
		towerTitle.gridy = 1;
		add(getTowerActionLabel(), towerTitle);

		final GridBagConstraints clearText = new GridBagConstraints();
		clearText.gridx = 0;
		clearText.gridy = 2;
		clearText.fill = GridBagConstraints.BOTH;
		clearText.weightx = 0.2;
		clearText.weighty = 1;
		clearText.insets = new Insets(0, 0, 0, 2);
		add(getClearScrollPane(), clearText);
		final GridBagConstraints orderedText = new GridBagConstraints();
		orderedText.gridx = 1;
		orderedText.gridy = 2;
		orderedText.fill = GridBagConstraints.BOTH;
		orderedText.weightx = 0.5;
		orderedText.weighty = 1;
		orderedText.insets = new Insets(0, 2, 0, 2);
		add(getOrderedScrollPane(), orderedText);
		final GridBagConstraints towerText = new GridBagConstraints();
		towerText.gridx = 2;
		towerText.gridy = 2;
		towerText.fill = GridBagConstraints.BOTH;
		towerText.weightx = 0.3;
		towerText.weighty = 1;
		towerText.insets = new Insets(0, 2, 0, 0);
		add(getTowerScrollPane(), towerText);
	}

	private ModelTower modelTower;

	ModelTower getModelTower() {
		return this.modelTower;
	}

	void setModelTower(final ModelTower m) {
		this.modelTower = m;
	}

	private ActionLabel modelTowerActionLabel;

	protected ActionLabel getModelTowerActionLabel() {
		if (this.modelTowerActionLabel == null) {
			this.modelTowerActionLabel = new ActionLabel();
			this.modelTowerActionLabel.setText("Model Tower");

			this.modelTowerActionLabel.setAction(new AbstractAction("ModelTower") {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(final ActionEvent ae) {
					final boolean useBuiltInHelpBrowser = (ae.getModifiers() & ActionEvent.SHIFT_MASK) == 0 ? false
							: true;
					TRTower.getInstance().showHelp(useBuiltInHelpBrowser, "Model Tower", "help/ModelTower.html");
				}
			});
		}
		return this.modelTowerActionLabel;
	}

	private ActionLabel clearActionLabel;

	protected ActionLabel getClearActionLabel() {
		if (this.clearActionLabel == null) {
			this.clearActionLabel = new ActionLabel();
			this.clearActionLabel.setText("Clear");
			this.clearActionLabel.setFont(this.clearActionLabel.getFont().deriveFont(Font.ITALIC));
			this.clearActionLabel.setAction(new AbstractAction("Clear") {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(final ActionEvent ae) {
					final boolean useBuiltInHelpBrowser = (ae.getModifiers() & ActionEvent.SHIFT_MASK) == 0 ? false
							: true;
					TRTower.getInstance().showHelp(useBuiltInHelpBrowser, "Clear", "help/Clear.html");
				}
			});
		}
		return this.clearActionLabel;
	}

	private ActionLabel orderedActionLabel;

	protected ActionLabel getOrderedActionLabel() {
		if (this.orderedActionLabel == null) {
			this.orderedActionLabel = new ActionLabel();
			this.orderedActionLabel.setText("Ordered");
			this.orderedActionLabel.setFont(this.orderedActionLabel.getFont().deriveFont(Font.ITALIC));
			this.orderedActionLabel.setAction(new AbstractAction("Ordered") {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(final ActionEvent ae) {
					final boolean useBuiltInHelpBrowser = (ae.getModifiers() & ActionEvent.SHIFT_MASK) == 0 ? false
							: true;
					TRTower.getInstance().showHelp(useBuiltInHelpBrowser, "Ordered", "help/Ordered.html");
				}
			});
		}
		return this.orderedActionLabel;
	}

	private ActionLabel towerActionLabel;

	protected ActionLabel getTowerActionLabel() {
		if (this.towerActionLabel == null) {
			this.towerActionLabel = new ActionLabel();
			this.towerActionLabel.setText("Tower");
			this.towerActionLabel.setFont(this.towerActionLabel.getFont().deriveFont(Font.ITALIC));
			this.towerActionLabel.setAction(new AbstractAction("Tower") {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(final ActionEvent ae) {
					final boolean useBuiltInHelpBrowser = (ae.getModifiers() & ActionEvent.SHIFT_MASK) == 0 ? false
							: true;
					TRTower.getInstance().showHelp(useBuiltInHelpBrowser, "Tower", "help/Tower.html");
				}
			});
		}
		return this.towerActionLabel;
	}

	private Color clearDefaultForeground;
	private JTextArea clearTextArea;

	protected JTextArea getClearTextArea() {
		if (this.clearTextArea == null) {
			this.clearTextArea = new JTextArea(7, 10);
			this.clearDefaultForeground = this.clearTextArea.getForeground();
			this.clearTextArea.setEditable(false);
			this.clearTextArea.setTabSize(1);
		}
		return this.clearTextArea;
	}

	private JScrollPane clearScrollPane;

	protected JScrollPane getClearScrollPane() {
		if (this.clearScrollPane == null) {
			this.clearScrollPane = new JScrollPane(getClearTextArea());
		}
		return this.clearScrollPane;
	}

	private Color orderedDefaultForeground;
	private JTextArea orderedTextArea;

	protected JTextArea getOrderedTextArea() {
		if (this.orderedTextArea == null) {
			this.orderedTextArea = new JTextArea(7, 20);
			this.orderedDefaultForeground = this.orderedTextArea.getForeground();
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

	private Color towerDefaultForeground;
	private JTextArea towerTextArea;

	protected JTextArea getTowerTextArea() {
		if (this.towerTextArea == null) {
			this.towerTextArea = new JTextArea(7, 15);
			this.towerDefaultForeground = this.towerTextArea.getForeground();
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

	@Override
	public void stimuStep() {

		if (!getModelTower().isAvailable()) {
			getClearTextArea().setForeground(Color.gray);
			getOrderedTextArea().setForeground(Color.gray);
			getTowerTextArea().setForeground(Color.gray);
			return;
		} else {
			getClearTextArea().setForeground(this.clearDefaultForeground);
			getOrderedTextArea().setForeground(this.orderedDefaultForeground);
			getTowerTextArea().setForeground(this.towerDefaultForeground);
		}

		if (!getModelTower().isChanged()) {
			// model tower not changed, nothing to do
			return;
		}

		final List<String> blockIdList = getModelTower().getExistingBlockIds();
		final Iterator<String> bi = blockIdList.iterator();
		final StringBuffer sbc = new StringBuffer();
		while (bi.hasNext()) {
			final String blockId = (bi.next());
			sbc.append(" " + blockId + " \t:" + "\t\t");
			if (getModelTower().isClear(blockId)) {
				sbc.append("clear");
			}
			sbc.append("\n");
		}
		final String clearString = sbc.toString();
		if (!clearString.equals(getClearTextArea().getText())) {
			getClearTextArea().setText(clearString);
		}

		final int numOfBlocks = getModelTower().getExistingBlockIds().size();
		final StringBuffer sbo = new StringBuffer();
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
