package indicators;

import java.util.*;

import data.PriceBar;
import data.QuoteHistory;


/**
 * Exponential Moving Average.
 */
public class EMA extends Indicator {
    private final int length;
    private final double multiplier;

    public EMA(QuoteHistory qh, int length) {
        super(qh);
        this.length = length;
        multiplier = 2. / (length + 0.);
    }

    @Override
    public double calculate(int upto) {
    	
    	if(!isValid(upto)) return 0;
    	
        List<PriceBar> priceBars = qh.getAll();
        int lastBar = priceBars.size() - 1;
        
        if(upto>0) lastBar = upto;
        
        int firstBar = lastBar - 2 * length + 1;
        double ema = priceBars.get(firstBar).getClose();

        for (int bar = firstBar; bar <= lastBar; bar++) {
            double barClose = priceBars.get(bar).getClose();
            ema += (barClose - ema) * multiplier;
        }

        value = ema;

        return value;
    }


	@Override
	public boolean isValid(int upto) {
		return (qh.size() > 2*length) && (upto>=2*length);
	}
}
