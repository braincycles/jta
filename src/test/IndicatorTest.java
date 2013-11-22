package test;

import static org.junit.Assert.*;

import indicators.Bollinger;
import indicators.IndicatorValue;
import indicators.SD;
import indicators.SMA;

import java.util.Vector;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import data.PriceBar;

public class IndicatorTest {

	private Vector<PriceBar> data = new Vector<PriceBar>();
	
	@Before
	public void setUp() throws Exception {

		data.add(new PriceBar("Test", new DateTime(2013,5,1,0,0,0),4,5,6,22,1000));
		data.add(new PriceBar("Test", new DateTime(2013,5,2,0,0,0),4,5,6,28,2000));
		data.add(new PriceBar("Test", new DateTime(2013,5,3,0,0,0),4,5,6,23,3000));
		data.add(new PriceBar("Test", new DateTime(2013,5,4,0,0,0),4,5,6,29,4000));
		data.add(new PriceBar("Test", new DateTime(2013,5,5,0,0,0),4,5,6,26,5000));
		data.add(new PriceBar("Test", new DateTime(2013,5,7,0,0,0),4,5,6,27,6000));
		
		data.add(new PriceBar("Test", new DateTime(2013,5,8,0,0,0),4,5,6,17,8000));
		data.add(new PriceBar("Test", new DateTime(2013,5,9,0,0,0),4,5,6,55,8000));
		data.add(new PriceBar("Test", new DateTime(2013,5,10,0,0,0),4,5,6,23,8000));
		data.add(new PriceBar("Test", new DateTime(2013,5,11,0,0,0),4,5,6,27,8000));
	}

	@Test
	public void testSMA() {
		double[] expected = new double[]{22,25,24.3333,25.5, 25.6, 26.6, 24.4, 30.8, 29.6, 29.8};
		
		SMA sma = new SMA(5);
		int i = 0;
		for(PriceBar pb : data) {
			double ave = sma.tick(pb, PriceBar.CLOSE).getValue(SMA.AVERAGE);
			assertEquals("", expected[i], ave, 0.0001);
			//SMA should only be valid after 5 ticks
			assertEquals("", (i>=4), sma.isValid());
			i++;
		}
	}
	
	@Test
	public void testSD() {
		double[] expected = new double[]{0,0,0,0, 3.04959, 2.302172, 4.66904, 14.28985, 14.72412, 14.66969};
		SD sd = new SD(5);
		int i = 0;
		double[] vals = new double[data.size()];
		
		for(PriceBar pb : data) {
			double sdv = sd.tick(pb, PriceBar.CLOSE).getValue(SD.SIGMA);
			vals[i] = pb.getClose();
			assertEquals("", expected[i], sdv, 0.0001);
			//SD should only be valid after 5 ticks
			assertEquals("", (i>=4), sd.isValid());
			i++;
		}
	}
	
	@Test
	public void testBollinger() {
		double multiple = 2.5;
		Bollinger boll = new Bollinger(5,new double[]{multiple,multiple});
		SD sd = new SD(5);
		SMA sma = new SMA(5);
		
		for(PriceBar pb : data) {
			IndicatorValue value = boll.tick(pb, PriceBar.CLOSE);
			double bollSig = value.getValue(Bollinger.SIGMA);
			double bollAve = value.getValue(Bollinger.MIDPOINT);
			double bollUpper = value.getValue(Bollinger.UPPERBAND);
			double bollLower = value.getValue(Bollinger.LOWERBAND);
			
			double sdv = sd.tick(pb, PriceBar.CLOSE).getValue(SD.SIGMA);
			double ave = sma.tick(pb, PriceBar.CLOSE).getValue(SMA.AVERAGE);

			// Bollinger sigma should be equal to SD value
			assertEquals("", sdv, bollSig, 0.0001);
			
			// Bollinger average should be equal to SMA value
			assertEquals("", ave, bollAve, 0.0001);
			
			// Check the upper and lower bands
			assertEquals("", ave+multiple*sdv, bollUpper, 0.0001);
			assertEquals("", ave-multiple*sdv, bollLower, 0.0001);
		}
	}
	
	

}
