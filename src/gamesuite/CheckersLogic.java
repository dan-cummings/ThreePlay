package gamesuite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * 
 * @author Daniel Cummings
 */
public class CheckersLogic implements IGameLogic {

	/** Current board status of the game. */
	private IPiece[][] board;
	/** Stores value of player making move. */
	private Player player;
	/** Size of game board. */
	private int size;
	/** ArrayList of possible moves. */
	private ArrayList<Move> moves;
	/** ArrayList for jumps if possible. */
	private ArrayList<Move> jumps;

	/**
	 * 
	 */
	public CheckersLogic() {
		this.size = 8;
		createBoard();
		moves = new ArrayList<Move>();
	}

	/**
	 * 
	 */
	private void createBoard() {
		this.board = new CheckersPiece[8][8];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 8; j++) {
				if (i % 2 == 0 && j % 2 == 1) {
					board[i][j] = new CheckersPiece(Player.BLACK);
				}
			}
		}
	}

	/**
	 * Checks whether the game is over. If there are no
	 * pieces for the player remaining on the board the game is over.
	 * @return True if no current player pieces are on the board.
	 * False otherwise.
	 */
	public boolean isGameOver() {
		boolean finished = true;
		for (IPiece[] i : this.board) {
			for (IPiece j : i) {
				if (j != null) {
					if (j.getOwner() == this.player) {
						finished = false;
						break;
					}
				}
			}
		}
		return finished;
	}

	@Override
	public final boolean isMove(final Move m) {
		boolean canMove = false;
		
		return canMove;
	}

	@Override
	public final void saveState(final String filename) {
		try {
			FileOutputStream strm = new FileOutputStream(filename);
			ObjectOutputStream ostrm = new ObjectOutputStream(strm);
			ostrm.writeObject(board);
			ostrm.writeObject(player);
			ostrm.close();
			strm.close();
		} catch (FileNotFoundException e) {
			//When filename points to a directory instead of a file.
		} catch (IOException e) {
			//When error occurs in IO.
		} finally {
			
		}
	}
	
	@Override
	public final void loadState(final String filename) {
		
		try {
			FileInputStream strm = new FileInputStream(filename);
			ObjectInputStream ostrm = new ObjectInputStream(strm);
			this.board = (IPiece[][]) ostrm.readObject();
			this.player = (Player) ostrm.readObject();
			ostrm.close();
			strm.close();
		} catch (FileNotFoundException e) {
			//When filename points to a directory instead of a file.
		} catch (IOException e) {
			//When error occurs in IO.
		} catch (ClassNotFoundException e) {
			//When class specified is not found.
		} finally {
			
		}
	}
	
}
