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
import java.io.Serializable;

/** 
 * 
 * @author Jaden Sella
 */
public class Othello implements IGameLogic, Serializable {
	
	/** Current board status of the game. */
	private OthelloPiece[][] board;
	
	/** Size of game board. */
	private int size;
	
	/** Determines whether or not the game is over. */
	private boolean gameover;
	
	/** Has the game been saved. */
	private boolean saved; 

	/** The current player. Othello always starts with black*/
	private Player player;
	
	/** Count for the number of pieces for player. */
	private int whiteCount, blackCount;
	
	private List<OthelloMove> moves;
	
	private Map<OthelloMove, OthelloPiece[][]> move;
	/** Collection of moves available on turn. */
	
	/** Computer player for the game. */
	private OthelloAI comp;
	/** Field to determine whether the game being played is
	 * against a computer.
	 */
	private boolean isComp;
	
	/** 
	 * Constructor for Othello, begins Othello game. 
	 */
	public Othello() {
		this.move = new HashMap<OthelloMove, OthelloPiece[][]>();
		this.moves = new ArrayList<OthelloMove>();
		this.size = 8;
		this.gameover = false;
		this.createBoard();
		this.player = Player.BLACK;
		this.moves = new ArrayList<OthelloMove>();
		this.countPieces();
	}
	
	/**
	 * Generates the initial board for the beginning of the game.
	 */
	private void createBoard() {
		this.board = new OthelloPiece[size][size];
		// setup initial board conditions
		board[4][4] = new OthelloPiece(Player.WHITE);
		board[3][3] = new OthelloPiece(Player.WHITE);
		board[3][4] = new OthelloPiece(Player.BLACK);
		board[4][3] = new OthelloPiece(Player.BLACK);
	}
	
	/** Allows user to set whether or not they are
	 * playing against an AI.
	 * @param ai True if this is an AI game.
	 */
	public void setAI(final boolean ai) {
		if (ai) {
			this.isComp = true;
			this.comp = new OthelloAI(this);
		}
	}
	
