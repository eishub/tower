package edu.stanford.robotics.trTower.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class HelpBrowser {
	public final Dimension defaultSize = new Dimension(540, 600);

	private JDialog dialog;

	private JPanel contentPanel;

	protected JPanel getContentPanel() {
		if (this.contentPanel == null) {
			this.contentPanel = new JPanel();
			this.contentPanel.setLayout(new GridBagLayout());
			final GridBagConstraints editorScroll = new GridBagConstraints();
			editorScroll.gridx = 0;
			editorScroll.gridy = 0;
			editorScroll.fill = GridBagConstraints.BOTH;
			editorScroll.weightx = 1;
			editorScroll.weighty = 1;
			editorScroll.insets = new Insets(5, 5, 5, 5);
			this.contentPanel.add(getEditorScrollPane(), editorScroll);
			final GridBagConstraints close = new GridBagConstraints();
			close.gridx = 0;
			close.gridy = 1;
			close.anchor = GridBagConstraints.EAST;
			close.insets = new Insets(15, 5, 10, 5);
			this.contentPanel.add(getCloseButton(), close);
		}
		return this.contentPanel;
	}

	private JEditorPane editorPane;

	protected JEditorPane getEditorPane() {
		if (this.editorPane == null) {
			this.editorPane = new JEditorPane();
		}
		return this.editorPane;
	}

	private JScrollPane editorScrollPane;

	protected JScrollPane getEditorScrollPane() {
		if (this.editorScrollPane == null) {
			this.editorScrollPane = new JScrollPane(getEditorPane());
		}
		return this.editorScrollPane;
	}

	private JButton closeButton;

	protected JButton getCloseButton() {
		if (this.closeButton == null) {
			this.closeButton = new JButton(new AbstractAction("Close") {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(final ActionEvent ae) {
					HelpBrowser.this.dialog.dispose();
				}
			});
		}
		return this.closeButton;
	}
}
