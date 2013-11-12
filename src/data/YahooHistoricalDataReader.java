package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.StringTokenizer;

import org.joda.time.*;




public class YahooHistoricalDataReader implements HistorialDataReader {

	private String sourceName = "Yahoo Finance";
	private File file;
	private FileReader freader = null;
	private LineNumberReader lnreader = null;


	public YahooHistoricalDataReader() {

	}



	public void open(String fileName) {
		try{
			file = new File(fileName);
			freader = new FileReader(file);
			lnreader = new LineNumberReader(freader);
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public void close() {
		try {
			freader.close();
			lnreader.close();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}


	public PriceHistory read(String symbol) {
		PriceHistory qh = new PriceHistory(symbol, PriceHistory.DAILY);
		return read(qh);
	}


	public PriceHistory read(PriceHistory ph) {		

		StringTokenizer st = null;
		try{
			String line = "";
			lnreader.readLine(); // ignore header
			while ((line = lnreader.readLine()) != null){

				st = new StringTokenizer(line, ",");

				DateTime dt = getDateTime(st.nextToken());
				double open = Double.parseDouble(st.nextToken());
				double high = Double.parseDouble(st.nextToken());
				double low = Double.parseDouble(st.nextToken());
				double close = Double.parseDouble(st.nextToken());
				double volume = Double.parseDouble(st.nextToken());
				ph.addPriceBar(new PriceBar(dt,open, high, low, close, volume));
			}
			ph.reverseData();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
		catch(NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		return ph;
	}


	public PriceHistory getHistoricalStockPrices(String symbol, DateTime date, int base, boolean latestFirst) {
		return getHistoricalStockPrices(symbol, date, date, base, latestFirst);
	}

	public PriceHistory getHistoricalStockPrices(String symbol, DateTime date, int base) {
		return getHistoricalStockPrices(symbol, date, date, base, true);
	}

	public PriceHistory getHistoricalStockPrices(String symbol, DateTime from, DateTime to, int base) {
		return getHistoricalStockPrices(symbol, from, to, base, true);
	}
	
	
	public PriceHistory[] getHistoricalStockPrices(String symbols[], DateTime from, DateTime to, int base, boolean latestFirst) {
		PriceHistory ph[] = new PriceHistory[0];
		int i = 0;
		for(String sym : symbols) {
			System.out.println("Getting " + symbols[i]);
			ph[i] =  getHistoricalStockPrices(sym, from, to, base, latestFirst);
			i++;
		}
		return ph;
	}
	
	public PriceHistory[] getHistoricalStockPrices(String symbols[], DateTime from, DateTime to, int base) {
		PriceHistory ph[] = new PriceHistory[symbols.length];
		int i = 0;
		for(String sym : symbols) {
			System.out.print("Getting " + symbols[i]);
			ph[i] =  getHistoricalStockPrices(sym, from, to, base, false);
			System.out.print(" " + ph[i].getPriceHistory().size() + " elements found.\n");
			i++;
		}
		return ph;
	}

	public PriceHistory getHistoricalStockPrices(String symbol, DateTime from, DateTime to, int base, boolean latestFirst) {

		PriceHistory history = new PriceHistory(symbol, base);

		int fromDay = from.getDayOfMonth();
		int fromMonth = from.getMonthOfYear()-1;
		int fromYear = from.getYear();

		int toDay = to.getDayOfMonth();
		int toMonth = to.getMonthOfYear()-1;
		int toYear = to.getYear();

		try {
			URL yahoofin = new URL("http://ichart.finance.yahoo.com/table.csv?s=" + symbol + 
					"&a="+fromMonth+"&b="+fromDay+"&c="+fromYear+"&d="+toMonth+"&e="+toDay+"&f="+toYear+"&g=d");

			URLConnection yc = yahoofin.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String inputLine;
			in.readLine(); // ignore header
			while ((inputLine = in.readLine()) != null) {

				String[] yahooStockInfo = inputLine.split(",");

				DateTime dt = getDateTime(yahooStockInfo[0]);

				double open = Double.valueOf(yahooStockInfo[1]);
				double high = Double.valueOf(yahooStockInfo[2]);
				double low = Double.valueOf(yahooStockInfo[3]);
				double close = Double.valueOf(yahooStockInfo[4]);
				double volume = Double.valueOf(yahooStockInfo[5]);

				PriceBar priceBar = new PriceBar(dt, open, high, low, close,  volume);
				history.addPriceBar(priceBar);
			}
			in.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			//log.error("Unable to get stockinfo for: " + symbol + ex);
		}
		if(false == latestFirst) history.reverseData();
		return history;
	}


	private DateTime getDateTime(String dateStr) {
		String monthStr="";
		int year=-1;
		int month=-1;
		int day=-1;
		try {
			StringTokenizer st = new StringTokenizer(dateStr,"-");
			String yearStr = st.nextToken();
			monthStr = st.nextToken();
			String dayStr = st.nextToken();


			year = Integer.parseInt(yearStr);
			if(year<2000) year = year+2000;
			day = Integer.parseInt(dayStr);


			month = Integer.parseInt(monthStr);



		}
		catch(NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		return new DateTime(year,month, day,0,0,0,0);
	}



	public String getSourceName() {
		return sourceName;
	}

}
