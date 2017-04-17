package gamesuite;

import javax.swing.JFrame;

/**
 * Utility class to instantiate the window and suite GUI.
 * 
 * @author Daniel Cummings
 * @version 0.1
 */
public final class GameSuite {
	
	/**
	 * private constructor. (not used)
	 */
	private GameSuite() { }
	
	/**
	 * Main method to create a frame window and populate it with
	 * view objects.
	 * @param args Allows IO for command line.
	 */
	public static void main(final String[] args) {
		JFrame frame = new JFrame("ThreePlay");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		GameSuiteGUI g = new GameSuiteGUI(frame);
		frame.getContentPane().add(g);
		frame.setResizable(true);
		frame.setVisible(true);
	}
}
