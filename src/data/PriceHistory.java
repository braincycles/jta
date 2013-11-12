package data;

import java.util.Collections;
import java.util.Vector;

public class PriceHistory {

	public static int DAILY = 0;
	public static int WEEKLY = 1;
	public static int MONTLY = 2;

	private int base = DAILY;

	public int getBase() {
		return base;
	}

	public void setBase(int base) {
		this.base = base;
	}


	private String symbol;
	private Vector<PriceBar> priceHistory;

	public PriceHistory() {
		
	}
	
	public PriceHistory(String symbol, int base) {
		this.symbol = symbol;
		this.base = base;
		priceHistory = new Vector<PriceBar>();
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public PriceHistory(Vector<PriceBar> _priceHistory) {
		this.priceHistory = _priceHistory;
	}


	public int addPriceBar(PriceBar priceBar) {
		priceHistory.add(priceBar);
		return priceHistory.size();
	}


	public Vector<PriceBar> getPriceHistory() {
		return priceHistory;
	}

	public void setPriceHistory(Vector<PriceBar> _priceHistory) {
		this.priceHistory = _priceHistory;
	}


	public void reverseData() {
		Collections.reverse(priceHistory);
	}

	public void calculateReturns(int price, boolean percentage) {
		double lastValue = 0;
		boolean isFirst = true;

		for(PriceBar pb : priceHistory) {

			double currentValue = pb.getPrice(price);
			
			if(percentage) {
				double ret = isFirst? 0 : (currentValue-lastValue)/lastValue;
				pb.setRet(ret);
			}
			else {
				double ret = isFirst? 0 : currentValue-lastValue;
				pb.setRet(ret);
			}
			lastValue = currentValue;
			isFirst = false;
		}
	}
	
	
	public static void calculateReturns(PriceHistory[] phi, int price, boolean percentage) {
		for(PriceHistory ph : phi) {
			ph.calculateReturns(price, percentage);
		}
	}

}



