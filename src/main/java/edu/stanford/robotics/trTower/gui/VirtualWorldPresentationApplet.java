package edu.stanford.robotics.trTower.gui;

import java.awt.Dimension;
import java.awt.Graphics;

import edu.stanford.robotics.trTower.Stimulator;
import edu.stanford.robotics.trTower.virtualWorld.VirtualWorld;

public class VirtualWorldPresentationApplet extends AnimatorApplet {
	private static final long serialVersionUID = 1L;
	private static int preferredWidth = 270;
	private static int preferredHeight = 225;
	private static Dimension preferredDimension = new Dimension(preferredWidth, preferredHeight);

	private VirtualWorld virtualWorld;

	public VirtualWorld getVirtualWorld() {
		return this.virtualWorld;
	}

	public void setVirtualWorld(final VirtualWorld vw) {
		this.virtualWorld = vw;
		this.virtualWorld.setVirtualWorldWidth(preferredWidth);
		this.virtualWorld.setVirtualWorldHeight(preferredHeight);
	}

	private Stimulator stimulator;

	public Stimulator getStimulator() {
		return this.stimulator;
	}

	public void setStimulator(final Stimulator s) {
		this.stimulator = s;
	}

	@Override
	public Dimension getPreferredSize() {
		return preferredDimension;
	}

	@Override
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}

	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	@Override
	public void paint(final Graphics g) {
		// [fix this] check for getVirtualWorld() != null?
		getVirtualWorld().render(g);
	}

	@Override
	protected void animStep() {
		getStimulator().step();
	}
}
