package gamesuite;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

/**
 * Panel containing all of the information for the Checkers game.
 * CheckersGUI acts as the view for the Checkers game.
 * @author Daniel Cummings
 * @version 0.1
 */
public class CheckersGUI extends JPanel 
implements MouseListener, MouseMotionListener {
	
	/** Button for moving between states of the game. */
	private JButton undo, redo;
	
	/** Version Serialize ID. */
	private static final long serialVersionUID = 1L;

	/** Image for the piece. */
	private ImageIcon bKing, bReg, rKing, rReg;

	/** Board square containers. */
	private JPanel[][] board;

	/** Controller. */
	private CheckersController game;

	/** Piece that was selected. */
	private JLabel piece;

	/** Position of piece on board. */
	private int xPos, yPos;

	/** adjustment measurement. */
	private int adjx, adjy;

	/** integer values for location of move. */
	private int fromX, fromY, toX, toY;

	/** Size of each square. */
	private final int size, sqSize;

	/** Panel to hold JPanel pieces. */
	private JPanel boardPanel;
	
	/** Panel for displaying the player turn. */
	private JLabel turn;

	/** Layered pane for drag. */
	private JLayeredPane pane;
	
	/** Current player. */
	private String player;

	/**
	 * Constructor for the checkers GUI.
	 * @param g The game controller for checkers.
	 */
	public CheckersGUI(final CheckersController g) {
		// Get game images.
		this.getImages();
		this.game = g;
		this.gameOption();
		// instantiate game objects
		this.size = 8;
		this.sqSize = 75;
		// Creating size of panels.
		Dimension panelSize =
				new Dimension(size * sqSize,
						size * sqSize);
		// Layout setup
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		//Instantiates layered pane.
		pane = new JLayeredPane();
		pane.setPreferredSize(panelSize);
		pane.setBounds(0, 0, panelSize.width, panelSize.height);
		pane.addMouseListener(this);
		pane.addMouseMotionListener(this);
		// Create panel to store board with grid layout of
		// board size.
		boardPanel = new JPanel(new GridLayout(size, size));
		boardPanel.setPreferredSize(panelSize);
		boardPanel.setBounds(0, 0, panelSize.width, panelSize.height);
		this.board = new JPanel[size][size];
		this.createBoard();
		pane.add(boardPanel, JLayeredPane.DEFAULT_LAYER);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridheight = panelSize.height;
		gbc.gridwidth = panelSize.width;
		this.add(pane, gbc);
		this.displayBoard();
		this.showMoveablePieces();
		
		turn = new JLabel();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		this.add(turn, gbc);
		getPlayer();
		
		undo = new JButton("Undo");
		redo = new JButton("Redo");
		undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				game.undo();
				getPlayer();
				resetColor();
				displayBoard();
				showMoveablePieces();
			}
		});
		redo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				game.redo();
				getPlayer();
				resetColor();
				displayBoard();
				showMoveablePieces();
			}
		});
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(undo, gbc);
		gbc.gridy = 1;
		this.add(redo, gbc);
	}

	/**
	 * Creates a dialog window to select which game mode
	 * the player wishes to play.
	 */
	private void gameOption() {
		Object[] options = {"Player vs. Computer", "Player vs. Player"};
		int type;
		do {
			type = JOptionPane.showOptionDialog(this,
				"Please select a game mode:",
				"Game Select",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null, options, options[0]);
		} while (type == JOptionPane.CLOSED_OPTION);
				
		if (type == JOptionPane.YES_OPTION) {
			this.game.setAI(true);
		} else {
			this.game.setAI(false);
		}
	}

	/**
	 * Gets location of click event, grabs component that is
	 * at that location. If the location contains no JLabel
	 * object the method returns without change to board.
	 * When JLabel is selected the piece is found and the board
	 * displays highlighted locations for the piece's valid moves.
	 * If the game is over or there is a stalemate pieces cannot be
	 * moved.
	 * @param e Created when user presses mouse key.
	 */
	public final void mousePressed(final MouseEvent e) { 
		if (game.gameOver() || game.isStalemate()) {
			return;
		}
		//get position of the event
		xPos = e.getX();
		yPos = e.getY();
		
		//grab the component at the location.
		Component temp = pane.findComponentAt(xPos, yPos);
		
		//If the piece is not a JPanel they can select it.
		if (!(temp instanceof JPanel)) {
			//Adjustments to keep piece centered
			//where user picked it up.
			adjx = temp.getParent().getLocation().x - e.getX();
			adjy = temp.getParent().getLocation().y - e.getY();
			
			//gets the integer 0-7 location of piece in 2d array.
			fromX = Math.floorDiv(yPos, 75);
			fromY = Math.floorDiv(xPos, 75);
			
			// Piece must have a move to move.
			if (game.hasMove(fromX, fromY)) {
				//Stores selected piece into field.
				piece = (JLabel) temp;
				this.resetColor();
				this.showMoves();
				//Move to drag layer.
				pane.add(piece, JLayeredPane.DRAG_LAYER);
				//Places piece at correct position.
				piece.setLocation(e.getX() + adjx,
						e.getY() + adjy);
			}
		} else {
			piece = null;
		}

	}

	/**
	 * If the user had selected a piece upon clicking the mouse
	 * key, method allows users to make possible movement within
	 * game logic. If the event location is an invalid one, piece
	 * gets reset to original position. Otherwise, model is updated
	 * and calls to update the view.
	 * @param e Event created when mouse key is released.
	 */
	public final void mouseReleased(final MouseEvent e) {
		// Checks to make sure piece is being moved.
		if (piece != null) {
			piece.setVisible(false);

			toX = Math.floorDiv(e.getY(), sqSize);
			toY = Math.floorDiv(e.getX(), sqSize);
			if (game.makeMove(fromX, fromY, toX, toY)) {
				//Tells the model the user
				//wants to move.
				//Sets visibility.
				piece.setVisible(true);
				//removes piece from drag layer.
				pane.remove(piece);
				//Check for game over.
				if (game.gameOver()) {
					this.handleGameOver();
				} else if (game.isStalemate()) {
					this.handleStalemate();
				}
			}
		}
		piece = null;
		
		//redisplay board.
		this.getPlayer();
		this.displayBoard();
		this.resetColor();
		this.showMoveablePieces();
	}

	

	/**
	 * Changes the graphical position of the piece to remain
	 * under the mouse cursor for simulated drag and drop.
	 * @param e Event created when mouse key is pressed and
	 * cursor is moved.
	 */
	public final void mouseDragged(final MouseEvent e) {
		// Keeps piece moving with the cursor while centered.
		if (piece != null) {
			if (!piece.isVisible()) {
				piece.setVisible(true);
			}
			piece.setLocation(e.getX() + adjx, e.getY() + adjy);
		}
	}
	
	/**
	 * Creates label for the pieces and places them into the
	 * proper position on the board.
	 */
	public void displayBoard() {
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				board[x][y].removeAll();
				if (game.getPiece(x, y) != null) {
					if (game.getPiece(x, y).isKinged()) {
						if (game.getPiece(x, y)
						.getOwner() == Player.BLACK) {
							board[x][y]
							.add(new JLabel(bKing));
						} else {
							board[x][y]
							.add(new JLabel(rKing));
						}
					} else {
						if (game.getPiece(x, y)
						.getOwner() == Player.BLACK) {
							board[x][y]
							.add(new JLabel(bReg));
						} else {
							board[x][y]
							.add(new JLabel(rReg));
						}
					}
				}
			}
		}
		boardPanel.revalidate();
		boardPanel.repaint();
	}
	
	/**
	 * Resets the background color of the board after 
	 * being called.
	 */
	public void resetColor() {
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if ((x % 2 == 0 && y % 2 == 0)
					|| ((x & 1) == 1 && (y & 1) == 1)) {
					board[x][y].setBackground(Color.gray);
				} else {
					board[x][y].setBackground(Color.white);
				}
			}
		}
	}

	/**
	 * Method to show possible moves for the current piece.
	 */
	private void showMoves() {
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if (game.isMove(fromX, fromY, x, y)) {
					board[x][y].setBackground(Color.YELLOW);
				}
			}
		}
	}

	/**
	 * Changes the background of panels to red if the piece has
	 * a move available.
	 */
	public void showMoveablePieces() {
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if (game.getPiece(x, y) != null) {
					if (game.hasMove(x, y)) {
						board[x][y].
						setBackground(Color.red);
					}
				}
			}
		}
	}
	
	/**
	 * Creates the board out of JPanels and places them onto the
	 * JPanel to hold the board.
	 */
	private void createBoard() {
		
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				board[x][y] = new JPanel(new BorderLayout());
				board[x][y].setBorder(
					new EtchedBorder(EtchedBorder.LOWERED));
				if ((x % 2 == 0 && y % 2 == 0)
					|| ((x & 1) == 1 && (y & 1) == 1)) {
					board[x][y].setBackground(Color.gray);
				} else {
					board[x][y].setBackground(Color.white);
				}
				boardPanel.add(board[x][y]);
			}
		}
	}
	
	/** 
	 * Method grabs the checkers images from the resource file.
	 */
	private void getImages() {
		bKing = new ImageIcon(this.getClass()
				.getResource("/BlackKing.png"));
		rKing = new ImageIcon(this.getClass()
				.getResource("/RedKing.png"));
		bReg = new ImageIcon(this.getClass()
				.getResource("/BlackReg.png"));
		rReg = new ImageIcon(this.getClass()
				.getResource("/RedReg.png"));
	}
	
	/**
	 * Helper method to handle the instance of a stalemate.
	 */
	private void handleStalemate() {
		int choice = JOptionPane.showConfirmDialog(this,
				"Game ended in stalemate, \n"
				+ "would you like to play again?",
				"Stalemate", JOptionPane.YES_NO_OPTION);
		if (choice == JOptionPane.YES_OPTION) {
			gameOption();
			game.reset();
			this.displayBoard();
			this.showMoveablePieces();
			this.revalidate();
			this.repaint();
		} else if (choice == JOptionPane.NO_OPTION) {
			JOptionPane.showMessageDialog(this,
					"If you would like to play"
					+ " again select New Game tab.",
					"Stalemate", JOptionPane.CANCEL_OPTION);
		} else {
			JOptionPane.showMessageDialog(this,
					"You may load or start a new game",
					"Stalemate", JOptionPane.CANCEL_OPTION);
		}
	}

	/**
	 * Helper method to handle the instance of game over.
	 */
	private void handleGameOver() {
		int choice = JOptionPane.showConfirmDialog(this,
				"Game ended, player: " + player + " wins, \n"
				+ "would you like to play again?",
				"Game over", JOptionPane.YES_NO_OPTION);
		if (choice == JOptionPane.YES_OPTION) {
			gameOption();
			game.reset();
			this.displayBoard();
			this.showMoveablePieces();
			this.revalidate();
			this.repaint();
		} else if (choice == JOptionPane.NO_OPTION) {
			JOptionPane.showMessageDialog(this,
					"If you would like to play"
					+ " again, select New Game tab.",
					"Game over", JOptionPane.CANCEL_OPTION);
		} else {
			JOptionPane.showMessageDialog(this,
					"You may load or start a new game",
					"Game over", JOptionPane.CANCEL_OPTION);
		}
	}
	
	/**
	 * Changes text within the turn label to show current player.
	 */
	private void getPlayer() {
		if (game.getPlayer() == Player.BLACK) {
			player = "Black";
		} else {
			player = "Red";
		}
		turn.setText("Current Player: " + player);
	}
	

	/** Unused methods from MouseListener. */
	@Override
	public void mouseEntered(final MouseEvent e) { }

	@Override
	public void mouseExited(final MouseEvent e) { }

	@Override
	public void mouseClicked(final MouseEvent e) { }
	
	@Override
	public void mouseMoved(final MouseEvent e) { }
}
