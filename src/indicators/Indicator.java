package indicators;

import data.PriceBar;
import data.PriceHistory;

/**
 * Base class for all classes implementing technical indicators.
 */
public abstract class Indicator {
	
	public static int SOFTLONG=1;
	public static int SOFTSHORT=-1;
	public static int HARDLONG=10;
	public static int HARDSHORT=-10;
	
    protected double value;
    protected Indicator parent;
    
    protected PriceHistory values;
    
    public abstract IndicatorValue tick(PriceBar pb, int type);
    
    public void updateValues(PriceBar pb, int window) {
    	values.addFirst(pb);
		if(values.size()>window) 
			values.removeLast();
    }

    public abstract boolean isValid();
    
    public Indicator() {
    	values = new PriceHistory();
    }

    public Indicator(Indicator parent) {
        this.parent = parent;
        //history = new ArrayList<IndicatorValue>();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" value: ").append(value);
        return sb.toString();
    }

    public double getValue() {
        return value;
    }


}
