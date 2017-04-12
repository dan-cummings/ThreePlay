package gamesuite;

//CHECKSTYLE:OFF
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 * @author Jaden Sella
 *
 */
public class OthelloLogicTest {

	/**
	 * Tests to make sure the constructor
	 * is working. 
	 */
	@Test
	public void testNewBoard() {
		Othello o = new Othello();
		assertFalse(o.isGameOver());
	}
	
	/**
	 * Tests to make sure the constructor
	 * is working. 
	 */
	@Test
	public void testInvalidMove() {
		Othello o = new Othello();
		
		assertTrue(o.getWhiteCount() == 2);
		assertTrue(o.getBlackCount() == 2);
		
		o.makeMove(1, 1);
		
		assertTrue(o.getWhiteCount() == 2);
		assertTrue(o.getBlackCount() == 2);
	}
	
	/** Tests to make sure the getPlayer
	 * method is working.
	 */
	@Test
	public void testGetPlayer() {
		Othello o = new Othello();
		assertTrue(o.getPlayer() == Player.BLACK);
	}
	
	/** 
	 * Tests move right function.
	 */
	@Test
	public void testMoveRight() {
		Othello o = new Othello();
		o.makeMove(2, 3);
		
		assertTrue(o.getPiece(2, 3).getOwner() == Player.BLACK);
		assertTrue(o.getPiece(3, 3).getOwner() == Player.BLACK);
		assertTrue(o.getWhiteCount() == 1);
		assertTrue(o.getBlackCount() == 4);
	}
	
	/** 
	 * Tests move down right function.
	 */
	@Test
	public void testMoveDownRight() {
		Othello o = new Othello();
		o.makeMove(5, 4);
		o.makeMove(5, 3);
		o.makeMove(5, 2);
		
		assertTrue(o.getPiece(4, 3).getOwner() == Player.BLACK);
		assertTrue(o.getPiece(5, 2).getOwner() == Player.BLACK);
		
		assertTrue(o.getBlackCount() == 6);
		assertTrue(o.getWhiteCount() == 1);
	}
	
	/** 
	 * Tests move up left function.
	 */
	@Test
	public void testMoveUpLeft() {
		Othello o = new Othello();
		o.makeMove(5, 4);
		o.makeMove(5, 3);
		o.makeMove(5, 2);
		
		assertTrue(o.getPlayer() == Player.WHITE);
		
		o.makeMove(5, 5);
		
		assertTrue(o.getPiece(5, 5).getOwner() == Player.WHITE);
		assertTrue(o.getPiece(4, 4).getOwner() == Player.WHITE);
		
		assertTrue(o.getBlackCount() == 5);
		assertTrue(o.getWhiteCount() == 3);
	}
	
	/** 
	 * Tests move up right function.
	 */
	@Test
	public void testMoveUpRight() {
		Othello o = new Othello();
		o.makeMove(5, 4);
		o.makeMove(5, 3);
		o.makeMove(5, 2);
		
		assertTrue(o.getPlayer() == Player.WHITE);
		
		o.makeMove(5, 5);
		
		assertTrue(o.getPiece(5, 5).getOwner() == Player.WHITE);
		assertTrue(o.getPiece(4, 4).getOwner() == Player.WHITE);
		
		assertTrue(o.getBlackCount() == 5);
		assertTrue(o.getWhiteCount() == 3);
		
		o.makeMove(3, 5);
		
		assertTrue(o.getPiece(3, 5).getOwner() == Player.BLACK);
		assertTrue(o.getPiece(4, 4).getOwner() == Player.BLACK);
		
		assertTrue(o.getBlackCount() == 7);
		assertTrue(o.getWhiteCount() == 2);
	}
	
	/** 
	 * Tests move down left function.
	 */
	@Test
	public void testMoveDownLeft() {
		Othello o = new Othello();
		o.makeMove(5, 4);
		o.makeMove(5, 3);
		o.makeMove(5, 2);
		
		assertTrue(o.getPlayer() == Player.WHITE);
		
		o.makeMove(5, 5);
		
		assertTrue(o.getPiece(5, 5).getOwner() == Player.WHITE);
		assertTrue(o.getPiece(4, 4).getOwner() == Player.WHITE);
		
		assertTrue(o.getBlackCount() == 5);
		assertTrue(o.getWhiteCount() == 3);
		
		o.makeMove(3, 5);
		
		assertTrue(o.getPiece(3, 5).getOwner() == Player.BLACK);
		assertTrue(o.getPiece(4, 4).getOwner() == Player.BLACK);
		
		assertTrue(o.getBlackCount() == 7);
		assertTrue(o.getWhiteCount() == 2);
		
		o.makeMove(3, 6);
		o.makeMove(2, 6);
		o.makeMove(6, 1);
		
		assertTrue(o.getPiece(6, 1).getOwner() == Player.WHITE);
		assertTrue(o.getPiece(5, 2).getOwner() == Player.WHITE);
		assertTrue(o.getPiece(4, 3).getOwner() == Player.WHITE);
		
		assertTrue(o.getBlackCount() == 5);
		assertTrue(o.getWhiteCount() == 7);
	}
	
