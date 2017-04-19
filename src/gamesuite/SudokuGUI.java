package gamesuite;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

/**
 * Panel containing all of the information for the sudoku game.
 * @author Daniel Cummings
 * Fitted to Sudoku by Brendon Murthum
 * @version 0.1
 */
public class SudokuGUI extends JPanel 
implements MouseListener, KeyListener {
	
	/**	Used in saving and loading. */
	private static final long serialVersionUID = 1L;

	/** Board square containers. */
	private JPanel[][] board;

	/** Model. */
	private SudokuLogic game;

	/** Piece that was selected. */
	@SuppressWarnings("unused")
	private JLabel piece;

	/** Position of piece on board. */
	private int xPos, yPos, oldX, oldY;
	
	/** integer values for location of chosen square. */
	private int squareX = 0, squareY = 0;

	/** Size of each square. */
	private final int size, sqSize;

	/** Panel to hold JPanel pieces. */
	private JPanel boardPanel;

	/** Layered pane for drag. */
	@SuppressWarnings("unused")
	private JLayeredPane pane;
	
	/** Settings of colors for the board. */
	/** Light-grey square boarders. */
	private final Color squareBorderColor = new Color(30, 30, 30);
	/** Grey background of initial squares. */
	private final Color initialColor = new Color(255, 255, 255);
	/** Grey background of non-initial squares. */
	private final Color nonInitialColor = new Color(255, 255, 255);
	/** Green background of selected squares. */
	private final Color selectedColor = new Color(230, 230, 230);
	/** Yellow background of error squares. */
	private final Color errorColor = new Color(255, 255, 200);
	/** Black text of initial squares. */
	private final Color initialTextColor = new Color(25, 25, 25);
	/** Grey text of completed squares. */
	private final Color completedTextColor = new Color(190, 190, 190);

	/**
	 * Constructor for the checkers GUI.
	 * @param gamel Sudoku game logic.
	 */
	public SudokuGUI(final SudokuLogic gamel) {
		this.setFocusable(true);
		this.addKeyListener(this);
		// Instantiate game objects
		this.game = gamel;
		this.gameOption();
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
		if (game.isCorrect()) {
			makeAllGold();
		}
		// Gets the position values of the user click.
		xPos = e.getX();
		yPos = e.getY();
		// Gets the integer 0-8 location of piece in 2d array.
		squareX = Math.floorDiv(yPos, 60);
		squareY = Math.floorDiv(xPos, 60);
		
		if (!game.isInitial(squareY, squareX)) {
			game.clickedOn(squareY, squareX);
			board[squareX][squareY].setBackground(selectedColor);
			oldX = game.lastClickedX();
			oldY = game.lastClickedY();
			if (!(oldX == squareX && oldY == squareY)) {
				board[oldX][oldY].setBackground(Color.white);
			}
		} else {
			return;
		}
		/*
		// For testing, outputs current click to console.
		System.out.print("Current Selected Piece: X: " 
			+ game.currentClickedX() + " Y: " 
			+ game.currentClickedY() +  "\n");
		System.out.print("xPos: " + xPos + " yPos: " + yPos + "\n");
		System.out.print("squareX: " + squareX 
				+ " squareY: " + squareY + "\n");
		*/
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
	 *  Locks all squares and sets the board to gold. To 
	 *  signal to the user a congratulations.
	 */
	private void makeAllGold() {
		int num;
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				board[x][y].removeAll();
				JLabel numberLabel = new JLabel();
				numberLabel.setFont(new Font("Arial", 0, 30));
				board[x][y].setBackground(errorColor);
				num = game.getNumber(y, x);
				// Sets label value.
				if (num != 0) {
					numberLabel.setText(
							Integer.toString(num));
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
				numberLabel.setForeground(initialTextColor);
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
				// Setting the display board
				int thick = 4;
				int thin = 1;
				int top = thin;
				int left = thin;
				int bottom = thin;
				int right = thin;
				if (x == 0 || x == 3 || x == 6) {
					left = thick;
				}
				if (y == 0 || y == 3 || y == 6) {
					top = thick;
				}
				if (y == 8) {
					bottom = thick;
				}
				if (x == 8) {
					right = thick;
				}
				board[x][y].setBorder(
				  new MatteBorder(left,
						  top, right, 
						  bottom,
				squareBorderColor));			
	
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
	 * Creates a dialog window to select which game difficulty
	 * the player wishes to play.
	 */
	private void gameOption() {
		Object[] options = {"Easy", "Medium", "Hard"};
		int type;
		do {
			type = JOptionPane.showOptionDialog(this,
				"Please select a game mode:",
				"Game Select",
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null, options, options[0]);
		} while (type == JOptionPane.CLOSED_OPTION);
				
		if (type == 0) {
			this.game.setDifficulty(25);
		} else if (type == 1) {
			this.game.setDifficulty(50);
		} else {
			this.game.setDifficulty(64);
		}
	}
	
	/**
	 * Creates label for the pieces and places them into the
	 * proper position on the board.
	 */
	public void displayBoard() {
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
					numberLabel.setForeground(
							initialTextColor);
				} else {
					board[x][y].setBackground(
							nonInitialColor);
					numberLabel.setForeground(
							completedTextColor);
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
	public final void keyTyped(final KeyEvent e) { }

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
		// When the game is finished, stall out this display.
		if (game.isCorrect()) {
			makeAllGold();
			return;
		}
		// Parse the user input
		try {
			// Turn the current selected white
			int currentX = game.currentClickedX();
			int currentY = game.currentClickedY();
			if (!game.isInitial(currentY, currentX)) {
				board[currentX][currentY].setBackground(
						Color.white);
			}
			
			int temp = Integer.parseInt(
					Character.toString(e.getKeyChar()));
			game.setNumber(temp);
			this.displayBoard();
			
		} catch (NumberFormatException m) {
			// Not a number
		} finally {
			// For Testing
			this.displayBoard();
		}
		
		// Near the game end
		if (game.isFilled()) {
			// For Testing
			// System.out.println("Board Filled!");
			showErrors();
		}
		// When the game is finished
		if (game.isCorrect()) {
			// For Testing
			// System.out.println("Board Correct!");
			makeAllGold();
		}
	}

	@Override
	public void keyReleased(final KeyEvent e) { }
}

