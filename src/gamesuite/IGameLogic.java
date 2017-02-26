package gamesuite;

/**
 * Interface for games to implement as basis for how a given game is
 * played. Games act as control for determine whether the game is over
 * or manipulate pieces of the game.
 * @author Daniel Cummings
 * @version 0.1
 */
public interface IGameLogic {
	
	/**
	 * Determines whether the game is over for given terminating rules.
	 * @return True if game completion terms are met, otherwise false.
	 */
	boolean isGameOver();
	
	/**
	 * Determines whether the move is valid based on the current board.
	 * Moves vary based on rules of piece movement.
	 * @param m Move provided by the user.
	 * @return True when the provided move is valid for current board.
	 * Otherwise the move is invalid, false.
	 */
	boolean isMove(Move m);
	
	/**
	 * Determines whether the move is valid based on the current board.
	 * Moves vary based on rules of piece movement.
	 * @param x position of the move checking
	 * @param y position of the move checking
	 * @return True when the provided move is valid for current board.
	 * Otherwise the move is invalid, false.
	 */
	boolean isMove(int x, int y);
	
	/**
	 * Writes objects into the specified file. The data is
	 * stored into the file as a corresponding state of the game.
	 * @param filename Name of file to save the game state.
	 * @throws Exception contains information about save error.
	 */
	void saveState(String filename) throws Exception;
	
	/**
	 * Reads objects from the specified file into the game.
	 * Allowing users to load from saved states.
	 * @param filename Name of file to load game state.
	 * @throws Exception contains information about load error.
	 */
	void loadState(String filename) throws Exception;
	
	
}
