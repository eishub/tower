package edu.stanford.robotics.trTower.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class ActionLabel extends JLabel {
	private static final long serialVersionUID = 1L;

	class MouseHandler extends MouseAdapter {
		@Override
		public void mouseClicked(final MouseEvent e) {
			getAction().actionPerformed(
					new ActionEvent(this, ActionEvent.ACTION_PERFORMED, getText(), e.getModifiersEx()));
		}

		@Override
		public void mouseEntered(final MouseEvent e) {
			setForeground(Color.blue);
		}

		@Override
		public void mouseExited(final MouseEvent e) {
			setForeground(Color.black);
		}
	}

	public ActionLabel() {
		setForeground(Color.black);
		addMouseListener(new MouseHandler());
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	}

	private Action action;

	public Action getAction() {
		return this.action;
	}

	public void setAction(final Action a) {
		this.action = a;
	}
}
