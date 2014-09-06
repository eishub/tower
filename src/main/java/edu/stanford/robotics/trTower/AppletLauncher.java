package edu.stanford.robotics.trTower;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import edu.stanford.robotics.trTower.gui.*;

public class AppletLauncher extends JApplet {

	private static Dimension defaultDimen = new Dimension(550, 400);
	private boolean inAnApplet = true;

	public AppletLauncher() {
		this(true);
	}

	public AppletLauncher(boolean inAnApplet) {
		this.inAnApplet = inAnApplet;
		if (inAnApplet) {
			// Hack to avoid ugly message about system event access check.
			getRootPane().putClientProperty("defeatSystemEventQueueCheck",
					Boolean.TRUE);
		}
	}

	public void init() {
		setContentPane(getAppContentPane());
		getApplet().init();
	}

	public void start() {
		getApplet().start();
	}

	public void stop() {
		getApplet().stop();
	}

	public void destroy() {
		getApplet().destroy();
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		// http: // slashdot.org/
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		AppletLauncher a = new AppletLauncher(false);
		// frame.setSize(defaultDimen);
		frame.setTitle("Application version: AppletLauncher");
		frame.setContentPane(a.getAppContentPane());
		frame.pack();
		a.getApplet().init();
		frame.setVisible(true);
		a.getApplet().start();
	}

	// --- components

	private JPanel appContentPane;

	protected Container getAppContentPane() {
		if (appContentPane == null) {
			appContentPane = new JPanel();
			appContentPane.setLayout(new BorderLayout());
			appContentPane.add(getApplet());
		}
		return appContentPane;
	}

	private JApplet applet;

	protected JApplet getApplet() {
		if (applet == null) {
			// --- modify this
			applet = new TRCallApplet();
		}
		return applet;
	}
}
