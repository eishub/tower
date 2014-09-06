package edu.stanford.robotics.trTower;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class AnimationTimer implements ActionListener {

	public AnimationTimer() {
		stopTimer();
	}

	public void actionPerformed(ActionEvent ae) {
		if (getStimulator() != null) {
			getStimulator().step();
		}
	}

	// --- attribuites
	private Stimulator stimulator;

	public Stimulator getStimulator() {
		return stimulator;
	}

	public void setStimultor(Stimulator s) {
		stimulator = s;
	}

	// --- components
	private Timer timer;

	protected Timer getTimer() {
		if (timer == null) {
			timer = new Timer(25, this); // default, but slider will override
			timer.setInitialDelay(0);
			timer.setCoalesce(true);
		}
		return timer;
	}

	// --- public methods
	public void setDelay(int delay) {
		getTimer().setDelay(delay);
	}

	// public int getDelay() {
	// return getTimer().getDelay();
	// }

	// Can be invoked from any thread
	public synchronized void startTimer() {
		if (!getTimer().isRunning()) {
			getTimer().start();
		}
	}

	public synchronized void stopTimer() {
		if (getTimer().isRunning()) {
			getTimer().stop();
		}
	}
}
