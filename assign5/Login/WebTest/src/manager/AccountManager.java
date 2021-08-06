package manager;

import java.util.HashMap;
import java.util.Map;

public class AccountManager {
	
	private static AccountManager accManager;
	private Map<String, String> accounts;
	
	
	/*
	 * Private constructor
	 */
	private AccountManager(){
		accounts = new HashMap<String, String>();
		accounts.put("Patrick", "1234");
		accounts.put("Molly", "FloPup");
	}
	
	
	/*
	 * Returns the current instance
	 */
	public static AccountManager getInstance() {
		if(accManager == null) {
			synchronized(AccountManager.class) {
				if(accManager == null) {
					accManager = new AccountManager();
				}
			}
		}
		return accManager;
	}
	
	/*
	 * Returns true if the user has successfully logged in
	 */
	public boolean loginUser(String username, String password) {
		if(accounts.containsKey(username) && password.equals(accounts.get(username))) {
			return true;
		}
		return false;
	}
	
	/*
	 * Returns true if the user has successfully registered
	 */
	public boolean registerUser(String username, String password) {
		if(accounts.containsKey(username)) {
			return false;
		}
		
		accounts.put(username, password);
		return true;
	}
	
}
