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
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;


/**
 * View for the user to interact with visual representations of
 * game pieces and boards.
 * @author Daniel Cummings
 * @version 0.1
 */
public class GameSuiteGUI extends JPanel {

	/** Version Serial ID. */
	private static final long serialVersionUID = 1L;

	/** Reference to the window that contains content. */
	private JFrame wind;

	/** Model which controls the game. */
	private IGameLogic game;

	/** Start games buttons. */
	private JButton checkersButton, reversiButton, sudokuButton;

	/** Menu buttons. */
	private JMenuItem newGames, save, load, exit;

	/** User interface for a checkers board. */
	private CheckersGUI checkersPanel;
	
	/** User interface for a reversi board. */
	//private ReversiGUI reversiPanel;
	
	/** User interface for a sudoku board. */
	private JPanel init, sudokuPanel, info;

	/**
	 * @param frame  
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
		// Instantiates buttons.
		checkersButton = new JButton("Checkers");
		reversiButton = new JButton("Reversi");
		sudokuButton = new JButton("Sudoku");
		
		// each button gets listener to activate commands when
		// pressed
		checkersButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				checkersUI();
			}
		});
		reversiButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				reversiUI();
			}
		});
		sudokuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				sudokuUI();
			}
		});
		init.add(checkersButton);
		init.add(reversiButton);
		init.add(sudokuButton);
		this.add(init);
		this.revalidate();
	}

	/**
	 * Creates the sudoku GUI when the player chooses the game.
	 */
	private void sudokuUI() {
		sudokuPanel = new JPanel();	
	}

	/**
	 * Resizes window to fit the game UI for the checkers game.
	 * Only called when the checkers button is selected.
	 */
	private void checkersUI() {
		wind.setSize(900, 700);
		game = new CheckersLogic();
		checkersPanel = new CheckersGUI((CheckersLogic) game);
		this.removeAll();
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbCon = new GridBagConstraints();
		gbCon.gridx = 0;
		gbCon.gridy = 0;
		gbCon.gridheight = 600;
		gbCon.gridwidth = 600;
		gbCon.anchor = GridBagConstraints.NORTHWEST;
		this.add(checkersPanel, gbCon);
		this.repaint();
		this.revalidate();
	}

	/**
	 * 
	 */
	private void reversiUI() {

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
