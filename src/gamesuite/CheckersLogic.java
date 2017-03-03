package gamesuite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Logic for the game of checkers. Model allows users to make moves,
 * forces jumps, controls current player, saves the game, loads the
 * game, and determines when the game is over. The model is called
 * from the controller.
 * @author Daniel Cummings
 * @version 1.0
 */
public class CheckersLogic implements IGameLogic {

	/** Current board status of the game. */
	private CheckersPiece[][] board;
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
	 * Constructor for checkers game.
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
				if (i % 2 == 0 && j % 2 == 0) {
					board[i][j] = new CheckersPiece(
							Player.BLACK);
				} else if ((i & 1) == 1 && (j & 1) == 1) {
					board[i][j] = new CheckersPiece(
							Player.BLACK);
				}
			}
		}

		//Setup red pieces
		for (int i = 5; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (i % 2 == 0 && j % 2 == 0) {
					board[i][j] = new CheckersPiece(
							Player.WHITE);
				} else if ((i & 1) == 1 && (j & 1) == 1) {
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
		int tx = m.getToX();
		int ty = m.getToY();
		int fx = m.getFromX();
		int fy = m.getFromY();
		if ((fx >= 0 && fx < size)
				&& (fy >= 0 && fy < size)
				&& (tx >= 0 && tx < size)
				&& (ty >= 0 && ty < size)) {
			if (board[fx][fy] != null
					&& (jumps.contains(board[fx][fy])
					|| moves.contains(board[fx][fy]))) {
				if (board[fx][fy].validMove(m, board)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Adjusts the current board to represent the board
	 * after the move has been made.
	 * @param m Move or list of moves for the game to make.
	 */
	public void makeMove(final Move m) {
		// Piece can jump.
		int tx = m.getToX();
		int ty = m.getToY();
		int fx = m.getFromX();
		int fy = m.getFromY();
		if (jumps.contains(board[fx][fy])) {
			if (tx < fx) {
				if (ty < fy) {
					//Jump forward to the right.
					board[tx][ty] = board[fx][fy];
					board[tx + 1][ty + 1] = null;
					board[fx][fy] = null;
				} else {
					//Jump forward to the left.
					board[tx][ty] = board[fx][fy];
					board[tx + 1][ty - 1] = null;
					board[fx][fy] = null;
				}
			} else {
				if (ty < fy) {
					//jump down to the left.
					board[tx][ty] = board[fx][fy];
					board[tx - 1][ty + 1] = null;
					board[fx][fy] = null;
				} else {
					//jump down to the right.
					board[tx][ty] = board[fx][fy];
					board[tx - 1][ty - 1] = null;
					board[fx][fy] = null;
				}
			}
			//Clears ArrayList of jump pieces.
			jumps.clear();
			
			//If that piece can jump again. It must.
			if (canJump(m.getToX(), m.getToY())) {
				jumps.add(board[m.getToX()][m.getToY()]);
			} else {
				//Otherwise switch players and look for moves.
				jumps.clear();
				moves.clear();
			}
		//Checks if the piece exists in moves Arraylist.
		} else if (moves.contains(board[fx][fy])) {
			board[tx][ty] = board[fx][fy];
			board[fx][fy] = null;
			//clear possible movable pieces.
			moves.clear();
			jumps.clear();
		} else {
			return;
		}
		//Checks whether the piece is a king after move.
		CheckersPiece piece = board[tx][ty];
		if (tx == 7 && piece.getOwner() 
				== Player.BLACK && !piece.isKinged()) {
			piece.setKinged(true);
		} else if (tx == 0 && piece.getOwner()
				== Player.WHITE && !piece.isKinged()) {
			piece.setKinged(true);
		}
		this.nextTurn();
		this.gameover = this.isGameOver();
		this.nextTurn();
		if (jumps.isEmpty() && moves.isEmpty() && !gameover) {
			this.nextTurn();
			this.checkJumps();
		}
		this.saved = false;
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
				//if the piece is owned by current player.
					board[x][y].canJump(true);
					if (board[x][y].getOwner() == player) {
						jumps.add(board[x][y]);
						//Has a jump.
						if (!canJump(x, y)) {
						//removes from arraylist 
						//if jump is not possible.
							board[x][y].canJump(
									false);
							jumps.remove(
								board[x][y]);
						}
					}
				}
			}
		}
		//if jumps is empty then moves are possible.
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
						moves.add(board[x][y]);
						if (!canMove(x, y)) {
							moves.remove(
								board[x][y]);
						}
					}
				}
			}
		}
		//if current player has no moves available
		//game is in stalemate.
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
		//Checks down to the right.
		if (isMove(new Move(x, y, x + 1, y + 1))) {
			can = true;
		//checks down to the left.
		} else if (isMove(new Move(x, y, x + 1, y - 1))) {
			can = true;
		//checks up to the right.
		} else if (isMove(new Move(x, y, x - 1, y + 1))) {
			can = true;
		//checks up to the left.
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

		//Checks down to the right.
		if (isMove(new Move(x, y, x + 2, y + 2))) {
			isJump = true;
		//Checks up to the left.
		} else if (isMove(new Move(x, y, x - 2, y - 2))) {
			isJump = true;
		//Checks down to the left.
		} else if (isMove(new Move(x, y, x + 2, y - 2))) {
			isJump = true;
		//Checks  up to the right.
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
			ostrm.writeObject(jumps);
			ostrm.writeObject(moves);
			ostrm.close();
			strm.close();
			saved = true;
		} catch (FileNotFoundException e) {
			//When filename points to a directory instead of a file.
			e.printStackTrace();
			throw new Exception("File name is occupied,"
					+ " please try another.");
		} catch (IOException e) {
			//When error occurs in IO.
			e.printStackTrace();
			throw new Exception("Error in saving took place,"
				+ " please try again with new file name.");
		} finally {
			System.out.println("Save attempted");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public final void loadState(final String filename) throws Exception {
		try {
			FileInputStream strm = new FileInputStream(filename);
			ObjectInputStream ostrm = new ObjectInputStream(strm);
			this.board = (CheckersPiece[][]) ostrm.readObject();
			this.player = (Player) ostrm.readObject();
			this.jumps = (ArrayList<IPiece>) ostrm.readObject();
			this.moves = (ArrayList<IPiece>) ostrm.readObject();
			ostrm.close();
			strm.close();
		} catch (FileNotFoundException e) {
			//When filename points to a directory instead of a file.
			e.printStackTrace();
			throw new Exception("File with"
					+ " that name does not exist.");
		} catch (IOException e) {
			//When error occurs in IO.
			e.printStackTrace();
			throw new Exception("Error durring reading, "
					+ "file corrupted.");
		} catch (ClassNotFoundException e) {
			//When class specified is not found.
			e.printStackTrace();
			throw new Exception("File corrupted,"
					+ " cannot recieve game state.");
		} finally {
			System.out.println("Load Attempted.");
		}
	}

	/**
	 * Getter for the board size.
	 * @return size of the game board.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Getter for the piece at requested location.
	 * @param x vertical position of piece.
	 * @param y horizontal position of piece.
	 * @return piece at given location.
	 */
	public CheckersPiece getPiece(final int x, final int y) {
		return (CheckersPiece) board[x][y];
	}

	/**
	 * Method checks whether the provided piece has a move.
	 * @param piece Checkers piece to look for move.
	 * @return True if the piece has a jump or standard move,
	 *  otherwise false.
	 */
	public boolean hasMove(final IPiece piece) {
		if (!jumps.isEmpty()) {
			return jumps.contains(piece);
		} else {
			return moves.contains(piece);
		}
	}

	/**
	 * Getter for the current player.
	 * @return current player who can move.
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Switches player after turn.
	 */
	private void nextTurn() {
		if (player == Player.WHITE) {
			player = Player.BLACK;
		} else {
			player = Player.WHITE;
		}
	}
	
	/**
	 * Getter for status of game in stalemate.
	 * @return True if current player cannot move.
	 */
	public boolean isStalemate() {
		return this.stalemate;
	}
	
	/**
	 * Getter method for the game completion status of the game.
	 * @return True if the game is determined to be finished.
	 */
	public boolean gameOver() {
		return this.gameover;
	}
	
	/**
	 * Getter for whether or not the current game state has been saved.
	 * @return True if the game has been saved this turn.
	 */
	public boolean isSaved() {
		return this.saved;
	}

	/**
	 *  Allows users to reset board after getting stalemate or gameover.
	 */
	public void reset() {
		this.stalemate = false;
		this.gameover = false;
		this.createBoard();
		this.player = Player.WHITE;
		this.moves.clear();
		this.jumps.clear();
		this.checkJumps();
		
	}
	
	/** Unused Method. */
	@Override
	public final boolean isMove(final int x, final int y) {
		return false;
	}
}
