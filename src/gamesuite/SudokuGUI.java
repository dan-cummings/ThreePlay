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
 * 
 */
public class SudokuGUI extends JPanel 
implements MouseListener, MouseMotionListener {
	
	/** Image for the piece. */
	private ImageIcon one, two, three, four, five;
	private ImageIcon six, seven, eight, nine;
	private ImageIcon bReg, rKing, rReg;

	/** Board square containers. */
	private JPanel[][] board;

	/** Model. */
	private SudokuLogic game;

	/** Piece that was selected. */
	private JLabel piece;

	/** Position of piece on board. */
	private int xPos, yPos;

	/** adjustment measurement. */
	// private int adjx, adjy;

	/** integer values for location of move. */
	// private int fromX, fromY, toX, toY;

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
	public SudokuGUI(final SudokuLogic gamel) {
		// Get game images.
		this.getImages();
		
		// instantiate game objects
		this.game = gamel;
		this.size = gamel.getSize();
		this.sqSize = 60;
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
		this.revalidate();
	}

	@Override
	public final void mousePressed(final MouseEvent e) { 
		
		xPos = e.getX();
		yPos = e.getY();
		System.out.print("xPos: " + xPos + " yPos: " + yPos);

	}


	/**
	 * Method to show errors on the Board
	 */
	private void showErrors() {
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if (game.isError(y,x)) {
					board[x][y].setBackground(Color.YELLOW);
				}
			}
		}
	}
	
	@Override
	public final void mouseReleased(final MouseEvent e) {
		
	}
	
	/**
	 * Creates the board out of JPanels and places them onto the
	 * JPanel to hold the board.
	 */
	private void createBoard() {
		
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				board[x][y] = new JPanel(new BorderLayout());
				if (game.isInitial(y,x)) {
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
	// MAKE IMAGES FOR THE NUMBERS?
	private void getImages() {
		one = new ImageIcon(this.getClass()
				.getResource("/BlackKing.png"));
		two = new ImageIcon(this.getClass()
				.getResource("/RedKing.png"));
		three = new ImageIcon(this.getClass()
				.getResource("/BlackReg.png"));
		four = new ImageIcon(this.getClass()
				.getResource("/RedReg.png"));
		five = new ImageIcon(this.getClass()
				.getResource("/RedReg.png"));
		six = new ImageIcon(this.getClass()
				.getResource("/RedReg.png"));
		seven = new ImageIcon(this.getClass()
				.getResource("/RedReg.png"));
		eight = new ImageIcon(this.getClass()
				.getResource("/RedReg.png"));
		nine = new ImageIcon(this.getClass()
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
				if (game.getNumber(x,y) == 1) {
					board[x][y].add(new JLabel(one));
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
				if (game.getNumber(x,y) == 1 ) {
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

	@Override
	public void mouseDragged(MouseEvent e) { }
}
