package strategy;

import data.PriceBar;

import indicators.Bollinger;
import indicators.IndicatorValue;

public class BollingerStrategy extends AbstractStrategy {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1610284705361986653L;
	private Bollinger boll;
	
	public BollingerStrategy(int period, double[] multiple) {
		super("Bollinger Strategy");
		this.boll = new Bollinger(period, multiple);
	}
	

	@Override
	public IndicatorValue tick(PriceBar priceBar, int type) {
		
		double price = priceBar.getPrice(type);
		IndicatorValue value = boll.tick(priceBar, type);
		double upperBand = value.getValue(Bollinger.UPPERBAND);
		double lowerBand = value.getValue(Bollinger.LOWERBAND);
		
		if(price > upperBand) {
			setWeight(price/upperBand);
			return new IndicatorValue(getName(), priceBar.getDate(), new double[]{SELL_SIGNAL*getWeight()}, "Hit top Bollinger band");
		}
		else if(price < lowerBand) {
			setWeight(price/lowerBand);
			return new IndicatorValue(getName(), priceBar.getDate(), new double[]{BUY_SIGNAL*getWeight()}, "Hit bottom Bollinger band");
		}
		else
			return new IndicatorValue(getName(), priceBar.getDate(), new double[]{0}, "");
	}
	
	



	@Override
	public boolean isValid() {
		return boll.isValid();
	}

}
