package gamesuite;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

/**
 * Panel containing all of the information for the Checkers game.
 * @author Daniel Cummings
 * @version 0.1
 */
public class CheckersGUI extends JPanel 
implements MouseListener, MouseMotionListener {
	
	/** Image for the piece. */
	private ImageIcon bKing, bReg, rKing, rReg;

	/** Board square containers. */
	private JPanel[][] board;

	/** Model. */
	private CheckersLogic game;

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

	/** Layered pane for drag. */
	private JLayeredPane pane;

	/**
	 * Constructor for the checkers GUI.
	 * @param gamel checkers game logic.
	 */
	public CheckersGUI(final CheckersLogic gamel) {
		// Get game images.
		this.getImages();
		
		// instantiate game objects
		this.game = gamel;
		this.size = gamel.getSize();
		this.sqSize = 75;
		// Creating size of panels.
		Dimension panelSize =
				new Dimension(size * sqSize,
						size * sqSize);
		// Layout setup
		this.setLayout(new BorderLayout(30, 15));
		this.setBorder(new EmptyBorder(20, 25, 15, 15));
		
		//Instantiates layered pane.
		pane = new JLayeredPane();
		pane.setPreferredSize(panelSize);
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
		this.add(pane, BorderLayout.CENTER);
		this.displayBoard();
		this.showMoveablePieces();
		this.revalidate();
	}

	@Override
	public final void mousePressed(final MouseEvent e) { 
		
		xPos = e.getX();
		yPos = e.getY();
		Component temp = pane.findComponentAt(xPos, yPos);
		if (!(temp instanceof JPanel)) {
			adjx = temp.getParent().getLocation().x - e.getX();
			adjy = temp.getParent().getLocation().y - e.getY();
			fromX = Math.floorDiv(yPos, 75);
			fromY = Math.floorDiv(xPos, 75);
			if (game.hasMove(game.getPiece(fromX, fromY))) {
				piece = (JLabel) temp;
				piece.setVisible(false);
				this.resetColor();
				this.showMoves();
				pane.add(piece, JLayeredPane.DRAG_LAYER);
				piece.setVisible(true);
			}
		} else {
			piece = null;
		}

	}

	@Override
	public final void mouseReleased(final MouseEvent e) {
		if (piece != null) {
			piece.setVisible(false);
			if (pane.getBounds().contains(e.getPoint())) {
				toX = Math.floorDiv(e.getY(), sqSize);
				toY = Math.floorDiv(e.getX(), sqSize);
				Move m = new Move(fromX, fromY, toX, toY);
				if (game.isMove(m)) {
					game.makeMove(m);
					piece.setVisible(true);
					pane.remove(piece);
				}
			}
			piece = null;
		}
		this.displayBoard();
		this.resetColor();
		this.showMoveablePieces();
	}

	@Override
	public final void mouseDragged(final MouseEvent e) {
		// Keeps piece moving with the cursor while centered.
		if (piece != null) {
			piece.setLocation(e.getX() + adjx, e.getY() + adjy);
		}
	}
	

	/**
	 * Method to show possible moves for the current piece.
	 */
	private void showMoves() {
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if (game.isMove(new Move(fromX, fromY, x, y))) {
					board[x][y].setBackground(Color.YELLOW);
				}
			}
		}
	}

	/**
	 * Changes the background of panels to red if the piece has
	 * a move available.
	 */
	private void showMoveablePieces() {
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if (game.getPiece(x, y) != null) {
					if (game.hasMove(game.getPiece(x, y))) {
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
	 * Creates label for the pieces and places them into the
	 * proper position on the board.
	 */
	private void displayBoard() {
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
	}
	
	/**
	 * Resets the background color of the board after 
	 * being called.
	 */
	private void resetColor() {
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
