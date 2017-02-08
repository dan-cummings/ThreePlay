package gamesuite;

import javax.swing.JFrame;

/**
 * Utility class to instantiate the window and suite GUI.
 * 
 * @author Daniel Cummings
 * @version 0.1
 */
public final class GameSuite{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * private constructor. (not used)
	 */
	private GameSuite() { };
	
	/**
	 * Main method to create a frame window and populate it with
	 * view objects.
	 */
	public static void main() {
		JFrame frame = new JFrame("ThreePlay");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GameSuiteGUI g = new GameSuiteGUI(frame);
		frame.getContentPane().add(g);
	}
}
