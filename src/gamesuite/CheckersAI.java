package gamesuite;

/**
 * AI class for checkers implementation.
 * @author Daniel Cummings
 * @version 0.1
 */
public class CheckersAI {
	/** MinMax tree for moves in checkers game. */
	private Node<Move> minmax;
	/** Determines the color of pieces for the AI. */
	private Player color;
	/** Model being used for evaluation. */
	private CheckersModel model;
	
	/**
	 * AI constructor for initial setup.
	 * @param p Color for the AI.
	 * @param m Model for the AI to use.
	 */
	public CheckersAI(final Player p, final CheckersModel m) {
		this.minmax = new Node<Move>(null, null);
		this.model = m;
		this.color = p;
	}
	
	/**
	 * Helper method to generate the move tree.
	 * @param depth Depth of the tree
	 * @param b Board being evaluated
	 * @param r Root node of the tree
	 */
	private void generateTree(final int depth,
			final CheckersPiece[][] b, final Node<Move> r) {
		
	}
}
