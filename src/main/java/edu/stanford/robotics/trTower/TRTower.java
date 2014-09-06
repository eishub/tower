package edu.stanford.robotics.trTower;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.text.*;
import java.util.*;

import edu.stanford.robotics.trTower.gui.*;

/**
 * Wouter:
 * 
 * Part of the program structures is described in Nils J. Nilsson:
 * Teleo-Reactive Programs and the Triple-Tower Architecture. Electronic
 * Transactions on Artificial Intelligence, Vol. 5 (2001), Section B, pp.
 * 99-110. http://www.ep.liu.se/ej/etai/2001/006/ see
 * http://www.ida.liu.se/ext/epa/ej/etai/2001/006/01006-etaibody.ps
 * 
 * Quote from there:
 * 
 * Aspects of the environment that are relevant to the agent's roles are sensed
 * and converted to primitive predicates and values These are stored at the
 * lowest level of the model tower Their presence there may immediately evoke
 * primitive actions at the bottom of the action tower. These actions, in turn,
 * affect the environment, and some of these effects may be sensed- creating a
 * loop in which the environment itself might play an important computational
 * role. The perception tower consists of rules that convert predicates stored
 * in the model tower into more abstract predicates which are then deposited at
 * higher levels in the model tower. These processes can continue until even the
 * highest levels of the model tower are populated. The action tower consists of
 * a loose hierarchy of action routines that are triggered by the contents of
 * the model tower. The lowest level action rou- tines are simple reflexes,
 * evoked by predicates corresponding to primitive percepts. More complex
 * actions are evoked by more abstract predicates appropriate for those actions.
 * High-level actions "call" other actions until the process bottoms out at the
 * primitive actions that actually affect the environment.
 * 
 * 
 * 
 * 
 * There is no technical docu on TRTower, so we had to reverse engineer the
 * source code. I have added comments on the inner workings through the code.
 * For instance in Stimulator.java the main listener pipes are set up.
 * 
 * Globally this works as follows. AnimationTimer is set up, and it calls step()
 * in the Stimulator. The stimulator at every step() calls the subscribed
 * listeners, which are the VirtualWorld, PerceptionTower and ActionTower.
 * 
 * VirtualWorld is the "scheduler" that synchronizes the GUI actions, rendering
 * of the world, execution of Durative actions, etc.
 * 
 * PerceptionTower updates the percepts by calls to getModelTower().setOn() etc
 * 
 * ActionTower
 * 
 * 
 * Blocks are referred to with upper case characters A,B,C ... The table is
 * referred to with TA.
 * 
 * The primitive actions available are: 1. pickup(x). If succesfull hand will be
 * holding block x 2. putdown(x,y). Requires hand to hold block x. If succesful
 * block x is on y. 3. nil(). Moves the hand to the home position.
 * 
 * the percepts are: 1. on(x,y) true if block x is on y. 2. Holding(x) true if
 * hand is holding block x.
 */

public class TRTower extends JApplet {

	private static Dimension defaultDimen = new Dimension(820, 614);

	private boolean inAnApplet;
	private static TRTower trTowerInstance;

	public TRTower() {
		this(true);
	}

	public TRTower(boolean inAnApplet) {
		this.inAnApplet = inAnApplet;
		if (inAnApplet) {
			// Hack to avoid ugly message about system event access check.
			getRootPane().putClientProperty("defeatSystemEventQueueCheck",
					Boolean.TRUE);
		}
		trTowerInstance = this;
	}

	public static TRTower getInstance() {
		return trTowerInstance;
	}

	// --- update these for each new version
	private String version = "1.0";

	public String getVersion() {
		return version;
	}

	private Calendar calendar = new GregorianCalendar(2001, 7 - 1, 28);

	public Calendar getCalendar() {
		return calendar;
	}

	// --- attributes
	// private boolean inAnApplet = true;
	// boolean isInAnApplet() { return inAnApplet; }
	// void setInAnApplet(boolean a) { inAnAnpplet = a; }

	// --- public methods

	private DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);

	public void showAbout() {
		JOptionPane.showMessageDialog(this, "TRTower\n\n" + "Version: "
				+ getVersion() + "\n" + df.format(getCalendar().getTime())
				+ "\n\n" + "Written by Chris T. K. Ng\n" + "\n"
				+ "with Professor Nils J. Nilsson\n" + "Robotics Laboratory\n"
				+ "Department of Computer Science\n" + "Stanford University.\n"
				+ "\n" + "This program is distributed in\n"
				+ "the hope that it will be useful,\n"
				+ "but WITHOUT ANY WARRANTY.\n" + "\n", "About TRTower",
				JOptionPane.INFORMATION_MESSAGE);
	}

	// private URL helpURL;
	public void showHelp(boolean useBuiltInHelpBrowser, String title,
			String location) {
		getHelpBrowserFactory().showHelp(inAnApplet, useBuiltInHelpBrowser,
				title, location);

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
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// TRTower a = TRTower.getInstance();
		// a.setInAnApplet(false);
		TRTower a = new TRTower(false);
		frame.setSize(defaultDimen);
		frame.setTitle("TRTower");
		frame.setContentPane(a.getAppContentPane());
		// frame.pack();
		a.getApplet().init();
		frame.setVisible(true);
		a.getApplet().start();
	}

	// --- components
	private HelpBrowserFactory helpBrowserFactory;

	protected HelpBrowserFactory getHelpBrowserFactory() {
		if (helpBrowserFactory == null) {
			helpBrowserFactory = new HelpBrowserFactory();
		}
		return helpBrowserFactory;
	}

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
			applet = new TRTowerApplet();
		}
		return applet;
	}
}
