package portfolio;

import java.util.HashMap;
import java.util.Map;

import trade.Stock;
import trade.Trade;
import trade.Transaction;

public class Position {
	
	public static int OPEN = 0;
	public static int CLOSED = 1;
	
	private Stock stock;
	private int amount;
	private double currentPrice=0;
	private double initialPrice;
	private double initialValue;
	
	private Map<Integer, Trade> trades;
	private boolean empty = true;
	
	
	public Position() {
		empty = true;
	}
	
	public Position(Trade trade) {
		super();
		setStock(trade.getStock());
		setAmount(trade.getAmount());
		trades = new HashMap<Integer, Trade>();
		trades.put(trade.getId(), trade);
		setInitialValue(amount*trade.getPrice());
		setInitialPrice(trade.getPrice());
		setCurrentPrice(trade.getPrice());
		setEmpty(false);
	}
	
	
	public double closePosition(double price) {
		setEmpty(true);
		return amount*price;
	}
	
	
	public Transaction adjust(Trade trade) {
		Transaction transaction = new Transaction(trade);
		if(trade.getStock().equals(stock) == false) {
			transaction.setSuccessful(false, WRONG_SYMBOL, "Wrong symbol on underlying asset");
			return transaction;
		}
		
		if(trade.getType() == Trade.SELL) {
			if(getAmount() < trade.getAmount()) {
				transaction.setSuccessful(false, NOT_ENOUGH_ASSET, "Not enough asset to sell");
				return transaction;
			}
		}

		trades.put(trade.getId(), trade);
		changeAmount(trade.getAmount());
		/* Add to the initial position price */
		setInitialValue(amount*trade.getPrice());
		
		transaction.setSuccessful(true, OK, "");
		return transaction;
	}

	public Stock getStock() {
		return stock;
	}

	private void setStock(Stock stock) {
		this.stock = stock;
	}

	public int getAmount() {
		return amount;
	}

	private void setAmount(int amount) {
		this.amount = amount;
	}
	
	
	private int changeAmount(int amount) {
		this.amount = this.amount + amount;
		return amount;
	}


	public double getInitialPrice() {
		return initialPrice;
	}


	private void setInitialPrice(double initialPrice) {
		this.initialPrice = initialPrice;
	}
	
	
	public boolean isActive() {
		return amount!=0;
	}


	public double getInitialValue() {
		return initialValue;
	}


	private void setInitialValue(double initialValue) {
		this.initialValue = initialValue;
	}


	public double getCurrentValue() {
		return currentPrice*amount;
	}
	
	public void setCurrentPrice(double price) {
		this.currentPrice = price;
	}
	
	
	
	
	public boolean isEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}




	/* Constants */
	public static int OK = 1;
	public static int NOT_ENOUGH_ASSET = 10001;
	public static int WRONG_SYMBOL = 10002;

}
