package data;

import java.text.DecimalFormat;

import org.joda.time.DateTime;


public class PriceBar implements Comparable<PriceBar> {
	DecimalFormat df = new DecimalFormat("+0.000;-0.000");
	
	public final static int OPEN = 100;
	public final static int CLOSE = 101;
	public final static int HIGH = 102;
	public final static int LOW = 103;
	public final static int RETURNS = 104;

	private String symbol;
	private DateTime date;
	private double volume;
	private double high;
	private double low;
	private double open;
	private double close;
	private double ret;
	
	private int dayNumber;
	private int overUnderSold = 0;
	private int priceAction = 0;


	@Override
	public boolean equals(Object obj) {
		return ((PriceBar) obj).getDate().toLocalDate().isEqual(date.toLocalDate());
	}

	public PriceBar(String symbol, double open, double high, double low, double close, double volume) {
		super();
		this.symbol = symbol;
		this.high = high;
		this.low = low;
		this.open = open;
		this.close = close;
		this.volume = volume;
		this.ret = 0;
		
		df = new DecimalFormat("+0.000;-0.000");
	}
	
	
	public double getFormattedPrice(int type) {
		switch(type) {
		  case CLOSE: 
			  return Double.parseDouble(df.format(getClose()));
		  case OPEN: 
			  return Double.parseDouble(df.format(getOpen()));
		  case HIGH: 
			  return Double.parseDouble(df.format(getHigh()));
		  case LOW: 
			  return Double.parseDouble(df.format(getLow()));
		  case RETURNS: 
			  return Double.parseDouble(df.format(getRet()));
		  default:
			  return Double.parseDouble(df.format(getClose()));
		}
	}
	
	
	public double getPrice(int type) {
		switch(type) {
		  case CLOSE: 
			  return getClose();
		  case OPEN: 
			  return getOpen();
		  case HIGH: 
			  return getHigh();
		  case LOW: 
			  return getLow();
		  case RETURNS: 
			  return getRet();
		  default:
			  return getClose();
		}
	}

	public double getRet() {
		return ret;
	}

	public void setRet(double ret) {
		this.ret = ret;
	}

	public PriceBar(String symbol, DateTime date, double open, double high, double low, double close, double volume) {
		super();
		this.symbol = symbol;
		this.high = high;
		this.low = low;
		this.open = open;
		this.close = close;
		this.date = date;
		this.volume = volume;
		this.ret = 0;
	}
	
	

	public PriceBar(int dayNumber, double open, double high, double low, double close, double volume) {
		super();
		this.dayNumber = dayNumber;
		this.low = low;
		this.open = open;
		this.close = close;
		this.volume = volume;
		this.ret = 0;
	}


	public int getDayNumber() {
		return dayNumber;
	}




	public void setDayNumber(int dayNumber) {
		this.dayNumber = dayNumber;
	}




	public double getHigh() {
		return high;
	}
	public void setHigh(double high) {
		this.high = high;
	}
	public double getLow() {
		return low;
	}
	public void setLow(double low) {
		this.low = low;
	}
	public double getOpen() {
		return open;
	}
	public void setOpen(double open) {
		this.open = open;
	}
	public double getClose() {
		return close;
	}
	public void setClose(double close) {
		this.close = close;
	}
	public DateTime getDate() {
		return date;
	}
	public void setDate(DateTime date) {
		this.date = date;
	}

	public long getDateLong() {
		return date.getMillis();
	}
	
	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public int getOverUnderSold() {
		return overUnderSold;
	}

	public void setOverUnderSold(int overUnderSold) {
		this.overUnderSold = overUnderSold;
	}

	public void changeOverUnder(int amount) {
		this.overUnderSold = this.overUnderSold + amount;
	}

	public int getPriceAction() {
		return priceAction;
	}

	public void setPriceAction(int priceAction) {
		this.priceAction = priceAction;
	}

	public int compareTo(PriceBar o) {
		return date.compareTo(o.getDate());
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public void setDecimalFormat(DecimalFormat df) {
		this.df = df;
	}

	@Override
	public String toString() {
		return "PriceBar: " + getSymbol();
	}





}
