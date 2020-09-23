package edu.stanford.robotics.trTower.gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.SoftBevelBorder;

import edu.stanford.robotics.trTower.TRTower;
import edu.stanford.robotics.trTower.TRWorld;

@SuppressWarnings("deprecation")
public class TRTowerApplet extends JApplet {
	private static final long serialVersionUID = 1L;
	protected static final int hs = 10;
	protected static final int vs = 10;

	public TRTowerApplet() {
		connect();
		setContentPane(getAppPanel());
	}

	private JPanel appPanel;

	protected JPanel getAppPanel() {
		if (this.appPanel == null) {
			this.appPanel = new JPanel();
			this.appPanel.setLayout(new GridBagLayout());
			final int ipadx = 0;
			final int ipady = 0;
			final Insets insets = new Insets(0, 0, 0, 0);
			final GridBagConstraints top = new GridBagConstraints();
			top.gridx = 0;
			top.gridy = 0;
			top.gridwidth = 3;
			top.fill = GridBagConstraints.HORIZONTAL;
			top.ipadx = ipadx;
			top.ipady = ipady;
			top.insets = insets;
			this.appPanel.add(getTopPanel(), top);
			final GridBagConstraints actionTower = new GridBagConstraints();
			actionTower.gridx = 0;
			actionTower.gridy = 1;
			actionTower.weightx = 0.34;
			// actionTower.weighty = 0.5;
			actionTower.fill = GridBagConstraints.BOTH;
			actionTower.ipadx = ipadx;
			actionTower.ipady = ipady;
			actionTower.insets = insets;
			this.appPanel.add(getActionTowerPanel(), actionTower);
			final GridBagConstraints virtualWorld = new GridBagConstraints();
			virtualWorld.gridx = 1;
			virtualWorld.gridy = 1;
			virtualWorld.weightx = 0.33;
			virtualWorld.fill = GridBagConstraints.BOTH;
			virtualWorld.ipadx = ipadx;
			virtualWorld.ipady = ipady;
			virtualWorld.insets = insets;
			this.appPanel.add(getVirtualWorldPanel(), virtualWorld);

			final GridBagConstraints durativeAction = new GridBagConstraints();
			durativeAction.gridx = 2;
			durativeAction.gridy = 1;
			durativeAction.weightx = 0.33;
			durativeAction.fill = GridBagConstraints.BOTH;
			durativeAction.ipadx = ipadx;
			durativeAction.ipady = ipady;
			durativeAction.insets = insets;
			this.appPanel.add(getDurativeActionPanel(), durativeAction);

			final GridBagConstraints perceptionModel = new GridBagConstraints();
			perceptionModel.gridx = 0;
			perceptionModel.gridy = 2;
			perceptionModel.gridwidth = 3;
			perceptionModel.weighty = 1;
			perceptionModel.fill = GridBagConstraints.BOTH;
			perceptionModel.ipadx = ipadx;
			perceptionModel.ipady = ipady;
			perceptionModel.insets = insets;
			this.appPanel.add(getPerceptionModelTowerPanel(), perceptionModel);

			final GridBagConstraints bottom = new GridBagConstraints();
			bottom.gridx = 0;
			bottom.gridy = 3;
			bottom.gridwidth = 3;
			bottom.fill = GridBagConstraints.HORIZONTAL;
			bottom.ipadx = ipadx;
			bottom.ipady = ipady;
			bottom.insets = insets;
			this.appPanel.add(getBottomPanel(), bottom);
		}
		return this.appPanel;
	}

	private JPanel topPanel;

