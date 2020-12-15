package edu.stanford.robotics.trTower.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.SoftBevelBorder;

import edu.stanford.robotics.trTower.AnimationTimer;
import edu.stanford.robotics.trTower.Stimulator;
import edu.stanford.robotics.trTower.modelTower.ModelTower;
import edu.stanford.robotics.trTower.virtualWorld.VirtualWorld;

public class VirtualWorldPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public VirtualWorldPanel() {
		final Border empty = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		final Border bevel = new SoftBevelBorder(BevelBorder.LOWERED);
		setBorder(BorderFactory.createCompoundBorder(bevel, empty));
		setLayout(new GridBagLayout());
		final GridBagConstraints title = new GridBagConstraints();
		title.gridx = 0;
		title.gridy = 0;
		title.weighty = 0.5;
		title.anchor = GridBagConstraints.NORTHWEST;
		add(getVirtualWorldActionLabel(), title);
		final GridBagConstraints applet = new GridBagConstraints();
		applet.gridx = 0;
		applet.gridy = 1;
		add(getVirtualWorldRenderPanel(), applet);
		final GridBagConstraints anim = new GridBagConstraints();
		anim.gridx = 0;
		anim.gridy = 2;
		anim.anchor = GridBagConstraints.SOUTH;
		anim.fill = GridBagConstraints.HORIZONTAL;
		anim.weightx = 1;
		anim.weighty = 0.5;
		add(getAnimationControlPanel(), anim);
	}

	private VirtualWorld virtualWorld;

	public VirtualWorld getVirtualWorld() {
		return this.virtualWorld;
	}

	public void setVirtualWorld(final VirtualWorld vw) {
		this.virtualWorld = vw;
		getVirtualWorldRenderPanel().setVirtualWorld(this.virtualWorld);
		getAnimationControlPanel().setVirtualWorld(this.virtualWorld);
	}

	private ModelTower modelTower;

	public ModelTower getModelTower() {
		return this.modelTower;
	}

	public void setModelTower(final ModelTower mt) {
		this.modelTower = mt;
		getAnimationControlPanel().setModelTower(this.modelTower);
	}

	private Stimulator stimulator;

	public Stimulator getStimulator() {
		return this.stimulator;
	}

	public void setStimulator(final Stimulator s) {
		this.stimulator = s;
		if (this.stimulator != null) {
			this.stimulator.addStimulatorListener(getVirtualWorldRenderPanel());
			this.stimulator.addStimulatorListener(getAnimationControlPanel());
		}
	}

	private AnimationTimer animationTimer;

	public AnimationTimer getAnimationTimer() {
		return this.animationTimer;
	}

	public void setAnimationTimer(final AnimationTimer at) {
		this.animationTimer = at;
		getAnimationControlPanel().setAnimationTimer(this.animationTimer);
	}

	private ActionLabel virtualWorldActionLabel;

	protected ActionLabel getVirtualWorldActionLabel() {
		if (this.virtualWorldActionLabel == null) {
			this.virtualWorldActionLabel = new ActionLabel();
			this.virtualWorldActionLabel.setText("Environment");
			this.virtualWorldActionLabel.setAction(new AbstractAction("Environment") {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(final ActionEvent ae) {
					/*
					 * Disabled W.Pasman 3may10, #706 boolean useBuiltInHelpBrowser =
					 * (ae.getModifiers()&ActionEvent.SHIFT_MASK)==0? false : true;
					 * TRTower.getInstance().showHelp(useBuiltInHelpBrowser, "Environment",
					 * "help/Environment.html");
					 */
				}
			});
		}
		return this.virtualWorldActionLabel;
	}

	private AnimationControlPanel animationControlPanel;

	protected AnimationControlPanel getAnimationControlPanel() {
		if (this.animationControlPanel == null) {
			this.animationControlPanel = new AnimationControlPanel();
		}
		return this.animationControlPanel;
	}

	private VirtualWorldRenderPanel virtualWorldRenderPanel;

	protected VirtualWorldRenderPanel getVirtualWorldRenderPanel() {
		if (this.virtualWorldRenderPanel == null) {
			this.virtualWorldRenderPanel = new VirtualWorldRenderPanel();
		}
		return this.virtualWorldRenderPanel;
	}
}
