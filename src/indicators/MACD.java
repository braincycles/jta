/*
Copyright (c) 2013 BlueApogee

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 */

package indicators;

import data.PriceBar;

/**
 * MACD
 */
public class MACD extends Indicator {
	public static int MACD = 0;
	
    private final int fastLength, slowLength;
    private Indicator fEMA;
    private Indicator sEMA;

    public MACD(int slowL, int fastL) {
        super();
        this.fastLength = fastL;
        this.slowLength = slowL;
        fEMA = new EMA(fastLength);
        sEMA = new EMA(slowLength);
    }

    @Override
    public IndicatorValue tick(PriceBar pb, int type) {
    	IndicatorValue fastEMA = fEMA.tick(pb, type);
    	IndicatorValue slowEMA = sEMA.tick(pb, type);
        
        value = fastEMA.getValue(EMA.AVERAGE) - slowEMA.getValue(EMA.AVERAGE);

        return new IndicatorValue(pb.getDate(), new double[]{value});
    }


	@Override
	public boolean isValid() {
		return fEMA.isValid() && sEMA.isValid();
	}
}
