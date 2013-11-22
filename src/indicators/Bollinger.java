package indicators;

import data.PriceBar;

public class Bollinger  extends Indicator  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5573183814020491332L;
	public static int UPPERBAND = 0;
	public static int LOWERBAND = 1;
	public static int MIDPOINT = 2;
	public static int SIGMA = 3;

	private final int period;
	private final double[] multiple;
	private Indicator average;
	private Indicator sd;

	public Bollinger(int period) {
		this(period, new double[]{2.0, 2.0});
	}

	public Bollinger(int p, double[] multiple) {
		super("BOLL"+ p + "," + multiple + ")");
		this.period = p;
		this.multiple = multiple;
		sd = new SD(period);
		average = new SMA(period);
	}



	@Override
	public IndicatorValue tick(PriceBar pb, int type) {
		updateValues(pb, period);
		
		double ave = average.tick(pb, type).getValue(SMA.AVERAGE);
		double sigma = sd.tick(pb, type).getValue(SD.SIGMA);
		
		double upperband = ave + sigma * multiple[0];
		double lowerband = ave - sigma * multiple[1];
		double[] result = new double[]{upperband, lowerband, ave, sigma};
		
		return new IndicatorValue(getName(), pb.getDate(),result); 
	}
	

	 @Override
	 public boolean isValid() {
		 return average.isValid() && sd.isValid();
	 }

}
