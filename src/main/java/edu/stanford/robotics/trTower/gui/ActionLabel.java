package edu.stanford.robotics.trTower.gui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class ActionLabel extends JLabel  {

    class MouseHandler extends MouseAdapter {
	public void mouseClicked(MouseEvent e) {
	    getAction().actionPerformed(new ActionEvent(this,
							ActionEvent.ACTION_PERFORMED,
							getText(),
							e.getModifiers()));
	}
	public void mouseEntered(MouseEvent e) {
	    setForeground(Color.blue);
	}
	public void mouseExited(MouseEvent e) {
	    setForeground(Color.black);
	}
    }
    public ActionLabel() {
	setForeground(Color.black);
	addMouseListener(new MouseHandler());
	setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }
    // --- attributes
    private Action action;
    public Action getAction() { return action; }
    public void setAction(Action a) { action = a; }

    
}
