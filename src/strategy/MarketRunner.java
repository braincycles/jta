package strategy;


import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;

import data.PriceBar;
import data.QuoteHistory;


public class MarketRunner {

	private List<QuoteHistory> quoteHistories;

	public MarketRunner() {
		quoteHistories = new LinkedList<QuoteHistory>();
	}





	public void addQuoteHistory(QuoteHistory qh) {
		quoteHistories.add(qh);
	}



	public void run() {

		for(QuoteHistory qh:quoteHistories ) {
			List<PriceBar> prices = qh.getPriceBars();
			
			BollingerStrategy bollStrat = new BollingerStrategy(14, new double[]{2.0, 2.0});
			//bollStrat.run();
			
			for(int i=0;i<prices.size();i++) {
				
				DateTime date = prices.get(i).getDate();
				double close = prices.get(i).getClose();
				
				
				
				
				
				
				System.err.println(date + " " + close + " " + prices.get(i).getPriceAction());
				
				
			}
			
			
			
			
			
			
		}



	}



}
