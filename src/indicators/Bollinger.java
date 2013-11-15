package indicators;

import data.PriceBar;
import data.PriceHistory;

public class Bollinger  extends Indicator  {
	public static int UPPERBAND = 0;
	public static int LOWERBAND = 1;
	public static int MIDPOINT = 2;
	public static int SIGMA = 3;

	private final int period;
	private final double multiple;
	private PriceHistory values;
	private Indicator average;
	private Indicator sd;

	public Bollinger(int period) {
		this(period, 1.0);
		values = new PriceHistory();
	}

	public Bollinger(int p, double multiple) {
		super();
		this.period = p;
		this.multiple = multiple;
		values = new PriceHistory();
		sd = new SD(period);
		average = new SMA(period);
	}



	@Override
	public IndicatorValue tick(PriceBar pb, int type) {
		values.addFirst(pb);
		if(values.size()>period) 
			values.removeLast();
		
		double mean = average.tick(pb, type).getValue(SMA.AVERAGE);
		double sigma = sd.tick(pb, type).getValue(SD.SIGMA);
		
		double upperband = mean + sigma * multiple;
		double lowerband = mean - sigma * multiple;
		double[] result = new double[]{upperband, lowerband, mean, sigma};
		
		return new IndicatorValue(pb.getDate(),result); 
	}
	

	 @Override
	 public boolean isValid() {
		 return average.isValid() && sd.isValid();
	 }

}
