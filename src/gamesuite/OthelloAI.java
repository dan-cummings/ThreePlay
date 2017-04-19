package gamesuite;

import java.util.ArrayList;
import org.apache.commons.lang3.SerializationUtils;

/**
 * AI implementation for Checkers game. The class utilizes
 * Minimax algorithms to generate the appropriate move at the 
 * designated skill level for the AI. 
 * @author Jaden Sella
 * @version 0.1
 */
public class OthelloAI {

	/** MinMax tree for moves in checkers game. */
	private Node<OthelloMove> minmax;
	/** Determines the color of pieces for the AI. */
	private static Player color = Player.WHITE;
	/** Model being used for evaluation. */
	private Othello model;
	/** Sets the maximum depth the move search tree will go. */
	private static int maxDepth = 3;
	/** Whether the it is the first move of the game. */
	private boolean firstMove;
	
	/**
	 * AI constructor to set skill level provide
	 * the game state for the AI.
	 * @param m Model for the AI to use.
	 */
	public OthelloAI(final Othello m) {
		this.minmax = new Node<OthelloMove>(null, null);
		this.model = m;
		this.firstMove = true;
	}
	
	/**
	 * Method generates and evaluates the possible moves
	 * for the AI. When one is found, the move is executed.
	 */
	public void yourMove() {
		if (firstMove) {
			ArrayList<OthelloMove> moves = this.model.getMoveList();
			int rand = (int) (Math.random() * (moves.size() - 1));
			OthelloMove disMove = moves.get(rand);
			this.model.makeMove(disMove);
			minmax.clear();
			this.firstMove = false;
			return;
		}
		//Create the tree.
		this.generateTree(1, minmax, model);
		//Node to contain the best move.
		Node<OthelloMove> disMove = new Node<OthelloMove>(null, null);
		//List of all nodes connected to min-max.
		ArrayList<Node<OthelloMove>> moves =
				(ArrayList<Node<OthelloMove>>) minmax.getLeaves();
		//Heads down each subtree evaluating for min-max and
		//setting each nodes value to the max of that subtree.
		moves.parallelStream().forEachOrdered(
				e -> e.setValue(miniMaxDatTree(e, 1)));
		int max = Integer.MIN_VALUE;
		//Find the maximum for possible moves this turn.
		for (Node<OthelloMove> m : moves) {
			if (m.getValue() == max) {
				if (Math.random() > .5) {
					disMove = m;
				}
			} else if (m.getValue() > max) {
				disMove = m;
			}
		}
		this.model.makeMove(disMove.getData());
		minmax.clear();
	}
	
	/**
	 * Helper method that implements a minimax algorithm
	 * to evaluate the given tree for the best possible
	 * move the AI has. Once the subtree has been evaluated the
	 * nodes value is returned to the caller.
	 * @param r Root node for the subtree being evaluated.
	 * @param depth The depth of the current subtree.
	 * @return The value of the node if r is a leaf or the depth has
	 * been reached. Otherwise returns either the max or minimum value
	 * of the nodes in the subtree of r depending on the depth.
	 */
	private int miniMaxDatTree(final Node<OthelloMove> r, final int depth) {
		if (r.isLeaf()) {
			return r.getValue();
		}
		ArrayList<Node<OthelloMove>> evalList =
				(ArrayList<Node<OthelloMove>>) r.getLeaves();
		//max
		if ((depth & 1) == 1) {
			int value = Integer.MIN_VALUE;
			int max = Integer.MIN_VALUE;
			for (Node<OthelloMove> m : evalList) {
				value = miniMaxDatTree(m, depth + 1);
				if (value == max) {
					if (Math.random() > 0.5) {
						max = value;
					}
				} else if (value > max) {
					max = value;
				}
			}
			return max;
		} else {
			int value = Integer.MAX_VALUE;
			int min = Integer.MAX_VALUE;
			for (Node<OthelloMove> m : evalList) {
				value = miniMaxDatTree(m, depth + 1);
				if (value == min) {
					if (Math.random() > 0.5) {
						min = value;
					}
				} else if (value < min) {
					min = value;
				}
			}
			return min;
		}
	}
	
	/**
	 * Constructs move tree for current turn.
	 * @param depth Depth of the tree
	 * @param r Root node of the tree
	 * @param mod Game state.
	 */
	private void generateTree(final int depth, final Node<OthelloMove> r,
			final Othello mod) {
		Othello hold;
		ArrayList<OthelloMove> list = mod.getMoveList();
		if (depth < OthelloAI.maxDepth) {
			//Adds moves for the turn on the tree.
			list.stream().forEach(e -> r.addleaf(e));
			//Iterates over the list to evaluate response moves to
			// each move in the list.
			OthelloMove m = null;
			for (int i = 0; i < list.size(); i++) {
				hold = SerializationUtils.clone(mod);
				m = list.get(i);
				//Puts model in the status after the move.
				hold.makeMove(m);
				//Recursively moves to the next level in the
				//tree.
				this.generateTree(depth + 1,
						r.getleaf(m), hold);
				//Returns model to the status of before move.
			}
		} else if (depth == OthelloAI.maxDepth) {
			list.stream().forEach(e -> r.addleaf(e));
			//Applies static analysis to each board and sets the
			//value of the board into the node.
			list.stream().forEach(
					e -> r.getleaf(e).setValue(
							analyzeStaticBoard(
					mod.getResultBoard(e))));
			
		} else {
			return;
		}
	}
	
	/**
	 * Method to analyze the given board on a function which returns the 
	 * boards value to the caller.
	 * @param board The board to apply the function against.
	 * @return The derived value of the given board.
	 */
	private int analyzeStaticBoard(OthelloPiece[][] board) {
		int posVal = 0;
		int negVal = 0;
		int locVal = 0;
		OthelloPiece temp;
		
		if(board == null) return 0;
		
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board.length; y++) {
				if (board[x][y] != null) {
					locVal = (Math.abs((x + 1) - 4)
							+ Math.abs((y + 1) - 4))
							/ 2;
					//Average distance to the center.
					temp = board[x][y];
					if (temp.getOwner()
							!= OthelloAI.color) {
						posVal += locVal + x;

					} else {
						negVal += locVal + (7 - x);
					}
				}
			}
		}
		return (posVal - negVal);
	}
	

	/**
	 * Setter method to allow the AI access to the
	 * current game state.
	 * @param m Current game state.
	 */
	public void setModel(final Othello m) {
		this.model = m;
	}
}
