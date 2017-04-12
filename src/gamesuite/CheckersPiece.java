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

	/**
	 * Checkers piece constructor to set owner and beginning state.
	 * @param p Which player owns this piece.
	 */
	public CheckersPiece(final Player p) {
		this.setOwner(p);
		this.setKinged(false);
	}

	@Override
	public final boolean validMove(final Move m, final IPiece[][] b) {
		int tX = m.getToX();
		int tY = m.getToY();
		int fX = m.getFromX();
		int fY = m.getFromY();
		
		if ((fX < 0 || fX > 7)
				|| (fY < 0 || fY > 7)
				|| (tX < 0 || tX > 7)
				|| (tY < 0 || tY > 7)) {
			return false;
		}
		
		boolean can = false;
		if (b[tX][tY] != null) {
			return can;
		}
		if (Math.abs(tX - fX) == 2 
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
		} else {
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

	@Override
	public final Player getOwner() {
		return owner;
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
