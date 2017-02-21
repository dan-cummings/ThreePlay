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
				if (i % 2 == 0 && (j & 1) == 1) {
					board[i][j] = new CheckersPiece(
							Player.BLACK);
				} else if ((i & 1) == 1 && j % 2 == 0) {
					board[i][j] = new CheckersPiece(
							Player.BLACK);
				}
			}
		}

		//Setup red pieces
		for (int i = 5; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (i % 2 == 0 && (j & 1) == 1) {
					board[i][j] = new CheckersPiece(
							Player.WHITE);
				} else if ((i & 1) == 1 && j % 2 == 0) {
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

		if ((m.getFromX() >= 0 && m.getFromX() < size)
				&& (m.getFromY() >= 0 && m.getFromY() < size)
				&& (m.getToX() >= 0 && m.getToX() < size)
				&& (m.getToY() >= 0 && m.getToY() < size)) {
			if (board[m.getFromX()][m.getFromY()] != null) {
				if (board[m.getFromX()][m.getFromY()]
						.validMove(m, board)) {
					return true;
				}
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
		// Piece can jump.
		if (jumps.contains(board[m.getFromX()][m.getFromY()])) {
			if (m.getToX() < m.getFromX()) {
				if (m.getToY() < m.getFromY()) {
					//Jump forward to the right.
					board[m.getToX()][m.getToY()] = 
							board[m.getFromX()]
								[m.getFromY()];
					board[m.getToX() + 1]
							[m.getToY() + 1] =
							null;
					board[m.getFromX()]
							[m.getFromY()] = null;
				} else {
					//Jump forward to the left.
					board[m.getToX()][m.getToY()] = 
							board[m.getFromX()]
								[m.getFromY()];
					board[m.getToX() + 1]
							[m.getToY() - 1] =
							null;
					board[m.getFromX()]
							[m.getFromY()] = null;
				}
			} else {
				if (m.getToY() < m.getFromY()) {
					//jump down to the left.
					board[m.getToX()][m.getToY()] = 
							board[m.getFromX()]
								[m.getFromY()];
					board[m.getToX() - 1]
							[m.getToY() + 1] =
							null;
					board[m.getFromX()]
							[m.getFromY()] = null;
				} else {
					//jump down to the right.
					board[m.getToX()][m.getToY()] = 
							board[m.getFromX()]
								[m.getFromY()];
					board[m.getToX() - 1]
							[m.getToY() - 1] =
							null;
					board[m.getFromX()]
							[m.getFromY()] = null;
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
				this.nextTurn();
				this.checkJumps();
			}
		//Checks if the piece exists in moves Arraylist.
		} else if (moves.contains(board[m.getFromX()][m.getFromY()])) {
			board[m.getToX()][m.getToY()] = 
					board[m.getFromX()][m.getFromY()];
			board[m.getFromX()][m.getFromY()] = null;
			//clear possible movable pieces.
			moves.clear();
			jumps.clear();
			//change players.
			this.nextTurn();
			//check all moves.
			this.checkJumps();
		}
		//Checks whether the piece is a king after move.
		CheckersPiece piece =
				(CheckersPiece) board[m.getToX()][m.getToY()];
		if (m.getToX() == 7 && piece.getOwner() 
				== Player.BLACK && !piece.isKinged()) {
			piece.setKinged(true);
		} else if (m.getToX() == 0 && piece.getOwner()
				== Player.WHITE && !piece.isKinged()) {
			piece.setKinged(true);
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
				//if the piece is owned by current player.
					if (board[x][y].getOwner() == player) {
						//Has a jump.
						if (canJump(x, y)) {
						//adds to arraylist if possible.
							jumps.add(board[x][y]);
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
						if (canMove(x, y)) {
							moves.add(board[x][y]);
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
		} catch (FileNotFoundException e) {
			//When filename points to a directory instead of a file.
			e.printStackTrace();
			throw new Exception("Please choose a "
					+ "different file name.");
		} catch (IOException e) {
			//When error occurs in IO.
			e.printStackTrace();
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
			throw new Exception("Error in read, file corrupted.");
		} catch (ClassNotFoundException e) {
			//When class specified is not found.
			e.printStackTrace();
			throw new Exception("Internal"
					+ " error please restart game.");
		} finally {
			System.out.println("Load Complete");
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
	 * Switches player when called.
	 */
	private void nextTurn() {
		if (player == Player.WHITE) {
			player = Player.BLACK;
		} else {
			player = Player.WHITE;
		}
	}
	
	/**
	 * Getter method for the player who is currently makeing
	 * a move.
	 * @return The current player who can move.
	 */
	public Player getCurrentPlayer() {
		return player;
	}
}