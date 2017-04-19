package gamesuite;
//CHECKSTYLE:OFF
import static org.junit.Assert.*;
import org.junit.Test;

public class SudokuTests {
	SudokuLogic game;
	
	@Test
	public void validInitialBoardTest() {
		game = new SudokuLogic(20);
		assertTrue(game.isSolvable());
	}
	
	@Test
	public void isFilledTest() {
		game = new SudokuLogic(20);
		assertFalse(game.isFilled());
	}
	
	@Test
	public void isCorrectTest() {
		game = new SudokuLogic(20);
		assertFalse(game.isCorrect());
	}
	
}
//CHECKSTYLE:ON
