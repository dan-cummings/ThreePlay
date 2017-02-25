package gamesuite;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

/**
 * 
 * @author Jaden Sella
 * @version 0.1
 */
public class OthelloGUI extends JPanel implements MouseListener {
	/** Game played. */
	private Othello game;
	
	/**
	 * 
	 * @param game
	 */
	public OthelloGUI(final Othello game) {
		super.getInsets();
	}

	@Override
	public void mouseClicked(final MouseEvent e) { }

	@Override
	public void mousePressed(final MouseEvent e) { }

	@Override
	public void mouseReleased(final MouseEvent e) { }

	@Override
	public void mouseEntered(final MouseEvent e) { }

	@Override
	public void mouseExited(final MouseEvent e) { }

}
