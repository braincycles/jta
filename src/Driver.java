
import indicators.ADX;
import indicators.Bollinger;
import indicators.EMA;
import indicators.Indicator;
import indicators.RSI2;
import indicators.SMA;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

import math.Stats;

import org.joda.time.DateTime;

import portfolio.Portfolio;
import portfolio.PortfolioManager;

import strategy.BackTester;
import strategy.BollingerStrategy;
import strategy.LimitsStrategy;
import trade.Stock;
import trade.StockTrade;
import trade.Trade;

import data.PriceBar;
import data.PriceHistory;
import data.Util;
import data.YahooHistoricalDataReader;

import logger.*;





public class Driver {

	private final static Logger LOGGER = Logger.getLogger(MyLogger.class
			.getName());

	public Indicator loadObject(String classString/*, HashMap hm*/) {
		try {
			Class<?> c = Class.forName(classString);
			Constructor<?> ctor = c.getConstructor(Integer.class);
			//Integer param1 = hm.get("xPos") ...;
			//Integer param2 = hm.get("yPos") ...;
			Integer param1 = 10;
			Indicator instanceOfTheClass = (Indicator) ctor.newInstance(10);
			return instanceOfTheClass;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;

	}


	public Driver() {


		//LOGGER.severe("Info Log");
		//LOGGER.severe("Info Log");
		//testStrategy();

		//testBollinger();

		//testIndicators();

		//testPortfolio();

		testBackTester();

		//testCorrelation();

	}


	private void testBackTester() {

		YahooHistoricalDataReader ydr = new YahooHistoricalDataReader();

		PriceHistory[] ph = ydr.getHistoricalStockPrices(
				new String[]{"YHOO", "AAPL"}, 
				new DateTime(2010,1,1,0,0,0), new DateTime(2013,10,4,0,0,0), PriceHistory.DAILY);

		BollingerStrategy boll = new BollingerStrategy(30, new double[]{3,2});	
		boll.setBandThresholds(new double[]{0.4, 0.4});
		
		Indicator[] indicators = new Indicator[]{boll};
		
		BackTester bt = new BackTester(ph, indicators);
		bt.setLimits(0.1, 0.15);
		bt.setThresholds(0, 0);
		bt.setupPortfolio("Backtest Portfolio", 10000, 2);
		bt.run();
	}


	private void testPortfolio() {
		PortfolioManager pman = new PortfolioManager();

		Portfolio p = new Portfolio("Test 1", 1000000);
		p.setAllowedConcurrentPositions(1);
		pman.addPortfolio(p);

		try {
			Trade t1 = new StockTrade(new DateTime(),new Stock("YHOO"),100, 67.45, 10);
			pman.registerTrade("Test 1", t1);
			pman.registerTrade("Test 1", t1);
		}
		catch (Exception e) {
			e.printStackTrace();
		}



		System.out.println(p.getCash());

	}

	private void testCorrelation() {
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
	}


	private void testIndicators() {
		YahooHistoricalDataReader ydr = new YahooHistoricalDataReader();

		PriceHistory[] ph = ydr.getHistoricalStockPrices(
				new String[]{"COKE"}, 
				new DateTime(2010,1,1,0,0,0), new DateTime(2013,10,4,0,0,0), PriceHistory.DAILY);

		//Util.pad(ph);

		SMA sma = new SMA(10);
		EMA ema = new EMA(10);

		RSI2 rsi = new RSI2(14);

		ADX adx = new ADX(14);

		for(int i=0;i<ph[0].size();i++) {

			System.out.println(i + " " + ph[0].getPriceHistory().get(i).getClose() 
					+ " " + sma.tick(ph[0].getPriceHistory().get(i), PriceBar.CLOSE).getFormattedValue(SMA.AVERAGE)
					+ " " + ema.tick(ph[0].getPriceHistory().get(i), PriceBar.CLOSE).getFormattedValue(SMA.AVERAGE)
					+ " " + rsi.tick(ph[0].getPriceHistory().get(i), PriceBar.CLOSE).getFormattedValue(RSI2.RSI)
					+ " " + adx.tick(ph[0].getPriceHistory().get(i), PriceBar.CLOSE).getFormattedValue(ADX.ADX));

		}


	}


	private void testStrategy() {
		YahooHistoricalDataReader ydr = new YahooHistoricalDataReader();
		PriceHistory[] ph = ydr.getHistoricalStockPrices(
				new String[]{"COKE"}, 
				new DateTime(2012,1,1,0,0,0), new DateTime(2013,10,4,0,0,0), PriceHistory.DAILY);

		Util.pad(ph);

		BollingerStrategy bs = new BollingerStrategy(30, new double[]{2,2});
		
	}


	private void testBollinger() {
		YahooHistoricalDataReader ydr = new YahooHistoricalDataReader();

		PriceHistory[] ph = ydr.getHistoricalStockPrices(
				new String[]{"COKE"}, 
				new DateTime(2012,1,1,0,0,0), new DateTime(2013,10,4,0,0,0), PriceHistory.DAILY);

		Util.pad(ph);

		Bollinger bol = new Bollinger(10, new double[]{2,2});

		for(int i=0;i<ph[0].size();i++) {

			System.out.println(i 
					+ " " + bol.tick(ph[0].getPriceHistory().get(i), PriceBar.CLOSE).getFormattedValue(Bollinger.LOWERBAND)
					+ " " + bol.tick(ph[0].getPriceHistory().get(i), PriceBar.CLOSE).getFormattedValue(Bollinger.MIDPOINT)
					+ " " + bol.tick(ph[0].getPriceHistory().get(i), PriceBar.CLOSE).getFormattedValue(Bollinger.UPPERBAND)
					+ " " + bol.tick(ph[0].getPriceHistory().get(i), PriceBar.CLOSE).getFormattedValue(Bollinger.SIGMA));

		}


	}

	@SuppressWarnings("unused")
	private void dummyData() {

		Vector<PriceBar> dum1 = new Vector<PriceBar>();
		Vector<PriceBar> dum2 = new Vector<PriceBar>();


		dum1.add(new PriceBar("Dummy", new DateTime(2013,5,1,0,0,0),4,5,6,22,8));
		dum1.add(new PriceBar("Dummy", new DateTime(2013,5,2,0,0,0),4,5,6,28,8));
		dum1.add(new PriceBar("Dummy", new DateTime(2013,5,3,0,0,0),4,5,6,23,8));
		dum1.add(new PriceBar("Dummy", new DateTime(2013,5,4,0,0,0),4,5,6,29,8));
		dum1.add(new PriceBar("Dummy", new DateTime(2013,5,5,0,0,0),4,5,6,26,8));
		dum1.add(new PriceBar("Dummy", new DateTime(2013,5,7,0,0,0),4,5,6,27,8));

		dum2.add(new PriceBar("Dummy", new DateTime(2013,5,1,0,0,0),4,5,6,289,8));
		dum2.add(new PriceBar("Dummy", new DateTime(2013,5,3,0,0,0),4,5,6,223,8));
		dum2.add(new PriceBar("Dummy", new DateTime(2013,5,4,0,0,0),4,5,6,245,8));
		dum2.add(new PriceBar("Dummy", new DateTime(2013,5,6,0,0,0),4,5,6,222,8));
		dum2.add(new PriceBar("Dummy", new DateTime(2013,5,7,0,0,0),4,5,6,249,8));
		dum2.add(new PriceBar("Dummy", new DateTime(2013,5,9,0,0,0),4,5,6,298,8));

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
		try {
			MyLogger.setup();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Problems with creating the log files");
		}

		new Driver();
	}

}
