package edu.stanford.robotics.trTower.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import edu.stanford.robotics.trTower.*;
import edu.stanford.robotics.trTower.virtualWorld.*;

public class DurativeArmActionStatusMessageLabel 
    extends JLabel implements StimulatorListener {

    public DurativeArmActionStatusMessageLabel() {
	setFont(getFont().deriveFont(Font.PLAIN));
	setText(" ");
	Border empty = BorderFactory.createEmptyBorder(2, 2, 2, 2);
	Border bevel = new SoftBevelBorder(SoftBevelBorder.LOWERED);
	setBorder(BorderFactory.createCompoundBorder(bevel, empty));
    }

    // --- attributes
    private VirtualWorld virtualWorld;
    public VirtualWorld getVirtualWorld() { return virtualWorld; }
    public void setVirtualWorld(VirtualWorld vw) { virtualWorld = vw; }

    
    // --- public methods
    public void stimuStep() {
	String s = " " + getVirtualWorld().getDurativeArmActionStatusMessage();
	if (!getText().equals(s))
	    setText(s);
    }

}
