package gamesuite;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

/**
 * Panel containing all of the information for the Checkers game.
 * @author Daniel Cummings
 * Fitted to Sudoku by Brendon Murthum
 * @version 0.1
 */
public class SudokuGUI extends JPanel 
implements MouseListener, KeyListener {
	
	/** Board square containers. */
	private JPanel[][] board;

	/** Model. */
	private SudokuLogic game;

	/** Piece that was selected. */
	private JLabel piece;

	/** Position of piece on board. */
	private int xPos, yPos;
	
	/** integer values for location of chosen square. */
	private int squareX = 0, squareY = 0;

	/** Size of each square. */
	private final int size, sqSize;

	/** Panel to hold JPanel pieces. */
	private JPanel boardPanel;

	/** Layered pane for drag. */
	private JLayeredPane pane;
	
	/** Settings of colors for the board. */
	/** Light-grey square boarders. */
	private final Color squareBorderColor = new Color(210, 210, 210);
	/** Grey background of initial squares. */
	private final Color initialColor = new Color(225, 225, 225);
	/** Green background of selected squares. */
	private final Color selectedColor = new Color(210, 250, 220);
	/** Yellow background of error squares. */
	private final Color errorColor = new Color(255, 255, 140);

	/**
	 * Constructor for the checkers GUI.
	 * @param gamel Sudoku game logic.
	 */
	public SudokuGUI(final SudokuLogic gamel) {
		this.setFocusable(true);
		this.addKeyListener(this);
		// Instantiate game objects
		this.game = gamel;
		this.size = gamel.getSize();
		this.sqSize = 60;
		// Creating size of panels.
		Dimension panelSize =
				new Dimension(size * sqSize,
						size * sqSize);
		// Layout setup
		this.setLayout(new BorderLayout(30, 15));
		
		//Instantiates layered pane.
		this.setPreferredSize(panelSize);
		this.addMouseListener(this);
		// Create panel to store board with grid layout of
		// board size.
		boardPanel = new JPanel(new GridLayout(size, size));
		boardPanel.setPreferredSize(panelSize);
		boardPanel.setBounds(0, 0, panelSize.width, panelSize.height);
		this.board = new JPanel[size][size];
		this.createBoard();
		this.add(boardPanel, BorderLayout.CENTER);
		this.revalidate();
	}
	
	@Override
	public final void mouseClicked(final MouseEvent e) { 
		if (game.isGameOver()) {
			return;
		}
		
		if (game.isFilled()) {
			showErrors();
		}
		
		if (!game.isInitial(squareY, squareX) && !game.isError(squareY, squareX)) {
			board[squareX][squareY].setBackground(Color.white);
		}
		
		xPos = e.getX();
		yPos = e.getY();
		
		//gets the integer 0-8 location of piece in 2d array.
		squareX = Math.floorDiv(yPos, 60);
		squareY = Math.floorDiv(xPos, 60);
		
		if (!game.isInitial(squareY, squareX)) {
			game.clickedOn(squareY, squareX);
			board[squareX][squareY].setBackground(selectedColor);
		} else {
			return;
		}
		
		// on click of piece, SudokuPiece[x][y].clickedOn()
		// SudokuPiece.isSelected() returns true if currently selected
		
		System.out.print("Current Selected Piece: X: " + game.currentClickedX() + " Y: " + game.currentClickedY() +  "\n");
		System.out.print("xPos: " + xPos + " yPos: " + yPos + "\n");
		System.out.print("squareX: " + squareX + " squareY: " + squareY + "\n");
	}

	/** 
	 * Method to show errors on the Board.
	 */
	private void showErrors() {
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if (game.isError(y, x)) {
					board[x][y].setBackground(errorColor);
				}
			}
		}
	}
	
	/**
	 * Creates the board out of JPanels and places them onto the
	 * JPanel to hold the board.
	 */
	private void createBoard() {
		int num;
		boardPanel.setBorder(new LineBorder(Color.black));
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				board[x][y] = new JPanel(new BorderLayout());
				JLabel numberLabel = new JLabel();
				numberLabel.setFont(new Font("Arial", 0, 30));
				board[x][y].setBackground(Color.white);
				board[x][y].setPreferredSize(
						new Dimension(sqSize, sqSize));
				num = game.getNumber(y, x);
				if (num != 0) {
					numberLabel.setText(
							Integer.toString(num));
				}
				
				if (game.isInitial(y, x)) {
					board[x][y].setBackground(initialColor);
				}
				
				// Setting specific border sizes
				// for the thicker middle lines
				board[x][y].setBorder(new MatteBorder(1, 1, 1, 1, squareBorderColor));
				if (x == 3 || x == 6) {
					board[x][y].setBorder(new MatteBorder(3, 1, 1, 1, squareBorderColor));
				}
				if (y == 3 || y == 6) {
					board[x][y].setBorder(new MatteBorder(1, 3, 1, 1, squareBorderColor));
				}
				if (x == 3 && y == 3) {
					board[x][y].setBorder(new MatteBorder(3, 3, 1, 1, squareBorderColor));
				}
				if (x == 3 && y == 6) {
					board[x][y].setBorder(new MatteBorder(3, 3, 1, 1, squareBorderColor));
				}
				if (x == 6 && y == 3) {
					board[x][y].setBorder(new MatteBorder(3, 3, 1, 1, squareBorderColor));
				}
				if (x == 6 && y == 6) {
					board[x][y].setBorder(new MatteBorder(3, 3, 1, 1, squareBorderColor));
				}
				
				
				numberLabel.setHorizontalAlignment(
						SwingConstants.CENTER);
				numberLabel.setVerticalAlignment(
						SwingConstants.CENTER);
				
				board[x][y].add(numberLabel);
				boardPanel.add(board[x][y]);
			}
		}
	}
	
	/**
	 * Creates label for the pieces and places them into the
	 * proper position on the board.
	 */
	
	private void displayBoard() {
		int num;
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				board[x][y].removeAll();
				JLabel numberLabel = new JLabel();
				numberLabel.setFont(new Font("Arial", 0, 30));
				board[x][y].setBackground(Color.white);
				num = game.getNumber(y, x);
				// Sets label value.
				if (num != 0) {
					numberLabel.setText(
							Integer.toString(num));
				}
				
				// Colors initial numbers.
				if (game.isInitial(y, x)) {
					board[x][y].setBackground(initialColor);
				}
				// If the location is currently selected.
				if (!game.isInitial(y, x) 
					&& x == game.currentClickedX()
					&& y == game.currentClickedY()) {
					board[x][y].setBackground(
							selectedColor);
				}
				
				numberLabel.setHorizontalAlignment(
						SwingConstants.CENTER);
				numberLabel.setVerticalAlignment(
						SwingConstants.CENTER);
				
				board[x][y].add(numberLabel);
				boardPanel.add(board[x][y]);
			}
		}
		boardPanel.revalidate();
	}
	
	@Override
	public final void keyTyped(final KeyEvent e) {
		
	}

	/** Unused methods from MouseListener. */
	@Override
	public void mouseEntered(final MouseEvent e) { }

	@Override
	public void mouseExited(final MouseEvent e) { }
	
	@Override
	public final void mouseReleased(final MouseEvent e) { }
	
	@Override
	public final void mousePressed(final MouseEvent e) { }

	@Override
	public final void keyPressed(final KeyEvent e) { 
		try {
			int temp = Integer.parseInt(
					Character.toString(e.getKeyChar()));
			game.setNumber(temp);
		} catch (NumberFormatException m) {
			m.printStackTrace();
			//not a number.
		} finally {
			System.out.println("Attempted to parse integer");
			System.out.println("Number: " + game.getNumber(squareY, squareX));
			this.displayBoard();
		}
		
		// Near the game end
		if(game.isFilled()){
			System.out.println("Board Filled!");
			showErrors();
		}
		// When the game is finished
		if(game.isCorrect()){
			System.out.println("Board Correct!");
		}
	}

	@Override
	public void keyReleased(final KeyEvent e) { }
}
