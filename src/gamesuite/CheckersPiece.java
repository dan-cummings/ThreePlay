package gamesuite;

import java.io.Serializable;

/**
 * Checkers Piece is used as a structure to represent pieces on
 * a checker board. Pieces maintain their valid movements as
 * well as whether or not they are king pieces.
 * @author Daniel Cummings
 */
public class CheckersPiece implements IPiece, Serializable {

	/** Serial version ID. */
	private static final long serialVersionUID = 1L;
	/** Which player owns this piece. */
	private Player owner;
	/** Tells if the piece has been made it to the end. */
	private boolean isKinged;
	/** Tells if the piece can jump. */
	private boolean canJump;

	/**
	 * Checkers piece constructor to set owner and beginning state.
	 * @param p Which player owns this piece.
	 */
	public CheckersPiece(final Player p) {
		this.setOwner(p);
		this.setKinged(false);
		this.canJump(false);
	}

	/**
	 * Returns the boolean for pieces being able to jump.
	 * @return true if the piece can jump this turn.
	 */
	public boolean hasJump() {
		return this.canJump;
	}
	
	/**
	 * Sets whether the piece can jump that turn.
	 * @param b Whether or not the piece can jump.
	 */
	public void canJump(final boolean b) {
		this.canJump = b;
	}

	@Override
	public final Player getOwner() {
		return owner;
	}
	
	@Override
	public final boolean validMove(final Move m, final IPiece[][] b) {
		int tX = m.getToX();
		int tY = m.getToY();
		int fX = m.getFromX();
		int fY = m.getFromY();
		
		boolean can = false;
		if (b[tX][tY] != null) {
			return can;
		}
		if (this.canJump 
				&& Math.abs(tX - fX) == 2 
				&& Math.abs(tY - fY) == 2) {
			if (tX - fX < 0 
				&& (isKinged || owner == Player.WHITE)) {
				if (tY - fY < 0) {
					if ((b[fX - 1][fY - 1] != null)
							&& (b[fX][fY].getOwner()
					!= b[fX - 1][fY - 1].getOwner())) {
						can = true;
					}
				} else {
					if ((b[fX - 1][fY + 1] != null) 
							&& b[fX][fY].getOwner()
					!= b[fX - 1][fY + 1].getOwner()) {
						can = true;
					}
				}
			} else if (tX - fX > 0 
				&& (isKinged || owner == Player.BLACK)) {
				if (tY - fY < 0) {
					if ((b[fX + 1][fY - 1] != null) 
							&& (b[fX][fY].getOwner()
					!= b[fX + 1][fY - 1].getOwner())) {
						can = true;
					}
				} else {
					if ((b[fX + 1][fY + 1] != null)
						&&	b[fX][fY].getOwner()
					!= b[fX + 1][fY + 1].getOwner()) {
						can = true;
					}
				}
			}
		} else if (!canJump) {
			if (isKinged) {
				if (Math.abs(tX - fX) == 1 
						&& Math.abs(tY - fY) == 1) {
					can = true;
				}
			} else if (b[fX][fY].getOwner() == Player.BLACK) {
				if (Math.abs(tY - fY) == 1 && (tX - fX) == 1) {
					can = true;
				}
			} else {
				if (Math.abs(tY - fY) == 1 && (tX - fX) == -1) {
					can = true;
				}
			}
		}
		return can;	
	}

	/**
	 * Gets the value of the pieces promotion to king.
	 * @return True if the piece has been promoted to king. False if
	 * the piece is not promoted.
	 */
	public boolean isKinged() {
		return isKinged;
	}

	/**
	 * Sets whether or not the piece has been promoted.
	 * @param kinged Whether or not the piece is promoted to king.
	 */
	public void setKinged(final boolean kinged) {
		this.isKinged = kinged;
	}

	/**
	 * Sets the owner of the checkers piece.
	 * @param p The player who owns the piece.
	 */
	private void setOwner(final Player p) {
		this.owner = p;
	}

}
