package data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
	private List<PriceBar> priceHistory;
	
	public PriceHistory(String symbol, int base) {
		this.symbol = symbol;
		this.base = base;
		priceHistory = new ArrayList<PriceBar>();
	}
	
	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public PriceHistory(List<PriceBar> _priceHistory) {
		this.priceHistory = _priceHistory;
	}
	
	
	public int addPriceBar(PriceBar priceBar) {
		priceHistory.add(priceBar);
		return priceHistory.size();
	}
	
	
	public List<PriceBar> getPriceHistory() {
		return priceHistory;
	}
	
	public void setPriceHistory(List<PriceBar> _priceHistory) {
		this.priceHistory = _priceHistory;
	}
	
	
	public void reverseData() {
		Collections.reverse(priceHistory);
	}
	

}
