package data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



/**
 * Holds and validates the priceBar history for a strategy.
 */
public class QuoteHistory extends PriceHistory {

	private static final String lineSep = System.getProperty("line.separator");
	
	public static int BUY =  10;
	public static int SELL = -10;
	
	public static int OPEN = 1;
	public static int CLOSE = 2;
	public static int HIGH = 3;
	public static int LOW = 4;

	private final List<PriceBar> priceBars;
	private final List<String> validationMessages;
	private final String quoteName;
	private boolean isHistRequestCompleted;
	private boolean isForex;
	private double priceAction;


	public QuoteHistory(String quoteName) {
		this.quoteName = quoteName;
		priceBars = new ArrayList<PriceBar>();
		validationMessages = new ArrayList<String>();
	}



	public QuoteHistory() {
		this("BackDataDownloader");
	}


	public void setIsForex(boolean isForex) {
		this.isForex = isForex;
	}

	public boolean getIsForex() {
		return isForex;
	}

	


	public String getQuoteName() {
		return quoteName;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (PriceBar priceBar : priceBars) {
			sb.append(priceBar).append(lineSep);
		}

		return sb.toString();
	}

	public boolean isValid() {
		// TODO: validate quote history
		boolean isValid = true;
		validationMessages.clear();
		return isValid;
	}
	
	public void reverseData() {
		Collections.reverse(priceBars);
	}

	public List<String> getValidationMessages() {
		return validationMessages;
	}

	

	public void addHistoricalPriceBar(PriceBar priceBar) {
		priceBars.add(priceBar);
	}

	

	public boolean getIsHistRequestCompleted() {
		return isHistRequestCompleted;
	}

	
	
	
	
	
	

	public double getPriceAction() {
		return priceAction;
	}

	public void setPriceAction(double priceAction) {
		this.priceAction = priceAction;
	}
	
}
