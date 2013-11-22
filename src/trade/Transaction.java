package trade;

import java.util.HashMap;
import java.util.Map;


public class Transaction {
	
	private boolean successful;

	private String portfolioName;
	private Trade trade;
	private Map<Integer,String> messages;

	
	public Transaction(Trade t) {
		this(t,"None");
	}
	
	public Transaction(Trade t, String portfolioName) {
		this.trade = t;
		messages = new HashMap<Integer,String>();
		this.portfolioName = portfolioName;
	}
	
	public void addMessage(int status, String message) {
		messages.put(new Integer(status),message);
	}
	
	public void addMessages(int status, String[] msgs) {
		for(int i = 0;i<msgs.length;i++)
			messages.put(new Integer(status),msgs[i]);
	}
	
	public Map<Integer,String> getMessages() {
		return messages;
	}
	
	/* Housekeeping */
	
	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(boolean successful, int status, String message) {
		this.successful = successful;
		this.messages.put(status, message);
	}

	public Trade getTrade() {
		return trade;
	}

	public void setTrade(Trade trade) {
		this.trade = trade;
	}

	public String getPortfolioName() {
		return portfolioName;
	}

	public void setPortfolioName(String portfolioName) {
		this.portfolioName = portfolioName;
	}
	
	
	
}
