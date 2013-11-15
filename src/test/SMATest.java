package test;

import static org.junit.Assert.*;

import indicators.SMA;

import java.util.Vector;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import data.PriceBar;

public class SMATest {

	private Vector<PriceBar> data = new Vector<PriceBar>();
	
	@Before
	public void setUp() throws Exception {

		data.add(new PriceBar(new DateTime(2013,5,1,0,0,0),4,5,6,22,1000));
		data.add(new PriceBar(new DateTime(2013,5,2,0,0,0),4,5,6,28,2000));
		data.add(new PriceBar(new DateTime(2013,5,3,0,0,0),4,5,6,23,3000));
		data.add(new PriceBar(new DateTime(2013,5,4,0,0,0),4,5,6,29,4000));
		data.add(new PriceBar(new DateTime(2013,5,5,0,0,0),4,5,6,26,5000));
		data.add(new PriceBar(new DateTime(2013,5,7,0,0,0),4,5,6,27,6000));
		
		data.add(new PriceBar(new DateTime(2013,5,8,0,0,0),4,5,6,17,8000));
		data.add(new PriceBar(new DateTime(2013,5,9,0,0,0),4,5,6,55,8000));
		data.add(new PriceBar(new DateTime(2013,5,10,0,0,0),4,5,6,23,8000));
		data.add(new PriceBar(new DateTime(2013,5,11,0,0,0),4,5,6,27,8000));
	}

	@Test
	public void testSMAAverage() {
		double[] expected = new double[]{22,25,24.3333,25.5, 25.6, 26.6, 24.4, 30.8, 29.6, 29.8};
		
		SMA sma = new SMA(5);
		int i = 0;
		for(PriceBar pb : data) {
			double ave = sma.tick(pb, PriceBar.CLOSE).getValue(SMA.AVERAGE);
			assertEquals("", expected[i++], ave, 0.0001);
			//SMA should only be valid after 5 ticks
			assertEquals("", (i>4), sma.isValid());
		}
	}

}
