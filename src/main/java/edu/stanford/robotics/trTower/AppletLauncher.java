package edu.stanford.robotics.trTower;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.stanford.robotics.trTower.gui.TRCallApplet;

@SuppressWarnings("deprecation")
public class AppletLauncher extends JApplet {
	private static final long serialVersionUID = 1L;

	public AppletLauncher() {
		this(true);
	}

	public AppletLauncher(final boolean inAnApplet) {
		if (inAnApplet) {
			// Hack to avoid ugly message about system event access check.
			getRootPane().putClientProperty("defeatSystemEventQueueCheck", Boolean.TRUE);
		}
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
		// http: // slashdot.org/
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {
				System.exit(0);
			}
		});

		final AppletLauncher a = new AppletLauncher(false);
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
			this.applet = new TRCallApplet();
		}
		return this.applet;
	}
}
