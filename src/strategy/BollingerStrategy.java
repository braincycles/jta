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
		super("Bollinger Strategy");
		this.boll = new Bollinger(period, new double[]{multiple, multiple});
	}
	
	public BollingerStrategy(int period, double[] multiple) {
		super("Bollinger Strategy");
		this.boll = new Bollinger(period, multiple);
	}
	

	@Override
	public IndicatorValue tick(PriceBar priceBar, int type) {
		
		double price = priceBar.getPrice(type);
		IndicatorValue value = boll.tick(priceBar, type);
		
		if(isValid() == false)
			return new IndicatorValue(getName(), priceBar.getDate(), new double[]{0}, getName() + " not valid");
		
		double upperBand = value.getValue(Bollinger.UPPERBAND);
		double lowerBand = value.getValue(Bollinger.LOWERBAND);
		double midBand = value.getValue(Bollinger.MIDPOINT);
		
		double midToUpper = upperBand - midBand;
		double midToLower = midBand - lowerBand;
		
		double distFromMid = Math.abs(price - midBand);
		
		double normDistToUpper = 0.0;
		double normDistToLower = 0.0;
		
		if(price>=midBand) {
			if(price <= upperBand) //Within upper band
				normDistToUpper = 1-(midToUpper-distFromMid)/midToUpper;
			else
				normDistToUpper = 1+(distFromMid-midToUpper)/midToUpper;
		}
		else {
			if(price >= lowerBand) //Within lower band
				normDistToLower = 1-(midToLower-distFromMid)/midToLower;
			else
				normDistToLower = 1+(distFromMid-midToLower)/midToLower;
		}
		
		//System.out.println(normDistToUpper + " " + upperBand + " " + price + " " + lowerBand + " " + normDistToLower + " " + (price>upperBand));
		
		if(normDistToUpper >= upperBandThreshold && price>=midBand) {
			setWeight(price/upperBand);
			return new IndicatorValue(getName(), priceBar.getDate(), new double[]{SELL_SIGNAL*getWeight()}, "Hit top Bollinger band");
		}
		else if(normDistToLower > lowerBandThreshold && price >= lowerBand) {
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


	public void setBandThresholds(double[] bandThresholds) {
		this.lowerBandThreshold = bandThresholds[0];
		this.upperBandThreshold = bandThresholds[1];
	}

	
	
	
	
}
