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
		int repeatCount = 0;
		int removedCount = 0;
		int rowRandom;
		int colRandom;
		int numberHolder;
		int counter;
		boolean shouldLoop;
		boolean shouldRepeat = false;
		
		while(removedCount < removeQuantity)
		{
			// To start from scratch if comes to dead end
			if(shouldRepeat)
			{
				shouldRepeat = false;
				removedCount = 0;
				for(int i =0; i<9; i++)
				{
					for(int j = 0; j<9; j++)
					{
						atempBoard[i][j] = array[i][j];
					}
				}
				// System.out.print("Repeat: " + repeatCount + "\n");
				repeatCount = repeatCount + 1;
			}
			
			// Take remove a square, hold onto that removed number
			// until it's checked to still be solvable
			counter = 0;
			shouldLoop = true;
			while(shouldLoop)
			{
				rowRandom = (int)(Math.random() * 9);
				colRandom = (int)(Math.random() * 9);
				numberHolder = atempBoard[rowRandom][colRandom];
				if(atempBoard[rowRandom][colRandom] != 0)
				{
					atempBoard[rowRandom][colRandom] = 0;
					if (solveBoard(atempBoard))
					{
						removedCount = removedCount + 1;
						shouldLoop = false;
						// System.out.println("Just Removed ["+ rowRandom + "]["+ colRandom +"]: " + numberHolder);
						// System.out.println("Remove Count: " + removedCount);
					}
					else
					{
						atempBoard[rowRandom][colRandom] = numberHolder;
						counter = counter + 1;
						if (counter > 90)
						{
							shouldRepeat = true;
							shouldLoop = false;
							if(repeatCount > 10)
							{
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
	
	// Generates a randomized completeBoard from the beginningBoard
	public static int[][] generateBoard()
	{
		// (1) Swap rows
		// (2) Swap cols
		// (3) Swap chunks
		// (4) Swap two numbers
		
		int[][] btempBoard = new int[9][9];
		btempBoard = outputBeginningBoard();
		
		int holdingNumber;
		int decidingRandom;
		
		// Swap some rows randomly
		decidingRandom = (int)(Math.random() * 2); // 0, 1, 2
		if(decidingRandom == 1)
		{
			// Swap row0 and row1
			for(int i=0; i<9; i++)
			{
				holdingNumber = btempBoard[0][i];
				btempBoard[0][i] = btempBoard[1][i];
				btempBoard[1][i] = holdingNumber;
			}
		} 
		if(decidingRandom == 2)
		{
			// Swap row0 and row2
			for(int i=0; i<9; i++)
			{
				holdingNumber = btempBoard[0][i];
				btempBoard[0][i] = btempBoard[2][i];
				btempBoard[2][i] = holdingNumber;
			}
		}
		decidingRandom = (int)(Math.random() * 3); // 0, 1, 2
		if(decidingRandom == 1)
		{
			// Swap row3 and row4
			for(int i=0; i<9; i++)
			{
				holdingNumber = btempBoard[3][i];
				btempBoard[3][i] = btempBoard[4][i];
				btempBoard[4][i] = holdingNumber;
			}
		} 
		if(decidingRandom == 2)
		{
			// Swap row3 and row5
			for(int i=0; i<9; i++)
			{
				holdingNumber = btempBoard[3][i];
				btempBoard[3][i] = btempBoard[5][i];
				btempBoard[5][i] = holdingNumber;
			}
		}
		decidingRandom = (int)(Math.random() * 3); // 0, 1, 2
		if(decidingRandom == 1)
		{
			// Swap row6 and row7
			for(int i=0; i<9; i++)
			{
				holdingNumber = btempBoard[6][i];
				btempBoard[6][i] = btempBoard[7][i];
				btempBoard[7][i] = holdingNumber;
			}
		} 
		if(decidingRandom == 2)
		{
			// Swap row6 and row8
			for(int i=0; i<9; i++)
			{
				holdingNumber = btempBoard[6][i];
				btempBoard[6][i] = btempBoard[8][i];
				btempBoard[8][i] = holdingNumber;
			}
		}
		
		// Swap some columns randomly
		decidingRandom = (int)(Math.random() * 3); // 0, 1, 2
		if(decidingRandom == 1)
		{
			// Swap col0 and col1
			for(int i=0; i<9; i++)
			{
				holdingNumber = btempBoard[i][0];
				btempBoard[i][0] = btempBoard[i][1];
				btempBoard[i][1] = holdingNumber;
			}
		} 
		if(decidingRandom == 2)
		{
			// Swap col0 and col2
			for(int i=0; i<9; i++)
			{
				holdingNumber = btempBoard[i][0];
				btempBoard[i][0] = btempBoard[i][2];
				btempBoard[i][2] = holdingNumber;
			}
		}
		decidingRandom = (int)(Math.random() * 3); // 0, 1, 2
		if(decidingRandom == 1)
		{
			// Swap col3 and col4
			for(int i=0; i<9; i++)
			{
				holdingNumber = btempBoard[i][3];
				btempBoard[i][3] = btempBoard[i][4];
				btempBoard[i][4] = holdingNumber;
			}
		} 
		if(decidingRandom == 2)
		{
			// Swap col3 and col5
			for(int i=0; i<9; i++)
			{
				holdingNumber = btempBoard[i][3];
				btempBoard[i][3] = btempBoard[i][5];
				btempBoard[i][5] = holdingNumber;
			}
		}
		decidingRandom = (int)(Math.random() * 3); // 0, 1, 2
		if(decidingRandom == 1)
		{
			// Swap col6 and col7
			for(int i=0; i<9; i++)
			{
				holdingNumber = btempBoard[i][6];
				btempBoard[i][6] = btempBoard[i][7];
				btempBoard[i][7] = holdingNumber;
			}
		} 
		if(decidingRandom == 2)
		{
			// Swap col6 and col8
			for(int i=0; i<9; i++)
			{
				holdingNumber = btempBoard[i][6];
				btempBoard[i][6] = btempBoard[i][8];
				btempBoard[i][8] = holdingNumber;
			}
		}
		
		// Swap two horizontal 3-tall chunks
		// Original "ABC" ->  After "BAC" "ACB" "CAB" "ABC"
		decidingRandom = (int)(Math.random() * 4); // 0, 1, 2, 3
		if (decidingRandom == 0)
		{
			for(int h=0; h<3; h++)
			{
				for(int i=0; i<9; i++)
				{
					holdingNumber = btempBoard[h][i];
					btempBoard[h][i] = btempBoard[h+3][i];
					btempBoard[h+3][i] = holdingNumber;
				}
			}
		}
		if (decidingRandom == 1)
		{
			for(int h=0; h<3; h++)
			{
				for(int i=0; i<9; i++)
				{
					holdingNumber = btempBoard[h][i];
					btempBoard[h][i] = btempBoard[h+6][i];
					btempBoard[h+6][i] = holdingNumber;
				}
			}
		}
		if (decidingRandom == 2)
		{
			for(int h=3; h<6; h++)
			{
				for(int i=0; i<9; i++)
				{
					holdingNumber = btempBoard[h][i];
					btempBoard[h][i] = btempBoard[h+3][i];
					btempBoard[h+3][i] = holdingNumber;
				}
			}
		}
		
		// Swap two vertical 3-wide chunks
		// Original "ABC" ->  After "BAC" "ACB" "CAB" "ABC"
		decidingRandom = (int)(Math.random() * 4); // 0, 1, 2, 3
		if (decidingRandom == 0)
		{
			for(int h=0; h<3; h++)
			{
				for(int i=0; i<9; i++)
				{
					holdingNumber = btempBoard[i][h];
					btempBoard[i][h] = btempBoard[i][h+3];
					btempBoard[i][h+3] = holdingNumber;
				}
			}
		}
		if (decidingRandom == 1)
		{
			for(int h=0; h<3; h++)
			{
				for(int i=0; i<9; i++)
				{
					holdingNumber = btempBoard[i][h];
					btempBoard[i][h] = btempBoard[i][h+6];
					btempBoard[i][h+6] = holdingNumber;
				}
			}
		}
		if (decidingRandom == 2)
		{
			for(int h=3; h<6; h++)
			{
				for(int i=0; i<9; i++)
				{
					holdingNumber = btempBoard[i][h];
					btempBoard[i][h] = btempBoard[i][h+3];
					btempBoard[i][h+3] = holdingNumber;
				}
			}
		}
		
		// On the overall board, swap the complete placements of two integers
		// "Wherever an x put a y, and a y an x."
		int randomX = (int)((Math.random() * 9) + 1);
		int randomY = (int)((Math.random() * 9) + 1);
		while(randomX == randomY)
		{
			randomY = (int)((Math.random() * 9) + 1);
		}
		for(int i =0; i<9; i++)
		{
			for(int j = 0; j<9; j++)
			{
				if(btempBoard[i][j] == randomX)
				{
					btempBoard[i][j] = randomY;
				}
				else if(btempBoard[i][j] == randomY)
				{
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
		// array[][] is the currentBoard
		// barray[][] is the initialBoard
		
		int[][] ctempBoard = new int[9][9];
		for(int i=0; i <9; i++)
		{
			for(int j=0; j<9;j++)
			{
				ctempBoard[i][j] = array[i][j];
			}
		}
		
		// Before any changes, if invalid, return the board before the change
		if(num < 1 || num > 9)
		{
			return ctempBoard;
		}
		
		System.out.print("Number at ["+row+"]["+col+"]: "+ctempBoard[row][col]+"\n");
		ctempBoard[row][col] = num;
		
		// Overwrite any input with the initialBoard
		// To catch inputs on given squares
		for(int i=0; i <9; i++)
		{
			for(int j=0; j<9;j++)
			{
				if(barray[i][j] != 0)
				{
					ctempBoard[i][j] = barray[i][j];
				}
			}
		}
		
		return ctempBoard;
	}
	
	
	
	public static int[][] outputBeginningBoard()
	{
		String[][] stringBoard = new String[9][9];
		int[][] someBoard = new int[9][9];
		
		// An arbitrary valid board to start from
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
				someBoard[i][j] = Integer.parseInt(stringBoard[i][j]);
			}
		}
		
		return someBoard;
	}
	
	public static boolean isSolved(int array[][])
	{
		for(int i =0; i<9; i++)
		{
			for(int j = 0; j<9; j++)
			{
				if(array[i][j] == 0)
				{
					return false;
				}
			}
		}
		return true;	
	}
	
	
	public static void main(String[] args) {
		
		int[][] beginningBoard = new int[9][9];
		int[][] completeBoard = new int[9][9];
		int[][] initialBoard = new int[9][9];
		int[][] currentBoard = new int[9][9];
		int[][] errorsBoard = new int[9][9];
		
		// For use of scrambling the beginningBoard
		int[][] holdingArray = new int[2][9];
		int[][] secondArray = new int[2][9];
		int[][] thirdArray = new int[6][9];
		
		// Set the beginning Board
		beginningBoard = outputBeginningBoard();
		
		// Set the completeBoard
		completeBoard = generateBoard();
		
		// Output the beginningBoard by method
		System.out.print("   Beginning \"Key\" Board: \n");
		outputToConsole(beginningBoard);
		System.out.print("   Complete Board: \n");
		outputToConsole(completeBoard);
		
		// Testing generation of initialBoard from completeBoard
		initialBoard = generateInitialBoard(4, completeBoard);
		System.out.print("   Initial Board: \n");
		outputToConsole(initialBoard);
		
		// Initialize currentBoard
		for(int i =0; i<9; i++)
		{
			for(int j = 0; j<9; j++)
			{
				currentBoard[i][j] = initialBoard[i][j];
			}
		}
		
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
		
		System.out.print("\n\n");
		System.out.print("   Let us solve a board!");
		System.out.print("\n   Current Board:\n");
		outputToConsole(currentBoard);
		System.out.print("\n");
		
		
		
		int value;
		int row;
		int col;
		while(!isSolved(currentBoard))
		{
			System.out.print("Enter a row: ");
			rowString = scanner.nextLine();
			row = Integer.parseInt(rowString);
			System.out.print("Enter a column: ");
			colString = scanner.nextLine();
			col = Integer.parseInt(colString);
			System.out.print("Enter a value: ");
			String valueString = scanner.nextLine();
			value = Integer.parseInt(valueString);
			
			currentBoard = playerEntry(value, row, col, currentBoard, initialBoard);
			System.out.print("   Current Board: \n");
			outputToConsole(currentBoard);
			System.out.print("\n");
		}
		System.out.print("CONGRATS!");
		
		
		scanner.close();
	}
}
