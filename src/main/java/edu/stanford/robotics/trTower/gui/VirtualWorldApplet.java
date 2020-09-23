package edu.stanford.robotics.trTower.gui;

import java.awt.Dimension;
import java.awt.Graphics;

import edu.stanford.robotics.trTower.virtualWorld.VirtualWorld;

public class VirtualWorldApplet extends AnimatorApplet {
	private static final long serialVersionUID = 1L;

	public VirtualWorldApplet() {

		getVirtualWorld().setVirtualWorldWidth(preferredWidth);
		getVirtualWorld().setVirtualWorldHeight(preferredHeight);

		// add in some blocks
		for (int i = 0; i < 2; i++) {
			getVirtualWorld().addNewBlock();
		}
	}

	private VirtualWorld virtualWorld;

	protected VirtualWorld getVirtualWorld() {
		if (this.virtualWorld == null) {
			this.virtualWorld = new VirtualWorld();
		}
		return this.virtualWorld;
	}

	public VirtualWorld getTheVirtualWorld() {
		return getVirtualWorld();
	}

	private static int preferredWidth = 330;
	private static int preferredHeight = 260;
	private static Dimension preferredDimension = new Dimension(preferredWidth, preferredHeight);

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
		getVirtualWorld().render(g);
	}

	@Override
	protected void animStep() {
	}
}
