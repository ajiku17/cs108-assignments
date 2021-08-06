package bank;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {

	
	private static final int DEFAULT_STARTING_BALANCE = 1000;
	private int id;
	private int balance;
	private int numWithdrawls;
	private int numDeposits;
	private Lock lock;
	private Condition notEnoughFunds;
	
	/*
	 * Public constructor
	 */
	public Account(int id, int startingBalance) {
		this.id = id;
		balance = startingBalance;
		numWithdrawls = 0;
		numDeposits = 0;
		lock = new ReentrantLock();
		notEnoughFunds = lock.newCondition();
	}
	
	/*
	 * Public constructor
	 */
	public Account(int id) {
		this(id, DEFAULT_STARTING_BALANCE);
	}
	
	
	/*
	 * Sets a new balance for the account
	 */
	public void setBalance(int newBalance) {
		lock.lock();
		try {
			this.balance = newBalance;
		}finally {
			lock.unlock();
		}
	}
	
	
	/*
	 * Withdraws the specified amount from the account
	 * @return true if the withdrawl was successful, false otherwise
	 */
	public boolean withdraw(int amount){
		lock.lock();
		try {
//			while(balance < amount) {
//				notEnoughFunds.await();
//			}
			balance -= amount;
			numWithdrawls++;
		}finally{
			lock.unlock();
		}
		return true;
	}
	
	
	/*
	 * Deposits the specified amount into the account's balance
	 */
	public void deposit(int amount) {
		lock.lock();
		try {
			balance += amount;
			numDeposits++;
//			notEnoughFunds.signalAll();
		}finally{
			lock.unlock();
		}
	}
	
	/*
	 * Returns the id of the account
	 */
	public int getID() {
		return id;
	}
	
	/*
	 * Returns the balance of the account
	 */
	public int getBalance() {
		return balance;
	}
	
	/*
	 * Returns the number of withdrawls from the account
	 */
	public int getNumWithdrawls() {
		return numWithdrawls;
	}
	
	/*
	 * Returns the number of deposits into the account
	 */
	public int getNumDeposits() {
		return numDeposits;
	}
	
	/*
	 * Returns the number of transactions this account took part in
	 */
	public int getNumTransactions() {
		return getNumDeposits() + getNumWithdrawls();
	}
	
	/*
	 * Overrides the Objects toString method, prints the contents 
	 * with a human-readable format
	 * @return accounts contents
	 */
	@Override
	public String toString() {
		return "Account ID = " + getID() + ", Balance = " + getBalance() + 
					", Number of transactions = " + getNumTransactions();
	}
	
	/*
	 * Overrides the Object's equals method
	 */
	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		
		if(!(other instanceof Account)) {
			return false;
		}
		
		Account otherAcc = (Account) other;
		
		return this.id == otherAcc.id;
	}
}
