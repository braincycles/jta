package data;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class PriceHistory {

	public static int DAILY = 0;
	public static int WEEKLY = 1;
	public static int MONTLY = 2;

	private int base = DAILY;
	private int counter = 0;
	

	public int getBase() {
		return base;
	}

	public void setBase(int base) {
		this.base = base;
	}
	
	
	public PriceBar tick() {
		return priceHistory.get(counter++);
	}


	private String symbol;
	private Vector<PriceBar> priceHistory;

	public PriceHistory() {
		priceHistory = new Vector<PriceBar>();
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
	
	public void addFirst(PriceBar priceBar) {
		priceHistory.add(0, priceBar);
	}
	
	public void addLast(PriceBar priceBar) {
		priceHistory.add(priceBar);
	}
	
	public PriceBar removeLast() {
		return priceHistory.remove(priceHistory.size()-1);
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
	
	
	public PriceBar getLastPriceBar() {
		return priceHistory.get(priceHistory.size() - 1);
	}

	public PriceBar getFirstPriceBar() {
		return priceHistory.get(0);
	}
	
	public List<PriceBar> getAll() {
		return priceHistory;
	}
	
	public int size() {
		return priceHistory.size();
	}
	
	public PriceBar getPriceBar(int index) {
		return priceHistory.get(index);
	}

	
	public List<PriceBar> getPriceBars() {
		return priceHistory;
	}
	
	
	public double[] getAll(int type) {
		List<PriceBar> bars = getAll();
		double[] data = new double[bars.size()];
		for(int i=0;i<data.length;i++) {
			if(type == PriceBar.OPEN)
				data[i] = bars.get(i).getOpen();
			if(type == PriceBar.CLOSE)
				data[i] = bars.get(i).getClose();
			if(type == PriceBar.HIGH)
				data[i] = bars.get(i).getHigh();
			if(type == PriceBar.LOW)
				data[i] = bars.get(i).getLow();
		}
		return data;
	}
	
	public double[] getAllReturns(int type) {
		List<PriceBar> bars = getAll();
		double[] data = new double[bars.size()];
		for(int i=1;i<data.length;i++) {
			if(type == PriceBar.OPEN)
				data[i] = bars.get(i).getOpen()-bars.get(i-1).getOpen();
			if(type == PriceBar.CLOSE)
				data[i] = bars.get(i).getClose()-bars.get(i-1).getClose();
			if(type == PriceBar.HIGH)
				data[i] = bars.get(i).getHigh()-bars.get(i-1).getHigh();
			if(type == PriceBar.LOW)
				data[i] = bars.get(i).getLow()-bars.get(i-1).getLow();
		}
		return data;
	}


}



