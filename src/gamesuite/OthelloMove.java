package gamesuite;

import java.io.Serializable;

/** 
 * Move class creates and stores moves of board pieces.
 * When a piece is moved the Move object stores the to
 * and from coordinates of the board.
 * @author Jaden Sella
 * @version 0.1
 */
public class OthelloMove implements Serializable {
	
	/** Default serial code. */
	private static final long serialVersionUID = 1L;
	/**Horizontal board location */
	private int x;
	/**Vertical board location*/
	private int y;

	/**
	 * Constructor method for Move class.
	 * @param tX Horizontal position the piece is moving to.
	 * @param tY Vertical position the piece is moving to.
	 * @param fX Horizontal origin of the piece.
	 * @param fY Vertical origin of the piece.
	 */
	public OthelloMove(final int fX, final int fY) {
		this.setX(fX);
		this.setY(fY);	
	}

	/**
	 * Gets the horizontal position the pieces is moving to.
	 * @return The Horizontal location the piece is moving to.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the horizontal position the piece is moving to.
	 * @param tX The Horizontal location the piece is moving to.
	 */
	private void setX(final int tX) {
		this.x = tX;
	}

	/**
	 * Gets the ending vertical position of the piece.
	 * @return Vertical position the pieces is moving to.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the ending vertical position of the piece.
	 * @param tY Vertical position the piece is moving to.
	 */
	private void setY(final int tY) {
		this.y = tY;
	}

}
