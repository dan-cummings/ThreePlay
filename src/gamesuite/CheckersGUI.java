package gamesuite;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 * Panel containing all of the information for the Checkers game.
 * @author Daniel Cummings
 * @version 0.1
 */
public class CheckersGUI extends JLayeredPane 
implements MouseListener, MouseMotionListener {
	
	/** Image for the piece. */
	private ImageIcon bKing, bReg, rKing, rReg;

	/** Icons for game pieces. */
	private ImageIcon[] icons;

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

	/**
	 * Constructor for the checkers GUI.
	 * @param gamel checkers game logic.
	 */
	public CheckersGUI(final CheckersLogic gamel) {
		this.getImages();
		this.game = gamel;
		this.size = gamel.getSize();
		this.sqSize = 75;
		Dimension paneSize =
				new Dimension(size * sqSize,
						size * sqSize);
		this.setLayout(new GridLayout(size, size));
		this.setPreferredSize(paneSize);
		this.setBounds(0, 0, paneSize.width, paneSize.height);
		this.board = new JPanel[size][size];
		
		this.createBoard();
		this.showMoveablePieces();
		this.setVisible(true);
	}

	@Override
	public final void mousePressed(final MouseEvent e) { 
		
		xPos = e.getY();
		yPos = e.getX();

		Component temp = this.findComponentAt(xPos, yPos);
		if (!(temp instanceof JPanel)) {
			adjx = temp.getParent().getLocation().y - e.getY();
			adjy = temp.getParent().getLocation().x - e.getX();
			piece = (JLabel) temp;
			showMoves();
			this.add(piece, JLayeredPane.DRAG_LAYER);
		} else {
			piece = null;
		}

	}

	@Override
	public final void mouseReleased(final MouseEvent e) {
		piece.setVisible(false);
		if (this.getBounds().contains(e.getPoint())) {
			toX = Math.floorDiv(e.getY(), sqSize);
			toY = Math.floorDiv(e.getX(), sqSize);
			Move m = new Move(fromX, fromY, toX, toY);
			if (game.isMove(m)) {
				game.makeMove(m);
			}
		} else {
			board[fromX][fromY].add(piece);
			piece.setVisible(true);
		}
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

		fromX = Math.floorDiv(xPos, 75);
		fromY = Math.floorDiv(yPos, 75);

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
	 * Creates the board out of JPanels and places them into the
	 * default layer of the board display.
	 */
	private void createBoard() {
		
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				board[x][y] = new JPanel(new BorderLayout());
				if ((x % 2 == 0 && y % 2 == 0)
						|| (x % 2 == 1 && y % 2 == 1)) {
					board[x][y].setBackground(Color.gray);
				} else {
					board[x][y].setBackground(Color.white);
				}
				board[x][y].setPreferredSize(
						new Dimension(sqSize, sqSize));
				this.add(board[x][y],
						JLayeredPane.DEFAULT_LAYER);
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
	 * Resets the background color of the board after 
	 * being called.
	 */
	private void resetColor() {
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if ((x % 2 == 0 && y % 2 == 0)
						|| (x % 2 == 1 && y % 2 == 1)) {
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
