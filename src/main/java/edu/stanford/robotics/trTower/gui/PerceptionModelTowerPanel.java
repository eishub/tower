package edu.stanford.robotics.trTower.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
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

public class PerceptionModelTowerPanel extends JPanel implements StimulatorListener {
	private static final long serialVersionUID = 1L;

	public PerceptionModelTowerPanel() {
		final Border empty = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		final Border bevel = new SoftBevelBorder(BevelBorder.LOWERED);
		setBorder(BorderFactory.createCompoundBorder(bevel, empty));
		setLayout(new GridBagLayout());

		final GridBagConstraints title = new GridBagConstraints();
		title.gridx = 0;
		title.gridy = 0;
		title.gridwidth = 4;
		title.anchor = GridBagConstraints.WEST;
		add(getTitlePanel(), title);

		// --- from PerceptionTowerPanel
		final GridBagConstraints primitiveTitle = new GridBagConstraints();
		primitiveTitle.gridx = 0;
		primitiveTitle.gridy = 1;
		add(getPrimitivePredicatesActionLabel(), primitiveTitle);
		final GridBagConstraints primitiveText = new GridBagConstraints();
		primitiveText.gridx = 0;
		primitiveText.gridy = 2;
		primitiveText.fill = GridBagConstraints.BOTH;
		primitiveText.weightx = 0.25;
		primitiveText.weighty = 1;
		primitiveText.insets = new Insets(0, 0, 0, 2);
		add(getPrimScrollPane(), primitiveText);

		// --- from ModelTowerPanel
		final GridBagConstraints clearTitle = new GridBagConstraints();
		clearTitle.gridx = 1;
		clearTitle.gridy = 1;
		add(getClearActionLabel(), clearTitle);
		final GridBagConstraints orderedTitle = new GridBagConstraints();
		orderedTitle.gridx = 2;
		orderedTitle.gridy = 1;
		add(getOrderedActionLabel(), orderedTitle);
		final GridBagConstraints towerTitle = new GridBagConstraints();
		towerTitle.gridx = 3;
		towerTitle.gridy = 1;
		add(getTowerActionLabel(), towerTitle);
		final GridBagConstraints clearText = new GridBagConstraints();
		clearText.gridx = 1;
		clearText.gridy = 2;
		clearText.fill = GridBagConstraints.BOTH;
		clearText.weightx = 0.25;
		clearText.weighty = 1;
		clearText.insets = new Insets(0, 2, 0, 2);
		add(getClearScrollPane(), clearText);
		final GridBagConstraints orderedText = new GridBagConstraints();
		orderedText.gridx = 2;
		orderedText.gridy = 2;
		orderedText.fill = GridBagConstraints.BOTH;
		orderedText.weightx = 0.25;
		orderedText.weighty = 1;
		orderedText.insets = new Insets(0, 2, 0, 2);
		add(getOrderedScrollPane(), orderedText);
		final GridBagConstraints towerText = new GridBagConstraints();
		towerText.gridx = 3;
		towerText.gridy = 2;
		towerText.fill = GridBagConstraints.BOTH;
		towerText.weightx = 0.25;
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

	@Override
	public void stimuStep() {
		primStimuStep();
		modelStimuStep();
	}

	private JPanel titlePanel;

	private JPanel getTitlePanel() {
		if (this.titlePanel == null) {
			this.titlePanel = new JPanel();
			this.titlePanel.setLayout(new GridBagLayout());
			final GridBagConstraints model = new GridBagConstraints();
			model.gridx = 0;
			model.gridy = 0;
			this.titlePanel.add(getModelTowerActionLabel(), model);
			final GridBagConstraints open = new GridBagConstraints();
			open.gridx = 1;
			open.gridy = 0;
			this.titlePanel.add(getOpenLabel(), open);
			final GridBagConstraints perception = new GridBagConstraints();
			perception.gridx = 2;
			perception.gridy = 0;
			this.titlePanel.add(getPerceptionTowerActionLabel(), perception);
			final GridBagConstraints close = new GridBagConstraints();
			close.gridx = 3;
			close.gridy = 0;
			this.titlePanel.add(getCloseLabel(), close);
		}
		return this.titlePanel;
	}

	private JLabel openLabel;

	protected JLabel getOpenLabel() {
		if (this.openLabel == null) {
			this.openLabel = new JLabel();
			this.openLabel.setText("( computed by");
			this.openLabel.setForeground(Color.black);
			this.openLabel.setFont(this.openLabel.getFont().deriveFont(Font.PLAIN));
			this.openLabel.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 0));
		}
		return this.openLabel;
	}

	private JLabel closeLabel;

	protected JLabel getCloseLabel() {
		if (this.closeLabel == null) {
			this.closeLabel = new JLabel();
			this.closeLabel.setText(")");
			this.closeLabel.setForeground(Color.black);
			this.closeLabel.setFont(this.closeLabel.getFont().deriveFont(Font.PLAIN));
		}
		return this.closeLabel;
	}

	// ==================== from PerceptionTowerPanel ====================

	private ActionLabel perceptionTowerActionLabel;

	protected ActionLabel getPerceptionTowerActionLabel() {
		if (this.perceptionTowerActionLabel == null) {
			this.perceptionTowerActionLabel = new ActionLabel();
			this.perceptionTowerActionLabel.setText("Perception Tower");
			this.perceptionTowerActionLabel.setAction(new AbstractAction("PerceptionTower") {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(final ActionEvent ae) {
					final boolean useBuiltInHelpBrowser = (ae.getModifiers() & ActionEvent.SHIFT_MASK) == 0 ? false
							: true;
					TRTower.getInstance().showHelp(useBuiltInHelpBrowser, "Perception Tower",
							"help/PerceptionTower.html");
				}
			});
		}
		return this.perceptionTowerActionLabel;
	}

	private ActionLabel primitivePredicatesActionLabel;

	protected ActionLabel getPrimitivePredicatesActionLabel() {
		if (this.primitivePredicatesActionLabel == null) {
			this.primitivePredicatesActionLabel = new ActionLabel();
			this.primitivePredicatesActionLabel.setText("Primitive Predicates");
			this.primitivePredicatesActionLabel
					.setFont(this.primitivePredicatesActionLabel.getFont().deriveFont(Font.ITALIC));
			this.primitivePredicatesActionLabel.setAction(new AbstractAction("PrimitivePredicates") {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(final ActionEvent ae) {
					final boolean useBuiltInHelpBrowser = (ae.getModifiers() & ActionEvent.SHIFT_MASK) == 0 ? false
							: true;
					TRTower.getInstance().showHelp(useBuiltInHelpBrowser, "Primitive Predicates",
							"help/PrimitivePredicates.html");
				}
			});
		}
		return this.primitivePredicatesActionLabel;
	}

	private Color defaultPrimForeground;
	private JTextArea primTextArea;

	protected JTextArea getPrimTextArea() {
		if (this.primTextArea == null) {
			this.primTextArea = new JTextArea(7, 8);
			this.defaultPrimForeground = this.primTextArea.getForeground();
			this.primTextArea.setEditable(false);
			this.primTextArea.setTabSize(1);
		}
		return this.primTextArea;
	}

	private JScrollPane primScrollPane;

	protected JScrollPane getPrimScrollPane() {
		if (this.primScrollPane == null) {
			this.primScrollPane = new JScrollPane(getPrimTextArea());
			this.primScrollPane.setMinimumSize(new Dimension(140, 110));
			this.primScrollPane.setPreferredSize(new Dimension(140, 110));
		}
		return this.primScrollPane;
	}

	protected void primStimuStep() {

		if (!getModelTower().isAvailable()) {
			getPrimTextArea().setForeground(Color.gray);
			return;
		} else {
			getPrimTextArea().setForeground(this.defaultPrimForeground);
		}

		if (!getModelTower().isChanged()) {
			// model tower not changed, nothing to do
			return;
		}

		final List<String> blockIdList = getModelTower().getExistingBlockIds();
		final Iterator<String> bi = blockIdList.iterator();
		final StringBuffer sb = new StringBuffer();
		while (bi.hasNext()) {
			final String blockId = (bi.next());
			sb.append(" " + blockId + " \t:" + "\t\t");
			if (getModelTower().isHolding(blockId)) {
				sb.append("being held");
			} else {
				sb.append("on ");
				if (getModelTower().isOn(blockId, getModelTower().getTableId())) {
					sb.append("Table (" + getModelTower().getTableId() + ")");
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
						System.out.println("PerceptionTowerPanel> block is not on anything!");
						System.exit(0);
					}
				}
			}
			sb.append("\n");
		}

		final String modelString = sb.toString();
		if (!modelString.equals(getPrimTextArea().getText())) {
			getPrimTextArea().setText(modelString);
		}
	}

	// ==================== from ModelTowerPanel ====================

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
			this.clearTextArea = new JTextArea(7, 7);
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
			this.clearScrollPane.setMinimumSize(new Dimension(100, 110));
			this.clearScrollPane.setPreferredSize(new Dimension(100, 110));
		}
		return this.clearScrollPane;
	}

	private Color orderedDefaultForeground;
	private JTextArea orderedTextArea;

	protected JTextArea getOrderedTextArea() {
		if (this.orderedTextArea == null) {
			this.orderedTextArea = new JTextArea(7, 17);
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
			this.orderedScrollPane.setMinimumSize(new Dimension(220, 110));
			this.orderedScrollPane.setPreferredSize(new Dimension(220, 110));
		}
		return this.orderedScrollPane;
	}

	private Color towerDefaultForeground;
	private JTextArea towerTextArea;

	protected JTextArea getTowerTextArea() {
		if (this.towerTextArea == null) {
			this.towerTextArea = new JTextArea(7, 12);
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
			this.towerScrollPane.setMinimumSize(new Dimension(170, 110));
			this.towerScrollPane.setPreferredSize(new Dimension(170, 110));
		}
		return this.towerScrollPane;
	}

	protected void modelStimuStep() {

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

		// --- clear
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
