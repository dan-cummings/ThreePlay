package gamesuite;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;


/**
 * Panel containing all of the information for the Othello game.
 * OthelloGUI acts as the view for the Othello game.
 * @author Jaden Sella
 * @version 0.1
 */
public class OthelloGUI extends JPanel 
implements MouseListener {
	
	/** Version Serialize ID. */
	private static final long serialVersionUID = 1L;

	/** Image for the piece. */
	private ImageIcon bReg, rReg;

	/** Board square containers. */
	private JPanel[][] board;

	/** Model. */
	private OthelloController game;

	/** integer values for location of move. */
	private int toX, toY;

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
	 * Constructor for the Othello GUI.
	 * @param gamel Othello game logic.
	 */
	public OthelloGUI(final OthelloController gamel) {
		// Get game images.
		this.getImages();
		
		// instantiate game objects
		this.game = gamel;
		this.gameOption();
		this.size = 8;
		this.sqSize = 75;
		// Creating size of panels.
		Dimension panelSize =
				new Dimension(size * sqSize,
						size * sqSize);
		// Layout setup
		this.setLayout(new BorderLayout(30, 15));
		
		//Instantiates layered pane.
		pane = new JLayeredPane();
		pane.setPreferredSize(panelSize);
		pane.addMouseListener(this);
		
		// Create panel to store board with grid layout of
		// board size.
		boardPanel = new JPanel(new GridLayout(size, size));
		boardPanel.setPreferredSize(panelSize);
		boardPanel.setBounds(0, 0, panelSize.width, panelSize.height);
		this.board = new JPanel[size][size];
		this.createBoard();
		pane.add(boardPanel, JLayeredPane.DEFAULT_LAYER);
		this.add(pane, BorderLayout.CENTER);
		this.displayBoard();
		this.showMoves();
		turn = new JLabel();
		turn.setHorizontalAlignment(size / 2);
		this.add(turn, BorderLayout.PAGE_START);
		getPlayer();
	}
	
	/**
	 * Creates a dialog window to select which game mode
	 * the player wishes to play.
	 */
	private void gameOption() {
		Object[] options = {"Player vs. Computer", "Player vs. Player"};
		int type = JOptionPane.showOptionDialog(this,
				"Please select a game mode:",
				"Game Select",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null, options, options[0]);
		if (type == JOptionPane.YES_OPTION) {
			this.game.setAI(true);
		} else {
			this.game.setAI(false);
		}
	}

	@Override
	public final void mousePressed(final MouseEvent e) { 

	}

	@Override
	public final void mouseClicked(final MouseEvent e) {
		// Checks to make sure piece is being moved.

		if (pane.getBounds().contains(e.getPoint())) {
			toX = Math.floorDiv(e.getY(), sqSize);
			toY = Math.floorDiv(e.getX(), sqSize);
			if (game.isMove(toX, toY)) {
				//Tells the model the user
				//wants to move.
				game.makeMove(toX, toY);
				//Check for game over.
				if (game.isGameOver()) {
					this.handleGameOver();
				}
			}
		}

		//redisplay board.
		this.getPlayer();
		this.displayBoard();
		this.resetColor();
		this.showMoves();
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
	public void showMoves() {
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if (game.isMove(x, y)) {
					board[x][y].setBackground(Color.YELLOW);
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
	 * Method grabs the Othello images from the resource file.
	 */
	private void getImages() {
		bReg = new ImageIcon(this.getClass()
				.getResource("/BlackReg.png"));
		rReg = new ImageIcon(this.getClass()
				.getResource("/RedReg.png"));
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
			game.reset();
			this.displayBoard();
			this.showMoves();
			this.revalidate();
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
	public final void mouseReleased(final MouseEvent e) { }
}
