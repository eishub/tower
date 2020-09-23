package edu.stanford.robotics.trTower.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

public class HelpBrowserFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	public HelpBrowserFrame() {
		setSize(new Dimension(540, 600));
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setContentPane(getFrameContentPanel());
	}

	public HelpBrowserFrame(final String title, final String urlString) {
		this();
		setTitle("Help: " + title);
		setPage(urlString);
	}

	private URL url;

	void setPage(final String urlString) {
		try {
			this.url = new URL("file:" + urlString);
			getEditorPane().setPage(this.url);
		} catch (final MalformedURLException e) {
			getEditorPane().setText("MalformedURLException: Cannot display " + urlString);
		} catch (final java.io.IOException e) {
			getEditorPane().setText("IOException: Cannot display " + urlString);
		}
	}

	private JPanel frameContentPanel;

	protected JPanel getFrameContentPanel() {
		if (this.frameContentPanel == null) {
			this.frameContentPanel = new JPanel();
			this.frameContentPanel.setLayout(new GridBagLayout());
			final GridBagConstraints editorScroll = new GridBagConstraints();
			editorScroll.gridx = 0;
			editorScroll.gridy = 0;
			editorScroll.fill = GridBagConstraints.BOTH;
			editorScroll.weightx = 1;
			editorScroll.weighty = 1;
			editorScroll.insets = new Insets(5, 5, 5, 5);
			this.frameContentPanel.add(getEditorScrollPane(), editorScroll);
			final GridBagConstraints close = new GridBagConstraints();
			close.gridx = 0;
			close.gridy = 1;
			close.anchor = GridBagConstraints.EAST;
			close.insets = new Insets(15, 5, 10, 5);
			this.frameContentPanel.add(getCloseButton(), close);
		}
		return this.frameContentPanel;
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
					dispose();
				}
			});
		}
		return this.closeButton;
	}

}
