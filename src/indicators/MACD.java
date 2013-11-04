package indicators;

import data.QuoteHistory;


/**
 * MACD
 */
public class MACD extends Indicator {
    private final int fastLength, slowLength;
    private Indicator fEMA;
    private Indicator sEMA;

    public MACD(QuoteHistory qh, int slowL, int fastL) {
        super(qh);
        this.fastLength = fastL;
        this.slowLength = slowL;
        fEMA = new EMA(qh, fastLength);
        sEMA = new EMA(qh, slowLength);
    }

    @Override
    public double calculate(int upto) {
        double fastEMA = fEMA.calculate(upto);
        double slowEMA = sEMA.calculate(upto);
        
        if(!isValid(upto)) return 0;
        
        value = fastEMA - slowEMA;

        return value;
    }


	@Override
	public boolean isValid(int upto) {
		if((qh.size() < slowLength) || (upto<slowLength) || !fEMA.isValid(upto) || !sEMA.isValid(upto))
			return false;
		else 
			return true;
	}
}
