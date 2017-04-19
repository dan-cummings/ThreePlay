package gamesuite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Stack;
import org.apache.commons.lang3.SerializationUtils;

/**
 * Logic for the game of checkers. Model allows users to make moves,
 * forces jumps, controls current player, saves the game, loads the
 * game, and determines when the game is over. The model is called
 * from the controller.
 * @author Daniel Cummings
 * @version 2.0
 */
public class OthelloController implements IGameLogic {

	/** Current board status of the game. */
	private Othello model;
	/** Has the game been saved. */
	private boolean saved;
	/** Stack to store game states if an undo is made. */
	private Stack<Othello> redo;
	/** Computer player for the game. */
	private OthelloAI comp;
	/** Field to determine whether the game being played is
	 * against a computer.
	 */
	private boolean isComp;
	/** Allows users to move back through previous board states. */
	private Stack<Othello> undo;
	
	/**
	 * Constructor for checkers game.
	 */
	public OthelloController() {
		this.model = new Othello();
		this.redo = new Stack<Othello>();
		this.isComp = false;
		undo = new Stack<Othello>();
		redo = new Stack<Othello>();
		this.store();
	}
	
	/** Allows user to set whether or not they are
	 * playing against an AI.
	 * @param ai True if this is an AI game.
	 */
	public void setAI(final boolean ai) {
		if (ai) {
			this.isComp = true;
			this.comp = new OthelloAI(this.model);
		}
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
			final int fy) {
		return this.model.validMove(new OthelloMove(fx, fy));
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
			final int fy) {
		 boolean made = this.model.makeMove(new OthelloMove(fx, fy));
		 if (made) {
			 if (isComp && !model.isGameOver()) {
				 comp.yourMove();
			 }
			 this.store();
		 }
		 return made;
	}
	
	/**
	 * Method to recover the most previous game state in memory.
	 * If game is at the beginning there are no such states and
	 * the method will not recover any state.
	 */
	public void undo() {
		if (undo.isEmpty()) {
			return;
		} else {
			redo.push(this.model);
			this.model = undo.pop();
			if (isComp) {
				comp.setModel(this.model);
			}
		}
	}
	
	/**
	 * Method attempt to recover a game state that existed
	 * before an undo was executed. If an undo is executed and
	 * a new move is made the states cannot be recovered.
	 * (Utilizes Apache Commons to clone object.)
	 */
	public void redo() {
		if (redo.isEmpty()) {
			return;
		} else {
			undo.push(SerializationUtils.clone(this.model));
			this.model = redo.pop();
			if (isComp) {
				comp.setModel(this.model);
			}
		}
	}
	
	/**
	 * Helper method stores board states into a file
	 * This file is used to get states of the game
	 * when undo is called. (Utilizes Apache Commons to 
	 * clone object.)
	 */
	private void store() {
		undo.push(SerializationUtils.clone(this.model));
		if (!redo.isEmpty()) {
			redo.clear();
		}
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
			ostrm.writeBoolean(this.isComp);
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
			if (o instanceof Othello) {
				this.model = (Othello) o;
			}
			this.isComp = ostrm.readBoolean();
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
			if (isComp) {
				this.comp = 
				new OthelloAI(this.model);
			}
		}
	}

	/**
	 * Getter for the piece at requested location.
	 * @param x vertical position of piece.
	 * @param y horizontal position of piece.
	 * @return piece at given location.
	 */
	public OthelloPiece getPiece(final int x, final int y) {
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
		this.model = new Othello();
		if (this.isComp) {
			comp = new OthelloAI(this.model);
		}
		this.store();
	}

	/** UNUSED METHODS. */
	@Override
	public final boolean isMove(final Move m) {
		return false;
	}
	
}
