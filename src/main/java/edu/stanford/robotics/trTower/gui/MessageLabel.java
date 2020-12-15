package edu.stanford.robotics.trTower.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.SoftBevelBorder;

public class MessageLabel extends JLabel {
	private static final long serialVersionUID = 1L;
	public static final int NIL = 0;
	public static final int ERROR = 1;
	public static final int INFO = 2;

	public MessageLabel() {
		setFont(getFont().deriveFont(Font.PLAIN));
		setText(" ");
		final Border empty = BorderFactory.createEmptyBorder(2, 2, 2, 2);
		final Border bevel = new SoftBevelBorder(BevelBorder.LOWERED);
		setBorder(BorderFactory.createCompoundBorder(bevel, empty));
		setMessage(MessageLabel.INFO, "To begin, click on \"New Block\" to create blocks.");
	}

	public void setMessage(final int messageType, final String messageText) {
		switch (messageType) {
		case NIL:
			setText(" ");
			break;
		case ERROR:
			setForeground(Color.red);
			setText(" Error! " + messageText);
			break;
		case INFO:
			setForeground(Color.black);
			setText(" " + messageText);
			break;
		}
	}
}
