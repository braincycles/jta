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

import math.Stats;
import data.PriceBar;
import data.PriceHistory;

public class SD extends Indicator {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4151070734258813652L;

	public static int SIGMA = 0;

	private int window;

	public SD(int window) {
		super("SD("+ window + ")");
		values = new PriceHistory();
		this.window = window;
	}


	@Override
	public IndicatorValue tick(PriceBar pb, int type) {
		updateValues(pb, window);

		if(isValid())
			return new IndicatorValue(getName(), pb.getDate(), new double[]{sd(values, type)});
		else 
			return new IndicatorValue(getName(), pb.getDate(), new double[]{0});
	}


	@Override
	public boolean isValid() {
		return (values.size() >= window);
	}


	private double sd(PriceHistory prices, int type) {
		return Stats.sd(prices, type);
	}

}
