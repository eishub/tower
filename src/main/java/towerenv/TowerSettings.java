package towerenv;

import java.util.prefs.Preferences;

/**
 * Static object to store elevator settings
 * 
 * @author W.Pasman
 * 
 */

public class TowerSettings {

	/**
	 * Use the global preference store for this user to store the settings.
	 */
	static public Preferences prefs = Preferences
			.userNodeForPackage(TowerSettings.class);

	/**
	 * get the currently selected Simulation.
	 * 
	 * @return preferred simulation as set by user, or "Random Rider Insertion"
	 *         as default.
	 */

	/**
	 * get preferred x position of top left corner of the window.
	 * 
	 * @return preferred x pos of top left corner set by user, or 0 by default
	 */
	public static int getX() {
		return TowerSettings.prefs.getInt("x", 0);
	}

	/**
	 * get preferred y position of top left corner of the window.
	 * 
	 * @return preferred y pos of top left corner set by user, or 0 by default
	 */
	public static int getY() {
		return TowerSettings.prefs.getInt("y", 0);
	}

	/**
	 * save the window settings
	 * 
	 * @param x
	 *            :x pos of top left corner
	 * @param y
	 *            :y pos of top left corner
	 */
	public static void setWindowParams(int x, int y) {
		TowerSettings.prefs.putInt("x", x);
		TowerSettings.prefs.putInt("y", y);

	}

}