package indicators;


import data.QuoteHistory;
import math.Stats;

public class Bollinger  extends Indicator  {

	private final int period;
	private final double multiple;
	private double mean, sigma;

	public Bollinger(QuoteHistory qh, int p) {
		this(qh, p, 1.0);
	}

	public Bollinger(QuoteHistory qh, int p, double multiple) {
		super(qh);
		this.period = p;
		this.multiple = multiple;
	}



	@Override
	public double calculate(int upto) {

		if(!isValid(upto)) return 0;

		double mean = Stats.mean(qh, upto, period, QuoteHistory.CLOSE);
		double sd = Stats.sd(qh, upto, period, QuoteHistory.CLOSE);

		this.mean = mean;
		this.sigma = sd;
		
		return getUpperBand() - getLowerBand(); 
	}

	/**
	 * This returns the mean of the sample of prices and is the same as the midpoint of the Bollinger Bands
	 * 
	 * @return Midpoint of the Bollinger Bands or the mean of the prices sampled.
	 */
	 public double getMidpoint() {
		return mean;
	}

	/**
	 * @return The upper band of the Bollinger Bands specified by the period and multiple.
	 */
	 public double getUpperBand() {
		 return mean + sigma * multiple;
	 }

	 /**
	  * @return The lower band of the Bollinger Bands specified by the period and multiple.
	  */
	 public double getLowerBand() {
		 return mean - sigma * multiple;
	 }

	 @Override
	 public boolean isValid(int upto) {
		 return (qh.size() > period) && (upto>=period);
	 }

}
