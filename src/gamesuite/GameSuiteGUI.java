package gamesuite;

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

	/** Action listener for buttons. */
	private ButtonListener listen;

	/**
	 * @param frame  
	 */
	public GameSuiteGUI(final JFrame frame) {
		this.wind = frame;
		GridLayout layout = new GridLayout(3, 2, 25, 15);
		this.setLayout(layout);
		listen = new ButtonListener();
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
		newGames.addActionListener(listen);
		save.addActionListener(listen);
		load.addActionListener(listen);
		exit.addActionListener(listen);
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
		checkersButton = new JButton("Checkers");
		reversiButton = new JButton("Reversi");
		sudokuButton = new JButton("Sudoku");
		checkersButton.addActionListener(listen);
		reversiButton.addActionListener(listen);
		sudokuButton.addActionListener(listen);
		this.add(checkersButton);
		this.add(reversiButton);
		this.add(sudokuButton);
		this.repaint();
	}

	/**
	 * Implementation of ActionListener class to handle button presses.
	 * @author Daniel Cummings
	 */
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			Object temp = e.getSource();
			if (temp == newGames) {

				createInitial();	
			} else if (temp == save) {

				if (game != null) {
					String filename = "";

					filename = JOptionPane.showInputDialog(
							wind, 
							"Enter name "
							+ "of file to save to",
							"Save",
						JOptionPane.PLAIN_MESSAGE);

					if (filename == "") {

						JOptionPane.showMessageDialog(
								wind,
								"Invalid file");

					} else {
						game.saveState(filename);
					}
				} else {
					JOptionPane.showMessageDialog(wind, 
							"Cannot save:"
							+ "No game has"
							+ " been started.");
				}

			} else if (temp == load) {
				if (game != null) {
					String filename = "";

					filename = JOptionPane.showInputDialog(
							wind,
							"Enter name of"
							+ " file to load from:",
							"Load",
						JOptionPane.PLAIN_MESSAGE);

					if (filename == "") {

						JOptionPane.showMessageDialog(
								wind,
								"Invalid file");

					} else {
						game.loadState(filename);
					}
				} else {
					JOptionPane.showMessageDialog(wind,
						"No game started, please"
							+ " choose a game then"
							+ " attempt to load.",
							"Load Failure",
						JOptionPane.ERROR_MESSAGE);
				}

			} else if (temp == exit) {
				System.exit(0);

			} else if (temp == checkersButton) {
				// launch checkers.
				checkersUI();
			} else if (temp == reversiButton) {
				// launch reversi.
				reversiUI();
			} else if (temp == sudokuButton) {
				// launch sudoku.
				sudokuUI();
			}

		}
	}

	/**
	 * 
	 */
	private void sudokuUI() {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 */
	private void checkersUI() {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 */
	private void reversiUI() {
		// TODO Auto-generated method stub

	}
}
