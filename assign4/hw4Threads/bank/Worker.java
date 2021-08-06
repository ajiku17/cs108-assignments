package bank;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class Worker implements Runnable{

	private BlockingQueue<Transaction> transactionQ;
	private int id;
	private CountDownLatch isDone;
	
	/*
	 * Public constructor
	 */
	public Worker(int id, BlockingQueue<Transaction> transactionQ, CountDownLatch isDone) {
		this.id = id;
		this.transactionQ = transactionQ;
		this.isDone = isDone;
	}
	
	public int getID() {
		return id;
	}
	
	/*
	 * Overrides the Runnable's run method
	 */
	@Override
	public void run() {
		while(true) {
			try {
				Transaction t = transactionQ.take();
				t.doTransfer();
				if(t.isNullTransaction())break;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		isDone.countDown();
	}

}
