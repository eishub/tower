package edu.stanford.robotics.trTower.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JApplet;

import edu.stanford.robotics.trTower.TRWorld;

@SuppressWarnings("deprecation")
public class TRCallApplet extends JApplet {
	private static final long serialVersionUID = 1L;
	protected static final int hs = 10;
	protected static final int vs = 10;

	public TRCallApplet() {
		connect();
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getTRWorldBox());
	}

	private TRWorld trWorld;

	protected TRWorld getTRWorld() {
		if (this.trWorld == null) {
			this.trWorld = new TRWorld();
		}
		return this.trWorld;
	}

	private VirtualWorldPresentationApplet virtualWorldPresentationApplet;

	protected VirtualWorldPresentationApplet getVirtualWorldPresentationApplet() {
		if (this.virtualWorldPresentationApplet == null) {
			this.virtualWorldPresentationApplet = new VirtualWorldPresentationApplet();
		}
		return this.virtualWorldPresentationApplet;
	}

	private TRCallPanel trCallPanel;

	protected TRCallPanel getTRCallPanel() {
		if (this.trCallPanel == null) {
			this.trCallPanel = new TRCallPanel();
			this.trCallPanel.setActionTower(getTRWorld().getActionTower());
		}
		return this.trCallPanel;
	}

	private AnimationControlPanel animationControlPanel;

	protected AnimationControlPanel getAnimationControlPanel() {
		if (this.animationControlPanel == null) {
			this.animationControlPanel = new AnimationControlPanel();
		}
		return this.animationControlPanel;
	}

	private StimulatorPanel stimulatorPanel;

	protected StimulatorPanel getStimulatorPanel() {
		if (this.stimulatorPanel == null) {
			this.stimulatorPanel = new StimulatorPanel();
		}
		return this.stimulatorPanel;
	}

	private Box virtualWorldBox;

	protected Box getVirtualWorldBox() {
		if (this.virtualWorldBox == null) {
			this.virtualWorldBox = Box.createVerticalBox();
			this.virtualWorldBox.add(Box.createVerticalStrut(vs));
			this.virtualWorldBox.add(getVirtualWorldPresentationApplet());
			this.virtualWorldBox.add(Box.createVerticalStrut(vs));
			this.virtualWorldBox.add(getAnimationControlPanel());
		}
		return this.virtualWorldBox;
	}

	private Box actionBox;

	protected Box getActionBox() {
		if (this.actionBox == null) {
			this.actionBox = Box.createVerticalBox();
			this.actionBox.add(getStimulatorPanel());
			this.actionBox.add(Box.createVerticalStrut(vs));
			this.actionBox.add(getTRCallPanel());
		}
		return this.actionBox;
	}

	private Box trWorldBox;

	protected Box getTRWorldBox() {
		if (this.trWorldBox == null) {
			this.trWorldBox = Box.createHorizontalBox();
			this.trWorldBox.add(getVirtualWorldBox());
			this.trWorldBox.add(Box.createRigidArea(new Dimension(hs, 0)));
			this.trWorldBox.add(getActionBox());
		}
		return this.trWorldBox;
	}

	private void connect() {
		getTRCallPanel().setActionTower(getTRWorld().getActionTower());

		getVirtualWorldPresentationApplet().setVirtualWorld(getTRWorld().getVirtualWorld());
		getVirtualWorldPresentationApplet().setStimulator(getTRWorld().getStimulator());

		getStimulatorPanel().setStimulator(getTRWorld().getStimulator());
	}

	@Override
	public void start() {
		getVirtualWorldPresentationApplet().start();
	}

	@Override
	public void stop() {
		getVirtualWorldPresentationApplet().stop();
	}

	@Override
	public void init() {
		getVirtualWorldPresentationApplet().init();
	}

	@Override
	public void destroy() {
		getVirtualWorldPresentationApplet().destroy();
	}
}
