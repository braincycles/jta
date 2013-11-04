package math;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.DistributionFactory;
import org.apache.commons.math.distribution.DistributionFactoryImpl;
import org.apache.commons.math.distribution.NormalDistribution;
import org.apache.commons.math.stat.StatUtils;

import data.QuoteHistory;

public class Stats {


	public static double variance(QuoteHistory qh,int window, int type) {
		return variance(qh, 0, window, type);
	}
	
	
	public static double priceMean(QuoteHistory qh, int index, int window, int type) {
		double[] data =  qh.getAllReturns(type);
		double[] resultData = new double[window];

		if(index>data.length-1) index = data.length-1;

		if(window>index) return 0.0;

		for(int i=0;i<window;i++) {
			resultData[i] = data[index-i];
		}
		return StatUtils.mean( resultData  );
	}
	
	public static double mean(QuoteHistory qh, int index, int window, int type) {
		double[] data =  qh.getAll(type);
		double[] resultData = new double[window];

		if(index>data.length-1) index = data.length-1;

		if(window>index) return 0.0;

		for(int i=0;i<window;i++) {
			resultData[i] = data[index-i];
		}
		return StatUtils.mean( resultData  );
	}

	public static double variance(QuoteHistory qh, int index, int window, int type) {
		double[] data =  qh.getAll(type);
		double[] resultData = new double[window];

		if(index>data.length-1) index = data.length-1;

		if(window>index) return 0.0;

		for(int i=0;i<window;i++) {
			resultData[i] = data[index-i];
		}
		return StatUtils.variance( resultData  );
	}


	public static double priceVariance(QuoteHistory qh, int index, int window, int type) {
		double[] data =  qh.getAllReturns(type);
		double[] resultData = new double[window];

		if(index>data.length-1) index = data.length-1;

		if(window>index-1) return 0.0;

		for(int i=0;i<window;i++) {
			resultData[i] = data[index-i];
		}
		return StatUtils.variance( resultData  );
	}


	public static double priceSD(QuoteHistory qh, int index, int window, int type) {
		return Math.sqrt(priceVariance(qh, index, window, type));	
	}


	public static double sd(QuoteHistory qh, int index, int window, int type) {
		return Math.sqrt(variance(qh, index, window, type));
	}

	public static double cumNormal(double mean, double sd, double value) {
		NormalDistribution n = DistributionFactory.newInstance().createNormalDistribution(mean, sd);
		try {
			return n.cumulativeProbability(value);
		}
		catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public static double invNormal(double mean, double sd, double value) {
		NormalDistribution n = DistributionFactory.newInstance().createNormalDistribution(mean, sd);
		try {
			return n.inverseCumulativeProbability(value);
		}
		catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
}
