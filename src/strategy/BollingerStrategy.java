package strategy;

import java.util.List;

import data.PriceBar;
import data.PriceHistory;

import indicators.Bollinger;
import indicators.IndicatorValue;

public class BollingerStrategy {
	
	private Bollinger boll;
	private PriceHistory quoteHistory;
	
	public BollingerStrategy(PriceHistory qh, int period) {
		this.quoteHistory = qh;
		this.boll = new Bollinger(period, 2);
	}
	
	
	public void run() {
		List<PriceBar> prices = quoteHistory.getPriceBars();
		
		for(int i=0;i<prices.size();i++) {
			PriceBar priceBar = prices.get(i);
			double close = priceBar.getClose();
			
			IndicatorValue value = boll.tick(priceBar, PriceBar.CLOSE);
			//System.err.println(prices.get(i).getDate() + " " + close + " " + boll.getLowerBand() + " " + boll.getMidpoint() + " "  + boll.getUpperBand());
			double upperBand = value.getValue(Bollinger.UPPERBAND);
			double lowerBand = value.getValue(Bollinger.LOWERBAND);
			double midPoint = value.getValue(Bollinger.MIDPOINT);
			
			
			if(close > upperBand) {
				priceBar.setPriceAction(-10);
				System.out.println(priceBar.getDate() + " Top " + close + " " + lowerBand + " " + midPoint + " "  + upperBand);
			}
			
			if(close < lowerBand) {
				priceBar.setPriceAction(10);
				System.out.println(priceBar.getDate() + " Bot " + close + " " + lowerBand + " " + midPoint + " "  + upperBand);
			}
			
		}
		
		
		
	}
	
	

}
