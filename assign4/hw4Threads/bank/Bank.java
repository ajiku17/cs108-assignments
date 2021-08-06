package bank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.HashMap;
import java.util.Map;

public class Bank {
	
	private BlockingQueue<Transaction> transactionQ;
	private Map<Integer, Account> accounts;
	private Thread[] workers;
	private CountDownLatch workersAreDone;
	
	public Bank(String transactionFileName, int numWorkers) {	
		workersAreDone = new CountDownLatch(numWorkers);
		transactionQ = new ArrayBlockingQueue<Transaction>(10);
		accounts = new HashMap<Integer, Account>();
		workers = new Thread[numWorkers];
		for(int i = 0; i < numWorkers; i++) {
			workers[i] = new Thread(new Worker(i, transactionQ, workersAreDone));
			workers[i].start();
		}
		long startTime = System.currentTimeMillis();
		runTransactionsFromFile(transactionFileName);
		poisonFillWorkers();
		try {
			workersAreDone.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Account acc : accounts.values()) 
			System.out.println(acc.toString());
		
		System.out.println("elapsed time: " + (System.currentTimeMillis() - startTime));
		
	}
	
	private void poisonFillWorkers(){
		for(int i = 0; i < workers.length; i++) {
			Transaction nullTrans = new Transaction(new Account(-1, 0), new Account(0, 0), 0);
			try {
				transactionQ.put(nullTrans);
			} catch (InterruptedException ignored) {
				// TODO Auto-generated catch block
				ignored.printStackTrace();
			}
		}
	}
	
	private void runTransactionsFromFile(String transactionFileName) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(transactionFileName));
			String line;
			while((line = reader.readLine()) != null) {
				String[] tokens = line.split(" ");
				if(!accounts.containsKey(Integer.valueOf(tokens[0])))
					accounts.put(Integer.valueOf(tokens[0]), new Account(Integer.valueOf(tokens[0])));
				if(!accounts.containsKey(Integer.valueOf(tokens[1])))
					accounts.put(Integer.valueOf(tokens[1]), new Account(Integer.valueOf(tokens[1])));
				Transaction t = new Transaction(accounts.get(Integer.valueOf(tokens[0])), accounts.get(Integer.valueOf(tokens[1])), Integer.valueOf(tokens[2]));
				try {
					transactionQ.put(t);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			reader.close();
		}catch(IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		if(args.length == 2) {
			Bank b = new Bank(args[0], Integer.valueOf(args[1]));
		}else {
			System.out.println("Please enter valid number of parameters");
		}
	}
}
