package edu.stanford.robotics.trTower.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class MessageLabel extends JLabel {

    // -- attributes
    public static final int NIL = 0;
    public static final int ERROR = 1;
    public static final int INFO = 2;
    
    public MessageLabel() {
	//	setMinimumSize(new Dimension(16, 16));
	setFont(getFont().deriveFont(Font.PLAIN));
	setText(" ");
	Border empty = BorderFactory.createEmptyBorder(2, 2, 2, 2);
	Border bevel = new SoftBevelBorder(SoftBevelBorder.LOWERED);
	setBorder(BorderFactory.createCompoundBorder(bevel, empty));
	setMessage(MessageLabel.INFO, 
		   "To begin, click on \"New Block\" to create blocks.");

    }

    // -- public methods
    public void setMessage(int messageType, String messageText) {
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

