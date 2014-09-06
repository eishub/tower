package edu.stanford.robotics.trTower.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;

public class HelpBrowserFrame extends JFrame {

    public HelpBrowserFrame() {
	setSize(new Dimension(540, 600));
	setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	setContentPane(getFrameContentPanel());
    }
    public HelpBrowserFrame(String title, String urlString) {
	this();
	setTitle("Help: " + title);
	setPage(urlString);
    }

    // --- methods
    private URL url;
    void setPage(String urlString) {
	try {
	    url = new URL("file:" + urlString);
	    getEditorPane().setPage(url);
	} catch (MalformedURLException e) {
	    getEditorPane().setText("MalformedURLException: Cannot display " 
				    + urlString);
	} catch (java.io.IOException e) {
	    getEditorPane().setText("IOException: Cannot display " 
				    + urlString);
	}
    }

    // --- components
    private JPanel frameContentPanel;
    protected JPanel getFrameContentPanel() {
	if (frameContentPanel == null) {
	    frameContentPanel = new JPanel();
//  	    frameContentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 
//  									5, 5));
	    frameContentPanel.setLayout(new GridBagLayout());
	    GridBagConstraints editorScroll = new GridBagConstraints();
	    editorScroll.gridx = 0;
	    editorScroll.gridy = 0;
	    editorScroll.fill = GridBagConstraints.BOTH;
	    editorScroll.weightx = 1;
	    editorScroll.weighty = 1;
	    editorScroll.insets = new Insets(5, 5, 5, 5);
	    frameContentPanel.add(getEditorScrollPane(), editorScroll);
	    GridBagConstraints close = new GridBagConstraints();
	    close.gridx = 0;
	    close.gridy = 1;
	    close.anchor = GridBagConstraints.EAST;
	    close.insets = new Insets(15, 5, 10, 5);
	    frameContentPanel.add(getCloseButton(), close);
	}
	return frameContentPanel;
    }

    private JEditorPane editorPane;
    protected JEditorPane getEditorPane() {
	if (editorPane == null) {
	    editorPane = new JEditorPane();
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
