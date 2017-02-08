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
	 * Determines whether the move is valid based on the given board.
	 * Moves vary based on rules of piece movement.
	 * @param m Move provided by the user.
	 * @param b Board status based on the move provided.
	 * @return True when the provided move is valid for provided board.
	 */
	boolean isMove(Move m, IPiece[][] b);
}
