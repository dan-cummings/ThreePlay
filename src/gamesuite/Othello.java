package gamesuite;

import java.util.ArrayList;
import java.util.List;

public class Othello {

	/** The current player. Othello always starts with black*/
	private Player currentPlayer = Player.BLACK;
	
	/** Has a move been submitted */
	private boolean submittedMode = false;
	
	// Create Board class for checkers and Othello?
	/** The board with all pieces */
	//public final Board board;
	
	private final int[][] adjacentFields = {{-1, -1}, {0, -1}, {1, -1}, {-1, 0}, {1, 0}, {-1, 1}, {0, 1}, {1, 1}};
	
	/** 
	 * Constructor for Othello
	 */
	public Othello(int width, int height) {
		//this.board = new Board(width, height);
		//checkState();
	}
}
