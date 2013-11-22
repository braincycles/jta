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
import data.PriceHistory;

public class SMA extends Indicator {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3653081977151475874L;

	public static int AVERAGE = 0;

	private int window;

	
	public SMA(Integer window) {
		super("SMA("+window+")");
		//values = new ArrayDeque<PriceBar>();
		values = new PriceHistory();
		this.window = window;
	}
	
	public SMA(int window) {
		this(new Integer(window));
	}
	

	@Override
	public IndicatorValue tick(PriceBar pb, int type) {
		values.addFirst(pb);
		if(values.size()>window) 
			values.removeLast();
		return new IndicatorValue(getName(), pb.getDate(), new double[]{average(values, type)});
	}
	

	@Override
	public boolean isValid() {
		return (values.size() >= window);
	}
	
	
	private double average(PriceHistory prices, int type) {
		double sum = 0.0;
		for(PriceBar pb : prices.getAll())
			sum += pb.getPrice(type);
		return sum/prices.size();
	}

}
