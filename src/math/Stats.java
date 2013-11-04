package math;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.random.CorrelatedRandomVectorGenerator;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import data.PriceHistory;
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

	/*
	public static double cumNormal(double mean, double sd, double value) {
		NormalDistribution n;// = DistributionFactory.newInstance().createNormalDistribution(mean, sd);
		try {
			return n.cumulativeProbability(value);
		}
		catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public static double invNormal(double mean, double sd, double value) {
		NormalDistribution n;// = DistributionFactory.newInstance().createNormalDistribution(mean, sd);
		try {
			return n.inverseCumulativeProbability(value);
		}
		catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	*/
	
	
	public static double[][] cov(PriceHistory h1, PriceHistory h2) {
		int length = h1.getPriceHistory().size();
		
		double[][] data = new double[length][length];
		
		for(int i=0;i<length;i++) {
			data[0][i] = h1.getPriceHistory().get(i).getClose();
			data[1][i] = h2.getPriceHistory().get(i).getClose();
		}
		
		
		PearsonsCorrelation pc = new PearsonsCorrelation(data);
		RealMatrix rm =  pc.computeCorrelationMatrix(data);
		return rm.getData();
	}
	
	
}
