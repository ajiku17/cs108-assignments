package tetris;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.*;

public class BoardTest {
	Board b;
	Piece pyr1, pyr2, pyr3, pyr4, s, sRotated;

	
	private boolean[][] toGrid(Board b){
		boolean[][]res = new boolean[b.getWidth()][b.getHeight()];
		for(int i = 0; i < b.getWidth(); i++) {
			for(int j = 0; j < b.getHeight(); j++) {
				res[i][j] = b.getGrid(i, j);
			}
		}
		return res;
	}
	
	
	// This shows how to build things in setUp() to re-use
	// across tests.
	
	// In this case, setUp() makes shapes,
	// and also a 3X6 board, with pyr placed at the bottom,
	// ready to be used by tests.
	@Before
	public void setUp() throws Exception {
		b = new Board(3, 6); 
		
		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();
		
		s = new Piece(Piece.S1_STR);
		sRotated = s.computeNextRotation();
		
		b.place(pyr1, 0, 0);
	}
	
	// Check the basic width/height/max after the one placement
	@Test
	public void testSample1() {
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(2, b.getColumnHeight(1));
		assertEquals(2, b.getMaxHeight());
		assertEquals(3, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(0, b.getRowWidth(2));
	}
	
	// Place sRotated into the board, then check some measures
	@Test
	public void testSample2() {
		b.commit();
		int result = b.place(sRotated, 1, 1);
		assertEquals(Board.PLACE_OK, result);
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(4, b.getColumnHeight(1));
		assertEquals(3, b.getColumnHeight(2));
		assertEquals(4, b.getMaxHeight());
	}
	
	// Make  more tests, by putting together longer series of 
	// place, clearRows, undo, place ... checking a few col/row/max
	// numbers that the board looks right after the operations.
	
	@Test
	public void testGetGrid() {
		assertTrue(Arrays.deepEquals(toGrid(b), new boolean[][] {
			new boolean[] {true, false, false, false, false, false},
			new boolean[] {true, true, false, false, false, false},
			new boolean[] {true, false, false, false, false, false},
		}
	));
	}
	
	@Test
	public void testClear1() {
		b.clearRows();
		assertTrue(Arrays.deepEquals(toGrid(b), new boolean[][] {
			new boolean[] {false, false, false, false, false, false},
			new boolean[] {true, false, false, false, false, false},
			new boolean[] {false, false, false, false, false, false},
		}));
		b.sanityCheck();
	}
	
	@Test
	public void testClear2() {
		b.commit();
		b.place(new Piece(new TPoint[] {
				new TPoint(0, 0), new TPoint(1, 0)
			}),  0, 3);
		b.sanityCheck();
		b.clearRows();
		assertTrue(Arrays.deepEquals(toGrid(b), new boolean[][] {
			new boolean[] {false, false, true, false, false, false},
			new boolean[] {true, false, true, false, false, false},
			new boolean[] {false, false, false, false, false, false},
		}));
		b.sanityCheck();
	}
	
	@Test
	public void testEmpty() {
		Board b = new Board(3,3);
		b.clearRows();
		assertTrue(Arrays.deepEquals(toGrid(b), new boolean[][]{
			new boolean[] {false, false, false},
			new boolean[] {false, false, false},
			new boolean[] {false, false, false}
		}));
	}
	
	
	@Test
	public void testClearTop() {
		Board b = new Board(4,2);
		Piece p = new Piece(Piece.STICK_STR);
		b.place(p.computeNextRotation(), 0, 1);
		b.commit();
		b.clearRows();
		assertTrue(Arrays.deepEquals(toGrid(b), new boolean[][] {
			new boolean[] {false, false},
			new boolean[] {false, false},
			new boolean[] {false, false},
			new boolean[] {false, false}
		}));
		
		b.sanityCheck();
	}
	
	@Test
	public void testPlace() {
		Board b = new Board(4,2);
		Piece p = new Piece(Piece.STICK_STR);
		assertTrue(Arrays.deepEquals(toGrid(b), new boolean[][] {
			new boolean[] {false, false},
			new boolean[] {false, false},
			new boolean[] {false, false},
			new boolean[] {false, false}
		}));
		int res = b.place(p.computeNextRotation(), 0, 0);
		assertTrue(Arrays.deepEquals(toGrid(b), new boolean[][] {
			new boolean[] {true, false},
			new boolean[] {true, false},
			new boolean[] {true, false},
			new boolean[] {true, false}
		}));
		assertEquals(res, Board.PLACE_ROW_FILLED);
		b.sanityCheck();
	}
	
	
	
	@Test
	public void testUndo1() {
		Board b = new Board(4,2);
		Piece p = new Piece(new TPoint[] {new TPoint(0 ,0), new TPoint(1 ,0), new TPoint(2, 0)});
		int res = b.place(p, 0, 0);
		assertTrue(Arrays.deepEquals(toGrid(b), new boolean[][] {
			new boolean[] {true, false},
			new boolean[] {true, false},
			new boolean[] {true, false},
			new boolean[] {false, false}
		}));
		b.undo();
		assertTrue(Arrays.deepEquals(toGrid(b), new boolean[][] {
			new boolean[] {false, false},
			new boolean[] {false, false},
			new boolean[] {false, false},
			new boolean[] {false, false}
		}));
		assertEquals(res, Board.PLACE_OK);
		b.sanityCheck();
	}
	
	@Test
	public void testUndo2() {
		Board b = new Board(4,2);
		Piece p = new Piece(new TPoint[] {new TPoint(0 ,0), new TPoint(1 ,0), new TPoint(2, 0)});
		int res = b.place(p, 0, 0);
		assertTrue(Arrays.deepEquals(toGrid(b), new boolean[][] {
			new boolean[] {true, false},
			new boolean[] {true, false},
			new boolean[] {true, false},
			new boolean[] {false, false}
		}));
		assertEquals(res, Board.PLACE_OK);
		b.undo();
		int secondAttempt = b.place(new Piece(Piece.S1_STR), 0, 1);
		assertEquals(secondAttempt, Board.PLACE_OUT_BOUNDS);
		b.undo();
		int thirdAttempt = b.place(new Piece(Piece.S1_STR), 0, 0);
		assertEquals(thirdAttempt, Board.PLACE_OK);
		assertTrue(Arrays.deepEquals(toGrid(b), new boolean[][] {
			new boolean[] {true, false},
			new boolean[] {true, true},
			new boolean[] {false, true},
			new boolean[] {false, false}
		}));
		b.sanityCheck();
	}
	
	@Test
	public void testCommit() {
		assertTrue(Arrays.deepEquals(toGrid(b), new boolean[][] {
			new boolean[] {true, false, false, false, false, false},
			new boolean[] {true, true, false, false, false, false},
			new boolean[] {true, false, false, false, false, false},
		}));
		b.commit();
		b.undo();
		assertTrue(Arrays.deepEquals(toGrid(b), new boolean[][] {
			new boolean[] {true, false, false, false, false, false},
			new boolean[] {true, true, false, false, false, false},
			new boolean[] {true, false, false, false, false, false},
		}));
	}
	
	
	
	
	
	
}
