package tetris;

import static org.junit.Assert.*;
import java.util.*;

import org.junit.*;

/*
  Unit test for Piece class -- starter shell.
 */
public class PieceTest {
	// You can create data to be used in the your
	// test cases like this. For each run of a test method,
	// a new PieceTest object is created and setUp() is called
	// automatically by JUnit.
	// For example, the code below sets up some
	// pyramid and s pieces in instance variables
	// that can be used in tests.
	private Piece pyr1, pyr2, pyr3, pyr4;
	private Piece s, sRotated;
	private Piece[] ps;
	
	private boolean TPointArrEquals(TPoint ar1 [], TPoint ar2 []) {
		if(ar1.length != ar2.length)return false;
		
		List<TPoint> list1 = new ArrayList<TPoint>();
		List<TPoint> list2 = new ArrayList<TPoint>();
		
		for(int i = 0; i < ar1.length; i++) {
			list1.add(ar1[i]);
			list2.add(ar2[i]);
		}
		
		for(int i = 0; i < list1.size(); i++) {
			int ind = list2.indexOf(list1.get(i));
			if(ind == -1)return false;
			list2.remove(ind);
			
		}
		return true;
	}
	
	@Before
	public void setUp() throws Exception {
		
		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();
		
		s = new Piece(Piece.S1_STR);
		sRotated = s.computeNextRotation();
		ps = Piece.getPieces();
	}
	
	// Here are some sample tests to get you started
	
	@Test
	public void testSampleSize() {
		// Check size of pyr piece
		assertEquals(3, pyr1.getWidth());
		assertEquals(2, pyr1.getHeight());
		
		// Now try after rotation
		// Effectively we're testing size and rotation code here
		assertEquals(2, pyr2.getWidth());
		assertEquals(3, pyr2.getHeight());
		
		// Now try with some other piece, made a different way
		Piece l = new Piece(Piece.STICK_STR);
		assertEquals(1, l.getWidth());
		assertEquals(4, l.getHeight());
	}
	
	
	// Test the skirt returned by a few pieces
	@Test
	public void testSampleSkirt() {
		// Note must use assertTrue(Arrays.equals(... as plain .equals does not work
		// right for arrays.
		assertTrue(Arrays.equals(new int[] {0, 0, 0}, pyr1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0, 1}, pyr3.getSkirt()));
		
