package gamesuite;

//CHECKSTYLE:OFF
import static org.junit.Assert.*;
import org.junit.Test;

public class CheckersTests {
	CheckersController game;
	
	@Test
	public void validMoveTest() {
		game = new CheckersController();
		assertTrue(game.isMove(5, 1, 4, 0));
	}
	
	@Test
	public void invalidMoveTest() {
		game = new CheckersController();
		assertFalse(game.isMove(2, 0, 3, 1));
	}
	
	@Test
	public void invalidMoveTest2() {
		game = new CheckersController();
		assertFalse(game.isMove(-1, -1, 3, 1));
	}
	
	@Test
	public void invalidMoveTest3() {
		game = new CheckersController();
		assertFalse(game.isMove(4, 4, 3, 1));
	}
	
	@Test
	public void invalidMoveTest4() {
		game = new CheckersController();
		assertFalse(game.isMove(100, 100, 3, 1));
	}
	
	@Test
	public void invalidMoveTest5() {
		game = new CheckersController();
		assertFalse(game.isMove(5, 100, 3, 1));
	}
	
	@Test
	public void invalidMoveTest6() {
		game = new CheckersController();
		assertFalse(game.isMove(5, -1, 3, 1));
	}
	
	@Test
	public void validJumpTest() {
		game = new CheckersController();
		game.makeMove(5, 1, 4, 0);
		game.makeMove(2, 2, 3, 1);
		assertTrue(game.isMove(4, 0, 2, 2));
	}
	
	@Test
	public void invalidJumpTest() {
		game = new CheckersController();
		assertFalse(game.isMove(5, 1, 3, 3));
	}
	
	@Test
	public void forceJumpTest() {
		game = new CheckersController();
		game.makeMove(5, 1, 4, 0);
		game.makeMove(2, 2, 3, 1);
		assertFalse(game.isMove(5, 3, 4, 2));
	}
	
	@Test
	public void forceJumpTest2() {
		game = new CheckersController();
		game.makeMove(5, 1, 4, 0);
		game.makeMove(2, 2, 3, 1);
		assertFalse(game.isMove(5, 3, 3, 5));
	}
	
	@Test
	public void forceJumpTest3() {
		game = new CheckersController();
		game.makeMove(5, 1, 4, 0);
		game.makeMove(2, 2, 3, 1);
		assertFalse(game.isMove(3, 1, 4, 3));
	}
	
	@Test
	public void forceJumpTestKing() {
		game = new CheckersController();
		assertFalse(game.isMove(1, 1, 0, 0));
	}
	
	@Test
	public void multiJumpTest() {
		game = new CheckersController();
		game.setupTest("MultiJump1");
		assertTrue(game.isMove(0, 0, 4, 4));
	}
	
	@Test
	public void multiJumpTest2() {
		game = new CheckersController();
		game.setupTest("MultiJump2");
		assertTrue(game.isMove(0, 0, 0, 4));
	}
	
	@Test
	public void multiJumpTest3() {
		game = new CheckersController();
		game.setupTest("MultiJump3");
		assertFalse(game.isMove(0, 0, 0, 4));
	}
	
	@Test
	public void stalemateTest() {
		game = new CheckersController();
		game.setupTest("Stalemate");
		assertTrue(game.isStalemate());
	}
	
	@Test
	public void gameoverTest() {
		game = new CheckersController();
		game.setupTest("GameOver");
		assertTrue(game.gameOver());
	}
}
//CHECKSTYLE:ON
