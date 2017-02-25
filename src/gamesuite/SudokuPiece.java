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
	/** Tells if the piece has been made it to the end. */
	private int num;
	private boolean isInitial;
	private boolean isError;
	private Player owner;
	/**
	 * Checkers piece constructor to set owner and beginning state.
	 * @param p Which player owns this piece.
	 */
	public SudokuPiece(final int num) {
		this.setNum(num);
	}

	private void setNum(int temp){
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

	@Override
	public boolean validMove(int x, int y, IPiece[][] b, Player p) {
		return false;
	}
}
