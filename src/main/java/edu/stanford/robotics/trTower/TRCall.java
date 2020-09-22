package edu.stanford.robotics.trTower;

import javax.swing.JApplet;

import edu.stanford.robotics.trTower.gui.TRCallApplet;

@SuppressWarnings("deprecation")
public class TRCall extends AppletLauncher {
	private static final long serialVersionUID = 1L;

	@Override
	public JApplet getApplet() {
		final TRCall t = new TRCall();
		return t.getTRCallApplet();
	}

	private TRCallApplet trCallApplet;

	protected TRCallApplet getTRCallApplet() {
		if (this.trCallApplet == null) {
			this.trCallApplet = new TRCallApplet();
		}
		return this.trCallApplet;
	}
}
