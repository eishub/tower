package edu.stanford.robotics.trTower.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;

import edu.stanford.robotics.trTower.*;

public class HelpBrowserFactory {

    public void showHelp(boolean isInAnApplet,
			 boolean useBuiltInHelpBrowser,
			 String title,
			 String location) {

	try {
	    // --- build URL
	    URL helpURL;
	    if (isInAnApplet) {
		// applet
		URL codeBase = TRTower.getInstance().getCodeBase();
		//		System.out.println("codeBase = " + codeBase.toString());
		helpURL = new URL(codeBase, location);
	    } else {
		// application
		helpURL = new URL("file:" + location);
	    }

	    // --- display URL
	    if (isInAnApplet && !useBuiltInHelpBrowser) {
		// Applet, and don't want to use builtin browser,
		// so use web browser
//  		TRTower.getInstance().showStatus("Opening: " +
//  						 helpURL.toString());
		TRTower.getInstance().getAppletContext().showDocument(helpURL,
								      title);
	    } else {
		// either it's an application, or specifically requires so,
		// use the built in help browser
		HelpBrowserDialog helpBrowserDialog = new HelpBrowserDialog();
		helpBrowserDialog.setPage(helpURL);
		helpBrowserDialog.setTitle(title);
	    }
	    
	} catch (MalformedURLException e) {

	    JOptionPane.showMessageDialog(TRTower.getInstance(),
					  "MalformedURLException: " + 
  					  "Cannot find " + location,
					  "Error",
					  JOptionPane.ERROR_MESSAGE);
	}
    }
    
}
