package gamesuite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

/**
 * Logic for the game of checkers. Model allows users to make moves,
 * forces jumps, controls current player, saves the game, loads the
 * game, and determines when the game is over. The model is called
 * from the controller.
 * @author Daniel Cummings
 * @version 1.0
 */
public class CheckersController {

	/** Current board status of the game. */
	private CheckersModel model;
	/** List of views that have authority. */
	private List<JPanel> views;
	/** Has the game been saved. */
	private boolean saved;

	/**
	 * Constructor for checkers game.
	 * @param view The view that is allowed access to the controller.
	 */
	public CheckersController(final JPanel view) {
		this.model = new CheckersModel();
		this.views = new ArrayList<JPanel>();
		this.views.add(view);
	}

	/**
	 * Checks whether the game is over. If there are no
	 * pieces for the player remaining on the board the game is over.
	 * @return True if no current player pieces are on the board.
	 * False otherwise.
	 */
	public boolean isGameOver() {
		return model.isGameOver();
	}

	/**
	 * Checks whether the move exists in the current state.
	 * @param fx Vertical position the piece is from.
	 * @param fy Horizontal position the piece is from.
	 * @param tx Vertical position the piece is moving to.
	 * @param ty Horizontal position the piece is moving to.
	 * @return True if the piece can move to the location, else false.
	 */
	public final boolean isMove(final int fx,
			final int fy, final int tx, final int ty) {
		return this.model.checkMove(new Move(fx, fy, tx, ty));
	}
	
	/**
	 * Checks whether the move can be made and if so
	 * commits that move on the current state.
	 * @param fx Vertical position the piece is from.
	 * @param fy Horizontal position the piece is from.
	 * @param tx Vertical position the piece is moving to.
	 * @param ty Horizontal position the piece is moving to.
	 * @return True if the piece can move to that location
	 * and move is made.
	 */
	public boolean makeMove(final int fx,
			final int fy, final int tx, final int ty) {
		return this.model.makeMove(new Move(fx, fy, tx, ty));
	}
	
	/**
	 * Writes objects into the specified file. The data is
	 * stored into the file as a corresponding state of the game.
	 * @param filename Name of file to save the game state.
	 * @throws Exception contains information about save error.
	 */
	public final void saveState(final String filename)  throws Exception {
		try {
			FileOutputStream strm = new FileOutputStream(filename);
			ObjectOutputStream ostrm = new ObjectOutputStream(strm);
			ostrm.writeObject(model);
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

	
	/**
	 * Reads objects from the specified file into the game.
	 * Allowing users to load from saved states.
	 * @param filename Name of file to load game state.
	 * @throws Exception contains information about load error.
	 */
	public final void loadState(final String filename) throws Exception {
		try {
			FileInputStream strm = new FileInputStream(filename);
			ObjectInputStream ostrm = new ObjectInputStream(strm);
			Object o = ostrm.readObject();
			if (o instanceof CheckersModel) {
				this.model = (CheckersModel) o;
			}
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
	 * Getter for the piece at requested location.
	 * @param x vertical position of piece.
	 * @param y horizontal position of piece.
	 * @return piece at given location.
	 */
	public CheckersPiece getPiece(final int x, final int y) {
		return this.model.getPiece(x, y);
	}
	
	/**
	 * Calls to the model to see if that location has a 
	 * valid move.
	 * @param x Vertical position.
	 * @param y Horizontal position.
	 * @return True if position has a move.
	 */
	public boolean hasMove(final int x, final int y) {
		return this.model.hasMove(x, y);
	}

	/**
	 * Getter for the current player.
	 * @return current player who can move.
	 */
	public Player getPlayer() {
		return this.model.getPlayer();
	}
	
	/**
	 * Getter for status of game in stalemate.
	 * @return True if current player cannot move.
	 */
	public boolean isStalemate() {
		return model.getStalemate();
	}
	
	/**
	 * Getter method for the game completion status of the game.
	 * @return True if the game is determined to be finished.
	 */
	public boolean gameOver() {
		return model.isGameOver();
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
		this.model = new CheckersModel();
	}
	
	/**
	 * REPLACE IN MODEL.
	 * @param test integer for test being run.
	 *
	public void setup(final int test) {
			if (test == 1) {
				board = new CheckersPiece[8][8];
				moves.clear();
				player = Player.BLACK;
				checkJumps();
			} else if (test == 2) {
				board = new CheckersPiece[8][8];
				player = Player.BLACK;
				moves.clear();
				jumps.clear();
				board[1][1] = new CheckersPiece(player);
				board[1][1].setKinged(true);
				board[2][2] = new CheckersPiece(Player.WHITE);
				checkJumps();
			} else if (test == 3) {
				board = new CheckersPiece[8][8];
				player = Player.BLACK;
				moves.clear();
				jumps.clear();
				board[1][1] = new CheckersPiece(player);
				board[1][1].setKinged(true);
				board[2][2] = new CheckersPiece(Player.WHITE);
				board[4][4] = new CheckersPiece(Player.WHITE);
				checkJumps();
			}
	}
	*/
	/** Unused Method. */
}
