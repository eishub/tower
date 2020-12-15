package edu.stanford.robotics.trTower.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.Iterator;

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
import edu.stanford.robotics.trTower.actionTower.ActionTower;
import edu.stanford.robotics.trTower.actionTower.TRCallDescription;
import edu.stanford.robotics.trTower.common.ListUtilities;

public class ActionTowerPanel extends JPanel implements StimulatorListener {
	private static final long serialVersionUID = 1L;

	public ActionTowerPanel() {
		final Border empty = BorderFactory.createEmptyBorder(2, 2, 2, 2);
		final Border bevel = new SoftBevelBorder(BevelBorder.LOWERED);
		setBorder(BorderFactory.createCompoundBorder(bevel, empty));
		setLayout(new GridBagLayout());
		final GridBagConstraints title = new GridBagConstraints();
		title.gridx = 0;
		title.gridy = 0;
		title.anchor = GridBagConstraints.WEST;
		add(getActionTowerActionLabel(), title);
		final GridBagConstraints trace = new GridBagConstraints();
		trace.gridx = 0;
		trace.gridy = 1;
		add(getTRCallTraceActionLabel(), trace);
		final GridBagConstraints scroll = new GridBagConstraints();
		scroll.gridx = 0;
		scroll.gridy = 2;
		scroll.fill = GridBagConstraints.BOTH;
		scroll.weightx = 1;
		scroll.weighty = 1;
		add(getScrollPane(), scroll);
	}

	private ActionTower actionTower;

	public ActionTower getActionTower() {
		return this.actionTower;
	}

	public void setActionTower(final ActionTower a) {
		this.actionTower = a;
	}

	private ActionLabel actionTowerActionLabel;

	protected ActionLabel getActionTowerActionLabel() {
		if (this.actionTowerActionLabel == null) {
			this.actionTowerActionLabel = new ActionLabel();
			this.actionTowerActionLabel.setText("Action Tower");
			this.actionTowerActionLabel.setAction(new AbstractAction("ActionTower") {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(final ActionEvent ae) {
					final boolean useBuiltInHelpBrowser = (ae.getModifiers() & ActionEvent.SHIFT_MASK) == 0 ? false
							: true;
					TRTower.getInstance().showHelp(useBuiltInHelpBrowser, "Action Tower", "help/ActionTower.html");
				}
			});
		}
		return this.actionTowerActionLabel;
	}

	private ActionLabel trCallTraceActionLabel;

	protected ActionLabel getTRCallTraceActionLabel() {
		if (this.trCallTraceActionLabel == null) {
			this.trCallTraceActionLabel = new ActionLabel();
			this.trCallTraceActionLabel.setText("TR Action Trace");
			this.trCallTraceActionLabel.setFont(this.trCallTraceActionLabel.getFont().deriveFont(Font.ITALIC));
			this.trCallTraceActionLabel.setAction(new AbstractAction("TRActionTrace") {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(final ActionEvent ae) {
					final boolean useBuiltInHelpBrowser = (ae.getModifiers() & ActionEvent.SHIFT_MASK) == 0 ? false
							: true;
					TRTower.getInstance().showHelp(useBuiltInHelpBrowser, "TR Action Trace", "help/TRActionTrace.html");
				}
			});
		}
		return this.trCallTraceActionLabel;
	}

	private Color defaultForeground;
	private JTextArea textArea;

	protected JTextArea getTextArea() {
		if (this.textArea == null) {
			this.textArea = new JTextArea(15, 15);
			this.defaultForeground = this.textArea.getForeground();
			this.textArea.setEditable(false);
		}
		return this.textArea;
	}

	private JScrollPane scrollPane;

	protected JScrollPane getScrollPane() {
		if (this.scrollPane == null) {
			this.scrollPane = new JScrollPane(getTextArea());
			this.scrollPane.setPreferredSize(new Dimension(200, 220));
		}
		return this.scrollPane;
	}

	@Override
	public void stimuStep() {
		if (!getActionTower().isAvailable()) {
			// if not available, just keep old data, gut gray out.
			getTextArea().setForeground(Color.gray);
			return;
		} else {
			getTextArea().setForeground(this.defaultForeground);
		}

		if (!getActionTower().isChanged()) {
			return;
		}
		final StringBuffer sb = new StringBuffer();
		final Iterator<TRCallDescription> i = getActionTower().getTRCallDescriptionList().iterator();
		while (i.hasNext()) {
			sb.append(" ");
			final TRCallDescription tr = (i.next());
			final int trFunction = tr.getTrFunction();
			switch (trFunction) {
			case TRCallDescription.MAKETOWER:
				sb.append("maketower");
				break;
			case TRCallDescription.MOVETOTABLE:
				sb.append("move-to-table");
				break;
			case TRCallDescription.MOVE:
				sb.append("move");
				break;
			case TRCallDescription.UNPILE:
				sb.append("unpile");
				break;
			case TRCallDescription.PICKUP:
				sb.append("pickup");
				break;
			case TRCallDescription.PUTDOWN:
				sb.append("putdown");
				break;
			case TRCallDescription.NIL:
				sb.append("nil");
				break;
			}

			if (trFunction != TRCallDescription.NIL) {
				sb.append(ListUtilities.listToString(tr.getParameterList()));
			}

			sb.append("\n");
		}
		final String trString = sb.toString();
		if (!trString.equals(getTextArea().getText())) {
			getTextArea().setText(trString);
		}
	}
}
