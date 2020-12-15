package edu.stanford.robotics.trTower.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import edu.stanford.robotics.trTower.StimulatorListener;
import edu.stanford.robotics.trTower.virtualWorld.VirtualWorld;

public class VirtualWorldRenderPanel extends JPanel implements StimulatorListener {
	private static final long serialVersionUID = 1L;
	private final Cursor defaultCursor;

	public VirtualWorldRenderPanel() {
		setOpaque(true);
		setBackground(Color.white);
		addMouseListener(getMouseInputHandler());
		addMouseMotionListener(getMouseInputHandler());
		setToolTipText("Click and drag to reposition blocks");
		this.defaultCursor = getCursor();
		setBorder(BorderFactory.createEtchedBorder(Color.white, Color.gray));
	}

	class MouseInputHandler extends MouseInputAdapter {
		@Override
		public void mousePressed(final MouseEvent e) {
			getVirtualWorld().setSelectPoint(e.getPoint());
		}

		@Override
		public void mouseReleased(final MouseEvent e) {
			getVirtualWorld().setSelectPoint(null);
		}

		@Override
		public void mouseDragged(final MouseEvent e) {
			getVirtualWorld().setTargetPoint(e.getPoint());
		}

		@Override
		public void mouseMoved(final MouseEvent e) {
			if (getVirtualWorld().getSelectPoint() == null) {
				// just moving, not dragging
				if (getVirtualWorld().isPointOverBlock(e.getPoint())) {
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				} else {
					setCursor(VirtualWorldRenderPanel.this.defaultCursor);
				}
			}
		}
	}

	private MouseInputHandler mouseInputHandler;

	protected MouseInputHandler getMouseInputHandler() {
		if (this.mouseInputHandler == null) {
			this.mouseInputHandler = new MouseInputHandler();
		}
		return this.mouseInputHandler;
	}

	// Wouter: HACK 16apr09, Koen wants more blocks.
	private static int preferredWidth = 550; // 270;
	private static int preferredHeight = 480; // 225;
	private static Dimension preferredDimension = new Dimension(preferredWidth, preferredHeight);

	@Override
	public Dimension getPreferredSize() {
		return preferredDimension;
	}

	@Override
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}

	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	private VirtualWorld virtualWorld;

	public VirtualWorld getVirtualWorld() {
		return this.virtualWorld;
	}

	public void setVirtualWorld(final VirtualWorld vw) {
		this.virtualWorld = vw;
		this.virtualWorld.setVirtualWorldWidth(preferredWidth);
		this.virtualWorld.setVirtualWorldHeight(preferredHeight);
	}

	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		getVirtualWorld().render(g);
	}

	@Override
	public void stimuStep() {
		if (getVirtualWorld().isChanged()) {
			repaint();
		}
	}
}
