package edu.stanford.robotics.trTower.gui;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JOptionPane;

import edu.stanford.robotics.trTower.TRTower;

public class HelpBrowserFactory {
	@SuppressWarnings("deprecation")
	public void showHelp(final boolean isInAnApplet, final boolean useBuiltInHelpBrowser, final String title,
			final String location) {

		try {
			URL helpURL;
			if (isInAnApplet) {
				final URL codeBase = TRTower.getInstance().getCodeBase();
				helpURL = new URL(codeBase, location);
			} else {
				helpURL = new URL("file:" + location);
			}

			if (isInAnApplet && !useBuiltInHelpBrowser) {
				TRTower.getInstance().getAppletContext().showDocument(helpURL, title);
			} else {
				final HelpBrowserDialog helpBrowserDialog = new HelpBrowserDialog();
				helpBrowserDialog.setPage(helpURL);
				helpBrowserDialog.setTitle(title);
			}

		} catch (final MalformedURLException e) {
			JOptionPane.showMessageDialog(TRTower.getInstance(), "MalformedURLException: " + "Cannot find " + location,
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
