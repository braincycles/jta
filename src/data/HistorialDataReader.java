package data;

import org.joda.time.DateTime;



public interface HistorialDataReader {
	
	public String getSourceName();
	
	public PriceHistory getHistoricalStockPrices(String symbol, DateTime from, DateTime to, int base, boolean latestFirst);
	

}
