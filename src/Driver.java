
import java.util.Vector;

import math.Stats;

import org.joda.time.DateTime;

import data.PriceBar;
import data.PriceHistory;
import data.Util;
import data.YahooHistoricalDataReader;





public class Driver {

	public Driver() {

		//dummyData();

		YahooHistoricalDataReader ydr = new YahooHistoricalDataReader();
		
		PriceHistory[] ph = ydr.getHistoricalStockPrices(
				new String[]{"ASYS", "ADVS", "AAPL", "YHOO", "DELL", "COKE"}, 
				new DateTime(2012,1,1,0,0,0), new DateTime(2013,10,4,0,0,0), PriceHistory.DAILY);
		
		PriceHistory.calculateReturns(ph, PriceBar.CLOSE, true);
		
		System.out.println("Padding");
		Util.pad(ph);
		System.out.println("Padded");
		

		int[] offset = {-2, -1,0,1, 2};
		double[][][] res = Stats.correlationOverWindow(ph, PriceBar.CLOSE, offset, -1);

		Stats.printCorrelationMatrix(res, offset);
		
		Stats.printCorrelationMatrix(ph, res, offset, 0.85);

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


	@SuppressWarnings("unused")
	private void dummyData() {

		Vector<PriceBar> dum1 = new Vector<PriceBar>();
		Vector<PriceBar> dum2 = new Vector<PriceBar>();


		dum1.add(new PriceBar(new DateTime(2013,5,1,0,0,0),4,5,6,22,8));
		dum1.add(new PriceBar(new DateTime(2013,5,2,0,0,0),4,5,6,28,8));
		dum1.add(new PriceBar(new DateTime(2013,5,3,0,0,0),4,5,6,23,8));
		dum1.add(new PriceBar(new DateTime(2013,5,4,0,0,0),4,5,6,29,8));
		dum1.add(new PriceBar(new DateTime(2013,5,5,0,0,0),4,5,6,26,8));
		dum1.add(new PriceBar(new DateTime(2013,5,7,0,0,0),4,5,6,27,8));

		dum2.add(new PriceBar(new DateTime(2013,5,1,0,0,0),4,5,6,289,8));
		dum2.add(new PriceBar(new DateTime(2013,5,3,0,0,0),4,5,6,223,8));
		dum2.add(new PriceBar(new DateTime(2013,5,4,0,0,0),4,5,6,245,8));
		dum2.add(new PriceBar(new DateTime(2013,5,6,0,0,0),4,5,6,222,8));
		dum2.add(new PriceBar(new DateTime(2013,5,7,0,0,0),4,5,6,249,8));
		dum2.add(new PriceBar(new DateTime(2013,5,9,0,0,0),4,5,6,298,8));

		PriceHistory ph1 = new PriceHistory(dum1);
		PriceHistory ph2 = new PriceHistory(dum2);

		Util.pad(ph1, ph2);

		for(int i=0;i<ph1.getPriceHistory().size();i++) {
			System.out.println(ph1.getPriceHistory().get(i).getDate() + " " + ph1.getPriceHistory().get(i).getClose());
		}

		System.out.println("---");

		for(int i=0;i<ph2.getPriceHistory().size();i++) {
			System.out.println(ph2.getPriceHistory().get(i).getDate() + " " + ph2.getPriceHistory().get(i).getClose());
		}



	}


	public static void main(String[] args) {
		new Driver();
	}

}
