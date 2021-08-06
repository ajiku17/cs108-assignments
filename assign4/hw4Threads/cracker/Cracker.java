package cracker;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.security.MessageDigest;

public class Cracker {
	// Array of chars used to produce strings
	public static final char[] CHARS = "abcdefghijklmnopqrstuvwxyz0123456789.,-!".toCharArray();	
	public static final String CHARS_STRING = "abcdefghijklmnopqrstuvwxyz0123456789.,-!";
	
	private static final String ALGORITHM = "SHA-1";
	
	public static String generateHash(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance(ALGORITHM);
			md.update(password.getBytes());
			return hexToString(md.digest());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static void crack(String hash, int maxChars, int numWorkers){
		CountDownLatch workersAreDone = new CountDownLatch(numWorkers);
		List<String> res = new ArrayList<String>();
		String st = CHARS_STRING;
		splitToN(numWorkers, st, res);
		int id = 1;
		long startTime = System.currentTimeMillis();
		for(String s : res) {
			Thread t = new Thread(new Worker(id++,	s, CHARS_STRING, maxChars, hexToArray(hash), ALGORITHM, workersAreDone));
			t.start();
		}
		
		try {
			workersAreDone.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("All done\nElapsed time: " + (System.currentTimeMillis() - startTime));
	}
	
	/*
	 Given a byte[] array, produces a hex String,
	 such as "234a6f". with 2 chars for each byte in the array.
	 (provided code)
	*/
	public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
	
	/*
	 Given a string of hex byte values such as "24a26f", creates
	 a byte[] array of those values, one byte value -128..127
	 for each 2 chars.
	 (provided code)
	*/
	public static byte[] hexToArray(String hex) {
		byte[] result = new byte[hex.length()/2];
		for (int i=0; i<hex.length(); i+=2) {
			result[i/2] = (byte) Integer.parseInt(hex.substring(i, i+2), 16);
		}
		return result;
	}
	
	// possible test values:
	// a 86f7e437faa5a7fce15d1ddcb9eaeaea377667b8
	// fm adeb6f2a18fe33af368d91b09587b68e3abcb9a7
	// a! 34800e15707fae815d7c90d49de44aca97e2d759
	// xyz 66b27417d37e024c46526c2f6d358a754fc552f3

	public static void main(String[] args) {
		if(args.length == 1) {
			Cracker.generateHash(args[0]);
		}else if(args.length == 3) {
			Cracker.crack(args[0], Integer.valueOf(args[1]), Integer.valueOf(args[2]));
		}		
	}
	
	private static void splitToN(int n, String st, List<String> results) {
		int lastIndex = 0;
		for(int i = 0; i < n - 1; i++) {
			int startIndex = lastIndex;
			lastIndex += (st.length() / n);
			
			results.add(st.substring(startIndex, lastIndex));
		}
		
		results.add(st.substring(lastIndex));
	}
}
