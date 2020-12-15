package edu.stanford.robotics.trTower.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import edu.stanford.robotics.trTower.modelTower.ModelTower;

public class PerceptionTowerPanel extends JPanel implements StimulatorListener {
	private static final long serialVersionUID = 1L;

	public PerceptionTowerPanel() {
		final Border empty = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		final Border bevel = new SoftBevelBorder(BevelBorder.LOWERED);
		setBorder(BorderFactory.createCompoundBorder(bevel, empty));
		setLayout(new GridBagLayout());
		final GridBagConstraints title = new GridBagConstraints();
		title.gridx = 0;
		title.gridy = 0;
		title.anchor = GridBagConstraints.WEST;
		add(getPerceptionTowerActionLabel(), title);
		final GridBagConstraints primitive = new GridBagConstraints();
		primitive.gridx = 0;
		primitive.gridy = 1;
		add(getPrimitivePredicatesActionLabel(), primitive);
		final GridBagConstraints scroll = new GridBagConstraints();
		scroll.gridx = 0;
		scroll.gridy = 2;
		scroll.fill = GridBagConstraints.BOTH;
		scroll.weightx = 1;
		scroll.weighty = 1;
		add(getScrollPane(), scroll);
	}

	private ModelTower modelTower;

	ModelTower getModelTower() {
		return this.modelTower;
	}

	void setModelTower(final ModelTower m) {
		this.modelTower = m;
	}

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

	private Color defaultForeground;
	private JTextArea textArea;

	protected JTextArea getTextArea() {
		if (this.textArea == null) {
			this.textArea = new JTextArea(7, 10);
			this.defaultForeground = this.textArea.getForeground();
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

	@Override
	public void stimuStep() {

		if (!getModelTower().isAvailable()) {
			getTextArea().setForeground(Color.gray);
			return;
		} else {
			getTextArea().setForeground(this.defaultForeground);
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
						System.out.println("PerceptionTowerPanel> block is not on anything!");
						System.exit(0);
					}
				}
			}
			sb.append("\n");
		}

		final String modelString = sb.toString();
		if (!modelString.equals(getTextArea().getText())) {
			getTextArea().setText(modelString);
		}
	}
}
