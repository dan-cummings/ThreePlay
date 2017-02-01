package gamesuite;
/**
 * 
 * @author Daniel Cummings
 *
 */
public interface IPiece {
	/**
	 * 
	 * @return ID of the player who owns the piece.
	 */
	Player owner();
	
	/**
	 * 
	 * @return true for valid move locations, false for invalid.
	 * @param m 
	 * @param b	
	 */
	boolean validMove(Move m, IPiece[][] b);
}
