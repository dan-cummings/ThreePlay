package gamesuite;

import java.util.Scanner;

public class SudokuLogic {
	
	/*
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
	 * 		User Solving Methods:
	 * 			Single Candidate method - to look to a spot to see if only
	 * 				option exists for that spot
	 * 			Single Position method - to look to a row, col, or box, to
	 * 				see that a number within it only exists at a spot
	 * 		Difficulties:
	 * 			Easy - Using Single Candidate and Single Position to solve
	 * 				35 squares of the board
	 * 			Medium - Using the former methods to solve 45 squares of
	 * 				the board
	 * 			Hard - TO ADD... using more methods
	 * 		Credit:
	 * 			This site describes the techniques used to solve a board.
	 * 			http://www.sudokuoftheday.com/techniques/
	 */

	// Generating a Board
	// Goal: Remove 30 squares by removing pieces that would
	// 		 leave the game still solvable by the given methods
	public static int[][] generateInitialBoard(int removeQuantity, int array[][])
	{
		int[][] atempBoard = new int[9][9];
		// Set all the values of atempBoard == array
		for(int i =0; i<9; i++)
		{
			for(int j = 0; j<9; j++)
			{
				atempBoard[i][j] = array[i][j];
			}
		}
		int removedCount = 0;
		int rowRandom;
		int colRandom;
		int numberHolder;
		int counter;
		boolean shouldLoop;
		
		while(removedCount < removeQuantity)
		{
			// Take remove a square, hold onto that removed number
			// until it's checked to still be solvable
			counter = 0;
			shouldLoop = true;
			while(shouldLoop)
			{
				rowRandom = (int)(Math.random() * 8);
				colRandom = (int)(Math.random() * 8);
				numberHolder = atempBoard[rowRandom][colRandom];
				if(atempBoard[rowRandom][colRandom] != 0)
				{
					atempBoard[rowRandom][colRandom] = 0;
					if (solveBoard(atempBoard))
					{
						removedCount = removedCount + 1;
						shouldLoop = false;
						// System.out.println("Just Removed ["+ rowRandom + "]["+ colRandom +"]: " + atempBoard[rowRandom][colRandom]);
						// System.out.println("Remove Count: " + removedCount + "\n");
					}
					else
					{
						atempBoard[rowRandom][colRandom] = numberHolder;
						counter = counter + 1;
						// System.out.println("Trial: " + counter);
					}
				}
			}	
		}
		
		// We're here once the removeQuantity is met
		// ... and one last check
		if (solveBoard(atempBoard) != true)
		{
			System.out.println("ERROR IN INTERNAL GENERATING MECHANIC!");
		}
		
		return atempBoard;
	}
	
