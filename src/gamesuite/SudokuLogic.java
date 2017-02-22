package gamesuite;

public class SudokuLogic {
	
	// To remove bits from the sampleBoard
	public static int[][] removeChunk(int row, int col, int array[][]){
		array[row][col] = 0;
		return array;
	}
	
	// Possible Array Contains
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
	// Remove a number from possible contains
	public static void arrayRemove(int array[], int num)
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
	
	
	// To see if remove is valid to still be solved
	public static boolean solvedBySingleCandidate(int row, int col, int array[][])
	{
		// Start with all possibility
		int possible[] = {1,2,3,4,5,6,7,8,9};
		// Eliminate row possibility
		for(int i=0; i< 9;i++)
		{
			if(arrayContains(possible, array[row][i]))
			{
				arrayRemove(possible, array[row][i]);
			}
		}
		// Eliminate col possibility
		for(int i=0; i< 9;i++)
		{
			if(arrayContains(possible, array[i][col]))
			{
				arrayRemove(possible, array[i][col]);
			}
		}
		
		// Eliminate box possibility
		// TO DO
		
		// See if there are multiple possibilities left in this box
		int stillPossible = 0;
		for(int i=0; i< 9;i++)
		{
			if(possible[i] != 0)
			{
				stillPossible = stillPossible + 1;
			}
			if(stillPossible > 1)
			{
				return false;
			}
		}
		return true;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("Initial Puzzle\n");	
		//New Idea
		String[][] stringBoard = new String[9][9];
		int[][] sampleBoard = new int[9][9];
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
				sampleBoard[i][j] = Integer.parseInt(stringBoard[i][j]);
			}
		}
		
		// Output the array
		int a_counter = 0;
		int b_counter = 0;
		for(int i = 0; i<9; i++)
		{
			b_counter = b_counter + 1;
			for(int j = 0; j<9;j++)
			{
				a_counter = a_counter + 1;
				System.out.print(sampleBoard[i][j]);
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
		
		System.out.println("After Row Swap\n");	
		
		// Swap Two Rows
		int row1 = 3;
		int row2 = 4;	
		holdingArray[0] = sampleBoard[row1];
		holdingArray[1] = sampleBoard[row2];
		sampleBoard[row1] = holdingArray[1];
		sampleBoard[row2] = holdingArray[0];
		
		// Output the array
		a_counter = 0;
		b_counter = 0;
		for(int i = 0; i<9; i++)
		{
			b_counter = b_counter + 1;
			for(int j = 0; j<9;j++)
			{
				a_counter = a_counter + 1;
				System.out.print(sampleBoard[i][j]);
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
		
		System.out.println("After Column Swap\n");
		
		// Swap Two Cols
		int col1 = 6;
		int col2 = 8;	
		for(int i=0; i<9;i++)
		{
			secondArray[0][i] = sampleBoard[i][col1];
		}
		for(int i=0; i<9;i++)
		{
			secondArray[1][i] = sampleBoard[i][col2];
		}
		for(int i=0; i<9;i++)
		{
			sampleBoard[i][col1] = secondArray[1][i];
		}
		for(int i=0; i<9;i++)
		{
			sampleBoard[i][col2] = secondArray[0][i];
		}
		
		// Output the array
		a_counter = 0;
		b_counter = 0;
		for(int i = 0; i<9; i++)
		{
			b_counter = b_counter + 1;
			for(int j = 0; j<9;j++)
			{
				a_counter = a_counter + 1;
				System.out.print(sampleBoard[i][j]);
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
		
		System.out.println("After Horizontal Thirds Swap\n");
		
		// Swap Six Rows, "Two Horizontal Chunks"
		// Swaps the top three rows (1,2,3) with a choice between (4,5,6) or (7,8,9)
		int chunk2 = 2;	// 1 or 2
		thirdArray[0] = sampleBoard[0];
		thirdArray[1] = sampleBoard[1];
		thirdArray[2] = sampleBoard[2];
		thirdArray[3] = sampleBoard[(3 * chunk2 ) + 0];
		thirdArray[4] = sampleBoard[(3 * chunk2 ) + 1];
		thirdArray[5] = sampleBoard[(3 * chunk2 ) + 2];
		sampleBoard[0] = thirdArray[3];
		sampleBoard[1] = thirdArray[4];
		sampleBoard[2] = thirdArray[5];
		sampleBoard[(3 * chunk2 ) + 0] = thirdArray[0];
		sampleBoard[(3 * chunk2 ) + 1] = thirdArray[1];
		sampleBoard[(3 * chunk2 ) + 2] = thirdArray[2];
		
		// Output the array
		a_counter = 0;
		b_counter = 0;
		for(int i = 0; i<9; i++)
		{
			b_counter = b_counter + 1;
			for(int j = 0; j<9;j++)
			{
				a_counter = a_counter + 1;
				System.out.print(sampleBoard[i][j]);
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
		
		System.out.println("After Removal \n");
		
		// Remove a block
		int whichrow = 3;
		int whichcol = 0;
		sampleBoard[whichrow][whichcol] = 0;
		
		// Easy Difficulty
		// Remove maybe 35 blocks
		// The puzzle solvable by two main techniques
		// http://www.sudokuoftheday.com/techniques/
		
		// Output the array
		a_counter = 0;
		b_counter = 0;
		for(int i = 0; i<9; i++)
		{
			b_counter = b_counter + 1;
			for(int j = 0; j<9;j++)
			{
				a_counter = a_counter + 1;
				System.out.print(sampleBoard[i][j]);
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
		
		// Trying method
		sampleBoard = removeChunk(2, 2, sampleBoard);
		sampleBoard = removeChunk(2, 3, sampleBoard);
		sampleBoard = removeChunk(5, 6, sampleBoard);	
		
		
		
		System.out.println("After Transfer to String \n");
		
		// Transfer int to string
		for(int i =0; i<9; i++)
		{
			for(int j = 0; j<9; j++)
			{
				 if(sampleBoard[i][j] == 0)
				 {
					 stringBoard[i][j] = " ";
				 } else {
					 stringBoard[i][j] = Integer.toString(sampleBoard[i][j]);
				 }
			}
		}
		
		// Output the string array
		a_counter = 0;
		b_counter = 0;
		for(int i = 0; i<9; i++)
		{
			b_counter = b_counter + 1;
			for(int j = 0; j<9;j++)
			{
				a_counter = a_counter + 1;
				System.out.print(stringBoard[i][j]);
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
}
