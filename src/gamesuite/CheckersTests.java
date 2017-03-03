package gamesuite;
//CHECKSTYLE:OFF
import static org.junit.Assert.*;
import org.junit.Test;

public class CheckersTests {
	CheckersLogic game;
	
	@Test
	public void validMoveTest() {
		game = new CheckersLogic();
		assertTrue(game.isMove(new Move(5, 1, 4, 0)));
	}
	
	@Test
	public void invalidMoveTest() {
		game = new CheckersLogic();
		assertFalse(game.isMove(new Move(2, 0, 3, 1)));
	}
	
	@Test
	public void invalidMoveTest2() {
		game = new CheckersLogic();
		assertFalse(game.isMove(new Move(-1, -1, 3, 1)));
	}
	
	@Test
	public void invalidMoveTest3() {
		game = new CheckersLogic();
		assertFalse(game.isMove(new Move(4, 4, 3, 1)));
	}
	
	@Test
	public void invalidMoveTest4() {
		game = new CheckersLogic();
		assertFalse(game.isMove(new Move(100, 100, 3, 1)));
	}
	
	@Test
	public void invalidMoveTest5() {
		game = new CheckersLogic();
		assertFalse(game.isMove(new Move(5, 100, 3, 1)));
	}
	
	@Test
	public void invalidMoveTest6() {
		game = new CheckersLogic();
		assertFalse(game.isMove(new Move(5, -1, 3, 1)));
	}
	
	@Test
	public void validJumpTest() {
		game = new CheckersLogic();
		game.makeMove(new Move(5, 1, 4, 0));
		game.makeMove(new Move(2, 2, 3, 1));
		assertTrue(game.isMove(new Move(4, 0, 2, 2)));
	}
	
	@Test
	public void invalidJumpTest() {
		game = new CheckersLogic();
		assertFalse(game.isMove(new Move(5, 1, 3, 3)));
	}
	
	@Test
	public void forceJumpTest() {
		game = new CheckersLogic();
		game.makeMove(new Move(5, 1, 4, 0));
		game.makeMove(new Move(2, 2, 3, 1));
		assertFalse(game.isMove(new Move(5, 3, 4, 2)));
	}
	
	@Test
	public void forceJumpTest2() {
		game = new CheckersLogic();
		game.makeMove(new Move(5, 1, 4, 0));
		game.makeMove(new Move(2, 2, 3, 1));
		assertFalse(game.isMove(new Move(5, 3, 3, 5)));
	}
	
	@Test
	public void forceJumpTest3() {
		game = new CheckersLogic();
		game.makeMove(new Move(5, 1, 4, 0));
		game.makeMove(new Move(2, 2, 3, 1));
		assertFalse(game.isMove(new Move(5, 3, 3, 5)));
	}
}
//CHECKSTYLE:ON