	/** 
	 * Tests to make sure the game works.
	 * Tests partial game. Tests reset function. 
	 */
	@Test
	public void testPartialGame() {
		Othello o = new Othello();
		assertTrue(o.isMove(3, 2));
		o.makeMove(3, 2);
		assertTrue(o.getPlayer() == Player.WHITE);
		assertTrue(o.getPiece(3, 2).getOwner() == Player.BLACK);
		assertTrue(o.getPiece(3, 3).getOwner() == Player.BLACK);
		
		assertFalse(o.isMove(2, 3));
		assertFalse(o.isMove(5, 4));
		assertTrue(o.isMove(2, 2));
		o.makeMove(2, 2);
		assertTrue(o.getPlayer() == Player.BLACK);
		assertTrue(o.getPiece(2, 2).getOwner() == Player.WHITE);
		assertTrue(o.getPiece(3, 3).getOwner() == Player.WHITE);
		assertTrue(o.getPiece(4, 3).getOwner() == Player.BLACK);
		assertTrue(o.getPiece(4, 4).getOwner() == Player.WHITE);
		assertTrue(o.getPiece(3, 2).getOwner() == Player.BLACK);
		assertTrue(o.getPiece(3, 4).getOwner() == Player.BLACK);
		
		o.makeMove(4, 5);
		assertTrue(o.getPiece(4, 5).getOwner() == Player.BLACK);
		assertTrue(o.getPiece(4, 4).getOwner() == Player.BLACK);
		
		assertTrue(o.getPlayer() == Player.WHITE);
		o.makeMove(5, 3);
		
		assertTrue(o.getPiece(4, 3).getOwner() == Player.WHITE);
		assertTrue(o.getPiece(5, 3).getOwner() == Player.WHITE);
		
		assertTrue(o.getBlackCount() == 4);
		assertTrue(o.getWhiteCount() == 4);
		
		o.reset();
		
		assertTrue(o.getBlackCount() == 2);
		assertTrue(o.getWhiteCount() == 2);
	}
	
	/** 
	 * Tests to make sure the game works.
	 * Tests full game. Tests reset function. 
	 */
	@Test
	public void testFullGame() {
		Othello o = new Othello();
		
		for (int i = 1; i < 7; i++) {
			for (int j = 1; j < 7; j++) {
				o.setPiece(i, j, Player.BLACK);
			}
		}
		for (int i = 3; i < 5; i++) {
			for (int j = 3; j < 5; j++) {
				o.setPiece(i, j, Player.WHITE);
			}
		}
		assertTrue(o.getPiece(2, 2).getOwner() == Player.BLACK);
		assertTrue(o.getPiece(1, 1).getOwner() == Player.BLACK);
		assertTrue(o.getPiece(6, 6).getOwner() == Player.BLACK);
		assertTrue(o.getPiece(3, 3).getOwner() == Player.WHITE);

		assertTrue(o.getPlayer() == Player.BLACK);
		o.makeMove(1, 1);
		assertTrue(o.getPlayer() == Player.WHITE);
		assertFalse(o.isGameOver());
		
		o.makeMove(0, 0);
		assertTrue(o.getPiece(0, 0).getOwner() == Player.WHITE);
		assertTrue(o.getPiece(5, 5).getOwner() == Player.BLACK);
		assertTrue(o.getPiece(4, 4).getOwner() == Player.WHITE);
		assertTrue(o.getPiece(3, 3).getOwner() == Player.WHITE);
		assertTrue(o.getPiece(2, 2).getOwner() == Player.WHITE);
		assertTrue(o.getPiece(1, 1).getOwner() == Player.WHITE);
		o.makeMove(1, 0);
		assertTrue(o.getPlayer() == Player.WHITE);
		o.makeMove(7, 7);
		assertTrue(o.getPiece(7, 7).getOwner() == Player.WHITE);
		o.makeMove(6, 7);
		assertTrue(o.getPlayer() == Player.WHITE);
		o.makeMove(0, 7);
		assertTrue(o.getPiece(0, 7).getOwner() == Player.WHITE);
		o.makeMove(0, 6);
		assertTrue(o.getPlayer() == Player.WHITE);
		o.makeMove(7, 0);
		assertTrue(o.getPiece(7, 0).getOwner() == Player.WHITE);
	}
	
	/** 
	 * Tests end of game scenario.
	 */
	@Test
	public void testEndGame() {
		Othello o = new Othello();
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				o.setPiece(i, j, Player.BLACK);
			}
		}
		assertTrue(o.isGameOver());
	}
	
	/** 
	 * Tests save and load of game.
	 * @throws Exception to handle load/save exceptions
	 *  
	 */
	@Test
	public void testSaveLoad() throws Exception {
		Othello o = new Othello();
		String s = "hello";
		o.saveState(s);
		o.loadState(s);
	}
}
//CHECKSTYLE:ON