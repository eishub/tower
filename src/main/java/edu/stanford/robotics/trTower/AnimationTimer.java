package edu.stanford.robotics.trTower;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class AnimationTimer implements ActionListener {
	public AnimationTimer() {
		stopTimer();
	}

	@Override
	public void actionPerformed(final ActionEvent ae) {
		if (getStimulator() != null) {
			getStimulator().step();
		}
	}

	private Stimulator stimulator;

	public Stimulator getStimulator() {
		return this.stimulator;
	}

	public void setStimultor(final Stimulator s) {
		this.stimulator = s;
	}

	private Timer timer;

	protected Timer getTimer() {
		if (this.timer == null) {
			this.timer = new Timer(25, this); // default, but slider will override
			this.timer.setInitialDelay(0);
			this.timer.setCoalesce(true);
		}
		return this.timer;
	}

	public void setDelay(final int delay) {
		getTimer().setDelay(delay);
	}

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
