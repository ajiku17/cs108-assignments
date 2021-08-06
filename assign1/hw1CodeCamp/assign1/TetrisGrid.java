//
// TetrisGrid encapsulates a tetris board and has
// a clearRows() capability.
package assign1;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class TetrisGrid {
	
	boolean[][]grid;
	
	
	/**
	 * Constructs a new instance with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */
	public TetrisGrid(boolean[][] grid) {
		this.grid = grid;
	}
	
	//checks if a row is full
	private boolean isFull(int col) {
		for(int i = 0; i < grid.length; i++) {
			if(!grid[i][col])return false;
		}
		return true;
	}
	
	// drops everything down starting with height row
	private void dropDownFrom(int column, int row) {
		for(int i = row; i < grid[column].length - 1; i++) {
			grid[column][i] = grid[column][i + 1];
		}
		grid[column][grid[column].length - 1] = false;
	}
	
	// calls dropDown for every column
	private void removeRow(int index) {
		for(int i = 0; i < grid.length; i++) {
			dropDownFrom(i, index);
		}
	}
	
	/**
	 * Does row-clearing on the grid (see handout).
	 */
	public void clearRows() {
		for(int i = 0; i < grid[0].length; i++) {
			if(isFull(i)) {
				removeRow(i);
				i--;
			}
		}
	}
	
	/**
	 * Returns the internal 2d grid array.
	 * @return 2d grid array
	 */
	boolean[][] getGrid() {
		return grid;
	}
}
