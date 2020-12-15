package edu.stanford.robotics.trTower.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class LabelButton extends JButton {
	private static final long serialVersionUID = 1L;

	public LabelButton() {
		final JLabel dummyLabel = new JLabel();
		setForeground(dummyLabel.getForeground().darker().darker());
		setBorderPainted(false);
		final ButtonIcon buttonIcon = new ButtonIcon();
		buttonIcon.setLabelButton(this);
		setIcon(buttonIcon);
		setPressedIcon(buttonIcon);
		final RolloverButtonIcon rolloverButtonIcon = new RolloverButtonIcon();
		rolloverButtonIcon.setLabelButton(this);
		setRolloverIcon(rolloverButtonIcon);
		setRolloverEnabled(true);
	}

	private Color rolloverColor = Color.white;

	public Color getRolloverColor() {
		return this.rolloverColor;
	}

	public void setRolloverColor(final Color c) {
		this.rolloverColor = c;
	}

	private String iconText;

	public String getIconText() {
		return this.iconText;
	}

	public void setIconText(final String t) {
		this.iconText = t;
	}
}

class RolloverButtonIcon extends ButtonIcon {
	public RolloverButtonIcon() {
		setTextColor(Color.white);
	}

	@Override
	void setLabelButton(final LabelButton b) {
		super.setLabelButton(b);
		setTextColor(Color.white);
	}
}

class ButtonIcon implements Icon {
	private LabelButton labelButton;

	LabelButton getLabelButton() {
		return this.labelButton;
	}

	void setLabelButton(final LabelButton b) {
		this.labelButton = b;
		setTextColor(this.labelButton.getForeground());
	}

	private Color textColor = Color.black;

	public Color getTextColor() {
		return this.textColor;
	}

	public void setTextColor(final Color c) {
		this.textColor = c;
	}

	@Override
	public int getIconWidth() {
		return getLabelButton().getIconText().length() * getLabelButton().getFont().getSize()
				+ getLabelButton().getMargin().left + getLabelButton().getMargin().right;
	}

	@Override
	public int getIconHeight() {
		return getLabelButton().getFont().getSize() + getLabelButton().getMargin().top
				+ getLabelButton().getMargin().bottom;
	}

	@Override
	public void paintIcon(final Component c, final Graphics g, final int x, final int y) {
		final Graphics2D g2 = (Graphics2D) g;
		final FontRenderContext frc = g2.getFontRenderContext();
		final Font f = getLabelButton().getFont();
		final Rectangle2D bounds = f.getStringBounds(getLabelButton().getIconText(), frc);
		g2.setColor(getTextColor());
		g2.drawString(getLabelButton().getIconText(), x, y + (int) (bounds.getHeight()));
	}
}
