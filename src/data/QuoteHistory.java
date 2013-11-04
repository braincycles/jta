package data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



/**
 * Holds and validates the priceBar history for a strategy.
 */
public class QuoteHistory {

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

	public List<PriceBar> getPriceBars() {
		return priceBars;
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

	public List<PriceBar> getAll() {
		return priceBars;
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

	public int size() {
		return priceBars.size();
	}

	public void addHistoricalPriceBar(PriceBar priceBar) {
		priceBars.add(priceBar);
	}

	public PriceBar getPriceBar(int index) {
		return priceBars.get(index);
	}

	public int getSize() {
		return priceBars.size();
	}

	public boolean getIsHistRequestCompleted() {
		return isHistRequestCompleted;
	}

	public PriceBar getLastPriceBar() {
		return priceBars.get(priceBars.size() - 1);
	}

	public PriceBar getFirstPriceBar() {
		return priceBars.get(0);
	}
	
	
	public double[] getAll(int type) {
		List<PriceBar> bars = getAll();
		double[] data = new double[bars.size()];
		for(int i=0;i<data.length;i++) {
			if(type == OPEN)
				data[i] = bars.get(i).getOpen();
			if(type == CLOSE)
				data[i] = bars.get(i).getClose();
			if(type == HIGH)
				data[i] = bars.get(i).getHigh();
			if(type == LOW)
				data[i] = bars.get(i).getLow();
		}
		return data;
	}
	
	public double[] getAllReturns(int type) {
		List<PriceBar> bars = getAll();
		double[] data = new double[bars.size()];
		for(int i=1;i<data.length;i++) {
			if(type == OPEN)
				data[i] = bars.get(i).getOpen()-bars.get(i-1).getOpen();
			if(type == CLOSE)
				data[i] = bars.get(i).getClose()-bars.get(i-1).getClose();
			if(type == HIGH)
				data[i] = bars.get(i).getHigh()-bars.get(i-1).getHigh();
			if(type == LOW)
				data[i] = bars.get(i).getLow()-bars.get(i-1).getLow();
		}
		return data;
	}

	public double getPriceAction() {
		return priceAction;
	}

	public void setPriceAction(double priceAction) {
		this.priceAction = priceAction;
	}
	
}
