package indicators;

import java.io.Serializable;

import data.PriceBar;
import data.PriceHistory;

/**
 * Base class for all classes implementing technical indicators.
 */
public abstract class Indicator implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 914405935110868757L;
	public static int SOFTLONG=1;
	public static int SOFTSHORT=-1;
	public static int HARDLONG=10;
	public static int HARDSHORT=-10;
	
	private String name;
	
    protected double value;
    protected Indicator parent;
    
    protected PriceHistory values;
    
    public abstract IndicatorValue tick(PriceBar pb, int type);
    
    public void updateValues(PriceBar pb, int window) {
    	values.addFirst(pb);
		if(values.size()>window) 
			values.removeLast();
    }

    
    /** Returns whether the indicator will return a valid value;
	 * 
	 * @return			boolean valid return value.
	 */
    public abstract boolean isValid();
    
    public Indicator(String name) {
    	values = new PriceHistory();
    	this.name = name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
    


}
