package trade;

import java.util.HashMap;
import java.util.Map;


public class Transaction {
	
	private boolean successful;

	private Map<Integer,String> messages;

	
	public Transaction() {
		messages = new HashMap<Integer,String>();
	}
	
	public void addMessage(int status, String message) {
		messages.put(new Integer(status),message);
	}
	
	public Map<Integer,String> getMessages() {
		return messages;
	}
	
	/* Housekeeping */
	
	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}
	
	
}
