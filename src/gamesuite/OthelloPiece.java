package gamesuite;

import java.io.Serializable;

/**
 * 
 * @author Jaden Sella
 */

public class OthelloPiece implements IPiece, Serializable {
	
	/** Serial version ID. */
	private static final long serialVersionUID = 1L;
	
	/** Which player owns this piece. */
	private Player owner;

	/**
	 * Checkers piece constructor to set owner and beginning state.
	 * @param p Which player owns this piece.
	 */
	public OthelloPiece(final Player p) {
		this.setOwner(p);
	}

	/**
	 * Sets the owner of the checkers piece.
	 * @param p The player who owns the piece.
	 */
	private void setOwner(final Player p) {
		this.owner = p;
	}
	
	/**
	 * Switches the owner of the checkers piece.
	 */
	public void switchOwner() {
		if (getOwner() == null) {
			return;
		}
		if (getOwner() == Player.WHITE) {
			setOwner(Player.BLACK);
		} else {
			setOwner(Player.WHITE);
		}
	}

	@Override
	public final Player getOwner() {
		return owner;
	}
	
	@Override
	public final boolean validMove(final Move m, final IPiece[][] b) {
		return false;
	}
}
