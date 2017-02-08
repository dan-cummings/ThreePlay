package gamesuite;

/**
 * Abstract Class to be extended by individual game types.
 * Each game type will define which game it is, how the
 * game is won, rules for valid movements on the board,
 * as well as storing the current board and player turn.
 * @author Daniel Cummings
 * @version 0.1
 */
public abstract class GameLogic implements IGameLogic {
	
	/** Stores value of player making move. */
	private Player player;
	/** Current board status of the game. */
	private IPiece[][] board;
	/** Which game the user is playing. */
	private GameType type;
	/** Size of the board. */
	private int size;
	
	/**
	 * Default constructor for GameLogic.
	 * @param gt Which game type the player has chosen.
	 * @param s Size of the game board.
	 */
	public GameLogic(final GameType gt, final int s) {
		this.setGameType(gt);
		this.setSize(s);
	}
	
	@Override
	public abstract boolean isGameOver();

	@Override
	public abstract boolean isMove(Move m, IPiece[][] b);
	
	/**
	 * Sets the size parameters for the game board.
	 * @param s The size of the game board.
	 */
	private void setSize(final int s) {
		this.size = s;
	}
	
	/**
	 * For access to the size of the game board.
	 * @return The size of the game board.
	 */
	public int getSize() {
		return this.size;
	}

	/** 
	 * Sets the game type for the game model being played.
	 * @param gt enumeration of which game the user selected.
	 */
	private void setGameType(final GameType gt) {
		this.type = gt;
	}
	
	/**
	 * Gets the current type of game being played.
	 * @return Which type of game is being played.
	 */
	public GameType getGameType() { 
		return this.type;
	}
}
