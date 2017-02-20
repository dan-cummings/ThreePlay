package gamesuite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private ArrayList<IPiece> moves;
	/** ArrayList for jumps if possible. */
	private ArrayList<IPiece> jumps;
	/** determines whether or not the game is in a stalemate. */
	private boolean stalemate;
	/** Determines whether or not the game is over. */
	private boolean gameover;
	/** Has the game been saved. */
	private boolean saved;
	
	/**
	 * Begins checkers game.
	 */
	public CheckersLogic() {
		this.size = 8;
		this.stalemate = false;
		this.gameover = false;
		this.createBoard();
		this.player = Player.WHITE;
		this.moves = new ArrayList<IPiece>();
		this.jumps = new ArrayList<IPiece>();
		this.checkJumps();
	}

	/**
	 * Generates the initial board for the beginning of the game.
	 */
	private void createBoard() {
		this.board = new CheckersPiece[size][size];
		//setup black pieces
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < size; j++) {
				if (i % 2 == 0 && j % 2 == 1) {
					board[i][j] = new CheckersPiece(
							Player.BLACK);
				} else if (i % 2 == 1 && j % 2 == 0) {
					board[i][j] = new CheckersPiece(
							Player.BLACK);
				}
			}
		}

		//Setup red pieces
		for (int i = 5; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (i % 2 == 0 && j % 2 == 1) {
					board[i][j] = new CheckersPiece(
							Player.WHITE);
				} else if (i % 2 == 1 && j % 2 == 0) {
					board[i][j] = new CheckersPiece(
							Player.WHITE);
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
		if ((m.getToY() < size && m.getToX() < size)
				&& (m.getToY() >= 0 && m.getToX() >= 0)) {
			if (board[m.getFromX()][m.getFromY()]
					.validMove(m, board)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public final boolean isMove(int x, int y, Player p) {
		return false;
	}

	/**
	 * Adjusts the current board to represent the board
	 * after the move has been made.
	 * @param m Move or list of moves for the game to make.
	 */
	public void makeMove(final Move m) {
		if (jumps.contains(board[m.getFromX()][m.getFromY()])) {
			
		} else if (moves.contains(board[m.getFromX()][m.getFromY()])) {
			
		} else {
			
		}
	}

	/**
	 * Called at the start of every turn. All pieces with
	 * possible jumps are added to arraylist. Any jumps found stops
	 * check for normal moves.
	 */
	private void checkJumps() {
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if (board[x][y] != null) { 
					if (board[x][y].getOwner() == player) {
						if (canJump(x, y)) {
							jumps.add(board[x][y]);
						}
					}
				}
			}
		}
		if (jumps.isEmpty()) {
			checkMoves();
		}
		
	}

	/**
	 * Method checks all possible pieces for standard moves.
	 * If none are found, game is in stalemate. Only gets called when
	 * no pieces have jumps available.
	 */
	private void checkMoves() {
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if (board[x][y] != null) { 
					if (board[x][y].getOwner() == player) {
						if (canMove(x, y)) {
							moves.add(board[x][y]);
						}
					}
				}
			}
		}
		if (moves.isEmpty()) {
			stalemate = true;
		}
	}

	/**
	 * Checks whether the piece at given position can move in
	 * each direction.
	 * @param x Vertical position of the piece.
	 * @param y Horizontal position of the piece.
	 * @return True if any moves are found, false if none are found.
	 */
	private boolean canMove(final int x, final int y) {
		boolean can = false;
		if (isMove(new Move(x, y, x + 1, y + 1))) {
			can = true;
		} else if (isMove(new Move(x, y, x + 1, y - 1))) {
			can = true;
		} else if (isMove(new Move(x, y, x - 1, y + 1))) {
			can = true;
		} else if (isMove(new Move(x, y, x - 1, y - 1))) {
			can = true;
		}
		return can;
	}

	/**
	 * Helper method to calculate all of the possible jump moves including
	 * jumps that move more than one space.
	 * @param x starting x position of the checkers piece.
	 * @param y starting y position of the checkers piece.
	 * @return True if jump moves are found false otherwise.
	 */
	private boolean canJump(final int x, final int y) {
		boolean isJump = false;

		if (isMove(new Move(x, y, x + 2, y + 2))) {
			isJump = true;
		} else if (isMove(new Move(x, y, x - 2, y - 2))) {
			isJump = true;
		} else if (isMove(new Move(x, y, x + 2, y - 2))) {
			isJump = true;
		} else if (isMove(new Move(x, y, x - 2, y + 2))) {
			isJump = true;
		}
		return isJump;
	}

	@Override
	public final void saveState(final String filename)  throws Exception {
		try {
			FileOutputStream strm = new FileOutputStream(filename);
			ObjectOutputStream ostrm = new ObjectOutputStream(strm);
			ostrm.writeObject(board);
			ostrm.writeObject(player);
			ostrm.close();
			strm.close();
		} catch (FileNotFoundException e) {
			//When filename points to a directory instead of a file.
			throw new Exception("Please choose a "
					+ "different file name.");
		} catch (IOException e) {
			//When error occurs in IO.
			throw new Exception("Error in write took "
				+ "place, try again with new file name.");
		} finally {
			saved = true;
		}
	}

	@Override
	public final void loadState(final String filename) throws Exception {
		try {
			FileInputStream strm = new FileInputStream(filename);
			ObjectInputStream ostrm = new ObjectInputStream(strm);
			this.board = (IPiece[][]) ostrm.readObject();
			this.player = (Player) ostrm.readObject();
			ostrm.close();
			strm.close();
		} catch (FileNotFoundException e) {
			//When filename points to a directory instead of a file.
			throw new Exception("File with"
					+ " that name does not exist.");
		} catch (IOException e) {
			//When error occurs in IO.
			throw new Exception("Error in read, file corrupted.");
		} catch (ClassNotFoundException e) {
			//When class specified is not found.
			throw new Exception("Internal"
					+ " error please restart game.");
		} finally {
			
		}
	}
}
