package assign1;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.*;

public class TetrisGridTest {
	
	
	@Test
	public void testConstr() {
		boolean[][] before =
			{	
				{true, true, false, },
				{false, true, true, }
			};
		boolean[][] internal = 
			{
				{true, true, false, },
				{false, true, true, }
			};
		TetrisGrid grid = new TetrisGrid(before);
		
		assertTrue(Arrays.deepEquals(internal, grid.getGrid()));
	}
	
	
	// Provided simple clearRows() test
	// width 2, height 3 grid
	@Test
	public void testClear1() {
		boolean[][] before =
		{	
			{true, true, false, },
			{false, true, true, }
		};
		
		boolean[][] after =
		{	
			{true, false, false},
			{false, true, false}
		};
		
		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}
	
	@Test
	public void testClear2() {
		boolean[][] before =
		{	
			{true, true, false, },
			{false, false, true, }
		};
		
		boolean[][] after =
		{	
			{true, true, false, },
			{false, false, true, }
		};
		
		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}
	
	@Test
	public void testClearFull() {
		boolean[][] before =
		{	
			{true, true, true, },
			{true, true, true, }
		};
		
		boolean[][] after =
		{	
			{false, false, false, },
			{false, false, false, }
		};
		
		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}
		
	@Test
	public void testClearempty() {
		boolean[][] before =
		{	
			{false, false, false, },
			{false, false, false, }
		};
		
		boolean[][] after =
		{	
			{false, false, false, },
			{false, false, false, }
		};
		
		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}
	
}
