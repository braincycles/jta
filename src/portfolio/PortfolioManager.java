package portfolio;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import logger.MyLogger;

import data.PriceBar;

import trade.Stock;
import trade.StockTrade;
import trade.Trade;
import trade.TradeIntegrity;
import trade.Transaction;

public class PortfolioManager {
	private final static Logger LOGGER = Logger.getLogger(MyLogger.class.getName());

	private Map<String, Portfolio> portfolios;

	private boolean allowedShortSelling = false;
	private boolean addToExisitingPositions = false;
	private double stopLoss = 0.05;
	private double takeProfit = 0.1;


	public PortfolioManager() {
		portfolios = new HashMap<String, Portfolio>();
		setLoggingLevel(Level.SEVERE);
	}

	public void setLimits(double stopLoss, double takeProfit) {
		this.stopLoss = stopLoss;
		this.takeProfit = takeProfit;
	}

	public void setLoggingLevel(Level level) {
		LOGGER.setLevel(level);
	}

	public Transaction registerTrade(String portfolioName, Trade t) {
		return registerTrade(portfolioName, t, new String[]{""});
	}

	public Transaction registerTrade(String portfolioName, Trade t, String[] messages) {
		Transaction transaction = new Transaction(t, portfolioName);
		Portfolio portfolio = getPortfolio(portfolioName);

		if(!TradeIntegrity.checkTradeBasics(t))
			transaction.setSuccessful(false, BAD_TRADE, "Trade did not meet integrity tests.");

		if(t.getType() == Trade.SELL) {
			if(getPortfolio(portfolioName).checkForPosition(t.getStock()) == false && allowedShortSelling == false)
				transaction.setSuccessful(false, SHORT_SELLING_NOT_ALLOWED, "Short selling is not allowed.");
			else {
				transaction = portfolio.sellPosition(t);
				transaction.addMessages(INFO, messages);
			}
		}
		else { //BUY
			if(portfolio == null)
				transaction.setSuccessful(false, NO_PORTFOLIO, "Portfolio \"" + portfolioName + "\" is not present.");
			else if(getPortfolio(portfolioName).checkForPosition(t.getStock()) == true && addToExisitingPositions == false)
				transaction.setSuccessful(false, CANNOT_ADD_TO_EXISITING_POSITION, "Cannot add to exisiting position.");
			else {
				transaction = portfolio.addPosition(t);
				transaction.addMessages(INFO, messages);
			}
		}
		printTrade(transaction);
		return transaction;
	}


	public void printTrade(Transaction transaction) {
		Trade trade = transaction.getTrade();

		if(transaction.isSuccessful()) {
			if(trade.getType() == Trade.BUY) {
				System.out.println(trade.getDate() + " Bought " + trade.getSymbol() + " @ " + trade.getPrice() + " {" + getPortfolioWorth(transaction.getPortfolioName()) + "} " + transaction.getMessages());
			}
			else {
				System.out.println(trade.getDate() + " Sold " + trade.getSymbol() + " @ " + trade.getPrice() + " {" + getPortfolioWorth(transaction.getPortfolioName()) + "} " + transaction.getMessages());	
			}


		}
		else {
			
			
		}




	}



	public void checkLimits(String portfolioName, PriceBar priceBar, int type, double tradingFee) throws Exception {

		double price = priceBar.getPrice(type);
		Stock stock = new Stock(priceBar.getSymbol());
		Portfolio portfolio = getPortfolio(portfolioName);

		if(portfolio.checkForPosition(stock)) {
			double initialPrice = portfolio.getPosition(stock).getInitialPrice();

			if(price <= initialPrice*(1-stopLoss)){
				Trade t = new StockTrade(priceBar.getDate(),new Stock(priceBar.getSymbol()), 0, priceBar.getPrice(type), tradingFee);
				registerTrade(portfolioName, t, new String[]{"Stop loss"});
			}
			else if(price >= initialPrice*(1+takeProfit)){
				Trade t = new StockTrade(priceBar.getDate(),new Stock(priceBar.getSymbol()), 0, priceBar.getPrice(type), tradingFee);
				registerTrade(portfolioName, t, new String[]{"Take profit"});
			}
		}
	}




	/* Housekeeping */

	public Portfolio createPortfolio(String name, double cash) {
		Portfolio portfolio = new Portfolio(name, cash);
		portfolios.put(portfolio.getName(), portfolio);
		return portfolio;
	}

	public double getPortfolioCash(String name) {
		return getPortfolio(name).getCash();
	}

	public double getPortfolioWorth(String name) {
		Portfolio portfolio = getPortfolio(name);
		double cash = portfolio.getCash();
		double positionValue = portfolio.getTotalWorth();
		return cash+positionValue;
	}


	public void updatePortfolio(String portfolioName, PriceBar[] priceBars, int type) {
		Portfolio portfolio = getPortfolio(portfolioName);

		if(portfolio.isEmpty() == false) {
			for(PriceBar priceBar : priceBars) {
				Position position = portfolio.getPosition(priceBar.getSymbol());
				if(position.isEmpty() == false)
					position.setCurrentPrice(priceBar.getPrice(type));
			}
		}
		else 
			LOGGER.severe("No portfolio named: " + portfolioName);
	}

	public int getNumberOfPortfolios() {
		return portfolios.size();
	}

	public Portfolio removePortfolio(Portfolio portfolio) {
		return portfolios.remove(portfolios.get(portfolio).getName());
	}

	public Portfolio removePortfolio(String name) {
		return portfolios.remove(name);
	}


	public void addPortfolio(Portfolio ... portfolio) {
		for(Portfolio p : portfolio) 
			portfolios.put(p.getName(), p);
	}

	public Portfolio getPortfolio(String portfolioName) {
		if(checkForPortfolio(portfolioName))
			return portfolios.get(portfolioName);
		else 
			return new Portfolio();
	}

	public boolean checkForPortfolio(String portfolioName) {
		return portfolios.containsKey(portfolioName);
	}

	public Portfolio getPortfolio(int index) {
		return portfolios.get(portfolios.keySet().toArray()[index]);
	}

	public Map<String, Portfolio> getPortfolios() {
		return portfolios;
	}

	public void setPortfolios(Map<String, Portfolio> portfolios) {
		this.portfolios = portfolios;
	}


	public void setConcurrentPositions(String name, int concurrentPositions) {
		portfolios.get(name).setAllowedConcurrentPositions(concurrentPositions);
	}




	public boolean isAllowedShortSelling() {
		return allowedShortSelling;
	}


	public void setAllowedShortSelling(boolean allowedShortSelling) {
		this.allowedShortSelling = allowedShortSelling;
	}






	public boolean canAddToExisitingPositions() {
		return addToExisitingPositions;
	}


	public void setAddToExisitingPositions(boolean addToExisitingPositions) {
		this.addToExisitingPositions = addToExisitingPositions;
	}






	/* Constants */
	public static int INFO = 505;
	public static int BAD_TRADE = 501;
	public static int NO_PORTFOLIO = 502;
	public static int SHORT_SELLING_NOT_ALLOWED = 503;
	public static int CANNOT_ADD_TO_EXISITING_POSITION = 504;

}
