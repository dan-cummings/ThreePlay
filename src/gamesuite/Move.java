package gamesuite;

/**
 * 
 * @author Daniel Cummings
 *
 */
public class Move {
	/** */
	private int toX;
	/** */
	private int toY;
	/** */
	private int frX;
	/** */
	private int frY;
	
	/**
	 * 
	 * Constructor method for Move class.
	 * 
	 * @param tX 
	 * @param tY
	 * @param fX
	 * @param fY
	 */
	public Move(final int tX, final int tY,
			final int fX, final int fY) {
		this.setToX(tX);
		this.setToY(tY);
		this.setFrX(fX);
		this.setFrY(fY);		
	}

	/**
	 * 
	 * @return 
	 */
	public int getToX() {
		return toX;
	}

	/**
	 * 
	 * @param tX
	 */
	public void setToX(final int tX) {
		this.toX = tX;
	}

	/**
	 * 
	 * @return
	 */
	public int getToY() {
		return toY;
	}

	/**
	 * 
	 * @param toY
	 */
	public void setToY(final int tY) {
		this.toY = tY;
	}

	/**
	 * 
	 * @return
	 */
	public int getFrX() {
		return frX;
	}

	/**
	 * 
	 * @param fX
	 */
	public void setFrX(final int fX) {
		this.frX = fX;
	}

	/**
	 * 
	 * @return
	 */
	public int getFrY() {
		return frY;
	}

	/**
	 * 
	 * @param fY
	 */
	public void setFrY(final int fY) {
		this.frY = fY;
	}
}
