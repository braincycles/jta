package math;

import java.text.DecimalFormat;

import org.apache.commons.math3.stat.StatUtils;
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


	public static double[][][] correlationOverWindow(PriceHistory[] h, int type, int[] offset, int window) {
		
		double[][][] results = new double[offset.length][h.length][h.length];
		
		for(int k=0;k<offset.length;k++) {
			results[k]= correlation(h, type, offset[k], window);
		}
		return results;
	}



	public static double[][] correlation(PriceHistory[] h, int type, int offset, int window) {
		double[][] results = new double[h.length][h.length];

		for(int i=0;i<h.length;i++) {
			for(int j=0;j<h.length;j++) {
				results[i][j] = correlation(h[i], h[j], type, offset, window);
			}
		}
		return results;
	}



	public static double correlation(PriceHistory h1, PriceHistory h2, int type, int offset, int window) {
		
		int length = (window<0) ? Math.min(h1.getPriceHistory().size(),h2.getPriceHistory().size()) : window;
		
		int window1 = 0;
		int window2 = 0;

		if(offset>0) window1 = Math.abs(offset); 
		if(offset<0) window2 = Math.abs(offset); 

		double[] data1 = new double[length];
		double[] data2 = new double[length];

		for(int i=0;i<length - Math.abs(offset);i++) {
			data1[i+window1] = h1.getPriceHistory().get(i).getPrice(type);
			data2[i+window2] = h2.getPriceHistory().get(i).getPrice(type);
		}

		PearsonsCorrelation pc = new PearsonsCorrelation();
		return pc.correlation(data1, data2);
	}

	
	
	public static void printCorrelationMatrix(PriceHistory[] ph, double[][][] corMat, int[] offset, double threshold) {
		DecimalFormat df = new DecimalFormat("+0.000;-0.000");

		for(int win=0;win<offset.length;win++) {
	
			for(int i=0;i<corMat[0].length;i++) {
				for(int j=0;j<corMat[0].length;j++)
					if(corMat[win][i][j]>=threshold)
						if(i>j)
							System.out.println(ph[i].getSymbol() + " and " + ph[j].getSymbol() + " (" + offset[win] + ") :" +  df.format(corMat[win][i][j]));
			}	
		}
	}
	
	
	public static void printCorrelationMatrix(double[][][] corMat, int[] offset) {
		DecimalFormat df = new DecimalFormat("+0.000;-0.000");
		String s = "";

		for(int win=0;win<offset.length;win++) {
			System.out.println("Window: " + offset[win]);
			
			s= "    ";
			for(int i=0;i<corMat[0].length;i++)
				s = s + i + "      ";
			
			System.out.println(s);
			s="";
	
			for(int i=0;i<corMat[0].length;i++) {
				for(int j=0;j<corMat[0].length;j++)
					s = s + " " + df.format(corMat[win][i][j]);

				System.out.println("" + i + " " + s);
				s="";
			}	
		}
	}

}
