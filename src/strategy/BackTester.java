package strategy;

import indicators.Indicator;
import indicators.IndicatorValue;

import java.util.ArrayList;
import java.util.List;

import portfolio.Portfolio;
import portfolio.PortfolioManager;

import trade.Stock;
import trade.StockTrade;
import trade.Trade;
import trade.TradeManager;
import util.DeepCopy;

import data.PriceBar;
import data.PriceHistory;

public class BackTester {
	List<PriceHistory> priceHistories;
	List<Indicator> indicators;
	List<String> indicatorsStr;
	
	private double tradingFee = 0.0;
	private String portfolioName;
	
	private double buyThreshold = 0;
	private double sellThreshold = 0;
	
	
	private PortfolioManager portman;

	int type = PriceBar.CLOSE;


	public BackTester() {
		priceHistories = new ArrayList<PriceHistory>();
		indicators = new ArrayList<Indicator>();
		portman = new PortfolioManager();
	}

	public BackTester(List<PriceHistory> priceHistories, List<Indicator> indicators) {
		this();
		this.priceHistories = priceHistories;
		this.indicators = indicators;
	}

	public BackTester(PriceHistory[] priceHistories, Indicator[] indicators) {
		this();
		addPriceHistories(priceHistories);
		addIndicators(indicators);
	}
	
	
	public Portfolio setupPortfolio (String name, double cash, int concurrentPositions) {
		this.portfolioName = name;
		Portfolio portfolio =  portman.createPortfolio(name, cash);
		portfolio.setAllowedConcurrentPositions(concurrentPositions);
		return portfolio;
	}


	private Indicator[][] setUpIndicators() {
		Indicator[][] indicatorArr = new Indicator[priceHistories.size()][indicators.size()];
		DeepCopy<Indicator> dc = new DeepCopy<Indicator>();

		for(int ph = 0;ph<priceHistories.size();ph++)
			for(int i =0;i<indicators.size();i++)
				indicatorArr[ph][i] = dc.copy(indicators.get(i));

		return indicatorArr;
	}

	

	
	public void run() {
		System.out.println("Portfolio cash: " + portman.getPortfolioCash(portfolioName));
		System.out.println("---------------------------------------------");
		
		try {
		Indicator[][] inds = setUpIndicators();

		for(int counter=0;counter<100;counter++) {
			
			IndicatorValue[][] data = getData(counter,inds);

			PriceBar[] bars = getBars(counter);	

			//Loop over bars
			for(int bar = 0; bar< bars.length;bar++) {
				portman.checkLimits(portfolioName, bars[bar], type, tradingFee);
				portman.updatePortfolio(portfolioName, bars, type);
				String[] messages = new String[indicators.size()];
				
				double netStrategyOutcome = 0;
				//Loop over indicators for this pricehistory and sum signal outputs from strategies
				
				for(int ind =0;ind<data[bar].length;ind++) {
					IndicatorValue strategyOutcome = data[bar][ind];
					netStrategyOutcome = netStrategyOutcome + strategyOutcome.getValue(AbstractStrategy.SIGNAL);
					messages[ind] = strategyOutcome.getMessage();
				}
				
				System.out.println(bars[bar].getClose() + " " + netStrategyOutcome);
				
				if(netStrategyOutcome > buyThreshold) {
					int amount = TradeManager.getAmountByPercentage(portman.getPortfolioCash(portfolioName), bars[bar].getClose(), 0.5);
					Trade t = new StockTrade(bars[bar].getDate(),new Stock(bars[bar].getSymbol()),amount, bars[bar].getPrice(type), tradingFee);
					portman.registerTrade(portfolioName, t, messages);
				}
				if(netStrategyOutcome < sellThreshold) {
					//Set amount to 0. This means sell all
					Trade t = new StockTrade(bars[bar].getDate(),new Stock(bars[bar].getSymbol()),0, bars[0].getPrice(type), tradingFee);
					portman.registerTrade(portfolioName, t, messages);
				}
				
			
			}
		}
		
		System.out.println("---------------------------------------------");
		System.out.println("Portfolio worth: " + portman.getPortfolioWorth(portfolioName));

		
		} catch(Exception e) {
			e.printStackTrace();
		}


	}



	public IndicatorValue[][] getData(int counter, Indicator[][] inds) {
		PriceBar[] bars = getBars(counter);
		IndicatorValue[][] data = new IndicatorValue[priceHistories.size()][indicators.size()];

		//Loop over bars
		for(int b = 0; b< bars.length;b++) 
			//Loop over indicators for this pricehistory
			for(int ind =0;ind<inds[b].length;ind++)
				data[b][ind] = inds[b][ind].tick(bars[b], type);

		return data;
	}




	private PriceBar[] getBars(int index) {
		int i = 0;
		PriceBar[] bars = new PriceBar[priceHistories.size()];
		for(PriceHistory ph : priceHistories)
			bars[i++]=ph.getPriceBar(index);
		return bars;
	}


	public void addPriceHistories(PriceHistory ... ph){
		for(PriceHistory phin : ph)
			priceHistories.add(phin);
	}


	public void addIndicators(Indicator ... ind){
		for(Indicator indin : ind)
			indicators.add(indin);
	}

	public double getTradingFee() {
		return tradingFee;
	}
	
	
	public void setLimits(double stopLoss, double takeProfit) {
		portman.setLimits(stopLoss, takeProfit);
	}
	
	public void setThresholds(double buyThreshold, double sellThreshold) {
		this.buyThreshold = buyThreshold;
		this.sellThreshold = -sellThreshold;
	}

}
