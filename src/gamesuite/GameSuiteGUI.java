package gamesuite;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * View for the user to interact with visual representations of
 * game pieces and boards.
 * @author Daniel Cummings
 * @version 0.1
 */
public class GameSuiteGUI extends JPanel {
	
	/** Version Serial ID. */
	private static final long serialVersionUID = 1L;
	
	/** Reference to the window that contains content. */
	private JFrame wind;
	
	/** Model which controls the game. */
	private GameLogic game;
	
	/**
	 * @param frame 
	 * 
	 */
	public GameSuiteGUI(final JFrame frame) {
		this.wind = frame;	
	}
}
