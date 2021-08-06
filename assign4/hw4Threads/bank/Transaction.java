package bank;

public class Transaction {

	private final Account from;
	private final Account to;
	private final int amount;
	
	public Transaction(Account from, Account to, int amount) {
		this.from = from;
		this.to = to;
		this.amount = amount;
	}
	
	/*
	 * Checks if this the null transaction(poison fill) which notifies the worker to terminate
	 */
	public boolean isNullTransaction() {
		return (from.getID() == -1 || to.getID() == -1);
	}
	
	/*
	 * Performs the operations necessary for a transfer.
	 * The caller should handle all the race conditions associated with this operation,
	 * this is just a transaction.
	 */
	public void doTransfer() {
		from.withdraw(amount);
		to.deposit(amount);
	}
	
	/*
	 * A simple toString method
	 */
	@Override
	public String toString() {
		return "from: " + from.getID() + " to: " + to.getID() + " amount: " + amount;
	}
	
}
