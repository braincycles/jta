package indicators;

import java.util.*;

import data.QuoteHistory;

/**
 * Base class for all classes implementing technical indicators.
 */
public abstract class Indicator {
	
	public static int SOFTLONG=1;
	public static int SOFTSHORT=-1;
	public static int HARDLONG=10;
	public static int HARDSHORT=-10;
	
    protected double value;
    protected QuoteHistory qh;
    private final List<IndicatorValue> history;
    protected Indicator parent;

    public abstract double calculate(int upto);// must be implemented in subclasses.
    
    public double calculate() {
    	return calculate(-1);
    }

    public Indicator() {
        history = new ArrayList<IndicatorValue>();
    }

    public abstract boolean isValid(int upto);

    public Indicator(QuoteHistory qh) {
        this();
        this.qh = qh;
    }

    public Indicator(Indicator parent) {
        this();
        this.parent = parent;
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

    public long getDate() {
        if (qh != null) {
            return qh.getLastPriceBar().getDateLong();
        } else {
            List<IndicatorValue> parentHistory = parent.getHistory();
            return parentHistory.get(parentHistory.size() - 1).getDate();
        }
    }


    public void addToHistory(long date, double value) {
        history.add(new IndicatorValue(date, value));
    }

    public List<IndicatorValue> getHistory() {
        return history;
    }


}
