package gamesuite;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;


/**
 * View for the user to interact with visual representations of
 * game pieces and boards.
 * @author Daniel Cummings
 * @version 0.2
 */
public class GameSuiteGUI extends JPanel {

	/** Version Serial ID. */
	private static final long serialVersionUID = 1L;

	/** Reference to the window that contains content. */
	private JFrame wind;

	/** Model which controls the game. */
	private transient IGameLogic game;

	/** Start games buttons. */
	private JButton checkersButton, othelloButton, sudokuButton;

	/** Menu buttons. */
	private JMenuItem newGames, save, load, exit;

	/** User interface for a checkers board. */
	private CheckersGUI checkersPanel;
	
	/** User interface for a Othello board. */
	private OthelloGUI othelloPanel;
	
	/** User interface for the sudoku board. */
	private SudokuGUI sudokuPanel;
	
	/** User interface for a sudoku board. */
	private JPanel init;

	/**
	 * @param frame of the GUI
	 */
	public GameSuiteGUI(final JFrame frame) {
		this.wind = frame;
		createBar();
		createInitial();

	}

	/**
	 * Sets up the command bar for the window.
	 */
	private void createBar() {
		// Creates drop menus for the window.
		JMenuBar bar = new JMenuBar();
		JMenu gameMenu = new JMenu("Game");
		this.newGames = new JMenuItem("New game");
		this.save = new JMenuItem("Save..");
		this.load = new JMenuItem("Load..");
		this.exit = new JMenuItem("Exit");
		newGames.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				createInitial();
			}
		});

		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				save();
			}
		});
		load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				load();
			}
		});
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				System.exit(0);
			}
		});
		gameMenu.add(newGames);
		gameMenu.add(save);
		gameMenu.add(load);
		gameMenu.add(exit);
		bar.add(gameMenu);
		this.wind.setJMenuBar(bar);

	}

	/**
	 * Creates buttons within the window to represent the games each player
	 * can play within the game suite.
	 */
	private void createInitial() {
		// Creates buttons for the initial games menu.
		this.removeAll();
		// initial panel creation.
		init = new JPanel(new GridLayout(3, 1));
		init.setPreferredSize(new Dimension(800, 550));
		wind.setSize(800, 600);
		// Instantiates buttons.
		checkersButton = new JButton("Checkers");
		othelloButton = new JButton("Othello");
		sudokuButton = new JButton("Sudoku");
		
		// each button gets listener to activate commands when
		// pressed
		checkersButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				checkersUI();
			}
		});
		othelloButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				othelloUI();
			}
		});
		sudokuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				sudokuUI();
			}
		});
		init.add(checkersButton);
		init.add(othelloButton);
		init.add(sudokuButton);
		this.add(init);
		this.revalidate();
		this.repaint();
	}

	/**
	 * Creates the sudoku GUI when the player chooses the game.
	 */
	private void sudokuUI() {
		wind.setSize(900, 700);
		game = new SudokuLogic(20);
		sudokuPanel = new SudokuGUI((SudokuLogic) game);
		this.removeAll();
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbCon = new GridBagConstraints();
		gbCon.gridx = 0;
		gbCon.gridy = 0;
		gbCon.gridheight = 700;
		gbCon.gridwidth = 700;
		gbCon.anchor = GridBagConstraints.NORTHWEST;
		this.add(sudokuPanel, gbCon);
		this.repaint();
		this.revalidate();
	}

	/**
	 * Resizes window to fit the game UI for the checkers game.
	 * Only called when the checkers button is selected.
	 */
	private void checkersUI() {
		wind.setSize(900, 700);
		game = new CheckersController();
		checkersPanel = new CheckersGUI((CheckersController) game);
		this.removeAll();
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 700;
		gbc.gridwidth = 700;
		checkersPanel.setBorder(new EtchedBorder());
		this.add(checkersPanel, gbc);
		this.repaint();
		this.revalidate();
	}

	/**
	 * Method to create the othello board.
	 */
	private void othelloUI() {
		wind.setSize(800, 700);
		this.setPreferredSize(new Dimension(800, 700));
		game = new OthelloController();
		othelloPanel = new OthelloGUI((OthelloController) game);
		this.removeAll();
		this.add(othelloPanel);
		this.repaint();
		this.revalidate();
	}

	/**
	 * Method to initiate save game state for current game.
	 */
	private void save() {
		if (game != null) {
			try {
				String filename = "";
				filename = JOptionPane.showInputDialog(
						wind, 
						"Enter name "
						+ "of file to save to",
						"Save",
						JOptionPane.PLAIN_MESSAGE);
				game.saveState(filename);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(wind,
						e.getMessage(),
						"Save Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(wind, 
					"Cannot save:"
							+ "No game has"
							+ " been started.",
							"Save Failure",
						JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Method to initiate load game state for current game.
	 */
	private void load() {
		if (game != null) {
			try {
				String filename = "";
				filename = JOptionPane.showInputDialog(
						wind,
						"Enter name of"
						+ " file to load from:",
						"Load",
						JOptionPane.PLAIN_MESSAGE);
					game.loadState(filename);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(
						wind,
						e.getMessage(),
						"Load Error",
						JOptionPane.ERROR_MESSAGE);
			} finally {
				if (game instanceof CheckersController) {
					checkersPanel.displayBoard();
					checkersPanel.resetColor();
					checkersPanel.showMoveablePieces();
					checkersPanel.repaint();
				} else if (game instanceof Othello) {
					othelloPanel.displayBoard();
					othelloPanel.showMoves();
					othelloPanel.repaint();
				} else if (game instanceof SudokuLogic) {
					sudokuPanel.displayBoard();
					sudokuPanel.repaint();
				}
			}
		} else {
			JOptionPane.showMessageDialog(wind,
					"No game started, please"
							+ " choose a game then"
							+ " attempt to load.",
							"Load Failure",
						JOptionPane.ERROR_MESSAGE);
		}
	}
}

