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
 * @author Jaden Sella
 */
public class Othello implements IGameLogic {
	
	/** Current board status of the game. */
	private OthelloPiece[][] board;
	
	/** Size of game board. */
	private int size;
	
	/** ArrayList of possible moves. */
	private ArrayList<IPiece> moves;
	
	/** Determines whether or not the game is over. */
	private boolean gameover;
	
	/** Has the game been saved. */
	private boolean saved; 

	/** The current player. Othello always starts with black*/
	private Player player;
	
	/** Count for the number of pieces for player. */
	private int whiteCount, blackCount;
	
	/** 
	 * Constructor for Othello, begins Othello game. 
	 */
	public Othello() {
		this.size = 8;
		this.gameover = false;
		this.createBoard();
		this.player = Player.BLACK;
		this.moves = new ArrayList<IPiece>();
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
	
	/**
	 * Checks whether the game is over. If there are no
	 * moves for either player on the board. 
	 * @return True if no player can make a move
	 * False otherwise.
	 */
	public boolean isGameOver() {
		boolean finished = true;
		// Check if all spaces are filled, if a space is not filled
		// then checks if that place has a valid move by either player
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (this.board[i][j].getOwner()
						== null) {
					if (board[i][j].validMove(
							i, j, board, player)) {
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
		return false;
	}
	
	@Override
	public final boolean isMove(final int x, final int y) {
		if (board[x][y].validMove(x, y, board, player)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Adjusts the current board to represent the board
	 * after the move has been made.
	 * @param x position of the move checking
	 * @param y position of the move checking
	 */
	public void makeMove(final int x, final int y) {
		if (!this.isMove(x, y)) {
			return;
		}
		int j, m, n;
		if (board[x + 1][y].getOwner() 
				!= null
				&& board[x + 1][y].getOwner() != player) {
			for (int i = x + 2; i < size; i++) {
				if (board[i][y].getOwner() == null) {
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
		if (board[x - 1][y].getOwner()
				!= null && board[x - 1][y].getOwner() 
				!= player) {
			for (int i = x - 2; i >= 0; i--) {
				if (board[i][y].getOwner() == null) {
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
		if (board[x][y + 1].getOwner() 
				!= null 
				&& board[x][y + 1].getOwner() != player) {
			for (int i = y + 2; i < size; i++) {
				if (board[x][i].getOwner() == null) { 
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
		
		if (board[x][y - 1].getOwner() 
				!= null 
				&& board[x][y - 1].getOwner() != player) {
			for (int i = y - 2; i >= 0; i--) {
				if (board[x][i].getOwner() == null) {
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
		j = y + 2;
		if (board[x + 1][y + 1].getOwner()
				!= null 
				&& board[x + 1][y + 1].getOwner() != player) {
			for (int i = x + 2; i < size; i++) {
				if (j >= size) {
					break;
				}
				if (board[i][j].getOwner() == null) {
					break;
				}
				if (board[i][j].getOwner() == player) {
					m = i - 1;
					for (n = i - 1; n > y; n--) {
						if (m == x) {
							break;
						}
						board[i][j].switchOwner();
						m--;
					}
					break;
				}
				j++;
			}
		}
		j = y + 2;
		if (board[x - 1][y + 1].getOwner()
				!= null 
				&& board[x - 1][y + 1].getOwner() != player) {
			for (int i = x - 2; i >= 0; i--) {
				if (j >= size) {
					break;
				}
				if (board[i][j].getOwner() == null) {
					break;
				}
				if (board[i][j].getOwner() == player) {
					m = i + 1;
					for (n = i - 1; n > y; n--) {
						if (m == x) { 
							break;
						}
						board[i][j].switchOwner();
						m++;
					}
					break;
				}
				j++;
			}
		}
		j = y - 2;
		if (board[x - 1][y - 1].getOwner() 
				!= null 
				&& board[x - 1][y - 1].getOwner() != player) {
			for (int i = x - 2; i >= 0; i--) {
				if (j < 0) {
					break;
				}
				if (board[i][j].getOwner() == null) {
					break;
				}
				if (board[i][j].getOwner() == player) {
					m = i + 1;
					for (n = i + 1; n < y; n++) {
						if (m == x) {
							break;
						}
						board[i][j].switchOwner();
						m++;
					}
					break;
				}
				j--;
			}
		}
		j = y - 2;
		if (board[x + 1][y - 1].getOwner() 
				!= null 
				&& board[x + 1][y - 1].getOwner() != player) {
			for (int i = x + 2; i < board.length; i++) {
				if (j < 0) {
					break;
				}
				if (board[i][j].getOwner() == null) {
					break;
				}
				if (board[i][j].getOwner() == player) {
					m = i - 1;
					for (n = i + 1; n < y; n++) {
						if (m == x) { 
							break;
						}
						board[i][j].switchOwner();
						m--;
					}
					break;
				}
				j--;
			}
		}
		
		this.nextTurn();
		this.gameover = this.isGameOver();
		this.nextTurn();
		if (moves.isEmpty() && !gameover) {
			this.nextTurn();
		}
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
		return board[x][y];
	}
	
	/**
	 * Switches current player to allow for moves.
	 */
	public void changePlayer() {
		if (player == Player.BLACK) {
			player = Player.WHITE;
		} else {
			player = Player.BLACK;
		}
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
	}
}
