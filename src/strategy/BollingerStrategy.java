package strategy;

import java.util.List;

import data.PriceBar;
import data.QuoteHistory;

import indicators.Bollinger;

public class BollingerStrategy {
	
	private int period;
	private Bollinger boll;
	private QuoteHistory quoteHistory;
	
	public BollingerStrategy(QuoteHistory qh, int p) {
		this.period = p;
		this.quoteHistory = qh;
		this.boll = new Bollinger(qh, period, 2);
	}
	
	
	public void run() {
		List<PriceBar> prices = quoteHistory.getPriceBars();
		
		for(int i=0;i<prices.size();i++) {
			PriceBar priceBar = prices.get(i);
			double close = priceBar.getClose();
			
			boll.calculate(i);
			//System.err.println(prices.get(i).getDate() + " " + close + " " + boll.getLowerBand() + " " + boll.getMidpoint() + " "  + boll.getUpperBand());
			
			if(close > boll.getUpperBand()) {
				priceBar.setPriceAction(-10);
				//System.err.println(priceBar.getDate() + " Top " + close + " " + boll.getLowerBand() + " " + boll.getMidpoint() + " "  + boll.getUpperBand());
			}
			
			if(close < boll.getLowerBand()) {
				priceBar.setPriceAction(10);
				//System.err.println(priceBar.getDate() + " Bot " + close + " " + boll.getLowerBand() + " " + boll.getMidpoint() + " "  + boll.getUpperBand());
			}
			
		}
		
		
		
	}
	
	

}