	/**
	 * Helper method to create a list of moves and store their
	 * subsequent board states.
	 */
	private void findMoves() {
		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++){
				if(validMove(i, j, player)){
					OthelloMove m = new OthelloMove(i, j);
					moves.add(m);
				}
			}
		}
	}
	
	/**
	 * Getter method for the list of moves.
	 * @return ArrayList of Move objects.
	 */
	public ArrayList<OthelloMove> getMoveList() {
		return (ArrayList<OthelloMove>) this.moves;
	}
	
	/**
	 * Method to check if a move exists. If it does
	 * the move is made and the next turn is activated.
	 * @param m Move that is being made.
	 * @return True if move can be made.
	 */
	public boolean makeMove(final OthelloMove m) {
		if (this.validMove(m)) {
			this.makeMove(m.getX(), m.getY());
			this.moves.clear();
			this.findMoves();
			this.gameover = this.isGameOver();
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Method to check if a move exists. 
	 * @param m Move that is being made.
	 * @return True if move can be made.
	 */
	public boolean validMove(final OthelloMove m){
		return validMove(m.getX(), m.getY(), player);
	}
	
	
	/**
	 * sets the place on the board to the desired Player.
	 * @param x location of the piece
	 * @param y location of the piece
	 * @param p desired player
	 */	
	public void setPiece(final int x, final int y, 
			final Player p) {
		board[x][y] = new OthelloPiece(p);
	}
	
	/**
	 * Checks whether the game is over. If there are no
	 * moves for either player on the board. 
	 * @return True if no player can make a move
	 * False otherwise.
	 */
	public boolean isGameOver() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (validMove(i, j, Player.WHITE) 
						|| 
						validMove(i, j, Player.BLACK)) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Checks whether the game is over. If there are no
	 * moves for either player on the board. 
	 * @return True if no player can make a move
	 * False otherwise.
	 */
	public boolean isEndTurn() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (validMove(i, j, player)) {
					return false;
				}
			}
		}
		return true;
	}
	
	@Override
	public final boolean isMove(final Move m) {
		return false;
	}
	
	@Override
	public final boolean isMove(final int x, final int y) {
		if (validMove(x, y, player)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Adjusts the current board to represent the board
	 * after the move has been made in the x++ directions.
	 * @param x position of the move checking
	 * @param y position of the move checking
	 */
	public void makeMoveRight(final int x, final int y) {
		int j;
		OthelloPiece p = new OthelloPiece(player);
		board[x][y] = p;
		if (board[x + 1][y]  
				!= null
				&& board[x + 1][y].getOwner() != player) {
			for (int i = x + 2; i < size; i++) {
				if (board[i][y] == null) {
					break;
				}
				if (board[i][y].getOwner() == player) {
					for (j = i - 1; j > x; j--) {
						board[j][y].switchOwner();
					}
					break;
				}
			}
		}
	}
	
	/**
	 * Adjusts the current board to represent the board
	 * after the move has been made in the x-- directions.
	 * @param x position of the move checking
	 * @param y position of the move checking
	 */
	public void makeMoveLeft(final int x, final int y) {
		int j;
		OthelloPiece p = new OthelloPiece(player);
		board[x][y] = p;
		if (board[x - 1][y] 
				!= null && board[x - 1][y].getOwner() 
				!= player) {
			for (int i = x - 2; i >= 0; i--) {
				if (board[i][y] == null) {
					break;
				}
				if (board[i][y].getOwner() == player) {
					for (j = i + 1; j < x; j++) {
						board[j][y].switchOwner();
					}
					break;
				}
			}
		}
	}
	
	/**
	 * Adjusts the current board to represent the board
	 * after the move has been made in the y++ directions.
	 * @param x position of the move checking
	 * @param y position of the move checking
	 */
	public void makeMoveUp(final int x, final int y) {
		int j;
		OthelloPiece p = new OthelloPiece(player);
		board[x][y] = p;
		if (board[x][y - 1] 
				!= null 
				&& board[x][y - 1].getOwner() != player) {
			for (int i = y - 2; i > 0; i--) {
				if (board[x][i] == null) { 
					break;
				}
				if (board[x][i].getOwner() == player) {
					for (j = i + 1; j < y; j++) {
						board[x][j].switchOwner();
					}
					break;
				}
			}
		}
	}
	
	/**
	 * Adjusts the current board to represent the board
	 * after the move has been made in the y++ directions.
	 * @param x position of the move checking
	 * @param y position of the move checking
	 */
	public void makeMoveDown(final int x, final int y) {
		int j;
		OthelloPiece p = new OthelloPiece(player);
		board[x][y] = p;
		if (board[x][y + 1] 
				!= null 
				&& board[x][y + 1].getOwner() != player) {
			for (int i = y + 2; i < size; i++) {
				if (board[x][i] == null) { 
					break;
				}
				if (board[x][i].getOwner() == player) {
					for (j = i - 1; j > y; j--) {
						board[x][j].switchOwner();
					}
					break;
				}
			}
		}
	}
	
	/**
	 * Adjusts the current board to represent the board
	 * after the move has been made in the y-- directions.
	 * @param x position of the move checking
	 * @param y position of the move checking
	 */
	public void makeMoveDownRight(final int x, final int y) {
		int j = y + 2, m, n;
		OthelloPiece p = new OthelloPiece(player);
		board[x][y] = p;
		if (board[x + 1][y + 1]
				!= null 
				&& board[x + 1][y + 1].getOwner() != player) {
			for (int i = x + 2; i < size; i++) {
				if (j >= size) {
					break;
				}
				if (board[i][j] == null) {
					break;
				}
				if (board[i][j].getOwner() == player) {
					m = i - 1;
					for (n = j - 1; n > y; n--) {
						if (m == x) {
							break;
						}
						board[m][n].switchOwner();
						m--;
					}
					break;
				}
				j++;
			}
		}
	}
	
	/**
	 * Adjusts the current board to represent the board
	 * after the move has been made in the y-- directions.
	 * @param x position of the move checking
	 * @param y position of the move checking
	 */
	public void makeMoveDownLeft(final int x, final int y) {
		int j = y + 2, m, n;
		OthelloPiece p = new OthelloPiece(player);
		board[x][y] = p;
		if (board[x - 1][y + 1]
				!= null 
				&& board[x - 1][y + 1].getOwner() != player) {
			for (int i = x - 2; i >= 0; i--) {
				if (j >= size) {
					break;
				}
				if (board[i][j] == null) {
					break;
				}
				if (board[i][j].getOwner() == player) {
					m = i + 1;
					for (n = j - 1; n > y; n--) {
						if (m == x) { 
							break;
						}
						board[m][n].switchOwner();
						m++;
					}
					break;
				}
				j++;
			}
		}
	}
	
	/**
	 * Adjusts the current board to represent the board
	 * after the move has been made in the y-- directions.
	 * @param x position of the move checking
	 * @param y position of the move checking
	 */
	public void makeMoveUpLeft(final int x, final int y) {
		int j = y - 2, m, n;
		OthelloPiece p = new OthelloPiece(player);
		board[x][y] = p;
		if (board[x - 1][y - 1]
				!= null 
				&& board[x - 1][y - 1].getOwner() != player) {
			for (int i = x - 2; i >= 0; i--) {
				if (j < 0) {
					break;
				}
				if (board[i][j] == null) {
					break;
				}
				if (board[i][j].getOwner() == player) {
					m = i + 1;
					for (n = j + 1; n < y; n++) {
						if (m == x) {
							break;
						}
						board[m][n].switchOwner();
						m++;
					}
					break;
				}
				j--;
			}
		}
	}
	
	/**
	 * Adjusts the current board to represent the board
	 * after the move has been made in the y-- directions.
	 * @param x position of the move checking
	 * @param y position of the move checking
	 */
	public void makeMoveUpRight(final int x, final int y) {
		int j = y - 2, m, n;
		OthelloPiece p = new OthelloPiece(player);
		board[x][y] = p;
		if (board[x + 1][y - 1] 
				!= null 
				&& board[x + 1][y - 1].getOwner() != player) {
			for (int i = x + 2; i < board.length; i++) {
				if (j < 0) {
					break;
				}
				if (board[i][j] == null) {
					break;
				}
				if (board[i][j].getOwner() == player) {
					m = i - 1;
					for (n = j + 1; n < y; n++) {
						if (m == x) { 
							break;
						}
						board[m][n].switchOwner();
						m--;
					}
					break;
				}
				j--;
			}
		}
	}
	
	/**
	 * Adjusts the current board to represent the board
	 * after the move has been made.
	 * @param x position of the move checking
	 * @param y position of the move checking
	 */
	public void makeMove(final int x, final int y) {
		if (!isMove(x, y)) {
			if (this.isEndTurn()) {
				this.nextTurn();
			}
			return;
		}
		
		if (x < size - 2) {
			if (validMoveRight(x, y, player)) {
				makeMoveRight(x, y);
			}
		}
		
		if (x > 2) {
			if (validMoveLeft(x, y, player)) {
				makeMoveLeft(x, y);
			}
		}
		
		if (y > 2) {
			if (validMoveUp(x, y, player)) {
				makeMoveUp(x, y);
			}
		}
		
		if (y < size - 2) {
			if (validMoveDown(x, y, player)) {
				makeMoveDown(x, y);
			}
		}
		
		if (y > 2 && x < size - 2) {
			if (validMoveUpRight(x, y, player)) {
				makeMoveUpRight(x, y);
			}
		}
		
		if (y > 2 && x > 2) {
			if (validMoveUpLeft(x, y, player)) {
				makeMoveUpLeft(x, y);
			}
		}
		
		if (y < size - 2 && x > 2) {
			if (validMoveDownLeft(x, y, player)) {
				makeMoveDownLeft(x, y);
			}
		}
		
		if (y < size - 2 && x < size - 2) {
			if (validMoveDownRight(x, y, player)) {
				makeMoveDownRight(x, y);
			}
		}
		
		this.nextTurn();
		this.gameover = isGameOver();
		if (gameover || isEndTurn()) {
			this.nextTurn();
		}
		
		countPieces();
		
		this.saved = false;
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
			saved = true;
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
			this.board = (OthelloPiece[][]) ostrm.readObject();
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
			System.out.println("Load Attempted.");
		}
	}

	/**
	 * Getter method to inform the display about the status of
	 * the piece at the given location.
	 * @param x Vertical position of the piece.
	 * @param y Horizontal position of the piece.
	 * @return Piece at given location.
	 */
	public OthelloPiece getPiece(final int x, final int y) {
		return this.board[x][y];
	}
	
	/**
	 * Counts the number of pieces that each player has on the board.
	 */
	private void countPieces() {
		whiteCount = 0;
		blackCount = 0;
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if (board[x][y] != null) {
					if (board[x][y].getOwner() 
							== Player.WHITE) {
						whiteCount++;
					} else {
						blackCount++;
					}
				}
			}
		}
	}
	
	/**
	 * Getter method for the number of white pieces on the board.
	 * @return number of white pieces on the board.
	 */
	public int getWhiteCount() {
		return this.whiteCount;
	}
	
	/**
	 * Getter method for the number of black pieces on the board.
	 * @return number of black pieces on the board.
	 */
	public int getBlackCount() {
		return this.blackCount;
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
		this.gameover = false;
		this.createBoard();
		this.player = Player.WHITE;
		this.moves.clear();
		this.countPieces();
	}
	
	/**
	 * Determines whether a player can place a piece at the 
	 * intended location in the x++ direction.  
	 * @return true if the move is to a valid location, false for invalid.
	 * @param x parameter location of current move checking.
	 * @param y parameter location of current move checking.
	 * @param p current player of current move checking.
	 */
	public final boolean validMoveRight(final int x, final int y, 
			final Player p) {
		if (board[x + 1][y]	!= null) { 
			if (board[x + 1][y].getOwner() != p) {
				for (int i = x + 2; i < board.length; i++) {
					if (board[i][y] == null) { 
						break;
					}
					if (board[i][y].getOwner() == p) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Determines whether a player can place a piece at the 
	 * intended location in the x-- direction.  
	 * @return true if the move is to a valid location, false for invalid.
	 * @param x parameter location of current move checking.
	 * @param y parameter location of current move checking.
	 * @param p current player of current move checking.
	 */
	public final boolean validMoveLeft(final int x, final int y, 
			final Player p) {
		if (board[x - 1][y]	!= null) {
			if (board[x - 1][y].getOwner() != p) {

				for (int i = x - 2; i >= 0; i--) {
					if (board[i][y] == null) { 
						break;
					}
					if (board[i][y].getOwner() == p) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Determines whether a player can place a piece at the 
	 * intended location in the y++ direction.  
	 * @return true if the move is to a valid location, false for invalid.
	 * @param x parameter location of current move checking.
	 * @param y parameter location of current move checking.
	 * @param p current player of current move checking.
	 */
	public final boolean validMoveDown(final int x, final int y, 
			final Player p) {
		if (board[x][y + 1]	!= null 
				&& board[x][y + 1].getOwner() != p) {
			for (int i = y + 2; i < board.length; i++) {
				if (board[x][i] == null) {
					break;
				}
				if (board[x][i].getOwner() == p) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Determines whether a player can place a piece at the 
	 * intended location in the y-- direction.  
	 * @return true if the move is to a valid location, false for invalid.
	 * @param x parameter location of current move checking.
	 * @param y parameter location of current move checking.
	 * @param p current player of current move checking.
	 */
	public final boolean validMoveUp(final int x, final int y, 
			final Player p) {
		if (board[x][y - 1]	!= null 
				&& board[x][y - 1].getOwner() != p) {
			for (int i = y - 2; i >= 0; i--) {
				if (board[x][i] == null) {
					break;
				}
				if (board[x][i].getOwner() == p) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Determines whether a player can place a piece at the intended
	 *  location in the x++, y++ direction.  
	 * @return true if the move is to a valid location, false for invalid.
	 * @param x parameter location of current move checking.
	 * @param y parameter location of current move checking.
	 * @param p current player of current move checking.
	 */
	public final boolean validMoveDownRight(final int x, final int y,
			final Player p) {
		int j = y + 2;
		
		if (board[x + 1][y + 1]	!= null 
				&& board[x + 1][y + 1].getOwner() != p) {
			for (int i = x + 2; i < board.length; i++) {
				if (j >= board.length) {
					break;
				}
				if (board[i][j] == null) {
					break;
				}
				if (board[i][j].getOwner() == p) {
					return true;
				}
				j++;
			}
		}
		return false;
	}
	
	/**
	 * Determines whether a player can place a piece at the 
	 * intended location in the x--, y++ direction.  
	 * @return true if the move is to a valid location, false for invalid.
	 * @param x parameter location of current move checking.
	 * @param y parameter location of current move checking. 
	 * @param p current player of current move checking.
	 */
	public final boolean validMoveDownLeft(final int x, final int y, 
			final Player p) {
		int j = y + 2;
		
		if (board[x - 1][y + 1]	!= null 
				&& board[x - 1][y + 1].getOwner() != p) {
			for (int i = x - 2; i >= 0; i--) {
				if (j >= board.length) {
					break;
				}
				if (board[i][j] == null) {
					break;
				}
				if (board[i][j].getOwner() == p) {
					return true;
				}
				j++;
			}
		}
		return false;
	}
	
	/**
	 * Determines whether a player can place a piece at the 
	 * intended location in the x--, y-- direction.  
	 * @return true if the move is to a valid location, false for invalid.
	 * @param x parameter location of current move checking.
	 * @param y parameter location of current move checking. 
	 * @param p current player of current move checking.
	 */
	public final boolean validMoveUpLeft(final int x, final int y,
			final Player p) {
		int j = y - 2;
		
		if (board[x - 1][y - 1]	!= null 
				&& board[x - 1][y - 1].getOwner() != p) {
			for (int i = x - 2; i >= 0; i--) {
				if (j < 0) {
					break;
				}
				if (board[i][j] == null) {
					break;
				}
				if (board[i][j].getOwner() == p) {
					return true;
				}
				j--;
			}
		}
		return false;
	}
	
	/**
	 * Determines whether a player can place a piece at the 
	 * intended location in the x++, y-- direction.  
	 * @return true if the move is to a valid location, false for invalid.
	 * @param x parameter location of current move checking.
	 * @param y parameter location of current move checking.
	 * @param p current player of current move checking.
	 */
	public final boolean validMoveUpRight(final int x, final int y,
			final Player p) {
		int j = y - 2;
		
		if (board[x + 1][y - 1]	!= null 
				&& board[x + 1][y - 1].getOwner() != p) {
			for (int i = x + 2; i < board.length; i++) {
				if (j < 0) {
					break;
				}
				if (board[i][j] == null) {
					break;
				}
				if (board[i][j].getOwner() == p) {
					return true;
				}
				j--;
			}
		}
		return false;
	}
	
	/**
	 * Determines whether a given piece can move to the player determined
	 * position.
	 * @return true if the move is to a valid location, false for invalid.
	 * @param x parameter location of current move checking.
	 * @param y parameter location of current move checking.
	 * @param p current player of current move checking.
	 */	
	public final boolean validMove(final int x, final int y,
			final Player p) {
		
		if (board[x][y] != null) {
			return false;
		}
		
		if (x < size - 2) {
			if (validMoveRight(x, y, p)) {
				return true;
			}
		}
		
		if (x > 2) {
			if (validMoveLeft(x, y, p)) {
				return true;
			}
		}
		
		if (y > 2) {
			if (validMoveUp(x, y, p)) {
				return true;
			}
		}
		
		if (y < size - 2) {
			if (validMoveDown(x, y, p)) {
				return true;
			}
		}
		
		if (y > 2 && x < size - 2) {
			if (validMoveUpRight(x, y, p)) {
				return true;
			}
		}
		
		if (y > 2 && x > 2) {
			if (validMoveUpLeft(x, y, p)) {
				return true;
			}
		}
		
		if (y < size - 2 && x > 2) {
			if (validMoveDownLeft(x, y, p)) {
				return true;
			}
		}
		
		if (y < size - 2 && x < size - 2) {
			if (validMoveDownRight(x, y, p)) {
				return true;
			}
		}
		
		return false;
	}
	/**
	 * returns the current player.
	 * @return returns the current player
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * returns the board size.
	 * @return the board size.
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Getter method for the resulting board for the
	 * provided Move.
	 * @param m Move to be inspected.
	 * @return Board for the provided Move object.
	 */
	public OthelloPiece[][] getResultBoard(final OthelloMove m) {
		if (validMove(m)) {
			return this.move.get(m);
		} else {
			return null;
		}
	}
	
	/**
	 * Determines whether or not the piece at that position on
	 * the board has a move this turn.
	 * @param x vertical location of piece.
	 * @param y horizontal location of piece.
	 * @return True if the piece has a move, false otherwise.
	 */
	public boolean hasMove(final int x, final int y) {
		for (OthelloMove m : moves) {
			if (m.getX() == x && m.getY() == y) {
				return true;
			}
		}
		return false;
	}

}
