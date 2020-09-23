package edu.stanford.robotics.trTower.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.stanford.robotics.trTower.AnimationTimer;
import edu.stanford.robotics.trTower.StimulatorListener;
import edu.stanford.robotics.trTower.modelTower.ModelTower;
import edu.stanford.robotics.trTower.virtualWorld.VirtualWorld;

public class AnimationControlPanel extends JPanel implements StimulatorListener {
	private static final long serialVersionUID = 1L;

	protected static final int hs = 10;
	protected static final int vs = 10;

	public AnimationControlPanel() {
		setLayout(new BorderLayout());
		add(getControlPanel());
	}

	private AnimationTimer animationTimer;

	public AnimationTimer getAnimationTimer() {
		return this.animationTimer;
	}

	public void setAnimationTimer(final AnimationTimer at) {
		this.animationTimer = at;
		this.animationTimer.setDelay(getSpeedSlider().getValue());
	}

	private VirtualWorld virtualWorld;

	public VirtualWorld getVirtualWorld() {
		return this.virtualWorld;
	}

	public void setVirtualWorld(final VirtualWorld vw) {
		this.virtualWorld = vw;
		resetButtonStates();
	}

	private ModelTower modelTower;

	public ModelTower getModelTower() {
		return this.modelTower;
	}

	public void setModelTower(final ModelTower mt) {
		this.modelTower = mt;
	}

	private boolean toStep = false;

	public boolean isToStep() {
		return this.toStep;
	}

	public void setToStep(final boolean s) {
		this.toStep = s;
	}

	class PlayGuiAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		PlayGuiAction() {
			super("Play", getPlayIcon());
		}

