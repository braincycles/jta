package portfolio;

import java.util.HashMap;
import java.util.Map;

import trade.Stock;
import trade.Trade;
import trade.Transaction;

public class Portfolio {


	private String name;
	private int allowedConcurrentPositions = 1;
	private double cash;
	private Map<Stock, Position> positions;
	private boolean empty =true;

	public Portfolio(String name, double cash) {
		positions = new HashMap<Stock, Position>();
		this.name = name;
		this.cash = cash;
		empty = false;
	}
	
	public Portfolio() {
		positions = new HashMap<Stock, Position>();
		empty = true;
	}

	
	public double getTotalWorth() {
		double currentValue = 0.0;
		for(Position pos : getPositions().values())
			currentValue = currentValue + pos.getCurrentValue();
		return currentValue;
	}

	public Transaction addPosition(Trade t) {
		if(checkForPosition(t.getStock()))
			/* Already have a position in this symbol */
			return addToPosition(t);
		else
			return addNewPosition(t);
	}


	public Transaction sellPosition(Trade t) {
		Transaction transation = new Transaction(t);
		Position position = getPosition(t.getStock());
		int oldAmount = position.getAmount();
		
		if(t.getAmount() == position.getAmount() || t.getAmount() == 0) {
			cash = cash + t.getCostOfTrade(position.getAmount());
			positions.remove(t.getStock());
			transation.setSuccessful(true, OK, "Position closed out.");
			return transation;
		}
		else if(t.getAmount() < position.getAmount()) {
			cash = cash + t.getCostOfTrade();
			position.adjust(t);
			transation.setSuccessful(true, OK, "Existing position sold. Old amount: " + oldAmount + ", new amount: " + position.getAmount());
			return transation;
		}
		else {
			transation.setSuccessful(false, INSUFFICIENT_ASSET, "Not enough asset to sell.");
			return transation;
		}

	}


	private Transaction addNewPosition(Trade t) {
		Transaction transation = new Transaction(t);
		if(getNumberCurrentPositions()<allowedConcurrentPositions) {

			if(cash >= t.getCostOfTrade()) {
				Position newPosition = new Position(t);
				positions.put(t.getStock(), newPosition);
				cash = cash - t.getCostOfTrade();
				transation.setSuccessful(true, OK, "Position added. New amount: " + newPosition.getAmount());
				return transation;
			} 
			transation.setSuccessful(false, INSUFFICIENT_FUNDS, "Insufficient cash in portfolio ("+name+"). Needed " + t.getCostOfTrade() + " but had " + cash);
			return transation;
		}
		transation.setSuccessful(false, POSITION_LIMIT, "Too many positions");
		return transation;
	}



	public Transaction addToPosition(Trade t) {
		Transaction transation = new Transaction(t);
		Position position = getPosition(t.getStock());	

		int oldAmount = position.getAmount();


		if(cash >= t.getCostOfTrade()) {
			cash = cash - t.getCostOfTrade();
			position.adjust(t);
			transation.setSuccessful(true, OK, "Existing position added to. Old amount: " + oldAmount + ", new amount: " + position.getAmount());
			return transation;
		}
		else {
			transation.setSuccessful(false, INSUFFICIENT_FUNDS, "Insufficient cash in portfolio ("+name+"). Needed " + t.getCostOfTrade() + " but had " + cash);
			return transation;
		}	
	}

	
	public Position getPosition(String symbol) {
		return getPosition(new Stock(symbol));
	}

	public Position getPosition(Stock stock) {
		if (checkForPosition(stock))
			return positions.get(stock);
		else
			return new Position();
	}


	public boolean checkForPosition(String symbol){
		return checkForPosition(new Stock(symbol));
	}
	
	
	public boolean checkForPosition(Stock stock){
		return positions.containsKey(stock);
	}

	public int getNumberCurrentPositions() {
		
		
		int activePositions = 0;
		for(Position p : positions.values())
			if(p.isActive())
				activePositions++;
		return activePositions;
	}

	public double getCash() {
		return cash;
	}

	public void setCash(double cash) {
		this.cash = cash;
	}

	public Map<Stock, Position> getPositions() {
		return positions;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}




	public int getAllowedConcurrentPositions() {
		return allowedConcurrentPositions;
	}


	public void setAllowedConcurrentPositions(int concurrentPositions) {
		this.allowedConcurrentPositions = concurrentPositions;
	}


	public boolean isEmpty() {
		return empty;
	}



	@Override
	public String toString() {
		return name + " (" + positions.size() + ") positions.";
	}



	/* Constants */
	public static int OK = 1000;
	public static int INSUFFICIENT_FUNDS = 1001;
	public static int POSITION_LIMIT = 1002;
	public static int INSUFFICIENT_ASSET = 1003;

}
