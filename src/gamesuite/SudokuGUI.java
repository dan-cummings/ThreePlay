package gamesuite;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
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

/**
 * Panel containing all of the information for the Checkers game.
 * @author Daniel Cummings
 * Fitted to Sudoku by Brendon Murthum
 * @version 0.1
 */
public class SudokuGUI extends JPanel 
implements MouseListener, MouseMotionListener {
	
	/** Board square containers. */
	private JPanel[][] board;

	/** Model. */
	private SudokuLogic game;

	/** Piece that was selected. */
	private JLabel piece;

	/** Position of piece on board. */
	private int xPos, yPos;
	
	/** integer values for location of chosen square. */
	private int squareX, squareY;

	/** Size of each square. */
	private final int size, sqSize;

	/** Panel to hold JPanel pieces. */
	private JPanel boardPanel;

	/** Layered pane for drag. */
	private JLayeredPane pane;
	
	/** Settings of colors for the board */
		// Light-grey square boarders
		Color squareBorderColor = new Color(210,210,210);
		// Grey background of initial squares
		Color initialColor = new Color(225,225,225);
		// Green background of initial squares
		Color selectedColor = new Color(210,250,220);

	/**
	 * Constructor for the checkers GUI.
	 * @param gamel Sudoku game logic.
	 */
	public SudokuGUI(final SudokuLogic gamel) {
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
		//this.displayBoard();
		this.revalidate();
	}

	@Override
	public final void mousePressed(final MouseEvent e) { 
		if (game.isGameOver()) {
			return;
		}
		if (game.isFilled()) {
			showErrors();
		}
		
		xPos = e.getX();
		yPos = e.getY();
		
		//gets the integer 0-7 location of piece in 2d array.
		squareX = Math.floorDiv(yPos, 60);
		squareY = Math.floorDiv(xPos, 60);
		
		game.clickedOn(squareY,squareX);
		
		// on click of piece, SudokuPiece[x][y].clickedOn()
		// SudokuPiece.isSelected() returns true if currently selected
		
		System.out.print("xPos: " + xPos + " yPos: " + yPos + "\n");
		System.out.print("squareX: " + squareX + " squareY: " + squareY + "\n");
	}

	/**
	 * Method to show errors on the Board
	 */
	private void showErrors() {
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if (game.isError(y, x)) {
					board[x][y].setBackground(Color.YELLOW);
				}
			}
		}
	}
	
	@Override
	public final void mouseReleased(final MouseEvent e) { 
		this.displayBoard();
	}
	
	/**
	 * Creates the board out of JPanels and places them onto the
	 * JPanel to hold the board.
	 */
	private void createBoard() {
		boardPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				board[x][y] = new JPanel(new BorderLayout());
				JLabel numberLabel = new JLabel();
				numberLabel.setFont(new Font("Arial", 0, 30));
				board[x][y].setBackground(Color.white);
				if (game.getNumber(y,x) == 1) {
					numberLabel.setText("1");
				} else if(game.getNumber(y,x) == 2){
					numberLabel.setText("2");
				} else if(game.getNumber(y,x) == 3){
					numberLabel.setText("3");
				} else if(game.getNumber(y,x) == 4){
					numberLabel.setText("4");
				} else if(game.getNumber(y,x) == 5){
					numberLabel.setText("5");
				} else if(game.getNumber(y,x) == 6){
					numberLabel.setText("6");
				} else if(game.getNumber(y,x) == 7){
					numberLabel.setText("7");
				} else if(game.getNumber(y,x) == 8){
					numberLabel.setText("8");
				} else if(game.getNumber(y,x) == 9){
					numberLabel.setText("9");
				}
				if(game.isInitial(y,x)){
					board[x][y].setBackground(initialColor);
				}
				
				// Setting specific border sizes for the thicker middle lines
				board[x][y].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, squareBorderColor));
				if(x == 3 || x == 6){
					board[x][y].setBorder(BorderFactory.createMatteBorder(3, 1, 1, 1, squareBorderColor));
				}
				if(y == 3 || y == 6){
					board[x][y].setBorder(BorderFactory.createMatteBorder(1, 3, 1, 1, squareBorderColor));
				}
				if(x == 3 && y == 3){
					board[x][y].setBorder(BorderFactory.createMatteBorder(3, 3, 1, 1, squareBorderColor));
				}
				if(x == 3 && y == 6){
					board[x][y].setBorder(BorderFactory.createMatteBorder(3, 3, 1, 1, squareBorderColor));
				}
				if(x == 6 && y == 3){
					board[x][y].setBorder(BorderFactory.createMatteBorder(3, 3, 1, 1, squareBorderColor));
				}
				if(x == 6 && y == 6){
					board[x][y].setBorder(BorderFactory.createMatteBorder(3, 3, 1, 1, squareBorderColor));
				}
				
				
				numberLabel.setHorizontalAlignment(SwingConstants.CENTER);
				numberLabel.setVerticalAlignment(SwingConstants.CENTER);
				
				//board[x][y].setBorder(BorderFactory.createLineBorder(squareBorderColor));
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
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				board[x][y].removeAll();
				JLabel numberLabel = new JLabel();
				numberLabel.setFont(new Font("Arial", 0, 30));
				board[x][y].setBackground(Color.white);
				if (game.getNumber(y,x) == 1) {
					numberLabel.setText("1");
				} else if(game.getNumber(y,x) == 2){
					numberLabel.setText("2");
				} else if(game.getNumber(y,x) == 3){
					numberLabel.setText("3");
				} else if(game.getNumber(y,x) == 4){
					numberLabel.setText("4");
				} else if(game.getNumber(y,x) == 5){
					numberLabel.setText("5");
				} else if(game.getNumber(y,x) == 6){
					numberLabel.setText("6");
				} else if(game.getNumber(y,x) == 7){
					numberLabel.setText("7");
				} else if(game.getNumber(y,x) == 8){
					numberLabel.setText("8");
				} else if(game.getNumber(y,x) == 9){
					numberLabel.setText("9");
				}
				if(game.isInitial(y,x)){
					board[x][y].setBackground(initialColor);
				}
				if(!game.isInitial(y,x) && x == game.currentClickedX() && y == game.currentClickedY()){
					board[x][y].setBackground(selectedColor);
				}
				
				// Setting specific border sizes for the thicker middle lines
				board[x][y].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, squareBorderColor));
				if(x == 3 || x == 6){
					board[x][y].setBorder(BorderFactory.createMatteBorder(3, 1, 1, 1, squareBorderColor));
				}
				if(y == 3 || y == 6){
					board[x][y].setBorder(BorderFactory.createMatteBorder(1, 3, 1, 1, squareBorderColor));
				}
				if(x == 3 && y == 3){
					board[x][y].setBorder(BorderFactory.createMatteBorder(3, 3, 1, 1, squareBorderColor));
				}
				if(x == 3 && y == 6){
					board[x][y].setBorder(BorderFactory.createMatteBorder(3, 3, 1, 1, squareBorderColor));
				}
				if(x == 6 && y == 3){
					board[x][y].setBorder(BorderFactory.createMatteBorder(3, 3, 1, 1, squareBorderColor));
				}
				if(x == 6 && y == 6){
					board[x][y].setBorder(BorderFactory.createMatteBorder(3, 3, 1, 1, squareBorderColor));
				}
				
				numberLabel.setHorizontalAlignment(SwingConstants.CENTER);
				numberLabel.setVerticalAlignment(SwingConstants.CENTER);
				
				//board[x][y].setBorder(BorderFactory.createLineBorder(squareBorderColor));
				board[x][y].add(numberLabel);
				boardPanel.add(board[x][y]);
			}
		}
		boardPanel.revalidate();
	}
	
	
	/*  Sets all selected boxes back to unselected 
	public boolean clearAllSelected()
	{
		for(int x=0; x<9;x++){
			for(int y=0;y<9;y++){
				// UPDATE ME
				// if(game.isSelected(x,y))
				// SudokuLogic.current[x][y]
			}
		}
		
		
	}
	*/
	
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
