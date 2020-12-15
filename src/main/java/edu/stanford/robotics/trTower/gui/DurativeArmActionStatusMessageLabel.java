package edu.stanford.robotics.trTower.gui;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.SoftBevelBorder;

import edu.stanford.robotics.trTower.StimulatorListener;
import edu.stanford.robotics.trTower.virtualWorld.VirtualWorld;

public class DurativeArmActionStatusMessageLabel extends JLabel implements StimulatorListener {
	private static final long serialVersionUID = 1L;

	public DurativeArmActionStatusMessageLabel() {
		setFont(getFont().deriveFont(Font.PLAIN));
		setText(" ");
		final Border empty = BorderFactory.createEmptyBorder(2, 2, 2, 2);
		final Border bevel = new SoftBevelBorder(BevelBorder.LOWERED);
		setBorder(BorderFactory.createCompoundBorder(bevel, empty));
	}

	private VirtualWorld virtualWorld;

	public VirtualWorld getVirtualWorld() {
		return this.virtualWorld;
	}

	public void setVirtualWorld(final VirtualWorld vw) {
		this.virtualWorld = vw;
	}

	@Override
	public void stimuStep() {
		final String s = " " + getVirtualWorld().getDurativeArmActionStatusMessage();
		if (!getText().equals(s)) {
			setText(s);
		}
	}
}
