package assign1;

import java.util.HashSet;
import java.util.Set;

// CS108 HW1 -- String static methods

public class StringCode {

	
	public StringCode() {};
	
	/**
	 * Given a string, returns the length of the largest run.
	 * A a run is a series of adajcent chars that are the same.
	 * @param str
	 * @return max run length
	 */
	public static int maxRun(String str) {
		if(str.length() == 0)return 0;
		int max = 0;
		char last = str.charAt(0);
		int counter = 1;
		for(int i = 1; i < str.length(); i++) {
			if(str.charAt(i) == last) {
				counter++;
			}else{
				if(counter > max)max = counter;
				counter = 1;
				last = str.charAt(i);
			}
		}
		
		return max;
	}

	//Extends a single character into an n-sized string
	private static String multiply(char c, int n) {
		String res = "";
		for(int i = 0; i < n; i++)
			res += c;
		
		return res;
	}
	
	/**
	 * Given a string, for each digit in the original string,
	 * replaces the digit with that many occurrences of the character
	 * following. So the string "a3tx2z" yields "attttxzzz".
	 * @param str
	 * @return blown up string
	 */
	public static String blowup(String str) {
		String res = "";
		for(int i = 0; i < str.length(); i++) {
			if(Character.isDigit(str.charAt(i))) {
				if(i == str.length() - 1)
					return res;
				int freq = str.charAt(i) - '0';
				char charToMult = str.charAt(i + 1);
				res += multiply(charToMult, freq);
			}else {
				res += str.charAt(i);
			}
		}
		return res;
	}
	
	/**
	 * Given 2 strings, consider all the substrings within them
	 * of length len. Returns true if there are any such substrings
	 * which appear in both strings.
	 * Compute this in linear time using a HashSet. Len will be 1 or more.
	 */
	public static boolean stringIntersect(String a, String b, int len) {
		Set<String> substrings = new HashSet<String>();
		for(int i = 0; i <= a.length() - len; i++) {
			String sub = a.substring(i, i + len);
			substrings.add(sub);
		}
		for(int i = 0; i <= b.length() - len; i++){
			String sub = b.substring(i, i + len);
			if(substrings.contains(sub))return true;
		}
		return false;
	}
}
