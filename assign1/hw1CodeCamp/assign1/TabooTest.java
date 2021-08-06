// TabooTest.java
// Taboo class tests -- nothing provided.
package assign1;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class TabooTest {

	private List<String> stringToList(String st){
		List<String> res = new ArrayList<String>();
		for(int i = 0; i < st.length(); i++) {
			res.add(st.charAt(i) + "");
		}
		return res;
	}
	
	
	private Set<String> stringToSet(String st){
		Set<String> res = new HashSet<String>();
		for(int i = 0; i < st.length(); i++) {
			res.add(st.charAt(i) + "");
		}
		return res;
	}
	
	@Test
	public void testNoFollow() {
		Taboo<String> t = new Taboo<String>(stringToList("acabax"));
		
		Set<String> noFollowA = stringToSet("cbx");

		assertTrue(noFollowA.equals(t.noFollow("a")));
		assertTrue(Collections.emptySet().equals(t.noFollow("x")));	
	}
	
	@Test
	public void testNoFollowSame() {
		Taboo<String> t = new Taboo<String>(stringToList("axaaaa"));
		
		Set<String> noFollow = stringToSet("ax");
		
		assertTrue(noFollow.equals(t.noFollow("a")));
		assertTrue(Collections.emptySet().equals(t.noFollow("ax")));
	}
	
	@Test
	public void testReduce1() {
		Taboo<String> t = new Taboo<String>(stringToList("acab"));
		
		List<String> res = new ArrayList<String>(stringToList("axc"));
		List<String> reduced = stringToList("acbxca");
		t.reduce(reduced);
		
		assertTrue(reduced.equals(res));
		
	}
	
	@Test
	public void testReduce2() {
		List<Integer> ls = new ArrayList<Integer>();
		ls.add(10);
		ls.add(15);
		ls.add(20);
		ls.add(99);
		ls.add(100);
		Taboo<Integer> t = new Taboo<Integer>(ls);
		
		List<Integer> empty = new ArrayList<Integer>();
		t.reduce(empty);
		assertTrue(Collections.emptyList().equals(empty));
		
		List<Integer> reducedls = new ArrayList<Integer>();
		reducedls.add(10);
		reducedls.add(20);
		reducedls.add(100);
		t.reduce(ls);
		assertTrue(reducedls.equals(ls));
		
	}
	
	@Test
	public void testNoFollowNullable() {
		List<String> rules = new ArrayList<String>();
		rules.add("a");
		rules.add("b");
		rules.add(null);
		rules.add("c");
		rules.add("d");
		
		Taboo<String> t = new Taboo<String>(rules);
		
		assertTrue(Collections.emptySet().equals(t.noFollow("b")));
		Set<String> noFollowC = new HashSet<String>();
		noFollowC.add("d");
		assertTrue(noFollowC.equals(t.noFollow("c")));
	}
	
	@Test
	public void testReduceNullable() {
		List<String> rules = new ArrayList<String>();
		rules.add("a");
		rules.add("b");
		rules.add(null);
		rules.add("c");
		rules.add("d");
		
		Taboo<String> t = new Taboo<String>(rules);
		
		List<String> reduced = stringToList("bacad");
		t.reduce(reduced);
		
		assertTrue(stringToList("bacad").equals(reduced));
	}
}
