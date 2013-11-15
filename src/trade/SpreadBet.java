package trade;

import org.joda.time.DateTime;

public class SpreadBet extends Trade{

	private boolean active;
	private int base;
	private int initialPoints;
	private Spread spread;
	private double bet;
	private double stopLossPrice;
	private double takeProfitPrice;

	public SpreadBet(DateTime date, Stock stock, int type, double bet, double initialPrice, int base, Spread spr, double stopLossPrice, double takeProfitPrice) {
		super(date, stock, );
		this.tradeType = type;
		this.stopLossPrice = stopLossPrice;
		this.takeProfitPrice = takeProfitPrice;
		this.base = base;
		this.spread = spr;
		this.bet = bet;	
		this.price = price;
		this.initialPoints = (int) ((base * initialPrice) + spread.getSpread(type));
		this.active = true;
	}
	
	public SpreadBet(int type, double bet, double initialPrice, int base, Spread spr, int stopLossPoints, int takeProfitPoints) {
		this(type, bet, initialPrice, base, spr, ((double)stopLossPoints/base), ((double)takeProfitPoints/base));
	}

	
	@Override
	public double getCurrentValue(double price) {
		return priceInPoints(price) - initialPoints;
	}
	
	public double closeProfit() {
		setActive(false);
		return (priceInPoints(takeProfitPrice)-initialPoints)*bet;
	}
	
	public double closeLoss() {
		setActive(false);
		return (priceInPoints(stopLossPrice)-initialPoints)*bet;
	}
	
	@Override
	public double close(double price) {
		setActive(false);
		return (priceInPoints(price) - initialPoints)*bet*this.tradeType;
	}

	public int priceInPoints(double price) {
		return (int)(price*base);
	}

	public int getBase() {
		return base;
	}

	public void setBase(int base) {
		this.base = base;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}




	public double getStopLossPrice() {
		return stopLossPrice;
	}




	public void setStopLossPrice(double stopLossPrice) {
		this.stopLossPrice = stopLossPrice;
	}




	public double getTakeProfitPrice() {
		return takeProfitPrice;
	}




	public void setTakeProfitPrice(double takeProfitPrice) {
		this.takeProfitPrice = takeProfitPrice;
	}

	@Override
	public double getCostOfTrade() {
		// TODO Auto-generated method stub
		return 0;
	}






}
