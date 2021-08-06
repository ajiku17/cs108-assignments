// Board.java
package tetris;

import java.util.Arrays;

import javax.management.RuntimeErrorException;

/**
 CS108 Tetris Board.
 Represents a Tetris board -- essentially a 2-d grid
 of booleans. Supports tetris pieces and row clearing.
 Has an "undo" feature that allows clients to add and remove pieces efficiently.
 Does not do any drawing or have any idea of pixels. Instead,
 just represents the abstract 2-d board.
*/
public class Board	{
	// Some ivars are stubbed out for you:
	private int width;
	private int height;
	private boolean[][] grid;
	private boolean DEBUG = true;
	boolean committed;
	
	private int[] widths;
	private int[] heights;
	
	private int[] copyWidths;
	private int[] copyHeights;
	private boolean [][] copyGrid;
	
	
	
	// Here a few trivial methods are provided:
	
	/**
	 Creates an empty board of the given width and height
	 measured in blocks.
	*/
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		grid = new boolean[width][height];
		committed = true;
		// TODO
		widths = new int[height];
		heights = new int[width];
		
		copyGrid = new boolean[width][height];
		
		copyWidths = new int[height];
		copyHeights = new int[width];
	}
	
	/**
	 Returns the width of the board in blocks.
	*/
	public int getWidth() {
		return width;
	}
	
	
	/**
	 Returns the height of the board in blocks.
	*/
	public int getHeight() {
		return height;
	}
	
	
	/**
	 Returns the max column height present in the board.
	 For an empty board this is 0.
	*/
	public int getMaxHeight() {
		//TODO
		int max = 0;
		for(int i = 0; i < width; i++) {
			if(heights[i] > max)max = heights[i];
		}
		return max;
	}
	
	
	/**
	 Checks the board for internal consistency -- used
	 for debugging.
	*/
	public void sanityCheck() {
		if (DEBUG) {
			int lastMetY = -1;
			int [] currentHeights = new int[heights.length];
			for(int j = 0; j < getHeight(); j++) {
				int rowWidth = 0;
				for(int i = 0; i < getWidth(); i++) {
					if(getGrid(i, j)) {
						lastMetY = j;
						rowWidth++;
						currentHeights[i] = j + 1;
					};
				}
				if(rowWidth != widths[j]) 
					throw new RuntimeErrorException(null, "Row width is mismatched on row " + j);
			}
			if(getMaxHeight() != lastMetY + 1) 
				throw new RuntimeErrorException(null, "Max height exception");
			
			if(!(Arrays.equals(heights, currentHeights))) 
				throw new RuntimeErrorException(null, "Column heights are mismatched");
			
		}
	}
	
	/**
	 Given a piece and an x, returns the y
	 value where the piece would come to rest
	 if it were dropped straight down at that x.
	 
	 <p>
	 Implementation: use the skirt and the col heights
	 to compute this fast -- O(skirt length).
	*/
	public int dropHeight(Piece piece, int x) {
		//TODO
		int[] skirt = piece.getSkirt();
		int res = 0;
		for(int i = 0; i < piece.getWidth(); i++) {
			if(heights[x + i] - skirt[i] > res)res = heights[x + i] - skirt[i];
		}
		return res;
	}
	
	
	/**
	 Returns the height of the given column --
	 i.e. the y value of the highest block + 1.
	 The height is 0 if the column contains no blocks.
	*/
	public int getColumnHeight(int x) {
		//TODO
		return heights[x];
	}
	
	
	/**
	 Returns the number of filled blocks in
	 the given row.
	*/
	public int getRowWidth(int y) {
		 //TODO
		 return widths[y];
	}
	
	
	/**
	 Returns true if the given block is filled in the board.
	 Blocks outside of the valid width/height area
	 always return true.
	*/
	public boolean getGrid(int x, int y) {
		//TODO
		if(x < 0 || x >= width || y < 0 || y >= height)return true;
		return grid[x][y];
	}
	
	
	public static final int PLACE_OK = 0;
	public static final int PLACE_ROW_FILLED = 1;
	public static final int PLACE_OUT_BOUNDS = 2;
	public static final int PLACE_BAD = 3;
	
	/**
	 Attempts to add the body of a piece to the board.
	 Copies the piece blocks into the board grid.
	 Returns PLACE_OK for a regular placement, or PLACE_ROW_FILLED
	 for a regular placement that causes at least one row to be filled.
	 
	 <p>Error cases:
	 A placement may fail in two ways. First, if part of the piece may falls out
	 of bounds of the board, PLACE_OUT_BOUNDS is returned.
	 Or the placement may collide with existing blocks in the grid
	 in which case PLACE_BAD is returned.
	 In both error cases, the board may be left in an invalid
	 state. The client can use undo(), to recover the valid, pre-place state.
	*/
	public int place(Piece piece, int x, int y) {
		// flag !committed problem
		if (!committed) throw new RuntimeException("place commit problem");
		committed = false;
			
		int result = PLACE_OK;
		
		if(x + piece.getWidth() > width || y + piece.getHeight() > height || x < 0 || y < 0)
			return PLACE_OUT_BOUNDS;
		
		for(TPoint p : piece.getBody()) 
				if(getGrid(x + p.x, y + p.y))return PLACE_BAD;	
		
		int [] r =  new int[piece.getWidth()];
		TPoint[] body = piece.getBody();
		for(TPoint p : body) {
			grid[x + p.x][y + p.y] = true;
			widths[y + p.y]++;
			if(widths[y + p.y] == width)result = PLACE_ROW_FILLED;
			r[p.x] = Math.max(r[p.x], p.y + 1);
		}
		
		for(int i = 0; i < r.length; i++) 
			heights[x + i] = Math.max(heights[x + i], y + r[i]);
		
		return result;
	}
	
	
	/**
	 Deletes rows that are filled all the way across, moving
	 things above down. Returns the number of rows cleared.
	*/
	public int clearRows() {
		committed = false;
		int rowsCleared = 0;
		int to = getMaxHeight();
		for(int i = 0; i < height; i++) {
			if(isRowFilled(i)) {
				to = i;
				rowsCleared++;
				break;
			}
		}
		int from  = to + 1;
		
		int maxH = getMaxHeight();
		for(; to < maxH; to++) {
			if(from >= height) {
				for(int i = 0; i < width; i++) {
					grid[i][to] = false;
				}
				widths[to] = 0;
				break;
			}
			while(isRowFilled(from)) {
				from++;
				rowsCleared++;
			}
			copy(from, to);
			from++;
		}
		
		for(int i = 0; i < getWidth(); i++) {
			int res = -1;
			for(int j = 0; j < getHeight(); j++) {
				if(getGrid(i, j)) res = j;
			}
			heights[i] = res + 1;
		}
		sanityCheck();
		return rowsCleared;
	}
	
	private void copy(int from, int to) {
		for(int i = 0; i < width; i++) {
			grid[i][to] = grid[i][from];
			grid[i][from] = false;
		}
		
		widths[to] = widths[from];
		widths[from] = 0;
	}


	private boolean isRowFilled(int row) {
		return widths[row] == width;
	}
	

	/**
	 Reverts the board to its state before up to one place
	 and one clearRows();
	 If the conditions for undo() are not met, such as
	 calling undo() twice in a row, then the second undo() does nothing.
	 See the overview docs.
	*/
	public void undo() {
		// YOUR CODE HERE
		committed = true;
		for(int i = 0; i < copyWidths.length; i++) {
			widths[i] = copyWidths[i];
		}
		
		for(int i = 0; i < copyHeights.length; i++) {
			heights[i] = copyHeights[i];
		}
		
		for(int i = 0; i < copyGrid.length; i++) {
			for(int j = 0; j < copyGrid[i].length; j++) {
				grid[i][j] = copyGrid[i][j];
			} 
		}
	}
	
	
	/**
	 Puts the board in the committed state.
	*/
	public void commit() {
		committed = true;
		for(int i = 0; i < widths.length; i++) {
			copyWidths[i] = widths[i];
		}
		
		for(int i = 0; i < heights.length; i++) {
			copyHeights[i] = heights[i];
		}
		
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				copyGrid[i][j] = grid[i][j];
			}
		}
	}


	
	/*
	 Renders the board state as a big String, suitable for printing.
	 This is the sort of print-obj-state utility that can help see complex
	 state change over time.
	 (provided debugging utility) 
	 */
	public String toString() {
		StringBuilder buff = new StringBuilder();
		for (int y = height-1; y>=0; y--) {
			buff.append('|');
			for (int x=0; x<width; x++) {
				if (getGrid(x,y)) buff.append('+');
				else buff.append(' ');
			}
			buff.append("|\n");
		}
		for (int x=0; x<width+2; x++) buff.append('-');
		return(buff.toString());
	}
	
	
}


