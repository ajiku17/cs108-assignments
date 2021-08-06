// HW1 2-d array Problems
// CharGrid encapsulates a 2-d grid of chars and supports
// a few operations on the grid.

package assign1;

public class CharGrid {
	private char[][] grid;

	/**
	 * Constructs a new CharGrid with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */
	public CharGrid(char[][] grid) {
		this.grid = grid;
	}
	
	// calculates the distance between the first column the character ch appeared and the last
	private int getLength(char ch) {
		int firstMet = 0;
		int lastMet = 0;
		for(int i = 0; i < grid[0].length; i++) {
			for(int j = 0; j < grid.length; j++) {
				if(grid[j][i] == ch) {
					if(firstMet == 0) 
						firstMet = i + 1;
					lastMet = i + 1;
				}
			}
		}
		if(firstMet == 0)return 0;
		return lastMet - firstMet + 1;
	}
	
	// calculates the distance between the first row the character ch appeared and the last
	private int getWidth(char ch) {
		int firstMet = 0;
		int lastMet = 0;
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				if(grid[i][j] == ch) {
					if(firstMet == 0) 
						firstMet = i + 1;
					lastMet = i + 1;
				}
			}
		}
		if(firstMet == 0)return 0;
		return lastMet - firstMet + 1;
	}
	
	/**
	 * Returns the area for the given char in the grid. (see handout).
	 * @param ch char to look for
	 * @return area for given char
	 */
	public int charArea(char ch) {
		return getLength(ch) * getWidth(ch);
	}
	
	/**
	 * Returns the count of '+' figures in the grid (see handout).
	 * @return number of + in grid
	 */
	public int countPlus() {
		int res = 0;
		for(int i = 0; i < grid[0].length; i++) {
			char lastMet = grid[0][i];
			int counter = 0;
			for(int j = 0; j < grid.length; j++) {
				if(grid[j][i] == lastMet) {
					counter++;
					if(counter >= 3) {
						int length = 3;
						while(length <= counter) {
							if(checkRow(j - length / 2 , i, length, lastMet)) res++;
							length += 2;
						}
					}
				}else {
					counter = 1;
					lastMet = grid[j][i];
				}
			}
		}
		return res;
	}
	
	//if x, y can be a center for a plus sign with arms of length len/2, proceeds to checking  
	//n consecutive ch characters
	private boolean checkRow(int centerX, int centerY, int len, char ch) {
		if(centerY - len / 2 < 0) {
			return false;
		}else {
			for(int i = 0; i < len; i++) {
				if(grid[centerX][centerY - len / 2 + i] != ch)
					return false;
			}
			return true;
		}
	}
	
}
