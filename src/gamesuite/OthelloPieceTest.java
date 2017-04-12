package gamesuite;

//CHECKSTYLE:OFF
import static org.junit.Assert.*;

import org.junit.Test;

public class OthelloPieceTest {

	@Test
	public void testOthelloPiece() {
		OthelloPiece p = new OthelloPiece(Player.WHITE);
		p.switchOwner();
		assertFalse(p.getOwner() == Player.WHITE);
	}
	
	@Test
	public void testSwitchPlayer() {
		OthelloPiece p = new OthelloPiece(Player.WHITE);
		p.switchOwner();
		assertFalse(p.getOwner() == Player.WHITE);
		assertTrue(p.getOwner() == Player.BLACK);
	}

	@Test
	public void testSwitchPlayer2(){
		OthelloPiece p = new OthelloPiece(Player.BLACK);
		p.switchOwner();
		assertTrue(p.getOwner() == Player.WHITE);
	}
}
//CHECKSTYLE:ON