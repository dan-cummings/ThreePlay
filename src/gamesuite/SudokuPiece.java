package gamesuite;

import java.io.Serializable;

/**
 * Checkers Piece is used as a structure to represent pieces on
 * a checker board. Pieces maintain their valid movements as
 * well as whether or not they are king pieces.
 * @author Daniel Cummings
 */
public class SudokuPiece implements IPiece, Serializable {

	/** Serial version ID. */
	private static final long serialVersionUID = 1L;
	/** Number stored in piece. */
	private int num;
	/** Value for initial number */
	private boolean isInitial;
	/** If piece is in error */
	private boolean isError;
	/** Owner of piece. */
	private Player owner;
	/** If piece is currently selected. */
	private boolean currentlySelected = false;
	
	/**
	 * Sudoku piece constructor to set beginning state.
	 * @param num value of the piece.
	 */
	public SudokuPiece(final int num) {
		this.setNum(num);
	}
	
	private boolean isSelected(){
		return currentlySelected;
	}
	private void clickedOn(){
		if(currentlySelected == true){
			this.currentlySelected = false;
		}
		if(currentlySelected == false){
			this.currentlySelected = true;
		}
	}

	public void setNum(int temp){
		if(temp < 10 && temp > -1){
			this.num = temp;
		}
	}
	
	public int getNum(){
		return num;
	}

	public boolean isInitial() {
		return isInitial;
	}

	public boolean isError() {
		return isError;
	}
	
	public void setInitial(boolean temp){
		this.isInitial = temp;
	}
	
	public void setError(boolean temp){
		this.isError = temp;
	}
	
	@Override
	public final Player getOwner() {
		return owner;
	}
	
	@Override
	public final boolean validMove(final Move m, final IPiece[][] b) {
		return true;
	}
}
