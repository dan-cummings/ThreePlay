package gamesuite;

/**
 * Interface to provide structure for board games within game suite.
 * @author Daniel Cummings
 * @version 0.1
 */
public interface IPiece {
	/**
	 * Which player owns the piece.
	 * @return ID of the player who owns the piece.
	 */
	Player getOwner();
	
	/**
	 * Determines whether a given piece can move to the player determined
	 * position.
	 * @return true if the move is to a valid location, false for invalid.
	 * @param m Move object containing pieces to and from locations.
	 * @param b	2d array of pieces representing the current board.
	 */
	boolean validMove(Move m, IPiece[][] b);
}
