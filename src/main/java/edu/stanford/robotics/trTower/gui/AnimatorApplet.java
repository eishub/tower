// Adapted from O'Reilly "Java 2D Graphics" and Sun JDK 1.3 demo examples
package edu.stanford.robotics.trTower.gui;

import java.awt.*;
import javax.swing.*;

public abstract class AnimatorApplet extends JApplet implements Runnable {

	private Thread animationThread;

	protected abstract void animStep();

	// --- attributes
	private long threadSleepInterval = 1;

	public long getThreadSleepInterval() {
		return threadSleepInterval;
	}

	public void setThreadSleepInterval(long l) {
		threadSleepInterval = l;
	}

	// --- public methods
	public boolean isRunning() {
		if (animationThread == null)
			return false;
		else
			return true;
	}

	public void start() {
		if (animationThread == null) {
			animationThread = new Thread(this);
			animationThread.start();
		}
	}

	public void stop() {
		animationThread = null;
	}

	public void run() {
		Thread thisThread = Thread.currentThread();
		while (animationThread == thisThread) {
			if (threadSleepInterval > 0) {
				try {
					Thread.currentThread().sleep(threadSleepInterval);
				} catch (InterruptedException e) {
				}
			}
			render();
			animStep();
		}
	}

	private Image offScreenImage;

	protected Image getOffScreenImage(Dimension d) {
		if (offScreenImage == null) {
			// if offScreenImage not instantiated
			offScreenImage = createImage(d.width, d.height);
		} else if (offScreenImage.getWidth(null) != d.width
				|| offScreenImage.getHeight(null) != d.height) {
			// or if it doesn't match the dimension
			offScreenImage = createImage(d.width, d.height);
		}
		return offScreenImage;
	}

	protected void render() {
		final Graphics g = getGraphics();
		Dimension d = getSize();
		if (d.width == 0 || d.height == 0) {
			System.out
					.println("AnimatorApplet.render()> d.width = 0 or d.height = 0");
		} else {

			final Image offScrImage = getOffScreenImage(d);
			Graphics imageGraphics = offScrImage.getGraphics();

			// Clear the image background
			imageGraphics.setColor(getBackground());
			imageGraphics.fillRect(0, 0, d.width, d.height);
			imageGraphics.setColor(getForeground());

			// Draw this component offscreen
			paint(imageGraphics);
			// Now put the offscreen image on the screen.

			// ??? put the following inside Swing invoke ????
			g.drawImage(offScrImage, 0, 0, null);
			// SwingUtilities.invokeLater(new Runnable() {
			// public void run() {
			// g.drawImage(offScrImage, 0, 0, null);
			// }
			// });

			// Clean up
			imageGraphics.dispose();
		}
		g.dispose();
	}

}
