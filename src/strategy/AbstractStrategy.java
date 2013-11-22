package strategy;

import indicators.Indicator;

public abstract class AbstractStrategy extends Indicator {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4449598603608590800L;

	private double weight = 1.0;

	public AbstractStrategy(String name) {
		super(name);
	}
	
	
	
	
	public double getWeight() {
		return weight;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}





	public static int SIGNAL = 0;
	
	public static int BUY_SIGNAL = 1;
	public static int SELL_SIGNAL = -1;
}
