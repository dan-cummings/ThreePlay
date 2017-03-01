package gamesuite;

/**
 * 
 * @author Jaden Sella
 */
public class OthelloPiece implements IPiece {

	/** Which player owns this piece. */
	private Player owner;

	/**
	 * Checkers piece constructor to set owner and beginning state.
	 * @param p Which player owns this piece.
	 */
	public OthelloPiece(final Player p) {
		this.setOwner(p);
	}

	/**
	 * Sets the owner of the checkers piece.
	 * @param p The player who owns the piece.
	 */
	private void setOwner(final Player p) {
		this.owner = p;
	}
	
	/**
	 * Sets the owner of the checkers piece.
	 */
	public void switchOwner() {
		if (this.owner == null) {
			return;
		}
		if (this.owner == Player.WHITE) {
			this.owner = Player.BLACK;
		} else {
			this.owner = Player.WHITE;
		}
	}

	@Override
	public final Player getOwner() {
		return owner;
	}
	
	@Override
	public final boolean validMove(final Move m, final IPiece[][] b) {
		return false;
	}

	@Override
	public final boolean validMove(final int x, final int y,
			final IPiece[][] b, final Player p) {
		
		if (b[x][y].getOwner() != null) {
			return false;
		}
		
		if (b[x + 1][y].getOwner() 
				!= null && b[x + 1][y].getOwner() != p) {
			for (int i = x + 2; i < b.length; i++) {
				if (b[i][y].getOwner() == null) { 
					break;
				}
				if (b[i][y].getOwner() == p) {
					return true;
				}
			}
		}
		
		if (b[x - 1][y].getOwner() 
				!= null && b[x - 1][y].getOwner() != p) {
			for (int i = x - 2; i >= 0; i--) {
				if (b[i][y].getOwner() == null) { 
					break;
				}
				if (b[i][y].getOwner() == p) {
					return true;
				}
			}
		}
		
		if (b[x][y + 1].getOwner() 
				!= null && b[x][y + 1].getOwner() != p) {
			for (int i = y + 2; i < b.length; i++) {
				if (b[x][i].getOwner() == null) {
					break;
				}
				if (b[x][i].getOwner() == p) {
					return true;
				}
			}
		}
		
		if (b[x][y - 1].getOwner() 
				!= null && b[x][y - 1].getOwner() != p) {
			for (int i = y - 2; i >= 0; i--) {
				if (b[x][i].getOwner() == null) {
					break;
				}
				if (b[x][i].getOwner() == p) {
					return true;
				}
			}
		}
		
		int j = y + 2;
		
		if (b[x + 1][y + 1].getOwner() 
			!= null && b[x + 1][y + 1].getOwner() != p) {
			for (int i = x + 2; i < b.length; i++) {
				if (j >= b.length) {
					break;
				}
				if (b[i][j].getOwner() == null) {
					break;
				}
				if (b[i][j].getOwner() == p) {
					return true;
				}
				j++;
			}
		}
		
		j = y + 2;
		
		if (b[x - 1][y + 1].getOwner() 
			!= null && b[x - 1][y + 1].getOwner() != p) {
			for (int i = x - 2; i >= 0; i--) {
				if (j >= b.length) {
					break;
				}
				if (b[i][j].getOwner() == null) {
					break;
				}
				if (b[i][j].getOwner() == p) {
					return true;
				}
				j++;
			}
		}
		
		j = y - 2;
		
		if (b[x - 1][y - 1].getOwner() 
			!= null && b[x - 1][y - 1].getOwner() != p) {
			for (int i = x - 2; i >= 0; i--) {
				if (j < 0) {
					break;
				}
				if (b[i][j].getOwner() == null) {
					break;
				}
				if (b[i][j].getOwner() == p) {
					return true;
				}
				j--;
			}
		}
		
		j = y - 2;
		
		if (b[x + 1][y - 1].getOwner() 
			!= null && b[x + 1][y - 1].getOwner() != p) {
			for (int i = x + 2; i < b.length; i++) {
				if (j < 0) {
					break;
				}
				if (b[i][j].getOwner() == null) {
					break;
				}
				if (b[i][j].getOwner() == p) {
					return true;
				}
				j--;
			}
		}
		
		return false;
	}
	
	

}
