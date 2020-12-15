package edu.stanford.robotics.trTower.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

class AnimationStatusLabel extends JLabel {
	private static final long serialVersionUID = 1L;

	private final Color backgroundColor;
	private final Color foregroundColor;

	AnimationStatusLabel() {
		setPreferredSize(new Dimension(50, 20));
		setMinimumSize(new Dimension(50, 20));
		setHorizontalAlignment(SwingConstants.CENTER);
		this.backgroundColor = getBackground();
		this.foregroundColor = getForeground();
		setFont(getFont().deriveFont(Font.PLAIN));
	}

	static final int PLAYING = 1;
	static final int PAUSED = 2;
	static final int STEPPING = 3;

	void setMode(final int mode) {
		setForeground(this.foregroundColor);
		this.isOn = true;

		switch (mode) {
		case PLAYING:
			setText("");
			getTimer().stop();
			break;
		case PAUSED:
			setText("paused");
			getTimer().restart();
			break;
		case STEPPING:
			setText("stepping");
			getTimer().stop();
			break;
		default:
			// do nothing
		}
	}

	private Timer timer;

	protected Timer getTimer() {
		if (this.timer == null) {
			this.timer = new Timer(750, new ActionHandler());
			this.timer.setInitialDelay(750);
			this.timer.setCoalesce(true);
		}
		return this.timer;
	}

	private boolean isOn = false;

	class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent ae) {
			if (AnimationStatusLabel.this.isOn) {
				setForeground(AnimationStatusLabel.this.backgroundColor);
				AnimationStatusLabel.this.isOn = false;
			} else {
				setForeground(AnimationStatusLabel.this.foregroundColor);
				AnimationStatusLabel.this.isOn = true;
			}
		}
	}
}
