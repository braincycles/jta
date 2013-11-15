package trade;

import java.util.HashMap;
import java.util.Map;

public class PortfolioManager {

	private Map<String, Portfolio> portfolios;


	public PortfolioManager() {

		portfolios = new HashMap<String, Portfolio>();


	}


	public Transaction registerTrade(String portfolioName, Trade t) {
		Transaction transaction = new Transaction();
		if(!TradeIntegrity.checkTradeBasics(t)) {
			transaction.setSuccessful(true);
			transaction.addMessage(BAD_TRADE, "Trade did not meet integrity tests.");
			return transaction;
		}
		Portfolio portfolio = getPortfolio(portfolioName);
		if(portfolio == null) {
			transaction.setSuccessful(false);
			transaction.addMessage(NO_PORTFOLIO, "Portfolio \"" + portfolioName + "\" is not present.");
			return transaction;
		}
		transaction = portfolio.addPosition(t);
		return transaction;
	}




	/* Housekeeping */
	
	public double getPortfolioCash(String name) {
		return getPortfolio(name).getCash();
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

	public Portfolio getPortfolio(String name) {
		return portfolios.get(name);
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



	/* Constants */
	public static int BAD_TRADE = 501;
	public static int NO_PORTFOLIO = 502;

}
