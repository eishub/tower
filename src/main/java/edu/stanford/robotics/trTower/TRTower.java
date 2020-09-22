package edu.stanford.robotics.trTower;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import edu.stanford.robotics.trTower.gui.HelpBrowserFactory;
import edu.stanford.robotics.trTower.gui.TRTowerApplet;

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
@SuppressWarnings("deprecation")
public class TRTower extends JApplet {
	private static final long serialVersionUID = 1L;

	private static Dimension defaultDimen = new Dimension(820, 614);

	private final boolean inAnApplet;
	private static TRTower trTowerInstance;

	public TRTower() {
		this(true);
	}

	public TRTower(final boolean inAnApplet) {
		this.inAnApplet = inAnApplet;
		if (inAnApplet) {
			// Hack to avoid ugly message about system event access check.
			getRootPane().putClientProperty("defeatSystemEventQueueCheck", Boolean.TRUE);
		}
		trTowerInstance = this;
	}

	public static TRTower getInstance() {
		return trTowerInstance;
	}

	// update for each new version
	private final String version = "1.0";

	public String getVersion() {
		return this.version;
	}

	private final Calendar calendar = new GregorianCalendar(2001, 7 - 1, 28);

	public Calendar getCalendar() {
		return this.calendar;
	}

	private final DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);

	public void showAbout() {
		JOptionPane.showMessageDialog(this,
				"TRTower\n\n" + "Version: " + getVersion() + "\n" + this.df.format(getCalendar().getTime()) + "\n\n"
						+ "Written by Chris T. K. Ng\n" + "\n" + "with Professor Nils J. Nilsson\n"
						+ "Robotics Laboratory\n" + "Department of Computer Science\n" + "Stanford University.\n" + "\n"
						+ "This program is distributed in\n" + "the hope that it will be useful,\n"
						+ "but WITHOUT ANY WARRANTY.\n" + "\n",
				"About TRTower", JOptionPane.INFORMATION_MESSAGE);
	}

	public void showHelp(final boolean useBuiltInHelpBrowser, final String title, final String location) {
		getHelpBrowserFactory().showHelp(this.inAnApplet, useBuiltInHelpBrowser, title, location);

	}

	@Override
	public void init() {
		setContentPane(getAppContentPane());
		getApplet().init();
	}

	@Override
	public void start() {
		getApplet().start();
	}

	@Override
	public void stop() {
		getApplet().stop();
	}

	@Override
	public void destroy() {
		getApplet().destroy();
	}

	public static void main(final String[] args) {
		final JFrame frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {
				System.exit(0);
			}
		});

		final TRTower a = new TRTower(false);
		frame.setSize(defaultDimen);
		frame.setTitle("TRTower");
		frame.setContentPane(a.getAppContentPane());
		a.getApplet().init();
		frame.setVisible(true);
		a.getApplet().start();
	}

	private HelpBrowserFactory helpBrowserFactory;

	protected HelpBrowserFactory getHelpBrowserFactory() {
		if (this.helpBrowserFactory == null) {
			this.helpBrowserFactory = new HelpBrowserFactory();
		}
		return this.helpBrowserFactory;
	}

	private JPanel appContentPane;

	protected Container getAppContentPane() {
		if (this.appContentPane == null) {
			this.appContentPane = new JPanel();
			this.appContentPane.setLayout(new BorderLayout());
			this.appContentPane.add(getApplet());
		}
		return this.appContentPane;
	}

	private JApplet applet;

	protected JApplet getApplet() {
		if (this.applet == null) {
			this.applet = new TRTowerApplet();
		}
		return this.applet;
	}
}