		@Override
		public void actionPerformed(final ActionEvent ae) {
			if (getVirtualWorld() != null) {
				getVirtualWorld().setDurativeActionRunning(true);
				setToStep(false);
				resetButtonStates();
			}
		}
	}

	private PlayGuiAction playGuiAction;

	protected PlayGuiAction getPlayGuiAction() {
		if (this.playGuiAction == null) {
			this.playGuiAction = new PlayGuiAction();
			this.playGuiAction.setEnabled(false);
		}
		return this.playGuiAction;
	}

	class PauseGuiAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		PauseGuiAction() {
			super("Pause", getPauseIcon());
		}

		@Override
		public void actionPerformed(final ActionEvent ae) {
			if (getVirtualWorld() != null) {
				getVirtualWorld().setDurativeActionRunning(false);
				setToStep(false);
				resetButtonStates();
			}
		}
	}

	private PauseGuiAction pauseGuiAction;

	protected PauseGuiAction getPauseGuiAction() {
		if (this.pauseGuiAction == null) {
			this.pauseGuiAction = new PauseGuiAction();
			this.pauseGuiAction.setEnabled(false);
		}
		return this.pauseGuiAction;
	}

	class StepGuiAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		StepGuiAction() {
			super("Step", getStepIcon());
		}

		@Override
		public void actionPerformed(final ActionEvent ae) {
			getVirtualWorld().setDurativeActionRunning(true);
			setToStep(true);
			resetButtonStates();
		}
	}

	private StepGuiAction stepGuiAction;

	protected StepGuiAction getStepGuiAction() {
		if (this.stepGuiAction == null) {
			this.stepGuiAction = new StepGuiAction();
			this.stepGuiAction.setEnabled(false);
		}
		return this.stepGuiAction;
	}

	protected void resetButtonStates() {
		if (getVirtualWorld() == null) {
			getPlayGuiAction().setEnabled(false);
			getPauseGuiAction().setEnabled(false);
			getStepGuiAction().setEnabled(false);
		} else {
			if (isToStep()) {
				getPlayGuiAction().setEnabled(true);
				getPauseGuiAction().setEnabled(true);
				getStepGuiAction().setEnabled(false);
				getAnimationStatusLabel().setMode(AnimationStatusLabel.STEPPING);
			} else {
				if (getVirtualWorld().isDurativeActionRunning()) {
					// playing
					getPlayGuiAction().setEnabled(false);
					getPauseGuiAction().setEnabled(true);
					getStepGuiAction().setEnabled(true);
					getAnimationStatusLabel().setMode(AnimationStatusLabel.PLAYING);
				} else {
					// paused
					getPlayGuiAction().setEnabled(true);
					getPauseGuiAction().setEnabled(false);
					getStepGuiAction().setEnabled(true);
					getAnimationStatusLabel().setMode(AnimationStatusLabel.PAUSED);
				}
			}
		}
	}

	@Override
	public void stimuStep() {
		if (isToStep()) {
			if (getModelTower().isChanged()) {
				getPauseButton().doClick();
			}
		}
	}

	private JPanel controlPanel;

	protected JPanel getControlPanel() {
		if (this.controlPanel == null) {
			this.controlPanel = new JPanel();
			this.controlPanel.setLayout(new GridBagLayout());
			final GridBagConstraints play = new GridBagConstraints();
			play.gridx = 0;
			play.gridy = 0;
			play.insets = new Insets(0, 8, 0, 2);
			this.controlPanel.add(getPlayButton(), play);
			final GridBagConstraints pause = new GridBagConstraints();
			pause.gridx = 1;
			pause.gridy = 0;
			pause.insets = new Insets(0, 2, 0, 2);
			this.controlPanel.add(getPauseButton(), pause);
			final GridBagConstraints step = new GridBagConstraints();
			step.gridx = 2;
			step.gridy = 0;
			step.insets = new Insets(0, 2, 0, 2);
			this.controlPanel.add(getStepButton(), step);
			final GridBagConstraints status = new GridBagConstraints();
			status.gridx = 3;
			status.gridy = 0;
			status.fill = GridBagConstraints.HORIZONTAL;
			status.weightx = 0.5;
			status.weighty = 1;
			this.controlPanel.add(getAnimationStatusLabel(), status);
			final GridBagConstraints speed = new GridBagConstraints();
			speed.gridx = 4;
			speed.gridy = 0;
			speed.weightx = 0.5;
			speed.fill = GridBagConstraints.HORIZONTAL;
			this.controlPanel.add(getSpeedSlider(), speed);
		}
		return this.controlPanel;
	}

	private JButton playButton;

	protected JButton getPlayButton() {
		if (this.playButton == null) {
			this.playButton = new JButton();
			this.playButton.setAction(getPlayGuiAction());
			this.playButton.setText(null);
			this.playButton.setDisabledIcon(getDisabledPlayIcon());
			this.playButton.setMargin(new Insets(4, 16, 4, 16));
			this.playButton.setToolTipText("Play");
		}
		return this.playButton;
	}

	private JButton pauseButton;

	protected JButton getPauseButton() {
		if (this.pauseButton == null) {
			this.pauseButton = new JButton();
			this.pauseButton.setAction(getPauseGuiAction());
			this.pauseButton.setText(null);
			this.pauseButton.setDisabledIcon(getDisabledPauseIcon());
			this.pauseButton.setMargin(new Insets(4, 8, 4, 8));
			this.pauseButton.setToolTipText("Pause");
		}
		return this.pauseButton;
	}

	private JButton stepButton;

	protected JButton getStepButton() {
		if (this.stepButton == null) {
			this.stepButton = new JButton();
			this.stepButton.setAction(getStepGuiAction());
			this.stepButton.setText(null);
			this.stepButton.setDisabledIcon(getDisabledStepIcon());
			this.stepButton.setMargin(new Insets(4, 8, 4, 8));
			this.stepButton.setToolTipText("Step (run until a primitive predicate changes)");
		}
		return this.stepButton;
	}

	private JSlider speedSlider;

	protected JSlider getSpeedSlider() {
		if (this.speedSlider == null) {
			this.speedSlider = new JSlider(1, 12, 6);
			this.speedSlider.setPreferredSize(new Dimension(50, 30));
			this.speedSlider.setMinimumSize(new Dimension(50, 30));
			this.speedSlider.setMajorTickSpacing(6);
			this.speedSlider.setMinorTickSpacing(1);
			this.speedSlider.setPaintTicks(true);
			this.speedSlider.setSnapToTicks(true);
			this.speedSlider.addChangeListener(getSpeedSliderListener());
			this.speedSlider.setToolTipText("Adjust speed of animation");
		}
		return this.speedSlider;
	}

	class SpeedSliderListener implements ChangeListener {
		@Override
		public void stateChanged(final ChangeEvent e) {
			final JSlider source = (JSlider) e.getSource();
			if (!source.getValueIsAdjusting()) {
				final int sliderValue = source.getValue();
				getAnimationTimer().setDelay(sliderValueToDelay(sliderValue));
			}
		}
	}

	private SpeedSliderListener speedSliderListener;

	protected SpeedSliderListener getSpeedSliderListener() {
		if (this.speedSliderListener == null) {
			this.speedSliderListener = new SpeedSliderListener();
		}
		return this.speedSliderListener;
	}

	protected static final int minSliderValue = 1;
	protected static final int sliderRange = 100;
	protected static final int maxSliderValue = minSliderValue + sliderRange - 1;

	protected int sliderValueToDelay(final int sliderValue) {
		switch (sliderValue) {
		case 12:
			return 0;
		case 11:
			return 1;
		case 10:
			return 2;
		case 9:
			return 3;
		case 8:
			return 4;
		case 7:
			return 6;
		case 6:
			return 10;
		case 5:
			return 18;
		case 4:
			return 26;
		case 3:
			return 34;
		case 2:
			return 50;
		default:
			return 66;
		}
	}

	class PlayIcon implements Icon {
		private final AffineTransform at = new AffineTransform();
		private final Polygon triangle = new Polygon();
		protected Paint paint = Color.black;

		public PlayIcon() {
			this.triangle.addPoint(0, 0);
			this.triangle.addPoint(0, getIconHeight());
			this.triangle.addPoint(getIconWidth(), Math.round(getIconHeight() / 2));
		}

		@Override
		public int getIconWidth() {
			return 16;
		}

		@Override
		public int getIconHeight() {
			return 16;
		}

		@Override
		public void paintIcon(final Component c, final Graphics g, final int x, final int y) {
			final Graphics2D g2 = (Graphics2D) g;
			g2.setPaint(this.paint);
			this.at.setToTranslation(x, y);
			final Shape shape = this.at.createTransformedShape(this.triangle);
			g2.fill(shape);
		}
	}

	class DisabledPlayIcon extends PlayIcon {
		public DisabledPlayIcon() {
			this.paint = Color.gray;
		}
	}

	class PauseIcon implements Icon {
		private final AffineTransform at = new AffineTransform();
		private final double stepBlockThicknessFactor = 0.375;
		protected Paint paint = Color.black;
		private final Rectangle2D rectangle;

		public PauseIcon() {
			this.rectangle = new Rectangle2D.Double(0, 0, getIconWidth() * this.stepBlockThicknessFactor,
					getIconHeight());
		}

		@Override
		public int getIconWidth() {
			return 16;
		}

		@Override
		public int getIconHeight() {
			return 16;
		}

		@Override
		public void paintIcon(final Component c, final Graphics g, final int x, final int y) {
			final Graphics2D g2 = (Graphics2D) g;
			g2.setPaint(this.paint);
			this.at.setToTranslation(x, y);
			Shape shape = this.at.createTransformedShape(this.rectangle);
			g2.fill(shape);
			this.at.setToTranslation(getIconWidth() * (1 - this.stepBlockThicknessFactor), 0);
			shape = this.at.createTransformedShape(shape);
			g2.fill(shape);
		}
	}

	class DisabledPauseIcon extends PauseIcon {
		public DisabledPauseIcon() {
			this.paint = Color.gray;
		}
	}

	class StepIcon implements Icon {
		private final AffineTransform at = new AffineTransform();
		protected Paint paint = Color.black;
		private final Polygon triangle = new Polygon();
		private final Rectangle2D rectangle;
		private final double stepLineThicknessFraction = 0.125;

		public StepIcon() {
			this.triangle.addPoint(0, 0);
			this.triangle.addPoint(0, getIconHeight());
			this.triangle.addPoint((int) Math.round(getIconWidth() * (1 - this.stepLineThicknessFraction)),
					Math.round(getIconHeight() / 2));
			this.rectangle = new Rectangle2D.Double(getIconWidth() * (1 - this.stepLineThicknessFraction), 0,
					getIconWidth() * this.stepLineThicknessFraction, getIconHeight());
		}

		@Override
		public int getIconWidth() {
			return 16;
		}

		@Override
		public int getIconHeight() {
			return 16;
		}

		@Override
		public void paintIcon(final Component c, final Graphics g, final int x, final int y) {
			final Graphics2D g2 = (Graphics2D) g;
			g2.setPaint(this.paint);
			this.at.setToTranslation(x, y);
			Shape shape = this.at.createTransformedShape(this.triangle);
			g2.fill(shape);
			shape = this.at.createTransformedShape(this.rectangle);
			g2.fill(shape);
		}
	}

	class DisabledStepIcon extends StepIcon {
		public DisabledStepIcon() {
			this.paint = Color.gray;
		}
	}

	private Icon playIcon;

	protected Icon getPlayIcon() {
		if (this.playIcon == null) {
			this.playIcon = new PlayIcon();
		}
		return this.playIcon;
	}

	private Icon disabledPlayIcon;

	protected Icon getDisabledPlayIcon() {
		if (this.disabledPlayIcon == null) {
			this.disabledPlayIcon = new DisabledPlayIcon();
		}
		return this.disabledPlayIcon;
	}

	private Icon pauseIcon;

	protected Icon getPauseIcon() {
		if (this.pauseIcon == null) {
			this.pauseIcon = new PauseIcon();
		}
		return this.pauseIcon;
	}

	private Icon disabledPauseIcon;

	protected Icon getDisabledPauseIcon() {
		if (this.disabledPauseIcon == null) {
			this.disabledPauseIcon = new DisabledPauseIcon();
		}
		return this.disabledPauseIcon;
	}

	private Icon stepIcon;

	protected Icon getStepIcon() {
		if (this.stepIcon == null) {
			this.stepIcon = new StepIcon();
		}
		return this.stepIcon;
	}

	private Icon disabledStepIcon;

	protected Icon getDisabledStepIcon() {
		if (this.disabledStepIcon == null) {
			this.disabledStepIcon = new DisabledStepIcon();
		}
		return this.disabledStepIcon;
	}

	private AnimationStatusLabel animationStatusLabel;

	protected AnimationStatusLabel getAnimationStatusLabel() {
		if (this.animationStatusLabel == null) {
			this.animationStatusLabel = new AnimationStatusLabel();
		}
		return this.animationStatusLabel;
	}
}
