package gamesuite;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

/**
 * 
 * @author Jaden Sella
 * @version 0.1
 */
public class OthelloGUI extends JPanel implements MouseListener {
	
	/** Game played. */
	private Othello game;
	
	/** Game board. */
	private JPanel backing;
	
	/** Board squares. */
	private JPanel[][] board;

	/** Size of the board. */
	private int size;
	
	/** Size of each board square. */
	private int sqSize;
	
	/** Image for piece. */
	private ImageIcon white, black;
	
	/** Label for count of pieces on the board. */
	private JLabel count;
	
	/** Color of the board. */
	private Color boardC;
	
	
	/**
	 * Creates an othello game panel for the user to
	 * play.
	 * @param gamel The model to enforce the view.
	 */
	public OthelloGUI(final Othello gamel) {
		
		boardC = new Color(182, 155, 76);
		size = 8;
		sqSize = 75;
		this.game = gamel;
		backing = new JPanel(new GridLayout(size, size));
		backing.addMouseListener(this);
		board = new JPanel[size][size];
		this.setLayout(new BorderLayout(30, 15));
		this.setPreferredSize(
				new Dimension(size * sqSize + 25,
						size * sqSize + 25));
		this.add(backing, BorderLayout.CENTER);
		this.count = new JLabel();
		this.setCountLabel();
		this.add(count, BorderLayout.PAGE_START);
		this.getImages();
		this.createBoard();
		this.displayBoard();
		//this.showMoves();
	}
	
	/**
	 * Creates the board out of JPanels and places them onto the
	 * panel holding the board.
	 */
	private void createBoard() {
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				board[x][y] = new JPanel(new BorderLayout());
				board[x][y].setBorder(
					new LineBorder(Color.black, 3));
				board[x][y].setBackground(boardC);
				backing.add(board[x][y]);
			}
		}
	}
	
	/**
	 * Method to show possible moves for the current player.
	 */
	public void showMoves() {
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if (game.isMove(x, y)) {
					board[x][y].setBackground(Color.YELLOW);
				} else {
					board[x][y].setBackground(boardC);
				}
			}
		}
	}
	
	/** 
	 * Method grabs the checkers images from the resource file.
	 */
	private void getImages() {
		white = new ImageIcon(this.getClass()
				.getResource("/BlackKing.png"));
		black = new ImageIcon(this.getClass()
				.getResource("/RedKing.png"));
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
					if (game.getPiece(x, y).getOwner()
							== Player.BLACK) {
						board[x][y].add(
							new JLabel(black));
					} else {
						board[x][y].add(
							new JLabel(white));
					}
				}
			}
		}
		backing.revalidate();
	}

	@Override
	public final void mouseClicked(final MouseEvent e) {
		Component temp = e.getComponent();
		if (temp instanceof JPanel) {
			int xPos = e.getY();
			int yPos = e.getX();
			if (game.isMove(xPos, yPos)) {
				game.makeMove(xPos, yPos);
				this.displayBoard();
				this.showMoves();
			}
		}
		backing.revalidate();
		backing.repaint();
	}
	
	/**
	 * Changes the labels for piece count to represent the
	 * current status of the board.
	 */
	private void setCountLabel() {
		int countW = game.getWhiteCount();
		int countB = game.getBlackCount();
		
		this.count.setText("Black pieces: "
		+ countB + "\t\t White Pieces: " + countW);
	}

	@Override
	public void mousePressed(final MouseEvent e) { }

	@Override
	public void mouseReleased(final MouseEvent e) { }

	@Override
	public void mouseEntered(final MouseEvent e) { }

	@Override
	public void mouseExited(final MouseEvent e) { }

}
