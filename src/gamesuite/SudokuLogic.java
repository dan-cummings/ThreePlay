package gamesuite;

import java.util.Scanner;

/**
 * @author Brendon Murthum
 */

/*
 *  Code:
 *  	"UPDATE ME" is at the spots for future updates
 * 
 *  Terms:
 *  	Board - The 9x9 complete array
 * 		Box - A 3x3 chunk of squares
 * 		Square - A single, changeable, smallest unit
 * 
 * 		completeBoard - The full correct board
 * 		initialBoard - The board first given to the player
 * 		currentBoard - The board as the game continues (load to this)
 * 		errorsBoard - The errors on the board as the game continues
 * 
 * 	User Solving Methods:
 * 		Single Candidate method - to look to a spot to see if only
 * 			option exists for that spot
 * 		Single Position method - to look to a row, col, or box, to
 * 			see that a number within it only exists at a spot
 * 	Difficulties:
 * 		Easy - Using Single Candidate and Single Position to solve
 * 			~30 squares of the board
 * 		Medium - Using the former methods to solve 50 squares of
 * 			the board
 * 		Hard - TO ADD... using more methods
 * 	Credit:
 * 		This site describes the techniques used to solve a board.
 * 		http://www.sudokuoftheday.com/techniques/
 *
 *	Methods:
 * 		int[][] generateInitialBoard(int removeQuantity, int array[][])
 *			- Returns an always-solvable board
 *		boolean solveBoard(int array[][])
 *			- Returns TRUE, if it is solveable by our given methods
 *			- Returns FALSE, if it is not solvable
 *			- This method used in validating an initial board
 * 		int solvedBySingleCandidate(int row, int col, int array[][])
 *			- Looks to solve a square by "Single Candidate Method"
 *			- Returns 0, if square is currently unsolvable
 * 			- Returns #, if solvable, where # is the solution
 *		void outputToConsole(int array[][])
 *			- Outputs the given board to the console
 *		int[][] generateBoard()
 *			- Returns a randomized complete board
 *			- Uses outputBeginningBoard()
 *		int[][] outputBeginningBoard()
 *			- Returns a "key" board to be randomized
 *		boolean isSolved(int array[][])
 *			- Returns TRUE, if the given board is full
 *			- Returns FALSE, if there is empty squares
 *
 *	To-Do:
 *		Transfer code to object-oriented focus
 *		Write in the "Single Position" method of solving
 *			- Allows for more complex puzzles
 *			- More spaces opened on the board as well
 *		Remove "static"
 */

public class SudokuLogic implements IGameLogic {
	
	/** Current board status of the game. */
	private SudokuPiece[][] board;
	private Player player;
	private int[][] completeBoard = new int[9][9];
	private int[][] initialBoard = new int[9][9];
	private int[][] currentBoard = new int[9][9];
	private boolean[][] errorsBoard = new boolean[9][9];
	public boolean gameComplete;
	private int size;
	private int clickedX, clickedY;
	private int removeThisMany = 5;
	
	/* Initializes completeBoard, initialBoard, currentBoard and errorsBoard */
	public SudokuLogic() {
		this.initializeGame();
		this.gameComplete = false;
		this.size = 9;
	}
	
	/* Returns the width of the board. SHOULD BE 9 FOR SUDOKU! */
	public int getSize(){
		return size;
	}
	
	/* Returns INT of the IPiece at row, col. */
	public int getNumber(int row, int col){
		//return currentBoard[row][col];
		return this.board[row][col].getNum();
	}
	
	/* Returns TRUE if a square at row, col, was initially set */
	public boolean isInitial(int row, int col){
		if(initialBoard[row][col] != 0){
			return true;
		}
		return false;
	}
	
	/**
	 * Changes the selected pieces number.
	 * @param num number to change selected piece to.
	 */
	public void setNumber(final int num) {
		board[clickedY][clickedX].setNum(num);
		
		if(board[clickedY][clickedX].getNum() != completeBoard[clickedY][clickedX]){
			errorsBoard[clickedY][clickedX] = true;
		} else {
			errorsBoard[clickedY][clickedX] = false;
		}
		
	}
	
	/* Returns TRUE if a square at row, col, is not parallel with the completeBoard */
	public boolean isError(int row, int col){
		return errorsBoard[row][col];
	}

