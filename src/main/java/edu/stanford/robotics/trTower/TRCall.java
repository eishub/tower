package edu.stanford.robotics.trTower;

import javax.swing.*;

import edu.stanford.robotics.trTower.gui.*;

public class TRCall extends AppletLauncher {

	//static JApplet getApplet() {
	public JApplet getApplet() {
		TRCall t = new TRCall();
		return t.getTRCallApplet();
	}

	// --- components
	private TRCallApplet trCallApplet;

	protected TRCallApplet getTRCallApplet() {
		if (trCallApplet == null) {
			trCallApplet = new TRCallApplet();
		}
		return trCallApplet;
	}
}
