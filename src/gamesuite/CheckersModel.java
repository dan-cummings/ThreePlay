package gamesuite;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import org.apache.commons.lang3.SerializationUtils;

/**
 * Checker models is a persistent collection of values and objects
 * which determines the state of the checkers game.
 * (Class utilizes Apache Commons.) 
 * @author Daniel Cummings
 * @version 2.0
 */
public class CheckersModel implements Serializable {

	/** Default serial coding. */
	private static final long serialVersionUID = 1L;
	/** Board represented as piece objects. */
	private CheckersPiece[][] board;
	/** Relates a move to a board state. */
	private Map<Move, CheckersPiece[][]> move;
	/** Collection of moves available on turn. */
	private List<Move> moves;
	/** Tree structure to hold jump chains available. */
	private Node<Move> jumpTree;
	/** Tells whether the game is over or not. */
	private boolean gameover;
	/** Tells whether the game is in stalemate. */
	private boolean stalemate;
	/** The player who is moving this turn. */
	private Player player;
	/** Holder for the last move tried on the board. */
	private Move tried;

	/**
	 * Constructor for the Checkers model which generates
	 * pieces and places them into location on the board.
	 */
	public CheckersModel() {
		this.move = new HashMap<Move, CheckersPiece[][]>();
		this.moves = new ArrayList<Move>();
		this.gameover = false;
		this.stalemate = false;
		this.player = Player.WHITE;
		this.jumpTree = new Node<Move>(null, null);
		createBoard();
		findMoves();
	}

