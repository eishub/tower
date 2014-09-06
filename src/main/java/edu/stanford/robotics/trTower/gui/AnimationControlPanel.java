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

	protected static final int hs = 10;
	protected static final int vs = 10;

	public AnimationControlPanel() {
		setLayout(new BorderLayout());
		// add(getControlBox());
		add(getControlPanel());
	}

	// --- attributes
	// private AnimatorApplet animatorApplet;
	// public AnimatorApplet getAnimatorApplet() { return animatorApplet; }
	// public void setAnimatorApplet(AnimatorApplet a) {
	// animatorApplet = a;
	// getSpeedSlider().setValue(delayToSliderValue(a.getThreadSleepInterval()));
	// // getSpeedSlider().asetValue(80);

	// // init children
	// resetButtonStates();
	// }

	private AnimationTimer animationTimer;

	public AnimationTimer getAnimationTimer() {
		return animationTimer;
	}

	public void setAnimationTimer(AnimationTimer at) {
		animationTimer = at;
		// getSpeedSlider().setValue(delayToSliderValue(animationTimer.getDelay()));
		animationTimer.setDelay(getSpeedSlider().getValue());
	}

	private VirtualWorld virtualWorld;

	public VirtualWorld getVirtualWorld() {
		return virtualWorld;
	}

	public void setVirtualWorld(VirtualWorld vw) {
		virtualWorld = vw;
		resetButtonStates();
	}

	private ModelTower modelTower;

	public ModelTower getModelTower() {
		return modelTower;
	}

	public void setModelTower(ModelTower mt) {
		modelTower = mt;
	}

	private boolean toStep = false;

	public boolean isToStep() {
		return toStep;
	}

	public void setToStep(boolean s) {
		toStep = s;
	}

	// --- GUI actions

	class PlayGuiAction extends AbstractAction {
		PlayGuiAction() {
			super("Play", getPlayIcon());
		}

		public void actionPerformed(ActionEvent ae) {
			// getAnimatorApplet().start();
			if (getVirtualWorld() != null) {
				getVirtualWorld().setDurativeActionRunning(true);
				setToStep(false);
				resetButtonStates();
			}
		}
	}

	private PlayGuiAction playGuiAction;

	protected PlayGuiAction getPlayGuiAction() {
		if (playGuiAction == null) {
			playGuiAction = new PlayGuiAction();
			playGuiAction.setEnabled(false);
		}
		return playGuiAction;
	}

	class PauseGuiAction extends AbstractAction {
		PauseGuiAction() {
			super("Pause", getPauseIcon());
		}

		public void actionPerformed(ActionEvent ae) {
			// getAnimatorApplet().stop();
			if (getVirtualWorld() != null) {
				getVirtualWorld().setDurativeActionRunning(false);
				setToStep(false);
				resetButtonStates();
			}
		}
	}

	private PauseGuiAction pauseGuiAction;

	protected PauseGuiAction getPauseGuiAction() {
		if (pauseGuiAction == null) {
			pauseGuiAction = new PauseGuiAction();
			pauseGuiAction.setEnabled(false);
		}
		return pauseGuiAction;
	}

	class StepGuiAction extends AbstractAction {
		StepGuiAction() {
			super("Step", getStepIcon());
		}

		public void actionPerformed(ActionEvent ae) {
			getVirtualWorld().setDurativeActionRunning(true);
			setToStep(true);
			resetButtonStates();
		}
	}

	private StepGuiAction stepGuiAction;

	protected StepGuiAction getStepGuiAction() {
		if (stepGuiAction == null) {
			stepGuiAction = new StepGuiAction();
			stepGuiAction.setEnabled(false);
		}
		return stepGuiAction;
	}

	// --- helpers
	// protected void resetButtonStates() {
	// if (getAnimatorApplet().isRunning()) {
	// getPlayGuiAction().setEnabled(false);
	// getPauseGuiAction().setEnabled(true);
	// } else {
	// getPlayGuiAction().setEnabled(true);
	// getPauseGuiAction().setEnabled(false);
	// }
	// }

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
				getAnimationStatusLabel()
						.setMode(AnimationStatusLabel.STEPPING);
			} else {
				if (getVirtualWorld().isDurativeActionRunning()) {
					// playing
					getPlayGuiAction().setEnabled(false);
					getPauseGuiAction().setEnabled(true);
					getStepGuiAction().setEnabled(true);
					getAnimationStatusLabel().setMode(
							AnimationStatusLabel.PLAYING);
				} else {
					// paused
					getPlayGuiAction().setEnabled(true);
					getPauseGuiAction().setEnabled(false);
					getStepGuiAction().setEnabled(true);
					getAnimationStatusLabel().setMode(
							AnimationStatusLabel.PAUSED);
				}
			}
		}
	}

	public void stimuStep() {
		if (isToStep()) {
			if (getModelTower().isChanged()) {
				getPauseButton().doClick();
			}
		}
	}

	// --- components
	private JPanel controlPanel;

	protected JPanel getControlPanel() {
		if (controlPanel == null) {
			controlPanel = new JPanel();
			controlPanel.setLayout(new GridBagLayout());
			GridBagConstraints play = new GridBagConstraints();
			play.gridx = 0;
			play.gridy = 0;
			play.insets = new Insets(0, 8, 0, 2);
			controlPanel.add(getPlayButton(), play);
			GridBagConstraints pause = new GridBagConstraints();
			pause.gridx = 1;
			pause.gridy = 0;
			pause.insets = new Insets(0, 2, 0, 2);
			controlPanel.add(getPauseButton(), pause);
			GridBagConstraints step = new GridBagConstraints();
			step.gridx = 2;
			step.gridy = 0;
			step.insets = new Insets(0, 2, 0, 2);
			controlPanel.add(getStepButton(), step);
			GridBagConstraints status = new GridBagConstraints();
			status.gridx = 3;
			status.gridy = 0;
			status.fill = GridBagConstraints.HORIZONTAL;
			status.weightx = 0.5;
			status.weighty = 1;
			controlPanel.add(getAnimationStatusLabel(), status);
			GridBagConstraints speed = new GridBagConstraints();
			speed.gridx = 4;
			speed.gridy = 0;
			speed.weightx = 0.5;
			speed.fill = GridBagConstraints.HORIZONTAL;
			controlPanel.add(getSpeedSlider(), speed);
		}
		return controlPanel;
	}

	// private Box controlBox;
	// protected Box getControlBox() {
	// if (controlBox == null) {
	// controlBox = Box.createHorizontalBox();
	// controlBox.add(Box.createRigidArea(new Dimension(hs, 0)));
	// controlBox.add(getPlayButton());
	// controlBox.add(Box.createRigidArea(new Dimension(hs, 0)));
	// controlBox.add(getPauseButton());
	// controlBox.add(Box.createHorizontalGlue());
	// controlBox.add(getSpeedSlider());
	// controlBox.add(Box.createRigidArea(new Dimension(hs, 0)));
	// }
	// return controlBox;
	// }

	private JButton playButton;

	protected JButton getPlayButton() {
		if (playButton == null) {
			playButton = new JButton();
			playButton.setAction(getPlayGuiAction());
			playButton.setText(null);
			playButton.setDisabledIcon(getDisabledPlayIcon());
			playButton.setMargin(new Insets(4, 16, 4, 16));
			playButton.setToolTipText("Play");
		}
		return playButton;
	}

	private JButton pauseButton;

	protected JButton getPauseButton() {
		if (pauseButton == null) {
			pauseButton = new JButton();
			pauseButton.setAction(getPauseGuiAction());
			pauseButton.setText(null);
			pauseButton.setDisabledIcon(getDisabledPauseIcon());
			pauseButton.setMargin(new Insets(4, 8, 4, 8));
			pauseButton.setToolTipText("Pause");
		}
		return pauseButton;
	}

	private JButton stepButton;

	protected JButton getStepButton() {
		if (stepButton == null) {
			stepButton = new JButton();
			stepButton.setAction(getStepGuiAction());
			stepButton.setText(null);
			stepButton.setDisabledIcon(getDisabledStepIcon());
			stepButton.setMargin(new Insets(4, 8, 4, 8));
			stepButton
					.setToolTipText("Step (run until a primitive predicate changes)");
		}
		return stepButton;
	}

	private JSlider speedSlider;

	protected JSlider getSpeedSlider() {
		if (speedSlider == null) {
			// speedSlider = new JSlider(minSliderValue, maxSliderValue);
			speedSlider = new JSlider(1, 12, 6);
			speedSlider.setPreferredSize(new Dimension(50, 30));
			speedSlider.setMinimumSize(new Dimension(50, 30));
			speedSlider.setMajorTickSpacing(6);
			speedSlider.setMinorTickSpacing(1);
			speedSlider.setPaintTicks(true);
			speedSlider.setSnapToTicks(true);
			speedSlider.addChangeListener(getSpeedSliderListener());
			speedSlider.setToolTipText("Adjust speed of animation");
		}
		return speedSlider;
	}

	class SpeedSliderListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider) e.getSource();
			if (!source.getValueIsAdjusting()) {
				int sliderValue = source.getValue();
				getAnimationTimer().setDelay(sliderValueToDelay(sliderValue));
			}
		}
	}

	private SpeedSliderListener speedSliderListener;

	protected SpeedSliderListener getSpeedSliderListener() {
		if (speedSliderListener == null) {
			speedSliderListener = new SpeedSliderListener();
		}
		return speedSliderListener;
	}

	// --- helper
	protected static final int minSliderValue = 1;
	protected static final int sliderRange = 100;
	protected static final int maxSliderValue = minSliderValue + sliderRange
			- 1;

	// protected long sliderValueToDelay(int sliderValue) {
	// return Math.round((double)sliderRange/sliderValue) - 1;
	// }
	protected int sliderValueToDelay(int sliderValue) {
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

	// protected int delayToSliderValue(long delay) {
	// return (int)Math.round((double)sliderRange / (delay + 1));
	// }

	class PlayIcon implements Icon {

		private AffineTransform at = new AffineTransform();
		private Polygon triangle = new Polygon();

		protected Paint paint = Color.black;

		public PlayIcon() {
			triangle.addPoint(0, 0);
			triangle.addPoint(0, getIconHeight());
			triangle.addPoint(getIconWidth(), Math.round(getIconHeight() / 2));
		}

		public int getIconWidth() {
			return 16;
		}

		public int getIconHeight() {
			return 16;
		}

		public void paintIcon(Component c, Graphics g, int x, int y) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setPaint(paint);
			at.setToTranslation(x, y);
			Shape shape = at.createTransformedShape(triangle);
			g2.fill(shape);
		}
	}

	class DisabledPlayIcon extends PlayIcon {
		public DisabledPlayIcon() {
			paint = Color.gray;
		}
	}

	class PauseIcon implements Icon {

		private AffineTransform at = new AffineTransform();
		private double stepBlockThicknessFactor = 0.375;
		protected Paint paint = Color.black;
		private Rectangle2D rectangle;

		public PauseIcon() {
			rectangle = new Rectangle2D.Double(0, 0, getIconWidth()
					* stepBlockThicknessFactor, getIconHeight());
		}

		public int getIconWidth() {
			return 16;
		}

		public int getIconHeight() {
			return 16;
		}

		public void paintIcon(Component c, Graphics g, int x, int y) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setPaint(paint);
			at.setToTranslation(x, y);
			Shape shape = at.createTransformedShape(rectangle);
			g2.fill(shape);
			at.setToTranslation(
					getIconWidth() * (1 - stepBlockThicknessFactor), 0);
			shape = at.createTransformedShape(shape);
			g2.fill(shape);
		}
	}

	class DisabledPauseIcon extends PauseIcon {
		public DisabledPauseIcon() {
			paint = Color.gray;
		}
	}

	class StepIcon implements Icon {

		private AffineTransform at = new AffineTransform();
		protected Paint paint = Color.black;
		private Polygon triangle = new Polygon();
		private Rectangle2D rectangle;
		private double stepLineThicknessFraction = 0.125;

		public StepIcon() {
			triangle.addPoint(0, 0);
			triangle.addPoint(0, getIconHeight());
			triangle.addPoint(
					(int) Math.round(getIconWidth()
							* (1 - stepLineThicknessFraction)),
					Math.round(getIconHeight() / 2));
			rectangle = new Rectangle2D.Double(getIconWidth()
					* (1 - stepLineThicknessFraction), 0, getIconWidth()
					* stepLineThicknessFraction, getIconHeight());
		}

		public int getIconWidth() {
			return 16;
		}

		public int getIconHeight() {
			return 16;
		}

		public void paintIcon(Component c, Graphics g, int x, int y) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setPaint(paint);
			at.setToTranslation(x, y);
			Shape shape = at.createTransformedShape(triangle);
			g2.fill(shape);
			shape = at.createTransformedShape(rectangle);
			g2.fill(shape);
		}
	}

	class DisabledStepIcon extends StepIcon {
		public DisabledStepIcon() {
			paint = Color.gray;
		}
	}

	private Icon playIcon;

	protected Icon getPlayIcon() {
		if (playIcon == null) {
			playIcon = new PlayIcon();
		}
		return playIcon;
	}

	private Icon disabledPlayIcon;

	protected Icon getDisabledPlayIcon() {
		if (disabledPlayIcon == null) {
			disabledPlayIcon = new DisabledPlayIcon();
		}
		return disabledPlayIcon;
	}

	private Icon pauseIcon;

	protected Icon getPauseIcon() {
		if (pauseIcon == null) {
			pauseIcon = new PauseIcon();
		}
		return pauseIcon;
	}

	private Icon disabledPauseIcon;

	protected Icon getDisabledPauseIcon() {
		if (disabledPauseIcon == null) {
			disabledPauseIcon = new DisabledPauseIcon();
		}
		return disabledPauseIcon;
	}

	private Icon stepIcon;

	protected Icon getStepIcon() {
		if (stepIcon == null) {
			stepIcon = new StepIcon();
		}
		return stepIcon;
	}

	private Icon disabledStepIcon;

	protected Icon getDisabledStepIcon() {
		if (disabledStepIcon == null) {
			disabledStepIcon = new DisabledStepIcon();
		}
		return disabledStepIcon;
	}

	private AnimationStatusLabel animationStatusLabel;

	protected AnimationStatusLabel getAnimationStatusLabel() {
		if (animationStatusLabel == null) {
			animationStatusLabel = new AnimationStatusLabel();
		}
		return animationStatusLabel;
	}

}
