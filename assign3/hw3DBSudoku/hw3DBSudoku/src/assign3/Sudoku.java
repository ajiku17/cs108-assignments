package assign3;

import java.util.*;

/*
 * Encapsulates a Sudoku grid to be solved.
 * CS108 Stanford.
 */
public class Sudoku {
	// Provided grid data for main/testing
	// The instance variable strategy is up to you.
	
	// Provided easy 1 6 grid
	// (can paste this text into the GUI too)
	public static final int[][] easyGrid = Sudoku.stringsToGrid(
	"1 6 4 0 0 0 0 0 2",
	"2 0 0 4 0 3 9 1 0",
	"0 0 5 0 8 0 4 0 7",
	"0 9 0 0 0 6 5 0 0",
	"5 0 0 1 0 2 0 0 8",
	"0 0 8 9 0 0 0 3 0",
	"8 0 9 0 4 0 2 0 0",
	"0 7 3 5 0 9 0 0 1",
	"4 0 0 0 0 0 6 7 9");
	
	
	// Provided medium 5 3 grid
	public static final int[][] mediumGrid = Sudoku.stringsToGrid(
	 "530070000",
	 "600195000",
	 "098000060",
	 "800060003",
	 "400803001",
	 "700020006",
	 "060000280",
	 "000419005",
	 "000080079");
	
	// Provided hard 3 7 grid
	// 1 solution this way, 6 solutions if the 7 is changed to 0
	public static final int[][] hardGrid = Sudoku.stringsToGrid(
	"3 7 0 0 0 0 0 8 0",
	"0 0 1 0 9 3 0 0 0",
	"0 4 0 7 8 0 0 0 3",
	"0 9 3 8 0 0 0 1 2",
	"0 0 0 0 4 0 0 0 0",
	"5 2 0 0 0 6 7 9 0",
	"6 0 0 0 2 1 0 4 0",
	"0 0 0 5 3 0 9 0 0",
	"0 3 0 0 0 0 0 5 1");
	
	
	public static final int SIZE = 9;  // size of the whole 9x9 puzzle
	public static final int PART = 3;  // size of each 3x3 part
	public static final int MAX_SOLUTIONS = 100;
	
	// Provided various static utility methods to
	// convert data formats to int[][] grid.
	
	/**
	 * Returns a 2-d grid parsed from strings, one string per row.
	 * The "..." is a Java 5 feature that essentially
	 * makes "rows" a String[] array.
	 * (provided utility)
	 * @param rows array of row strings
	 * @return grid
	 */
	public static int[][] stringsToGrid(String... rows) {
		int[][] result = new int[rows.length][];
		for (int row = 0; row<rows.length; row++) {
			result[row] = stringToInts(rows[row]);
		}
		return result;
	}
	
	
	/**
	 * Given a single string containing 81 numbers, returns a 9x9 grid.
	 * Skips all the non-numbers in the text.
	 * (provided utility)
	 * @param text string of 81 numbers
	 * @return grid
	 */
	public static int[][] textToGrid(String text) {
		int[] nums = stringToInts(text);
		if (nums.length != SIZE*SIZE) {
			throw new RuntimeException("Needed 81 numbers, but got:" + nums.length);
		}
		
		int[][] result = new int[SIZE][SIZE];
		int count = 0;
		for (int row = 0; row<SIZE; row++) {
			for (int col=0; col<SIZE; col++) {
				result[row][col] = nums[count];
				count++;
			}
		}
		
		return result;
	}
	
	
	/**
	 * Given a string containing digits, like "1 23 4",
	 * returns an int[] of those digits {1 2 3 4}.
	 * (provided utility)
	 * @param string string containing ints
	 * @return array of ints
	 */
	public static int[] stringToInts(String string) {
		int[] a = new int[string.length()];
		int found = 0;
		for (int i=0; i<string.length(); i++) {
			if (Character.isDigit(string.charAt(i))) {
				a[found] = Integer.parseInt(string.substring(i, i+1));
				found++;
			}
		}
		int[] result = new int[found];
		System.arraycopy(a, 0, result, 0, found);
		return result;
	}


	// Provided -- the deliverable main().
	// You can edit to do easier cases, but turn in
	// solving hardGrid.
	public static void main(String[] args) {
		Sudoku sudoku;
		sudoku = new Sudoku(hardGrid);
		
		System.out.println(sudoku); // print the raw problem
		int count = sudoku.solve();
		System.out.println("solutions:" + count);
		System.out.println("elapsed:" + sudoku.getElapsed() + "ms");
		System.out.println(sudoku.getSolutionText());
	}
	
	
	
	private int[][] grid;
	private int size;
	private int squareSize;
	private final int UNASSIGNED = 0;
	boolean solved;
	long elapsedTime;
	String solutionText = "";

	/**
	 * Sets up based on the given ints.
	 */
	public Sudoku(int[][] ints) {
		// YOUR CODE HERE
		grid = new int[ints.length][];
		for(int i =0; i < ints.length; i++) {
			grid[i] = new int[ints[i].length];
			System.arraycopy(ints[i], 0, grid[i], 0, ints[i].length); 
		}
		
		size = ints.length;
		squareSize = (int)Math.sqrt(size);
		solved = false;
	}
	
	
	@Override
	public String toString() {
		String res = "";
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				res += (" "  + grid[i][j]);
			}
			res += "\n";
		}
		return res;
	}
	
	private boolean checkSquare(int row, int col, int newValue) {
		for(int i = 0; i < squareSize; i++) {
			for(int j = 0; j < squareSize; j++) {
				if(grid[row + i][col + j] == newValue)return false;
			}
		}
		return true;
	}
	
	private boolean noConflict(int row, int col, int newValue) {
		for(int i = 0; i < size; i++) {
			if(grid[row][i] == newValue)return false;
		}
		
		for(int i = 0; i < size; i++) {	
			if(grid[i][col] == newValue)return false;
		}
		
		int squareStartRow = (row / squareSize)  * squareSize;
		int squareStartCol = (col / squareSize) * squareSize;
//		System.out.println(squareStartRow + " " + squareStartCol);
		
		return checkSquare(squareStartRow, squareStartCol, newValue);
	}
	
	private int solveHelper(int spot) {
		if(spot >= size*size) {
			if(!solved) {
				for(int i = 0; i < grid.length; i++) {
					for(int j = 0; j < grid[i].length; j++) {
						solutionText += (" "  + grid[i][j]);
					}
					solutionText += "\n";
				}
				solved = true;
			}
//			System.out.println(toString());
			return 1;
		}
		
		int row = spot / size; 
		int col = spot % size;
//		System.out.println(row + " " + col);
		
		
		if(grid[row][col] != UNASSIGNED)
			return solveHelper(spot + 1);
		
		int res = 0;
		for(int i = 1; i <= size; i++) {
			if(noConflict(row, col, i)) {
				grid[row][col] = i;
				res += solveHelper(spot + 1);
				grid[row][col] = UNASSIGNED;
				
			}
		}
		
		return res;
	}
	
	
	/**
	 * Solves the puzzle, invoking the underlying recursive search.
	 */
	public int solve() {
		long startTime = System.currentTimeMillis();
		int res = solveHelper(0);
		elapsedTime = System.currentTimeMillis() - startTime;
		return res;
	}
	
	public String getSolutionText() {
		if(solved)
			return solutionText;
		return "Was not able to solve"; 
	}
	
	public long getElapsed() {
		return elapsedTime; // YOUR CODE HERE
	}

}
