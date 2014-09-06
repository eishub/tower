package towerenv;

/** 
 * Copyright (C) 2010 Koen V. Hindriks
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import edu.stanford.robotics.trTower.AnimationTimer;
import edu.stanford.robotics.trTower.Stimulator;
import edu.stanford.robotics.trTower.gui.ActionLabel;
import edu.stanford.robotics.trTower.gui.VirtualWorldPanel;
import edu.stanford.robotics.trTower.modelTower.ModelTower;
import edu.stanford.robotics.trTower.virtualWorld.VirtualWorld;

/**
 * See VirtualWorldRenderPanel to set preferred width and height of virtual
 * world panel.
 * 
 * @modified Koen V. Hindriks
 * @modified W.Pasman EIS 0.3 #1390
 * @modified W.Pasman #1618 remember win dimensions
 */
public class TowerEnvironment extends JFrame {

	private static final long serialVersionUID = 7245297479395128795L;

	VirtualWorld virtualWorld;
	TowerInterface environmentInterface;
	MyAnimationTimer animationTimer;

	/**
	 * Launches tower environment user interface.
	 */
	public TowerEnvironment(TowerInterface environmentInterface) {
		super("Tower Environment");
		setLocation(TowerSettings.getX(), TowerSettings.getY());
		this.environmentInterface = environmentInterface;
		setLayout(new BorderLayout());
		VirtualWorldPanel vwp = new VirtualWorldPanel();
		Stimulator stim = new Stimulator();
		virtualWorld = new VirtualWorld();
		vwp.setVirtualWorld(virtualWorld);

		// fix for TRAC 667: step button wants to see a "changed" bit in a model
		// tower.
		ModelTower mt = new ModelTower();
		mt.setChanged(true);
		vwp.setModelTower(mt);

		animationTimer = new MyAnimationTimer();

		// CHECK not sure whether following is needed.
		vwp.setStimulator(stim);
		animationTimer.setStimultor(stim);
		stim.addStimulatorListener(virtualWorld);
		vwp.setAnimationTimer(animationTimer);
		add(vwp, BorderLayout.CENTER);
		// re-using TRTowerApplet.getTopPanel() does not work,
		// because about box does not work because TRTower has not been
		// instantiated.
		// We have to hack the about box anyway and it's easier to make our own
		// top panel.
		add(getTopPanel(), BorderLayout.NORTH);

		add(getButtonPanel(), BorderLayout.SOUTH);
		pack();
		virtualWorld.setToBeReset(true);
		virtualWorld.stimuStep();
		animationTimer.startTimer();
		setVisible(true);
		addWindowListeners();
	}

	private void addWindowListeners() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentMoved(ComponentEvent e) {
				TowerSettings.setWindowParams(getX(), getY());
			}

			@Override
			public void componentResized(ComponentEvent e) {
				TowerSettings.setWindowParams(getX(), getY());
			}
		});
	}

	public void pause() {
		virtualWorld.setDurativeActionRunning(false);
	}

	public void start() {
		virtualWorld.setDurativeActionRunning(true);
	}

	/**
	 * Returns the virtual world object.
	 * 
	 * @return virtual world.
	 */
	public VirtualWorld getWorld() {
		return virtualWorld;
	}

	/**
	 * TODO
	 * 
	 * @return
	 */
	public JPanel getTopPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(getAboutActionLabel(), BorderLayout.EAST);
		return panel;
	}

	protected ActionLabel getAboutActionLabel() {
		ActionLabel aboutActionLabel;
		aboutActionLabel = new ActionLabel();
		aboutActionLabel.setText("About");
		aboutActionLabel.setFont(aboutActionLabel.getFont().deriveFont(
				Font.PLAIN));
		aboutActionLabel.setAction(new AbstractAction("About") {
			public void actionPerformed(ActionEvent ae) {
				showAbout();
			}
		});
		return aboutActionLabel;
	}

	/**
	 * Shows 'About' popup.
	 */
	public void showAbout() {
		JOptionPane.showMessageDialog(this, "TRTower\n\n" + "\n\n"
				+ "Written by Chris T. K. Ng\n" + "\n"
				+ "with Professor Nils J. Nilsson\n" + "Robotics Laboratory\n"
				+ "Department of Computer Science\n" + "Stanford University.\n"
				+ "\n" + "This program is distributed in\n"
				+ "the hope that it will be useful,\n"
				+ "but WITHOUT ANY WARRANTY.\n" + "\n"
				+ "Modified as GOAL plug-in\n "
				+ "by K.V. Hindriks 2010, W.Pasman 2009\n", "About TRTower",
				JOptionPane.INFORMATION_MESSAGE);
	}

	JPanel getButtonPanel() {
		JPanel bpanel = new JPanel(new BorderLayout());

		JButton newblock = new JButton("New Block");
		newblock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				virtualWorld.setToAddNewBlock(true);
			}
		});
		bpanel.add(newblock, BorderLayout.WEST);

		JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				virtualWorld.setToBeReset(true);
			}
		});
		bpanel.add(reset, BorderLayout.EAST);

		return bpanel;
	}

	/**
	 * Closes the user interface. Remembers the settings for next time we open.
	 */
	public void close() {
		TowerSettings.setWindowParams(getX(), getY());
		animationTimer.stopTimer();
		virtualWorld = null;
		animationTimer = null;
		environmentInterface = null;
		dispose();
		setVisible(false);
	}

}

/**
 * modified version to make getTimer function public, so that we can check if it
 * is running.
 * 
 * @author wouter
 * 
 */
class MyAnimationTimer extends AnimationTimer {
	public Timer getTimer() {
		return super.getTimer();
	}
}