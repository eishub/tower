// Adapted from O'Reilly "Java 2D Graphics" and Sun JDK 1.3 demo examples
package edu.stanford.robotics.trTower.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JApplet;

@SuppressWarnings("deprecation")
public abstract class AnimatorApplet extends JApplet implements Runnable {
	private static final long serialVersionUID = 1L;
	private Thread animationThread;

	protected abstract void animStep();

	private long threadSleepInterval = 1;

	public long getThreadSleepInterval() {
		return this.threadSleepInterval;
	}

	public void setThreadSleepInterval(final long l) {
		this.threadSleepInterval = l;
	}

	public boolean isRunning() {
		if (this.animationThread == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void start() {
		if (this.animationThread == null) {
			this.animationThread = new Thread(this);
			this.animationThread.start();
		}
	}

	@Override
	public void stop() {
		this.animationThread = null;
	}

	@Override
	public void run() {
		final Thread thisThread = Thread.currentThread();
		while (this.animationThread == thisThread) {
			if (this.threadSleepInterval > 0) {
				try {
					Thread.sleep(this.threadSleepInterval);
				} catch (final InterruptedException e) {
				}
			}
			render();
			animStep();
		}
	}

	private Image offScreenImage;

	protected Image getOffScreenImage(final Dimension d) {
		if (this.offScreenImage == null) {
			// if offScreenImage not instantiated
			this.offScreenImage = createImage(d.width, d.height);
		} else if (this.offScreenImage.getWidth(null) != d.width || this.offScreenImage.getHeight(null) != d.height) {
			// or if it doesn't match the dimension
			this.offScreenImage = createImage(d.width, d.height);
		}
		return this.offScreenImage;
	}

	protected void render() {
		final Graphics g = getGraphics();
		final Dimension d = getSize();
		if (d.width == 0 || d.height == 0) {
			System.out.println("AnimatorApplet.render()> d.width = 0 or d.height = 0");
		} else {
			final Image offScrImage = getOffScreenImage(d);
			final Graphics imageGraphics = offScrImage.getGraphics();

			// Clear the image background
			imageGraphics.setColor(getBackground());
			imageGraphics.fillRect(0, 0, d.width, d.height);
			imageGraphics.setColor(getForeground());

			// Draw this component offscreen
			paint(imageGraphics);
			// Now put the offscreen image on the screen.

			// ??? put the following inside Swing invoke ????
			g.drawImage(offScrImage, 0, 0, null);

			// Clean up
			imageGraphics.dispose();
		}
		g.dispose();
	}
}
