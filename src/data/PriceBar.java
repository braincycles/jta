package data;

import org.joda.time.DateTime;


public class PriceBar {

	private DateTime date;
	private double volume;
	private double high;
	private double low;
	private double open;
	private double close;
	private int dayNumber;
	private int overUnderSold = 0;
	private int priceAction = 0;


	public PriceBar(double open, double high, double low, double close, double volume) {
		super();
		this.high = high;
		this.low = low;
		this.open = open;
		this.close = close;
		this.volume = volume;
	}

	public PriceBar(DateTime date, double open, double high, double low, double close, double volume) {
		super();
		this.high = high;
		this.low = low;
		this.open = open;
		this.close = close;
		this.date = date;
		this.volume = volume;
	}

	public PriceBar(int dayNumber, double open, double high, double low, double close, double volume) {
		super();
		this.dayNumber = dayNumber;
		this.low = low;
		this.open = open;
		this.close = close;
		this.volume = volume;
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






}
