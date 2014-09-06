package edu.stanford.robotics.trTower.gui;

import java.awt.*;
import javax.swing.*;

import edu.stanford.robotics.trTower.*;

public class TRCallApplet extends JApplet {

	protected static final int hs = 10;
	protected static final int vs = 10;

	public TRCallApplet() {
		connect();

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getTRWorldBox());
	}

	// --- components
	private TRWorld trWorld;

	protected TRWorld getTRWorld() {
		if (trWorld == null) {
			trWorld = new TRWorld();
		}
		return trWorld;
	}

	private VirtualWorldPresentationApplet virtualWorldPresentationApplet;

	protected VirtualWorldPresentationApplet getVirtualWorldPresentationApplet() {
		if (virtualWorldPresentationApplet == null) {
			virtualWorldPresentationApplet = new VirtualWorldPresentationApplet();
		}
		return virtualWorldPresentationApplet;
	}

	private TRCallPanel trCallPanel;

	protected TRCallPanel getTRCallPanel() {
		if (trCallPanel == null) {
			trCallPanel = new TRCallPanel();
			trCallPanel.setActionTower(getTRWorld().getActionTower());
		}
		return trCallPanel;
	}

	private AnimationControlPanel animationControlPanel;

	protected AnimationControlPanel getAnimationControlPanel() {
		if (animationControlPanel == null) {
			animationControlPanel = new AnimationControlPanel();
		}
		return animationControlPanel;
	}

	private StimulatorPanel stimulatorPanel;

	protected StimulatorPanel getStimulatorPanel() {
		if (stimulatorPanel == null) {
			stimulatorPanel = new StimulatorPanel();
		}
		return stimulatorPanel;
	}

	// --- GUI components
	private Box virtualWorldBox;

	protected Box getVirtualWorldBox() {
		if (virtualWorldBox == null) {
			virtualWorldBox = Box.createVerticalBox();
			virtualWorldBox.add(Box.createVerticalStrut(vs));
			virtualWorldBox.add(getVirtualWorldPresentationApplet());
			virtualWorldBox.add(Box.createVerticalStrut(vs));
			virtualWorldBox.add(getAnimationControlPanel());
		}
		return virtualWorldBox;
	}

	private Box actionBox;

	protected Box getActionBox() {
		if (actionBox == null) {
			actionBox = Box.createVerticalBox();
			actionBox.add(getStimulatorPanel());
			actionBox.add(Box.createVerticalStrut(vs));
			actionBox.add(getTRCallPanel());
		}
		return actionBox;
	}

	private Box trWorldBox;

	protected Box getTRWorldBox() {
		if (trWorldBox == null) {
			trWorldBox = Box.createHorizontalBox();
			trWorldBox.add(getVirtualWorldBox());
			trWorldBox.add(Box.createRigidArea(new Dimension(hs, 0)));
			trWorldBox.add(getActionBox());
		}
		return trWorldBox;
	}

	// --- private methods
	private void connect() {
		getTRCallPanel().setActionTower(getTRWorld().getActionTower());

		getVirtualWorldPresentationApplet().setVirtualWorld(
				getTRWorld().getVirtualWorld());
		getVirtualWorldPresentationApplet().setStimulator(
				getTRWorld().getStimulator());

// code broken...
//		getAnimationControlPanel().setAnimatorApplet(
//				getVirtualWorldPresentationApplet());

		getStimulatorPanel().setStimulator(getTRWorld().getStimulator());
	}

	// --- public methods
	public void start() {
		getVirtualWorldPresentationApplet().start();
	}

	public void stop() {
		getVirtualWorldPresentationApplet().stop();
	}

	public void init() {
		getVirtualWorldPresentationApplet().init();
	}

	public void destroy() {
		getVirtualWorldPresentationApplet().destroy();
	}
}
