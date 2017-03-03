package gamesuite;

import java.io.Serializable;

/**
 * Sudoku Piece is used as a structure to represent squares on
 * sudoku board. Contains boolean values for errors and initially
 * defined squares. 
 * @author Brendon Murthum, Daniel Cummings
 */
public class SudokuPiece implements IPiece, Serializable {

	/** Serial version ID. */
	private static final long serialVersionUID = 1L;
	/** Number stored in piece. */
	private int num;
	/** Value for initial number. */
	private boolean isInitial;
	/** If piece is in error. */
	private boolean isError;
	/** Owner of piece. */
	@SuppressWarnings("unused")
	private Player owner;
	/** If piece is currently selected. */
	private boolean currentlySelected;
	
	/**
	 * Sudoku piece constructor to set beginning state.
	 * @param numl value of the piece.
	 */
	public SudokuPiece(final int numl) {
		this.setNum(numl);
		this.currentlySelected = false;
	}
	
	/**
	 * Sets the number of the square.
	 * @param temp - The number to set the square to.
	 */
	public void setNum(final int temp) {
		if (temp < 10 && temp > -1) {
			this.num = temp;
		}
	}
	
	/**
	 * Gets the number of the square. Used in the logic class.
	 * @return - Returns the INT of the square.
	 */
	public int getNum() {
		return num;
	}

	/**
	 * Determines if the square was initially set.
	 * @return - Returns TRUE if square was initial.
	 */
	public boolean isInitial() {
		return isInitial;
	}

	/**
	 * Determines if the square is an error on the board.
	 * @return - Returns TRUE if the square is an error.
	 */
	public boolean isError() {
		return isError;
	}
	
	/**
	 * Sets a square to be seen as initially set.
	 * @param temp - Set TRUE if initially set, FALSE if not.
	 */
	public void setInitial(final boolean temp) {
		this.isInitial = temp;
	}
	
	/**
	 * Sets a square to be seen as an error.
	 * @param temp - Set TRUE if is an error, FALSE if not.
	 */
	public void setError(final boolean temp) {
		this.isError = temp;
	}
	
	@Override
	public final Player getOwner() {
		return null;
	}
	
	@Override
	public final boolean validMove(final Move m, final IPiece[][] b) {
		return true;
	}


}
