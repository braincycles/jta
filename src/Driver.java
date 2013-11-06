
import math.Stats;

import org.joda.time.DateTime;

import data.PriceBar;
import data.PriceHistory;
import data.Util;
import data.YahooHistoricalDataReader;





public class Driver {

	public Driver() {
		
		YahooHistoricalDataReader ydr = new YahooHistoricalDataReader();
		
		
		PriceHistory ph = ydr.getHistoricalStockPrices("BP.L", new DateTime(2013,10,1,0,0,0), new DateTime(2013,10,4,0,0,0), PriceHistory.DAILY);
		
		PriceHistory ph2 = ydr.getHistoricalStockPrices("BP" , new DateTime(2013,10,1,0,0,0), new DateTime(2013,10,4,0,0,0), PriceHistory.DAILY);
		
		Util.pad(ph, ph2);
		
		
		//double rm = Stats.cov(ph, ph2);
		//System.err.println(rm);
		
		
		//for(PriceBar pb : ph2.getPriceHistory()) 
		//	System.err.println(pb.getDate().toString() + " " + pb.getClose());
		
		//for(PriceBar pb : ph.getPriceHistory()) 
		//	System.err.println(pb.getClose());
		
		
		for(int i=0;i<ph.getPriceHistory().size();i++) {
			System.err.println(ph.getPriceHistory().get(i).getClose() + " " + ph2.getPriceHistory().get(i).getClose());
		}
		
		
		//ydr.open("C:\\Users\\simon.COMSOL\\Desktop\\goog.csv");
		//QuoteHistory qh = ydr.read();
		//ydr.close();
		
		//Trade sb = new SpreadBet(Trade.LONG,1,100.00,100,new SimpleSpread(2),100.20,99.60);
		
		//double price = 100.00;
		//Price straight away
		//System.err.println(sb.getCurrentValue(price));
		//Price after 10 point movement
		//price = 100.10;
		//System.err.println(sb.getCurrentValue(price));
		
		
		
		//MarketRunner mr = new MarketRunner();
		//mr.addQuoteHistory(qh);
		//mr.run();
		
		//double mean = Stats.priceMean(qh, 100, 30, QuoteHistory.CLOSE);
		//double sd = Stats.priceSD(qh, 100, 30, QuoteHistory.CLOSE);
		//System.err.println(mean + " " + sd);
				
		//for(int i=0;i<99;i++) {
			//System.err.println((double)(i/100.0) + " " + (Stats.invNormal(mean, sd, (double)(i/100.0))));
		//}
		//mean = 0.0;
		//for(int i=-200;i<200;i++) {
			//System.err.println((double)(i/10.0) + " " + (Math.abs(Stats.cumNormal(mean, sd, (double)(i/10.0)))));
		//}
		
		//for(int i=0;i<qh.size();i++) {
			//Stats.priceSD(qh,i,30, QuoteHistory.CLOSE);
			//System.err.println(i + " "  + qh.getPriceBar(i).getClose() + " " + Stats.priceSD(qh,i,30, QuoteHistory.CLOSE));
		//}
		
		
		
	}
	
	public static void main(String[] args) {
		new Driver();
	}
	
}