	public static boolean solveBoard(int array[][])
	{
		int[][] tempBoard = new int[9][9];
		// Set all values of tempBoard == array
		for(int i =0; i<9; i++)
		{
			for(int j = 0; j<9; j++)
			{
				tempBoard[i][j] = array[i][j];
			}
		}
		
		// System.out.print("   Within solveBoard() BEFORE:\n");
		// outputToConsole(tempBoard);
		
		// This will improve as more methods of 
		// solving come into play
		boolean solved = false;
		boolean madeProgress = false;
		while(solved == false)
		{
			// To make sure progress is being made
			madeProgress = false;
			
			// Try entire board for Single Candidate solutions
			for(int i=0;i<9;i++)
			{
				for(int j=0;j<9;j++)
				{
					// If square is empty
					if(tempBoard[i][j] == 0)
					{
						// If solvable square, solve it
						if(solvedBySingleCandidate(i,j,tempBoard) != 0)
						{
							tempBoard[i][j] = solvedBySingleCandidate(i,j,tempBoard);
							madeProgress = true;
						}
					}
				}
			}
			
			// Add another solving method HERE
			
			// Check if solved 
			solved = true;
			for(int i=0;i<9;i++)
			{
				for(int j=0;j<9;j++)
				{
					if(tempBoard[i][j] == 0)
					{
						solved = false;
					}
				}
			}
			if(solved == true)
			{
				// System.out.print("   Within solveBoard() AFTER:\n");
				// outputToConsole(tempBoard);
				return true;
			}
			if(madeProgress == false)
			{
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
	
	
	// Returns true if square is solvable by Single Candidate method
	public static int solvedBySingleCandidate(int row, int col, int array[][])
	{
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
		for(int i=0; i< 9;i++)
		{
			if(arrayContains(possible, array[i][col]))
			{
				SCarrayRemove(possible, array[i][col]);
			}
		}
		// Eliminate all numbers in that box from possibility				
		for(int i=0; i<3; i++)
		{
			for(int j=0; j<3; j++)
			{
				if(arrayContains( possible, array[(((row/3)*3)+i)][(((col/3)*3)+j )] ))
				{
					SCarrayRemove(possible, array[(((row/3)*3)+i)][(((col/3)*3)+j )] );
				}
			}
		}
		// See if there are multiple possibilities left in this square
		int stillPossible = 0;
		int lastRemaining = 0;
		for(int i=0; i< 9;i++)
		{
			if(possible[i] != 0)
			{
				stillPossible = stillPossible + 1;
				lastRemaining = possible[i];
			}
			if(stillPossible > 1)
			{
				return 0;
			}
		}
		return lastRemaining;
	}
	
	// Output the board to the console
	public static void outputToConsole(int array[][])
	{
		
		int a_counter = 0;
		int b_counter = 0;
		for(int i = 0; i<9; i++)
		{
			b_counter = b_counter + 1;
			for(int j = 0; j<9;j++)
			{
				a_counter = a_counter + 1;
				if(array[i][j] == 0)
				{
					System.out.print("-");
				}
				else
				{
					System.out.print(array[i][j]);
				}
				if(a_counter == 3)
				{
					System.out.print(" ");
					a_counter = 0;
				}
			}
			System.out.print("\n");
			if(b_counter == 3)
			{
				System.out.print("\n");
				b_counter = 0;
			}
		}
		
	}
	
	
	
	public static void main(String[] args) {
		
		String[][] stringBoard = new String[9][9];
		int[][] beginningBoard = new int[9][9];
		int[][] completeBoard = new int[9][9];
		int[][] initialBoard = new int[9][9];
		int[][] currentBoard = new int[9][9];
		int[][] errorsBoard = new int[9][9];
		
		// For use of scrambling the beginningBoard
		int[][] holdingArray = new int[2][9];
		int[][] secondArray = new int[2][9];
		int[][] thirdArray = new int[6][9];
		
		// A Sample Starting Board
		stringBoard[0] = "3 2 9 6 5 7 8 4 1".split(" ");
		stringBoard[1] = "7 4 5 8 3 1 2 9 6".split(" ");
		stringBoard[2] = "6 1 8 2 4 9 3 7 5".split(" ");
		stringBoard[3] = "1 9 3 4 6 8 5 2 7".split(" ");
		stringBoard[4] = "2 7 6 1 9 5 4 8 3".split(" ");
		stringBoard[5] = "8 5 4 3 7 2 6 1 9".split(" ");
		stringBoard[6] = "4 3 2 7 1 6 9 5 8".split(" ");
		stringBoard[7] = "5 8 7 9 2 3 1 6 4".split(" ");
		stringBoard[8] = "9 6 1 5 8 4 7 3 2".split(" ");
		
		//Transfer the string data to int
		for(int i =0; i<9; i++)
		{
			for(int j = 0; j<9; j++)
			{
				beginningBoard[i][j] = Integer.parseInt(stringBoard[i][j]);
			}
		}
		
		// Output the array
		System.out.println("   Beginning \"Key\" Board:");	
		outputToConsole(beginningBoard);
		
		// Set the completeBoard
		// completeBoard = beginningBoard;
		for(int i =0; i<9; i++)
		{
			for(int j = 0; j<9; j++)
			{
				completeBoard[i][j] = beginningBoard[i][j];
			}
		}
		
		// Swap Two Rows
		int row1 = 3;
		int row2 = 4;	
		holdingArray[0] = beginningBoard[row1];
		holdingArray[1] = beginningBoard[row2];
		beginningBoard[row1] = holdingArray[1];
		beginningBoard[row2] = holdingArray[0];
		
		// Output the array
		// System.out.println("   After Row Swap:");	
		// outputToConsole(beginningBoard);
		
		// Swap Two Cols
		int col1 = 6;
		int col2 = 8;	
		for(int i=0; i<9;i++)
		{
			secondArray[0][i] = beginningBoard[i][col1];
		}
		for(int i=0; i<9;i++)
		{
			secondArray[1][i] = beginningBoard[i][col2];
		}
		for(int i=0; i<9;i++)
		{
			beginningBoard[i][col1] = secondArray[1][i];
		}
		for(int i=0; i<9;i++)
		{
			beginningBoard[i][col2] = secondArray[0][i];
		}
		
		// Output the array
		// System.out.println("   After Column Swap:");
		// outputToConsole(beginningBoard);
		
		
		// Swap Six Rows, "Two Horizontal Chunks"
		// Swaps the top three rows (1,2,3) with a choice between (4,5,6) or (7,8,9)
		int chunk2 = 2;	// 1 or 2
		thirdArray[0] = beginningBoard[0];
		thirdArray[1] = beginningBoard[1];
		thirdArray[2] = beginningBoard[2];
		thirdArray[3] = beginningBoard[(3 * chunk2 ) + 0];
		thirdArray[4] = beginningBoard[(3 * chunk2 ) + 1];
		thirdArray[5] = beginningBoard[(3 * chunk2 ) + 2];
		beginningBoard[0] = thirdArray[3];
		beginningBoard[1] = thirdArray[4];
		beginningBoard[2] = thirdArray[5];
		beginningBoard[(3 * chunk2 ) + 0] = thirdArray[0];
		beginningBoard[(3 * chunk2 ) + 1] = thirdArray[1];
		beginningBoard[(3 * chunk2 ) + 2] = thirdArray[2];
		
		// Output the array
		// System.out.println("   After Horizontal Thirds Swap:");
		// outputToConsole(beginningBoard);
		
		
		
		System.out.print("\n\n");
		
		// Output the beginningBoard by method
		System.out.print("   Beginning \"Key\" Board: \n");
		outputToConsole(beginningBoard);
		System.out.print("   Complete Board: \n");
		outputToConsole(completeBoard);
		
		// Testing generation of initialBoard from completeBoard
		initialBoard = generateInitialBoard(35, completeBoard);
		System.out.print("   Initial Board: \n");
		outputToConsole(initialBoard);
		
		// Testing solvedBySingleCandidate(int row, int col, int array[][])
		System.out.print("   Testing solvedBySingleCandidate():\n");
		System.out.print("   Type in a [row][col] to see if that square is immediately solveable:\n");
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter the row: ");
		String rowString = scanner.nextLine();
		System.out.print("Enter the col: ");
		String colString = scanner.nextLine();
		int testRow = Integer.parseInt(rowString);
		int testCol = Integer.parseInt(colString);
		scanner.close();
		if(solvedBySingleCandidate(testRow, testCol, initialBoard) != 0)
		{
			System.out.print("Method solvedBySingleCandidate() RETURNED: ");
			System.out.print(solvedBySingleCandidate(testRow, testCol, initialBoard) + "\n");
		}
		else
		{
			System.out.print("Method solvedBySingleCandidate() RETURNED: 0 (or, false)\n");
		}
		
		// Testing solveBoard()
		System.out.print("Can Solve Initial Board: ");
		if(solveBoard(initialBoard))
		{
			System.out.print("Yes!");
		}
		else
		{
			System.out.print("No.");
		}
		
		
	}
}