	/* This initializes the game. board
	 * 		- Sets the IPiece board
	 * 		- Generates the array of the completeBoard
	 * 		- Generates the array of the initialBoard
	 * 		- Generates the array of the errorsBoard
	 * 		- Generates the array of the currentBoard
	 * 			- This is the board the player interacts with
	 * 		- Populates the IPiece board with SudokuPiece
	 */
	private void initializeGame(){
		this.board = new SudokuPiece[9][9];
		this.completeBoard = generateBoard();
		this.initialBoard = generateInitialBoard(removeThisMany);
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				this.errorsBoard[i][j] = false;
				this.currentBoard[i][j] = initialBoard[i][j];
				board[i][j] = new SudokuPiece(currentBoard[i][j]);
			}
		}
	}
	
	/** Generates the initialBoard */
	public int[][] generateInitialBoard(int removeQuantity) {
		int[][] atempBoard = new int[9][9];
		// Set all the values of atempBoard == array
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				atempBoard[i][j] = this.completeBoard[i][j];
			}
		}
		int repeatCount = 0;
		int removedCount = 0;
		int rowRandom;
		int colRandom;
		int numberHolder;
		int counter;
		boolean shouldLoop;
		boolean shouldRepeat = false;
		
		while (removedCount < removeQuantity) {
			// Starts again if comes to dead end
			if (shouldRepeat) {
				shouldRepeat = false;
				removedCount = 0;
				for (int i = 0; i < 9; i++) {
					for (int j = 0; j < 9; j++) {
						atempBoard[i][j] = this.completeBoard[i][j];
					}
				}
				// Testing
				// System.out.print("Repeat: " + repeatCount + "\n");
				repeatCount = repeatCount + 1;
			}
			
			/* 
			 * Remove a square. Hold onto that removed number
			 * until it's checked to still be solvable
			 */
			counter = 0;
			shouldLoop = true;
			while (shouldLoop) {
				rowRandom = (int) (Math.random() * 9);
				colRandom = (int) (Math.random() * 9);
				numberHolder = atempBoard[rowRandom][colRandom];
				if (atempBoard[rowRandom][colRandom] != 0) {
					atempBoard[rowRandom][colRandom] = 0;
					if (solveBoard(atempBoard)) {
						removedCount = removedCount + 1;
						shouldLoop = false;
						// Testing
						// System.out.println("Just Removed ["+ rowRandom + "]["+ colRandom +"]: " + numberHolder);
						// System.out.println("Remove Count: " + removedCount);
					} else {
						atempBoard[rowRandom][colRandom] = numberHolder;
						counter = counter + 1;
						if (counter > 90){
							shouldRepeat = true;
							shouldLoop = false;
							if(repeatCount > 10){
								System.out.print("ERROR: unable to remove " + removeQuantity + " squares.\n");
								System.out.print("Removed " + removedCount + " squares.\n");
								return atempBoard;
							}
						}
					}
				}
			}
			
			
			
		}
		
		// We're here once the removeQuantity is met
		// ... and one last check
		if (solveBoard(atempBoard) != true){
			System.out.println("ERROR IN INTERNAL GENERATING MECHANIC!");
		}
		
		return atempBoard;
	}
	
	public static boolean solveBoard(int array[][]){
		int[][] tempBoard = new int[9][9];
		// Set all values of tempBoard == array
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				tempBoard[i][j] = array[i][j];
			}
		}
		
		// System.out.print("   Within solveBoard() BEFORE:\n");
		// outputToConsole(tempBoard);
		
		// This will improve as more methods of 
		// solving come into play
		boolean solved = false;
		boolean madeProgress = false;
		while (solved == false) {
			// To make sure progress is being made
			madeProgress = false;
			
			// Try entire board for Single Candidate solutions
			for(int i=0;i<9;i++){
				for(int j=0;j<9;j++){
					// If square is empty
					if(tempBoard[i][j] == 0){
						// If solvable square, solve it
						if(solvedBySingleCandidate(i,j,tempBoard) != 0){
							tempBoard[i][j] = solvedBySingleCandidate(i,j,tempBoard);
							madeProgress = true;
						}
					}
				}
			}
			
			// UPDATE ME: Add a future solving method HERE
			
			// Check if solved 
			solved = true;
			for(int i=0;i<9;i++){
				for(int j=0;j<9;j++){
					if(tempBoard[i][j] == 0){
						solved = false;
					}
				}
			}
			if(solved == true){
				// System.out.print("   Within solveBoard() AFTER:\n");
				// outputToConsole(tempBoard);
				return true;
			}
			if(madeProgress == false){
				return false;
			}
		}
		
		// This is a catch-all and should not be seen
		System.out.println("ERROR IN INTERNAL SOLVING MECHANIC!");	
		return false;
	}
	
	// To remove bits from the Board
	public static int[][] removeSquare(int row, int col, int array[][]){
		array[row][col] = 0;
		return array;
	}
	
	// Clicked on piece
	public void clickedOn(int row, int col){
		clickedX = col;
		clickedY = row;
		
	}
	
	// Returns the col of the current square selected
	public int currentClickedX(){
		return clickedX;
	}
	// Returns the row of the current square selected
	public int currentClickedY(){
		return clickedY;
	}
	
	// Returns if the piece is selected
	
	
	// Returns true if the PossibleArray (of SingleCandidate) contains num
	public static boolean arrayContains(int array[], int num)
	{
		for(int i=0; i<9; i++)
		{
			if(array[i] == num)
			{
				return true;
			}
		}
		return false;
	}
	
	// Removes a number from possible contains
	public static void SCarrayRemove(int array[], int num)
	{
		// No need to check for emptiness
		if (num == 0)
		{
			return;
		}
		// Remove the num from the array
		for(int i=0; i<9; i++)
		{
			if(array[i] == num)
			{
				array[i] = 0;
				return;
			}
		}
	}
	
	
	/*
	 *  Returns: 0, if square is not solvable
	 *  		 #, where # is the number found
	 */
	public static int solvedBySingleCandidate(int row, int col, int array[][]){
		// Start with all possibility
		int possible[] = {1,2,3,4,5,6,7,8,9};
		// If square is filled, return true
		if(array[row][col] != 0)
		{
			return array[row][col];
		}
		// Eliminates all numbers in that row from possibility
		for(int i=0; i< 9;i++)
		{
			if(arrayContains(possible, array[row][i]))
			{
				SCarrayRemove(possible, array[row][i]);
			}
		}
		
		/* Print possible options for a certain spot
		 * For Testing
		System.out.println("After row removal:");
		for(int i=0; i<9; i++)
		{
			System.out.print(possible[i] + " ");
		}
		System.out.print("\n");
		*/
		
		// Eliminate all numbers in that column from possibility
		for(int i=0; i< 9;i++){
			if(arrayContains(possible, array[i][col])){
				SCarrayRemove(possible, array[i][col]);
			}
		}
		// Eliminate all numbers in that box from possibility				
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				if(arrayContains( possible, array[(((row/3)*3)+i)][(((col/3)*3)+j )] )){
					SCarrayRemove(possible, array[(((row/3)*3)+i)][(((col/3)*3)+j )] );
				}
			}
		}
		// See if there are multiple possibilities left in this square
		int stillPossible = 0;
		int lastRemaining = 0;
		for(int i=0; i< 9;i++){
			if(possible[i] != 0){
				stillPossible = stillPossible + 1;
				lastRemaining = possible[i];
			}
			if(stillPossible > 1){
				return 0;
			}
		}
		return lastRemaining;
	}
	
	/* Outputs a boardArray to the console for testing */
	public static void outputToConsole(int array[][]){
		int a_counter = 0;
		int b_counter = 0;
		for(int i = 0; i<9; i++){
			b_counter = b_counter + 1;
			for(int j = 0; j<9;j++){
				a_counter = a_counter + 1;
				if(array[i][j] == 0){
					System.out.print("-");
				}
				else{
					System.out.print(array[i][j]);
				}
				if(a_counter == 3){
					System.out.print(" ");
					a_counter = 0;
				}
			}
			System.out.print("\n");
			if(b_counter == 3){
				System.out.print("\n");
				b_counter = 0;
			}
		}	
	}
	
	/* Generates a randomized completeBoard from the beginningBoard */
	public static int[][] generateBoard(){
		int[][] btempBoard = new int[9][9];
		btempBoard = outputBeginningBoard();
		int holdingNumber;
		int decidingRandom;
		
		/* Swap some random rows */
		decidingRandom = (int)(Math.random() * 2); // 0, 1, 2
		if(decidingRandom == 1){
			for(int i=0; i<9; i++){
				holdingNumber = btempBoard[0][i];
				btempBoard[0][i] = btempBoard[1][i];
				btempBoard[1][i] = holdingNumber;
			}
		} 
		if(decidingRandom == 2){
			for(int i=0; i<9; i++){
				holdingNumber = btempBoard[0][i];
				btempBoard[0][i] = btempBoard[2][i];
				btempBoard[2][i] = holdingNumber;
			}
		}
		decidingRandom = (int)(Math.random() * 3); // 0, 1, 2
		if(decidingRandom == 1){
			for(int i=0; i<9; i++)
			{
				holdingNumber = btempBoard[3][i];
				btempBoard[3][i] = btempBoard[4][i];
				btempBoard[4][i] = holdingNumber;
			}
		} 
		if(decidingRandom == 2){
			for(int i=0; i<9; i++){
				holdingNumber = btempBoard[3][i];
				btempBoard[3][i] = btempBoard[5][i];
				btempBoard[5][i] = holdingNumber;
			}
		}
		decidingRandom = (int)(Math.random() * 3); // 0, 1, 2
		if(decidingRandom == 1){
			for(int i=0; i<9; i++){
				holdingNumber = btempBoard[6][i];
				btempBoard[6][i] = btempBoard[7][i];
				btempBoard[7][i] = holdingNumber;
			}
		} 
		if(decidingRandom == 2){
			for(int i=0; i<9; i++){
				holdingNumber = btempBoard[6][i];
				btempBoard[6][i] = btempBoard[8][i];
				btempBoard[8][i] = holdingNumber;
			}
		}
		
		/* Swap some random columns */
		decidingRandom = (int)(Math.random() * 3); // 0, 1, 2
		if(decidingRandom == 1){
			for(int i=0; i<9; i++){
				holdingNumber = btempBoard[i][0];
				btempBoard[i][0] = btempBoard[i][1];
				btempBoard[i][1] = holdingNumber;
			}
		} 
		if(decidingRandom == 2){
			for(int i=0; i<9; i++){
				holdingNumber = btempBoard[i][0];
				btempBoard[i][0] = btempBoard[i][2];
				btempBoard[i][2] = holdingNumber;
			}
		}
		decidingRandom = (int)(Math.random() * 3); // 0, 1, 2
		if(decidingRandom == 1){
			for(int i=0; i<9; i++){
				holdingNumber = btempBoard[i][3];
				btempBoard[i][3] = btempBoard[i][4];
				btempBoard[i][4] = holdingNumber;
			}
		} 
		if(decidingRandom == 2){
			for(int i=0; i<9; i++){
				holdingNumber = btempBoard[i][3];
				btempBoard[i][3] = btempBoard[i][5];
				btempBoard[i][5] = holdingNumber;
			}
		}
		decidingRandom = (int)(Math.random() * 3); // 0, 1, 2
		if(decidingRandom == 1){
			for(int i=0; i<9; i++){
				holdingNumber = btempBoard[i][6];
				btempBoard[i][6] = btempBoard[i][7];
				btempBoard[i][7] = holdingNumber;
			}
		} 
		if(decidingRandom == 2){
			for(int i=0; i<9; i++){
				holdingNumber = btempBoard[i][6];
				btempBoard[i][6] = btempBoard[i][8];
				btempBoard[i][8] = holdingNumber;
			}
		}
		
		/*
		 *  Swap two horizontal 3-tall chunks
		 *  EX: "ABC" ->  After "BAC" "ACB" "CAB" "ABC"
		 */
		decidingRandom = (int)(Math.random() * 4); // 0, 1, 2, 3
		if (decidingRandom == 0){
			for(int h=0; h<3; h++){
				for(int i=0; i<9; i++){
					holdingNumber = btempBoard[h][i];
					btempBoard[h][i] = btempBoard[h+3][i];
					btempBoard[h+3][i] = holdingNumber;
				}
			}
		}
		if (decidingRandom == 1){
			for(int h=0; h<3; h++){
				for(int i=0; i<9; i++){
					holdingNumber = btempBoard[h][i];
					btempBoard[h][i] = btempBoard[h+6][i];
					btempBoard[h+6][i] = holdingNumber;
				}
			}
		}
		if (decidingRandom == 2){
			for(int h=3; h<6; h++){
				for(int i=0; i<9; i++){
					holdingNumber = btempBoard[h][i];
					btempBoard[h][i] = btempBoard[h+3][i];
					btempBoard[h+3][i] = holdingNumber;
				}
			}
		}
		
		/*
		 *  Swap two vertical 3-wide chunks
		 *  Original "ABC" ->  After "BAC" "ACB" "CAB" "ABC"
		 */
		decidingRandom = (int)(Math.random() * 4); // 0, 1, 2, 3
		if (decidingRandom == 0){
			for(int h=0; h<3; h++){
				for(int i=0; i<9; i++){
					holdingNumber = btempBoard[i][h];
					btempBoard[i][h] = btempBoard[i][h+3];
					btempBoard[i][h+3] = holdingNumber;
				}
			}
		}
		if (decidingRandom == 1){
			for(int h=0; h<3; h++){
				for(int i=0; i<9; i++){
					holdingNumber = btempBoard[i][h];
					btempBoard[i][h] = btempBoard[i][h+6];
					btempBoard[i][h+6] = holdingNumber;
				}
			}
		}
		if (decidingRandom == 2){
			for(int h=3; h<6; h++){
				for(int i=0; i<9; i++){
					holdingNumber = btempBoard[i][h];
					btempBoard[i][h] = btempBoard[i][h+3];
					btempBoard[i][h+3] = holdingNumber;
				}
			}
		}
		
		/*
		 *  On the overall board, swap the complete placements of two integers
		 *  EX: "Wherever an x put a y, and a y an x."
		 */
		int randomX = (int)((Math.random() * 9) + 1);
		int randomY = (int)((Math.random() * 9) + 1);
		while(randomX == randomY){
			randomY = (int)((Math.random() * 9) + 1);
		}
		for(int i =0; i<9; i++){
			for(int j = 0; j<9; j++){
				if(btempBoard[i][j] == randomX){
					btempBoard[i][j] = randomY;
				}
				else if(btempBoard[i][j] == randomY){
					btempBoard[i][j] = randomX;
				}
			}
		}
		return btempBoard;
	}
	
	// Player Entry method
	// A player wants to enter a number to the board
	public static int[][] playerEntry(int num, int row, int col, int array[][], int barray[][])
	{
		// UPDATE ME: make these arrays point to class private values
		// array[][] is the currentBoard
		// barray[][] is the initialBoard
		int[][] ctempBoard = new int[9][9];
		for(int i=0; i <9; i++){
			for(int j=0; j<9;j++){
				ctempBoard[i][j] = array[i][j];
			}
		}
		// If invalid, return the board before the change
		if(num < 1 || num > 9){
			return ctempBoard;
		}
		if(barray[row][col] != 0){
			return ctempBoard;
		}
		ctempBoard[row][col] = num;
		return ctempBoard;
	}
	
	/* Returns an array of a "key" board. Internal uses. */
	public static int[][] outputBeginningBoard()
	{
		String[][] stringBoard = new String[9][9];
		int[][] someBoard = new int[9][9];
		stringBoard[0] = "3 2 9 6 5 7 8 4 1".split(" ");
		stringBoard[1] = "7 4 5 8 3 1 2 9 6".split(" ");
		stringBoard[2] = "6 1 8 2 4 9 3 7 5".split(" ");
		stringBoard[3] = "1 9 3 4 6 8 5 2 7".split(" ");
		stringBoard[4] = "2 7 6 1 9 5 4 8 3".split(" ");
		stringBoard[5] = "8 5 4 3 7 2 6 1 9".split(" ");
		stringBoard[6] = "4 3 2 7 1 6 9 5 8".split(" ");
		stringBoard[7] = "5 8 7 9 2 3 1 6 4".split(" ");
		stringBoard[8] = "9 6 1 5 8 4 7 3 2".split(" ");
		for(int i =0; i<9; i++){
			for(int j = 0; j<9; j++){
				someBoard[i][j] = Integer.parseInt(stringBoard[i][j]);
			}
		}
		return someBoard;
	}
	
	/*
	 *  Returns TRUE if the board is filled completely
	 *  Returns FALSE if the board is not filled
	 *  Used to trigger end of game
	 */
	public boolean isFilled()
	{
		for (int i =0; i<9; i++) {
			for (int j = 0; j<9; j++) {
				if (board[i][j].getNum() == 0) {
					return false;
				}
			}
		}
		return true;	
	}
	
	/* 
	 *  Compares two boards. 
	 *  Returns FALSE if there is a difference between them
	 *  Returns TRUE, otherwise
	 *  Used to generate the errorBoard
	 */
	public boolean isCorrect(){
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (board[i][j].getNum() != completeBoard[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean isGameOver() { 
		if(isFilled() && isCorrect()){
			return true;
		}
		return false;	
	}

	@Override
	public boolean isMove(Move m) {	return false; }

	@Override
	public boolean isMove(int x, int y) { return false; }

	@Override
	public void saveState(String filename) throws Exception { }

	@Override
	public void loadState(String filename) throws Exception { } 
	
}
