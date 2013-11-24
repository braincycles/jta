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
	private double upperBandThreshold = 0.8;
	private double lowerBandThreshold = 0.8;
	
	public BollingerStrategy(int period, double multiple) {
		this(period, new double[]{multiple, multiple});
	}
	
	public BollingerStrategy(int period, double[] multiple) {
		super("Bollinger Strategy");
		this.boll = new Bollinger(period, multiple);
	}
	

	@Override
	public IndicatorValue tick(PriceBar priceBar, int type) {
		
		double price = priceBar.getPrice(type);
		IndicatorValue bollValue = boll.tick(priceBar, type);
		
		if(isValid() == false)
			return new IndicatorValue(getName(), priceBar.getDate(), new double[]{0}, getName() + " not valid");
		
		double upperBand = bollValue.getValue(Bollinger.UPPERBAND);
		double lowerBand = bollValue.getValue(Bollinger.LOWERBAND);
		double midBand = bollValue.getValue(Bollinger.MIDPOINT);
		
		double value = 0.0;
		if(price>=midBand) {
			value = getPercentInRange(midBand, upperBand, price);
			setWeight(value);
		}
		else {
			value = getPercentInRange(midBand, lowerBand, price);
			setWeight(value);
		}
		
		if(value >= upperBandThreshold && price>=midBand) {
			
			return new IndicatorValue(getName(), priceBar.getDate(), new double[]{SELL_SIGNAL*getWeight()}, "Hit top Bollinger band");
		}
		else if(value > lowerBandThreshold && price >= lowerBand) {
			return new IndicatorValue(getName(), priceBar.getDate(), new double[]{BUY_SIGNAL*getWeight()}, "Hit bottom Bollinger band");
		}
		else
			return new IndicatorValue(getName(), priceBar.getDate(), new double[]{0}, "");
	}
	
	
	private double getPercentInRange(double low, double high, double value) {
		return (value-low)/(high-low);
	}


	@Override
	public boolean isValid() {
		return boll.isValid();
	}


	public void setBandThresholds(double[] bandThresholds) {
		this.lowerBandThreshold = bandThresholds[0];
		this.upperBandThreshold = bandThresholds[1];
	}
	
}