	// Wouter: quick hack, to get this panel
	public JPanel getTopPanel() {
		if (this.topPanel == null) {
			this.topPanel = new JPanel();
			this.topPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			this.topPanel.setLayout(new GridBagLayout());
			final GridBagConstraints trTower = new GridBagConstraints();
			trTower.gridx = 0;
			trTower.gridy = 0;
			trTower.anchor = GridBagConstraints.WEST;
			trTower.weightx = 1;
			trTower.weighty = 1;
			this.topPanel.add(getTRTowerActionLabel(), trTower);
			final GridBagConstraints about = new GridBagConstraints();
			about.gridx = 1;
			about.gridy = 0;
			about.insets = new Insets(0, 0, 0, 10);
			about.anchor = GridBagConstraints.EAST;
			this.topPanel.add(getAboutActionLabel(), about);
		}
		return this.topPanel;
	}

	private ActionLabel trTowerActionLabel;

	protected ActionLabel getTRTowerActionLabel() {
		if (this.trTowerActionLabel == null) {
			this.trTowerActionLabel = new ActionLabel();
			this.trTowerActionLabel.setText("Teleo-Reactive Tower Builder");
			this.trTowerActionLabel.setFont(this.trTowerActionLabel.getFont().deriveFont(Font.ITALIC, 18));
			this.trTowerActionLabel.setAction(new AbstractAction("TRTower") {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(final ActionEvent ae) {
					final boolean useBuiltInHelpBrowser = (ae.getModifiers() & ActionEvent.SHIFT_MASK) == 0 ? false
							: true;
					TRTower.getInstance().showHelp(useBuiltInHelpBrowser, "TR Tower", "help/TRTowerBuilder.html");
				}
			});
		}
		return this.trTowerActionLabel;
	}

	private ActionLabel aboutActionLabel;

	protected ActionLabel getAboutActionLabel() {
		if (this.aboutActionLabel == null) {
			this.aboutActionLabel = new ActionLabel();
			this.aboutActionLabel.setText("About");
			this.aboutActionLabel.setFont(this.aboutActionLabel.getFont().deriveFont(Font.PLAIN));
			this.aboutActionLabel.setAction(new AbstractAction("About") {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(final ActionEvent ae) {
					TRTower.getInstance().showAbout();
				}
			});
		}
		return this.aboutActionLabel;
	}

	private TRWorld trWorld;

	protected TRWorld getTRWorld() {
		if (this.trWorld == null) {
			this.trWorld = new TRWorld();
		}
		return this.trWorld;
	}

	private ActionTowerPanel actionTowerPanel;

	protected ActionTowerPanel getActionTowerPanel() {
		if (this.actionTowerPanel == null) {
			this.actionTowerPanel = new ActionTowerPanel();
			this.actionTowerPanel.setActionTower(getTRWorld().getActionTower());
			getTRWorld().getStimulator().addStimulatorListener(this.actionTowerPanel);

		}
		return this.actionTowerPanel;
	}

	private VirtualWorldPanel virtualWorldPanel;

	protected VirtualWorldPanel getVirtualWorldPanel() {
		if (this.virtualWorldPanel == null) {
			this.virtualWorldPanel = new VirtualWorldPanel();
			this.virtualWorldPanel.setVirtualWorld(getTRWorld().getVirtualWorld());
			this.virtualWorldPanel.setModelTower(getTRWorld().getModelTower());
			this.virtualWorldPanel.setStimulator(getTRWorld().getStimulator());
			this.virtualWorldPanel.setAnimationTimer(getTRWorld().getAnimationTimer());
		}
		return this.virtualWorldPanel;
	}

	private TRCallPanel trCallPanel;

	protected TRCallPanel getTRCallPanel() {
		if (this.trCallPanel == null) {
			this.trCallPanel = new TRCallPanel();
			this.trCallPanel.setActionTower(getTRWorld().getActionTower());
		}
		return this.trCallPanel;
	}

	private StimulatorPanel stimulatorPanel;

	protected StimulatorPanel getStimulatorPanel() {
		if (this.stimulatorPanel == null) {
			this.stimulatorPanel = new StimulatorPanel();
			this.stimulatorPanel.setStimulator(getTRWorld().getStimulator());

		}
		return this.stimulatorPanel;
	}

	private JPanel bottomPanel;

