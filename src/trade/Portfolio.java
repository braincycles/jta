package trade;

import java.util.LinkedList;
import java.util.List;

public class Portfolio {
	
	private int allowedConcurrentTrades = 1;
	private double cash;
	private List<Trade> trades;
	
	
	public Portfolio(double cash) {
		trades = new LinkedList<Trade>();
		this.cash = cash;
	}
	
	public void execute(Trade trade) {
		if(getNumberCurrentTrades(Trade.ACTIVE)<=allowedConcurrentTrades) {
			trades.add(trade);
			cash += trade.getInitialPrice();
		}
	}
	
	
	public void run(){
		
	}
	
	public int getNumberCurrentTrades(int status) {
		int activeTrades = 0;
		for(Trade t : trades)
			if(t.getStatus() == status)
				activeTrades++;
		return activeTrades;
	}
	
	

}