	/**
	 * Helper method to create a list of moves and store their
	 * subsequent board states.
	 */
	private void findMoves() {
		//Generates all possible moves for the board state and player.
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				if (board[x][y] != null) { 
				//if the piece is owned by current player.
					if (board[x][y].getOwner() == player) {
						generateJumps(x, y,
						jumpTree, board);
					}
				}
			}
		}
		// if the tree is not empty, concat jumps.
		if (!jumpTree.isLeaf()) {
			concatJumps();
			jumpTree.clear();
		} else {
			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) {
					if (board[x][y] != null) { 
				//if the piece is owned by current player.
						if (board[x][y].getOwner()
								== player) {
							generateStdMove(x, y,
									board);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Private helper method to concatenate a series of jumps
	 * into a single move. Boards are stored into map.
	 */
	private void concatJumps() {
		
		//Get depth of the last nodes in the tree.
		int max = jumpTree.calcDepth(jumpTree) - 1;
		
		//Stack to store Nodes into.
		Stack<Node<Move>> stack = new Stack<Node<Move>>();
		
		//Adds nodes to the stack.
		jumpTree.findAtDepth(max, stack);
		
		//ArrayList for new concatenated jumps.
		Stack<Move> tempMoves = new Stack<Move>();
		
		//ArrayList to temporarily store boards.
		Stack<CheckersPiece[][]> boards =
				new Stack<CheckersPiece[][]>();

		for (Node<Move> temp : stack) {
			Move last = temp.getData();
			while (temp.getRoot() != jumpTree) {
				temp = temp.getRoot();
				}
			Move first = temp.getData();
			tempMoves.push(new Move(first.getFromX(),
					first.getFromY(), last.getToX(),
					last.getToY()));
			boards.push(move.get(last));
		}
		//Clears possible moves on the board.
		move.clear();
		//Adds any moves generated from process to the 
		moves.addAll(tempMoves);
		while (!tempMoves.isEmpty()) {
			move.put(tempMoves.pop(), boards.pop());
		}
		
	}
	/**
	 * Helper method to calculate all of the possible jump moves including
	 * jumps that move more than one space.
	 * @param x Vertical position of checkers piece.
	 * @param y Horizontal position of checkers piece.
	 * @param root Root node of the subtree.
	 * @param tboard Board to be analyzed.
	 */
	private void generateJumps(final int x, final int y,
				final Node<Move> root,
				final CheckersPiece[][] tboard) {
		Node<Move> temp;
		Move m = new Move(x, y, x + 2, y + 2);
		//Checks down to the right.
		if (isMove(m, tboard)) {
			temp = new Node<Move>(m, root);
			root.addleaf(temp);
			move.put(m, commitMove(m, tboard));
			generateJumps(m.getToX(), m.getToY(),
					temp, commitMove(m, tboard));
		}
		//Checks up to the left.
		m = new Move(x, y, x - 2, y - 2);
		if (isMove(m, tboard)) {
			temp = new Node<Move>(m, root);
			root.addleaf(temp);
			move.put(m, commitMove(m, tboard));
			generateJumps(m.getToX(), m.getToY(),
					temp, commitMove(m, tboard));
		}
		//Checks down to the left.
		m = new Move(x, y, x + 2, y - 2);
		if (isMove(m, tboard)) {
			temp = new Node<Move>(m, root);
			root.addleaf(temp);
			move.put(m, commitMove(m, tboard));
			generateJumps(m.getToX(), m.getToY(),
					temp, commitMove(m, tboard));
		}
		//Checks  up to the right.
		m = new Move(x, y, x - 2, y + 2);
		if (isMove(m, tboard)) {
			temp = new Node<Move>(m, root);
			root.addleaf(temp);
			move.put(m, commitMove(m, tboard));
			generateJumps(m.getToX(), m.getToY(),
					temp, commitMove(m, tboard));
		}
	}

	/**
	 * Finds standard moves available for the piece at the location.
	 * @param x Vertical position of the piece.
	 * @param y Horizontal position of the piece.
	 * @param b Board to check moves on.
	 */
	private void generateStdMove(final int x, final int y,
			final CheckersPiece[][] b) {
		//Checks down to the right.
		Move m = new Move(x, y, x + 1, y + 1);
		if (isMove(m, b)) {
			moves.add(m);
			move.put(m, commitMove(m, b));
		}
		//checks down to the left.
		m = new Move(x, y, x + 1, y - 1);
		if (isMove(m, b)) {
			moves.add(m);
			move.put(m, commitMove(m, b));
		}
		//checks up to the right.
		m = new Move(x, y, x - 1, y + 1);
		if (isMove(m, b)) {
			moves.add(m);
			move.put(m, commitMove(m, b));
		}
		//checks up to the left.
		m = new Move(x, y, x - 1, y - 1);
		if (isMove(m, b)) {
			moves.add(m);
			move.put(m, commitMove(m, b));
		}
	}

	/**
	 * Changes the temporary board to the state following the
	 * given move.
	 * @param m Move to be applied to the temporary board.
	 * @param b Board to make the move on.
	 * @return The resulting board from the move.
	 */
	private CheckersPiece[][] commitMove(final Move m,
			final CheckersPiece[][] b) {
		CheckersPiece[][] temp = new CheckersPiece[8][8];
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				if (b[x][y] != null) {
					temp[x][y] = 
					SerializationUtils.clone(b[x][y]);
				}
			}
		}
		int tx = m.getToX();
		int ty = m.getToY();
		int fx = m.getFromX();
		int fy = m.getFromY();
		if (Math.abs(tx - fx) == 2 
				&& Math.abs(ty - fy) == 2) {
			if (tx < fx) {
				if (ty < fy) {
					//Jump forward to the right.
					temp[tx][ty] = temp[fx][fy];
					temp[tx + 1][ty + 1] = null;
					temp[fx][fy] = null;
				} else {
					//Jump forward to the left.
					temp[tx][ty] = temp[fx][fy];
					temp[tx + 1][ty - 1] = null;
					temp[fx][fy] = null;
				}
			} else {
				if (ty < fy) {
					//jump down to the left.
					temp[tx][ty] = temp[fx][fy];
					temp[tx - 1][ty + 1] = null;
					temp[fx][fy] = null;
				} else {
					//jump down to the right.
					temp[tx][ty] = temp[fx][fy];
					temp[tx - 1][ty - 1] = null;
					temp[fx][fy] = null;
				}
			}
		} else {
			temp[tx][ty] = temp[fx][fy];
			temp[fx][fy] = null;
			
		}
		//Checks whether the piece is a king after move.
		if (tx == 7 && temp[tx][ty].getOwner() 
			== Player.BLACK && !temp[tx][ty].isKinged()) {
			temp[tx][ty].setKinged(true);
		} else if (tx == 0 && temp[tx][ty].getOwner()
			== Player.WHITE && !temp[tx][ty].isKinged()) {
			temp[tx][ty].setKinged(true);
		}
		return temp;
	}
	
	/**
	 * Method to check if a move exists. If it does
	 * the move is made and the next turn is activated.
	 * @param m Move that is being made.
	 * @return True if move can be made.
	 */
	public boolean makeMove(final Move m) {
		if (this.checkMove(m)) {
			this.board = this.move.get(this.tried);
			this.nextTurn();
			this.moves.clear();
			this.move.clear();
			this.findMoves();
			this.gameover = this.isGameOver();
			if (!this.gameover) {
				this.stalemate = this.isStalemate();
			}
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Generates the initial board for the beginning of the game.
	 */
	private void createBoard() {
		this.board = new CheckersPiece[8][8];
		//setup black pieces
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 8; j++) {
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
		for (int i = 5; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
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
	 * Checks whether the current player has pieces left.
	 * @return True if current player has no pieces, false otherwise.
	 */
	public boolean isGameOver() {
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				if (board[x][y] != null) {
					if (board[x][y].getOwner() == player) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Returns the validity of the movement.
	 * @param m Move being assessed.
	 * @param b Board to check the move on.
	 * @return True if the move can be made on the board, false otherwise.
	 */
	public boolean isMove(final Move m, final CheckersPiece[][] b) {
		return b[m.getFromX()][m.getFromY()].validMove(m, b);
	}
	
	/**
	 * Checks whether the moves list contains the instance of move.
	 * @param m The move being attempted.
	 * @return True if the moves list contains the move.
	 */
	public boolean checkMove(final Move m) {
		for (Move in : moves) {
			if (m.getToX() == in.getToX()
					&& m.getToY() == in.getToY()
					&& m.getFromX() == in.getFromX()
					&& m.getFromY() == in.getFromY()) {
				this.tried = in;
				return true;
			}
		}
		return false;
	}

	/**
	 * Switches the current player.
	 */
	public void nextTurn() {
		if (player == Player.BLACK) {
			player = Player.WHITE;
		} else {
			player = Player.BLACK;
		}
	}

	/**
	 * Method returns boolean for whether the game is in stalemate.
	 * @return True if board is in stalemate, false otherwise.
	 */
	private boolean isStalemate() {
		return moves.isEmpty();
	}
	
	/**
	 * Getter method for the stalemate boolean.
	 * @return True if the game is in stalemate, otherwise false.
	 */
	public boolean getStalemate() {
		return this.stalemate;
	}
	
	/**
	 * Method to get the checkers piece at provided index location.
	 * @param x Vertical index for piece.
	 * @param y Horizontal index for piece.
	 * @return The piece at the indexed location on board.
	 */
	public CheckersPiece getPiece(final int x, final int y) {
		return this.board[x][y];
	}
	
	/**
	 * Determines whether or not the piece at that position on
	 * the board has a move this turn.
	 * @param x vertical location of piece.
	 * @param y horizontal location of piece.
	 * @return True if the piece has a move, false otherwise.
	 */
	public boolean hasMove(final int x, final int y) {
		for (Move m : moves) {
			if (m.getFromX() == x && m.getFromY() == y) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Getter method for the list of moves.
	 * @return ArrayList of Move objects.
	 */
	public ArrayList<Move> getMoveList() {
		return (ArrayList<Move>) this.moves;
	}
	
	/**
	 * Getter method for the resulting board for the
	 * provided Move.
	 * @param m Move to be inspected.
	 * @return Board for the provided Move object.
	 */
	public CheckersPiece[][] getResultBoard(final Move m) {
		if (checkMove(m)) {
			return this.move.get(m);
		} else {
			return null;
		}
	}

	/**
	 * Getter method for the current player.
	 * @return The current player.
	 */
	public Player getPlayer() {
		return this.player;
	}
	
	/**
	 * Method for testing the model class.
	 * @param s String to decide which test type this is.
	 */
	public void testSetup(final String s) {
		if (s.equals("Stalemate")) {
			board = new CheckersPiece[8][8];
			board[0][0] = new CheckersPiece(Player.WHITE);
			board[7][7] = new CheckersPiece(Player.BLACK);
			this.moves.clear();
			this.move.clear();
			findMoves();
			stalemate = isStalemate();
		} else if (s.equals("MultiJump1")) {
			board = new CheckersPiece[8][8];
			this.nextTurn();
			board[0][0] = new CheckersPiece(Player.BLACK);
			board[1][1] = new CheckersPiece(Player.WHITE);
			board[3][3] = new CheckersPiece(Player.WHITE);
			this.moves.clear();
			this.move.clear();
			this.findMoves();
		} else if (s.equals("MultiJump2")) {
			board = new CheckersPiece[8][8];
			this.nextTurn();
			this.moves.clear();
			this.move.clear();
			board[0][0] = new CheckersPiece(Player.BLACK);
			board[0][0].setKinged(true);
			board[1][1] = new CheckersPiece(Player.WHITE);
			board[1][3] = new CheckersPiece(Player.WHITE);
			this.findMoves();
		} else if (s.equals("MultiJump3")) {
			board = new CheckersPiece[8][8];
			this.nextTurn();
			this.moves.clear();
			this.move.clear();
			board[0][0] = new CheckersPiece(Player.BLACK);
			board[0][0].setKinged(true);
			board[1][1] = new CheckersPiece(Player.WHITE);
			board[1][3] = new CheckersPiece(Player.WHITE);
			board[3][3] = new CheckersPiece(Player.WHITE);
			board[5][5] = new CheckersPiece(Player.WHITE);
			this.findMoves();
		} else {
			board = new CheckersPiece[8][8];
			this.moves.clear();
			this.move.clear();
			board[0][0] = new CheckersPiece(Player.BLACK);
			this.findMoves();
		}
	}
}
