package test;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import trade.Portfolio;
import trade.PortfolioManager;
import trade.Stock;
import trade.StockTrade;
import trade.Trade;
import trade.Transaction;

import java.util.logging.Level;
import java.util.logging.Logger;

public class testPortfolio {
	
	private final static Logger LOGGER = Logger.getLogger(testPortfolio.class.getName()); 
	
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testBasics() throws Exception {	
		
		int initialCash = 100000;
		
		PortfolioManager pman = new PortfolioManager();
		
		Portfolio p1 = new Portfolio("Test 1", initialCash);
		Portfolio p2 = new Portfolio("Test 2", initialCash);
		p1.setAllowedConcurrentTrades(1);
		p1.setAllowedConcurrentTrades(1);
		pman.addPortfolio(p1, p2);
		
		assertEquals(2,pman.getNumberOfPortfolios(),0);
		
		try {
			Trade t1 = new StockTrade(new DateTime(),new Stock("YHOO"),100, 67.45, 10);
			
			// Try to add trade to a non-existent portfolio, should be unsuccessful
			Transaction tran1 = pman.registerTrade("Does not exist", t1);
			assertEquals(false, tran1.isSuccessful());
			
			// Lets add to an existing portfolio, should be successful
			Transaction tran2 = pman.registerTrade("Test 1", t1);
			assertEquals(true, tran2.isSuccessful());
			
			LOGGER.log(Level.FINE, "hg");
			double cash = pman.getPortfolioCash("Test 1");
			assertEquals(100000-t1.getCostOfTrade(), cash, 0);
			
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	

}
