package edu.stanford.robotics.trTower.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;

import edu.stanford.robotics.trTower.*;

public class HelpBrowserDialog extends JDialog {

    public final Dimension defaultSize = new Dimension(540, 600);

    public HelpBrowserDialog() {
	setTitle("Help: ");
	setSize(defaultSize);
	setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	setContentPane(getContentPanel());
	setVisible(true);
    }
    
    // --- methods
    void setPage(URL url) {
	try {
	    getEditorPane().setPage(url);
	} catch (java.io.IOException e) {
	    getEditorPane().setText("IOException: Cannot display " 
				    + url.toString());
	}
    }

    // --- components
    private JPanel contentPanel;
    protected JPanel getContentPanel() {
	if (contentPanel == null) {
	    contentPanel = new JPanel();
	    contentPanel.setLayout(new GridBagLayout());
	    GridBagConstraints editorScroll = new GridBagConstraints();
	    editorScroll.gridx = 0;
	    editorScroll.gridy = 0;
	    editorScroll.fill = GridBagConstraints.BOTH;
	    editorScroll.weightx = 1;
	    editorScroll.weighty = 1;
	    editorScroll.insets = new Insets(5, 5, 5, 5);
	    contentPanel.add(getEditorScrollPane(), editorScroll);
	    GridBagConstraints close = new GridBagConstraints();
	    close.gridx = 0;
	    close.gridy = 1;
	    close.anchor = GridBagConstraints.EAST;
	    close.insets = new Insets(15, 5, 10, 5);
	    contentPanel.add(getCloseButton(), close);
	}
	return contentPanel;
    }

    private JEditorPane editorPane;
    protected JEditorPane getEditorPane() {
	if (editorPane == null) {
	    editorPane = new JEditorPane();
	    editorPane.setEditable(false);
	}
	return editorPane;
    }
    
    private JScrollPane editorScrollPane;
    protected JScrollPane getEditorScrollPane() {
	if (editorScrollPane == null) {
	    editorScrollPane = new JScrollPane(getEditorPane());
	}
	return editorScrollPane;
    }

    private JButton closeButton;
    protected JButton getCloseButton() {
	if (closeButton == null) {
	    closeButton = new JButton(new AbstractAction("Close") {
		    public void actionPerformed(ActionEvent ae) {
			dispose();
		    }
		});
	}
	return closeButton;
    }
}
