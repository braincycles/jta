package strategy;

import data.PriceBar;

import indicators.IndicatorValue;

public class LimitsStrategy extends AbstractStrategy {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1004561769597398182L;
	
	private double initialPrice;
	private double takeProfit;
	private double stopLoss;
	
	private boolean firstValue = true;


	public LimitsStrategy(double takeProfit, double stopLoss) {
		super("Limits Strategy");
		this.takeProfit = takeProfit;
		this.stopLoss = stopLoss;
	}
	

	@Override
	public IndicatorValue tick(PriceBar priceBar, int type) {
		double price = priceBar.getPrice(type);
		
		if(firstValue) {
			initialPrice = price;
			firstValue = false;
		}
		
		
		
		if(price <= initialPrice*(1-stopLoss))
			return new IndicatorValue(getName(), priceBar.getDate(), new double[]{SELL_SIGNAL*getWeight()}, "Stop loss reached.");
		else if(price >= initialPrice*(1+takeProfit))
			return new IndicatorValue(getName(), priceBar.getDate(), new double[]{SELL_SIGNAL*getWeight()}, "Take profit limit reached");
		else
			return new IndicatorValue(getName(), priceBar.getDate(), new double[]{0}, "");
	}


	@Override
	public boolean isValid() {
		return true;
	}

}
