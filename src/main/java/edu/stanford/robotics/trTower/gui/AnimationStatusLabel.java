package edu.stanford.robotics.trTower.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import edu.stanford.robotics.trTower.*;
import edu.stanford.robotics.trTower.virtualWorld.*;

class AnimationStatusLabel extends JLabel {
	
    private Color backgroundColor;
    private Color foregroundColor;
    AnimationStatusLabel() {
	setPreferredSize(new Dimension(50, 20));
	setMinimumSize(new Dimension(50, 20));
	//	setMaximumSize(new Dimension(50, 20));
	setHorizontalAlignment(SwingConstants.CENTER);
	backgroundColor = getBackground();
	foregroundColor = getForeground();
	setFont(getFont().deriveFont(Font.PLAIN));
    }
    

    static final int PLAYING = 1;
    static final int PAUSED = 2;
    static final int STEPPING = 3;
    // --- methods
    void setMode(int mode) {

	setForeground(foregroundColor);
	isOn = true;
	
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
	if (timer == null) {
	    timer = new Timer(750, new ActionHandler());
	    timer.setInitialDelay(750);
	    timer.setCoalesce(true);
	}
	return timer;
    }

    private boolean isOn = false;
    class ActionHandler implements ActionListener {
	public void actionPerformed(ActionEvent ae) {
	    if (isOn) {
		setForeground(backgroundColor);
		isOn = false;
	    } else {
		setForeground(foregroundColor);
		isOn = true;
	    }
	}   
    }
}