	protected JPanel getBottomPanel() {
		if (this.bottomPanel == null) {
			this.bottomPanel = new JPanel();
			this.bottomPanel.setLayout(new GridLayout(1, 2, 5, 5));
			this.bottomPanel.add(getMessageLabel());
			this.bottomPanel.add(getDurativeArmActionStatusMessageLabel());
		}
		return this.bottomPanel;
	}

	private MessageLabel messageLabel;

	protected MessageLabel getMessageLabel() {
		if (this.messageLabel == null) {
			this.messageLabel = new MessageLabel();
		}
		return this.messageLabel;
	}

	private DurativeArmActionStatusMessageLabel durativeArmActionStatusMessageLabel;

	protected DurativeArmActionStatusMessageLabel getDurativeArmActionStatusMessageLabel() {
		if (this.durativeArmActionStatusMessageLabel == null) {
			this.durativeArmActionStatusMessageLabel = new DurativeArmActionStatusMessageLabel();
			this.durativeArmActionStatusMessageLabel.setVirtualWorld(getTRWorld().getVirtualWorld());
			getTRWorld().getStimulator().addStimulatorListener(this.durativeArmActionStatusMessageLabel);
		}
		return this.durativeArmActionStatusMessageLabel;
	}

	private PerceptionModelTowerPanel perceptionModelTowerPanel;

	protected PerceptionModelTowerPanel getPerceptionModelTowerPanel() {
		if (this.perceptionModelTowerPanel == null) {
			this.perceptionModelTowerPanel = new PerceptionModelTowerPanel();
			this.perceptionModelTowerPanel.setModelTower(getTRWorld().getModelTower());
			getTRWorld().getStimulator().addStimulatorListener(this.perceptionModelTowerPanel);
		}
		return this.perceptionModelTowerPanel;
	}

	private JPanel durativeActionPanel;

	protected JPanel getDurativeActionPanel() {
		if (this.durativeActionPanel == null) {
			this.durativeActionPanel = new JPanel();
			final Border empty = BorderFactory.createEmptyBorder(5, 5, 5, 5);
			final Border bevel = new SoftBevelBorder(BevelBorder.LOWERED);
			this.durativeActionPanel.setBorder(BorderFactory.createCompoundBorder(bevel, empty));
			this.durativeActionPanel.setLayout(new GridBagLayout());
			final GridBagConstraints trCall = new GridBagConstraints();
			trCall.gridx = 0;
			trCall.gridy = 0;
			trCall.fill = GridBagConstraints.BOTH;
			trCall.weightx = 1;
			trCall.weighty = 1;
			this.durativeActionPanel.add(getTRCallPanel(), trCall);
			final GridBagConstraints control = new GridBagConstraints();
			control.gridx = 0;
			control.gridy = 1;
			control.insets = new Insets(10, 0, 0, 0);
			control.fill = GridBagConstraints.HORIZONTAL;
			this.durativeActionPanel.add(getControlPanel(), control);
		}
		return this.durativeActionPanel;
	}

	private ControlPanel controlPanel;

	protected ControlPanel getControlPanel() {
		if (this.controlPanel == null) {
			this.controlPanel = new ControlPanel();
			this.controlPanel.setVirtualWorld(getTRWorld().getVirtualWorld());
			this.controlPanel.setModelTower(getTRWorld().getModelTower());
			this.controlPanel.setPerceptionTower(getTRWorld().getPerceptionTower());
			this.controlPanel.setActionTower(getTRWorld().getActionTower());
		}
		return this.controlPanel;
	}

	private void connect() {
		getControlPanel().setMessageLabel(getMessageLabel());
		getTRCallPanel().setMessageLabel(getMessageLabel());
	}

	@Override
	public void start() {
		getTRWorld().getAnimationTimer().startTimer();
	}

	@Override
	public void stop() {
		getTRWorld().getAnimationTimer().stopTimer();
	}

	@Override
	public void init() {
		getTRWorld().getAnimationTimer().startTimer();
	}

	@Override
	public void destroy() {
	}
}
