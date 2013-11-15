package trade;

import org.joda.time.DateTime;

public class StockTrade extends Trade {

	private double fee;
	
	public StockTrade(DateTime date, Stock stock, int amount, double price, double fee) throws Exception {
		super(date, stock, amount, price);
		this.fee = fee;
	}

	
	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}


	@Override
	public double getCostOfTrade() {
		return fee+(price*amount);
	}
	
	
	

}
