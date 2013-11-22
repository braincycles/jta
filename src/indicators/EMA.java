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

import java.util.*;

import data.PriceBar;

/**
 * Exponential Moving Average.
 */
public class EMA extends Indicator {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5174492027786626269L;

	public static int AVERAGE = 0;
	
	private final int window;
	private final double multiplier;
	private Deque<PriceBar> priceBars;

	public EMA(int window) {
		super("EMA("+window+")");
		this.window = window;
		multiplier = 2. / (window + 0.);
		priceBars = new ArrayDeque<PriceBar>();
	}

	@Override
	public IndicatorValue tick(PriceBar pb, int type) {
		priceBars.addFirst(pb);
		if(priceBars.size()>(2 * window + 1)) 
			priceBars.removeLast();

		return new IndicatorValue(getName(), pb.getDate(), new double[]{average(priceBars, type)});
	}


	private double average(Deque<PriceBar> prices, int type) {
		double ema = priceBars.getFirst().getPrice(type);

		for(PriceBar pbi : priceBars) {
			double barClose = pbi.getPrice(type);
			ema += (barClose - ema) * multiplier;
		}
		return ema;
	}


	@Override
	public boolean isValid() {
		return (priceBars.size() > 2*window);
	}

}
