package assign1;

import java.util.*;

public class Appearances {
	
	public Appearances() {} ;
	
	//counts frequencies of elements in a list
	private static <T> void populateFreq(Iterator<T> it, Map<T, Integer> freq) {
		while(it.hasNext()) {
			T next = it.next();
			if(freq.containsKey(next)) {
				freq.put(next, freq.get(next) + 1);
			}else {
				freq.put(next, 1);
			}
		}
	}
	
	//compares two frequency maps
	private static <T> int countSameOccurances(Map<T, Integer> freqA, Map<T, Integer> freqB) {
		int count = 0;
		for (T key : freqA.keySet()) {
			if(freqB.containsKey(key)) {
				if(freqA.get(key) == freqB.get(key))
					count++;
			}
		}
		return count;
	}
	
	
	/**
	 * Returns the number of elements that appear the same number
	 * of times in both collections. Static method. (see handout).
	 * @return number of same-appearance elements
	 */
	public static <T> int sameCount(Collection<T> a, Collection<T> b) {
		Map<T, Integer> freqA = new HashMap<T, Integer>();
		Map<T, Integer> freqB = new HashMap<T, Integer>();
		Iterator<T> itA = a.iterator();
		Iterator<T> itB = b.iterator();
		
		populateFreq(itA, freqA);
		populateFreq(itB, freqB);
		
		return countSameOccurances(freqA, freqB); 
	}
	
}
