package cracker;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

public class Worker implements Runnable{

	private int id;
	private char[] startingChars;
	private char[] possibleChars;
	private byte[] target;
	private int maxLength;
	private MessageDigest md;
	private CountDownLatch isDone;
	
	
	public Worker(int id, String startingChars, String possibleChars, int maxLength, byte[] target, 
								String algorithm, CountDownLatch isDone) {
		this.id = id;
		this.possibleChars = possibleChars.toCharArray();
		this.startingChars = startingChars.toCharArray();
		this.maxLength = maxLength;
		this.target = target;
		this.isDone = isDone;
		try {
			md = MessageDigest.getInstance(algorithm);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getID() {
		return id;
	}
	
	public String chars() {
		return startingChars.toString();
	}
	
	private void dfs(String current) {
		if(current.length() > maxLength)return;
		md.update(current.getBytes());
		if(Arrays.equals(target, md.digest())) {
			System.out.println(current);
		}
		
		for(int i = 0; i < possibleChars.length; i++) {
			dfs(current + possibleChars[i]);
		}
	}
	
	@Override
	public void run() {
		for(int i = 0; i < startingChars.length; i++) {
			dfs(startingChars[i] + "");
		}
		isDone.countDown();
	}

}
