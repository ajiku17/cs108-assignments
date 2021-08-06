/*
 HW1 Taboo problem class.
 Taboo encapsulates some rules about what objects
 may not follow other objects.
 (See handout).
*/
package assign1;

import java.util.*;

import com.sun.xml.internal.ws.api.server.Container;

public class Taboo<T> {
	
	private Map<T, Set<T>> noFollowMap;
	
	
	/**
	 * Constructs a new Taboo using the given rules (see handout.)
	 * @param rules rules for new Taboo
	 */
	public Taboo(List<T> rules) {
		noFollowMap = new HashMap<T, Set<T>>();
		
	
		for(int i = 0; i < rules.size() - 1; i++) {
			if(rules.get(i) == null || rules.get(i + 1) == null)continue;
			if(noFollowMap.containsKey(rules.get(i))) {
				noFollowMap.get(rules.get(i)).add(rules.get(i + 1));
			}else {
				Set<T> ls = new HashSet<T>();
				ls.add(rules.get(i + 1));
				noFollowMap.put(rules.get(i), ls);
			}
		}
	}
	
	/**
	 * Returns the set of elements which should not follow
	 * the given element.
	 * @param elem
	 * @return elements which should not follow the given element
	 */
	public Set<T> noFollow(T elem) {
		if(!noFollowMap.containsKey(elem)) {
			return Collections.emptySet(); 
		}
		 return noFollowMap.get(elem); // TODO YOUR CODE HERE
	}
	
	/**
	 * Removes elements from the given list that
	 * violate the rules (see handout).
	 * @param list collection to reduce
	 */
	public void reduce(List<T> list) {
		for(int i = 1; i < list.size(); i++) {
			if(noFollowMap.containsKey(list.get(i - 1))) {
				if(noFollowMap.get(list.get(i - 1)).contains(list.get(i))) {
					list.remove(i);
					i--;
				}
			}
		}
	}
}
