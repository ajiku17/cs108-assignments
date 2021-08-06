// Test cases for CharGrid -- a few basic tests are provided.
package assign1;

import static org.junit.Assert.*;
import org.junit.Test;

public class CharGridTest {
	
	@Test
	public void testCharArea1() {
		char[][] grid = new char[][] {
				{'a', 'y', ' '},
				{'x', 'a', 'z'},
			};
		
		
		CharGrid cg = new CharGrid(grid);
				
		assertEquals(4, cg.charArea('a'));
		assertEquals(1, cg.charArea('z'));
	}
	
	
	@Test
	public void testCharArea2() {
		char[][] grid = new char[][] {
				{'c', 'a', ' '},
				{'b', ' ', 'b'},
				{' ', ' ', 'a'}
			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(6, cg.charArea('a'));
		assertEquals(3, cg.charArea('b'));
		assertEquals(1, cg.charArea('c'));
	}
	
	// TODO Add more tests
	@Test
	public void testCharAreaRow() {
		char[][] grid = new char[][] {
				{'c', 'a', 'a', 'c'},
				{'b', ' ', 'b', 'f'},
				{' ', ' ', ' ', 'f'},
				{' ', ' ', ' ', 'f'}
			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(3, cg.charArea('b'));
		assertEquals(4, cg.charArea('c'));
		assertEquals(2, cg.charArea('a'));
	}
	
	@Test
	public void testCharAreaCol() {
		char[][] grid = new char[][] {
				{'c', 'b', ' ', ' '},
				{'a', ' ', ' ', ' '},
				{'a', 'b', ' ', ' '},
				{'c', 'f', 'f', ' '}
			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(3, cg.charArea('b'));
		assertEquals(4, cg.charArea('c'));
		assertEquals(2, cg.charArea('a'));
	}
	
	@Test
	public void testCharAreaEdge() {
		char[][] grid = new char[][] {
				{'c', 'b', ' ', 'i'},
				{'a', ' ', ' ', ' '},
				{'a', 'b', ' ', ' '},
				{'g', 'f', 'f', 'h'}
			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(1, cg.charArea('c'));
		assertEquals(1, cg.charArea('g'));
		assertEquals(1, cg.charArea('h'));
		assertEquals(1, cg.charArea('i'));
	}
	
	@Test
	public void testCharAreaZero() {
		char[][] grid = new char[][] {
				{'c', 'b', ' ', 'i'},
				{'a', ' ', ' ', ' '},
				{'a', 'b', ' ', ' '},
				{'g', 'f', 'f', 'h'}
			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(0, cg.charArea('z'));
		
	}	
	
	@Test
	public void testCountPlusBasic() {
		char[][] grid = new char[][] {
			{'c', 'b', ' ', 'i'},
			{'b', 'b', 'b', ' '},
			{'a', 'b', ' ', ' '},
			{'g', ' ', 'f', 'h'}
		};
	
		CharGrid cg = new CharGrid(grid);
		assertEquals(1, cg.countPlus());
	}
	
	
	@Test
	public void testCountPlusOverlap1() {
		char[][] grid = new char[][] {
			{'c', 'b', 'b', 'i'},
			{'b', 'b', 'b', 'b'},
			{'a', 'b', 'b', ' '},
			{'g', ' ', 'f', 'h'}
		};
	
		CharGrid cg = new CharGrid(grid);
		assertEquals(2, cg.countPlus());
	}
	
	@Test
	public void testCountPlusOverlap2() {
		char[][] grid = new char[][] {
			{' ', ' ', ' ', 'i', ' ', ' ', ' '},
			{'i', 'd', 'i', 'i', 'i', 'd', 'c'},
			{' ', ' ', ' ', 'i', ' ', ' ', ' '},
			{'i', 'i', 'i', 'i', 'i', 'i', 'i'},
			{' ', ' ', ' ', 'i', ' ', ' ', ' '},
			{' ', ' ', 'c', 'i', 'c', ' ', 'c'},
			{'i', 'd', ' ', 'i', ' ', 'd', ' '},
			{' ', ' ', ' ', 'i', ' ', ' ', ' '},
		};
	
		CharGrid cg = new CharGrid(grid);
		assertEquals(4, cg.countPlus());
	}
	
	@Test
	public void testCountPlusBounds() {
		char[][] grid = new char[][] {
			{' ', 'a', ' ', ' ', ' ', ' ', 'b'},
			{' ', 'a', 'i', ' ', ' ', ' ', 'b'},
			{'a', 'a', 'a', ' ', 'c', ' ', 'b'},
			{'i', 'a', 'i', ' ', ' ', ' ', 'b'},
			{' ', 'a', ' ', 'd', ' ', ' ', 'b'},
			{' ', ' ', 'd', ' ', 'c', ' ', 'b'},
			{' ', 'c', ' ', 'c', ' ', 'c', ' '},
			{' ', ' ', ' ', ' ', ' ', ' ', ' '},
		};
	
		CharGrid cg = new CharGrid(grid);
		assertEquals(1, cg.countPlus());
	}
	
	
	@Test
	public void testCountPlusZero() {
		char[][] grid = new char[][] {
			{'c', 'b', 'b', 'i'},
			{'b', 'd', 'b', 'b'},
			{'a', 'b', 'b', ' '},
			{'g', ' ', 'f', 'h'}
		};
	
		CharGrid cg = new CharGrid(grid);
		assertEquals(0, cg.countPlus());
	}
	
	
	
}