		assertTrue(Arrays.equals(new int[] {0, 0, 1}, s.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0}, sRotated.getSkirt()));
	}
	
	@Test
	public void testBody() {
		// TODO 
		Piece p1 = new Piece(Piece.PYRAMID_STR);
		
		TPoint[] t1 = new TPoint[] {new TPoint(0,0), new TPoint(1,0), new TPoint(2,0), new TPoint(1,1)};
		
		assertTrue(TPointArrEquals(p1.getBody(), t1));
		
		Piece p2 = new Piece(Piece.L1_STR);
		TPoint[] t2 = new TPoint[] {new TPoint(0,0), new TPoint(0,1), new TPoint(0,2), new TPoint(1,0)};
		assertTrue(TPointArrEquals(p2.getBody(), t2));
		
		Piece p3 = new Piece(Piece.SQUARE_STR);
		TPoint[] t3 = new TPoint[] {new TPoint(0,0), new TPoint(1,0), new TPoint(0,1), new TPoint(1,1)};
		assertTrue(TPointArrEquals(p3.getBody(), t3));
	}
	
	@Test
	public void testEquals() {
		Piece stick1 = new Piece(Piece.STICK_STR);
		Piece stick2 = new Piece(Piece.STICK_STR);
		Piece stick3 = new Piece(Piece.STICK_STR);
		assertTrue(stick1.equals(new Piece(Piece.STICK_STR)));
		assertTrue(stick1.equals(stick1));	// test for reflexiveness
		
		assertTrue(stick1.equals(stick2));	//
		assertTrue(stick2.equals(stick1));	// test for symmetry
		
		assertTrue(stick2.equals(stick3));	//
		assertTrue(stick1.equals(stick2));	// test for transitiveness
		
		assertTrue(!(stick1.equals(new Piece(Piece.PYRAMID_STR))));
		
		assertTrue(ps[Piece.PYRAMID].equals(new Piece(Piece.PYRAMID_STR)));
		
		/*
		 * Non-tetris pieces
		 */
		assertTrue(new Piece(Piece.BAGEL_STR).equals(new Piece(new TPoint[] {
			new TPoint(0, 0), new TPoint(0, 1), new TPoint(0, 2), new TPoint(1, 0), new TPoint(1, 2),
			new TPoint(2, 0), new TPoint(2, 1), new TPoint(2, 2)
		})));
		
		assertTrue(!(new Piece(Piece.STICK_STR).equals(new Piece(new TPoint[] {
				new TPoint(0, 2), new TPoint(1, 2), new TPoint(2, 2), new TPoint(3, 2) 
		}))));
		
	}
	
	@Test
	public void testSkirt() {
		TPoint[] t = new TPoint[] {new TPoint(0,0), new TPoint(1,0), new TPoint(0,1), new TPoint(1,1)};
		Piece sq = new Piece(t);
		assertTrue(Arrays.equals(sq.getSkirt(), new int[] {0,0}));
		
		//some random non-tetris piece
		TPoint[] t1 = new TPoint[] {new TPoint(0,2), new TPoint(1,2), 
									new TPoint(2,2), new TPoint(1,1), new TPoint(1,0)};
		Piece p = new Piece(t1);
		assertTrue(Arrays.equals(p.getSkirt(), new int[] {2,0,2}));
		
		Piece a = ps[Piece.S1];
		assertTrue(Arrays.equals(a.getSkirt(), new int[] {0, 0, 1}));
		
		TPoint[] body = new TPoint[] {new TPoint(0, 2), new TPoint(1, 2), new TPoint(2, 2)};
		Piece custom = new Piece(body);
		assertTrue(Arrays.equals(custom.getSkirt(), new int[] {2, 2, 2}));
		
		
	}
	
	@Test
	public void testComputeRotation() {
		Piece square = new Piece(Piece.SQUARE_STR);
		assertTrue(square.equals((new Piece(Piece.SQUARE_STR).computeNextRotation())));
		Piece s2 = new Piece(Piece.S2_STR);
		assertTrue(s2.equals((new Piece(Piece.S2_STR)).computeNextRotation().computeNextRotation()) );
		TPoint[] t = new TPoint[] {
				new TPoint(1,0), new TPoint(0,1), new TPoint(1,1), new TPoint(2,1),
		};
		
		Piece rotatedPyr = new Piece(t);
		assertTrue(rotatedPyr.equals(new Piece(Piece.PYRAMID_STR).computeNextRotation().computeNextRotation()));
		assertTrue(!s2.equals(new Piece(Piece.S1_STR)));
	}
	
	
	@Test
	public void testFastRotation() {
		TPoint[] t = new TPoint[] {new TPoint(0,0), new TPoint(1,0), new TPoint(0,1), new TPoint(0,2)};
		Piece L1 = new Piece(t);
		Piece[] ps = Piece.getPieces();
		assertTrue(L1.equals(ps[Piece.L1].fastRotation().fastRotation().fastRotation().fastRotation()));
		TPoint[] t2 = new TPoint[]{new TPoint(0,0), new TPoint(1,0), new TPoint(1,1), new TPoint(2,1)};
		Piece S1 = new Piece(t2);
		assertTrue(S1.equals(ps[Piece.S1]));
		assertTrue(S1.computeNextRotation().computeNextRotation().equals(ps[Piece.S1].fastRotation().fastRotation()));
	}
	
	
	
	
	@Test
	public void testDimensions() {
		Piece p = new Piece(Piece.L1_STR);
		assertEquals(3, p.getHeight());
		assertEquals(2, p.getWidth());
		assertEquals(2, pyr1.getHeight());
		assertEquals(3, pyr1.getWidth());
		/*
		 * Proceed to non-stadard tetris piece tests
		 */
		Piece b = new Piece(Piece.BAGEL_STR);
		assertEquals(3, b.getHeight());
		assertEquals(3, b.getWidth());
		Piece l = new Piece(Piece.BIG_L_STR);
		assertEquals(3, l.getHeight());
		assertEquals(3, l.getWidth());
		Piece c = new Piece(new TPoint[] {new TPoint(0, 2), new TPoint(1, 2), new TPoint(2, 2)});
		assertEquals(3, c.getHeight());
		assertEquals(3, c.getWidth());
		/*
		 * Proceed to testing pieces from getPieces() array
		 */
		Piece d = ps[Piece.STICK];
		assertEquals(4, d.getHeight());
		assertEquals(1, d.getWidth());
		
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
