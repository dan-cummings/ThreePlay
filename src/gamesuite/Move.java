package gamesuite;

import java.io.Serializable;

/** 
 * Move class creates and stores moves of board pieces.
 * When a piece is moved the Move object stores the to
 * and from coordinates of the board.
 * @author Daniel Cummings
 * @version 0.1
 */
public class Move implements Serializable {
	
	/** Default serial code. */
	private static final long serialVersionUID = 1L;
	/**Horizontal board location piece moving to.*/
	private int toX;
	/**Vertical board location the piece is moving to.*/
	private int toY;
	/**Horizontal board location the piece moves from.*/
	private int frX;
	/**Vertical board location the pieces moves from.*/
	private int frY;
	
	/**
	 * Constructor method for Move class.
	 * @param tX Horizontal position the piece is moving to.
	 * @param tY Vertical position the piece is moving to.
	 * @param fX Horizontal origin of the piece.
	 * @param fY Vertical origin of the piece.
	 */
	public Move(final int fX, final int fY,
			final int tX, final int tY) {
		this.setToX(tX);
		this.setToY(tY);
		this.setFromX(fX);
		this.setFromY(fY);		
	}

	/**
	 * Gets the horizontal position the pieces is moving to.
	 * @return The Horizontal location the piece is moving to.
	 */
	public int getToX() {
		return toX;
	}

	/**
	 * Sets the horizontal position the piece is moving to.
	 * @param tX The Horizontal location the piece is moving to.
	 */
	private void setToX(final int tX) {
		this.toX = tX;
	}

	/**
	 * Gets the ending vertical position of the piece.
	 * @return Vertical position the pieces is moving to.
	 */
	public int getToY() {
		return toY;
	}

	/**
	 * Sets the ending vertical position of the piece.
	 * @param tY Vertical position the piece is moving to.
	 */
	private void setToY(final int tY) {
		this.toY = tY;
	}

	/**
	 * Gets the pieces horizontal origin.
	 * @return Horizontal origin of piece.
	 */
	public int getFromX() {
		return frX;
	}

	/**
	 * Sets the pieces horizontal origin.
	 * @param fX Horizontal origin of piece.
	 */
	private void setFromX(final int fX) {
		this.frX = fX;
	}

	/**
	 * Gets the pieces vertical origin.
	 * @return Vertical origin of piece.
	 */
	public int getFromY() {
		return frY;
	}

	/**
	 * Sets the pieces vertical origin.
	 * @param fY Vertical origin of piece.
	 */
	private void setFromY(final int fY) {
		this.frY = fY;
	}
}
