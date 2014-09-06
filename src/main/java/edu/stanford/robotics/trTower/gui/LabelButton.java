package edu.stanford.robotics.trTower.gui;

import java.awt.*;
import javax.swing.*;
import java.awt.font.*;
import java.awt.geom.*;

public class LabelButton extends JButton {

    public LabelButton() {
	JLabel dummyLabel = new JLabel();
	setForeground(dummyLabel.getForeground().darker().darker());
	setBorderPainted(false);
	//	setFocusPainted(false);
	ButtonIcon buttonIcon = new ButtonIcon();
	buttonIcon.setLabelButton(this);
	setIcon(buttonIcon);
	setPressedIcon(buttonIcon);
	RolloverButtonIcon rolloverButtonIcon = new RolloverButtonIcon();
	rolloverButtonIcon.setLabelButton(this);
	setRolloverIcon(rolloverButtonIcon);
	setRolloverEnabled(true);
    }

    // --- attributes
    private Color rolloverColor = Color.white;
    public Color getRolloverColor() { return rolloverColor; }
    public void setRolloverColor(Color c) {rolloverColor = c; }
    
    private String iconText;
    public String getIconText() { return iconText;}
    public void setIconText(String t) { iconText = t; } 
    
    // --- overrides methods
    //    public boolean isFocusTraversable() { return false; }

}

class RolloverButtonIcon extends ButtonIcon {

    public RolloverButtonIcon() {
	setTextColor(Color.white);
    }
    void setLabelButton(LabelButton b) { 
	super.setLabelButton(b);
	setTextColor(Color.white);
    }
}

class ButtonIcon implements Icon {

    // --- attributes
    private LabelButton labelButton;
    LabelButton getLabelButton() { return labelButton; }
    void setLabelButton(LabelButton b) { 
	labelButton = b;
	setTextColor(labelButton.getForeground());
    }

    private Color textColor = Color.black;
    public Color getTextColor() { return textColor; }
    public void setTextColor(Color c) { textColor = c; }

    // --- public methods
    public int getIconWidth() {
	return getLabelButton().getIconText().length() 
	    * getLabelButton().getFont().getSize()
	    + getLabelButton().getMargin().left
	    + getLabelButton().getMargin().right;
    }
    public int getIconHeight() {
	return getLabelButton().getFont().getSize()
	    + getLabelButton().getMargin().top
	    + getLabelButton().getMargin().bottom;
    }

    public void paintIcon(Component c,
			  Graphics g,
			  int x,
			  int y) {
	Graphics2D g2 = (Graphics2D)g;
	FontRenderContext frc = g2.getFontRenderContext();
	Font f = getLabelButton().getFont();
	Rectangle2D bounds = 
	    f.getStringBounds(getLabelButton().getIconText(), frc);
	g2.setColor(getTextColor());
	g2.drawString(getLabelButton().getIconText(),
		      x, //+ (float)(getIconWidth()-bounds.getWidth())/2,
		      y + (int)(bounds.getHeight()));
    }

}
