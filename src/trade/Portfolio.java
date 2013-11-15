package trade;

import java.util.LinkedList;
import java.util.List;

public class Portfolio {


	private String name;
	private int allowedConcurrentTrades = 1;
	private double cash;
	private List<Position> positions;


	public Portfolio(String name, double cash) {
		positions = new LinkedList<Position>();
		this.name = name;
		this.cash = cash;
	}


	public Transaction addPosition(Trade t) {
		if(checkForPosition(t.getStock()))
			/* Already have a position in this symbol */
			return addToPosition(t);
		else
			return addNewPosition(t);
	}




	private Transaction addNewPosition(Trade t) {
		Transaction transation = new Transaction();
		if(getNumberCurrentPositions()<allowedConcurrentTrades) {
			if(cash >= t.getCostOfTrade()) {
				Position newPosition = new Position(t);
				positions.add(newPosition);
				cash = cash - t.getCostOfTrade();
				transation.setSuccessful(true);
				transation.addMessage(OK, "Position added");
				return transation;
			}
			transation.setSuccessful(false);
			transation.addMessage(INSUFFICIENT_FUNDS, "Insufficient cash in portfolio ("+name+"). Needed " + t.getCostOfTrade() + " but had " + cash);
			return transation;
		}
		transation.setSuccessful(false);
		transation.addMessage(POSITION_LIMIT, "Too many positions.");
		return transation;
	}



	public Transaction addToPosition(Trade t) {
		Transaction transation = new Transaction();
		Position position = getPosition(t.getStock());	

		if(t.getType() == Trade.SELL) {
			if(t.getAmount() < position.getAmount()) {
				cash = cash + t.getCostOfTrade();
				transation.setSuccessful(true);
				transation.addMessage(OK, "Existing position sold.");
				return transation;
			}
			if(t.getAmount() == position.getAmount()) {
				cash = cash + t.getCostOfTrade();
				positions.remove(position);
				transation.setSuccessful(true);
				transation.addMessage(OK, "Position closed out.");
				return transation;
			}
			else {
				transation.setSuccessful(false);
				transation.addMessage(INSUFFICIENT_ASSET, "Not enough asset to sell.");
				return transation;
			}
		}
		else {
			if(cash >= t.getCostOfTrade()) {
				cash = cash + t.getCostOfTrade();
				transation.setSuccessful(true);
				transation.addMessage(OK, "Existing position added to.");
				return transation;
			}
			else {
				transation.setSuccessful(false);
				transation.addMessage(INSUFFICIENT_FUNDS, "Insufficient cash in portfolio ("+name+"). Needed " + t.getCostOfTrade() + " but had " + cash);
				return transation;
			}	
		}
	}


	private Position getPosition(Stock stock) {
		for(Position pos : positions) {
			if(pos.getStock().equals(stock))
				return pos;
		}
		return new Position();
	}


	public boolean checkForPosition(Stock stock){
		for(Position pos : positions) {
			if(pos.getStock().equals(stock))
				return true;
		}
		return false;
	}

	public int getNumberCurrentPositions() {
		int activePositions = 0;
		for(Position p : positions)
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

	public List<Position> getPositions() {
		return positions;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}




	public int getAllowedConcurrentTrades() {
		return allowedConcurrentTrades;
	}


	public void setAllowedConcurrentTrades(int allowedConcurrentTrades) {
		this.allowedConcurrentTrades = allowedConcurrentTrades;
	}




	/* Constants */
	public static int OK = 1000;
	public static int INSUFFICIENT_FUNDS = 1001;
	public static int POSITION_LIMIT = 1002;
	public static int INSUFFICIENT_ASSET = 1003;
}
