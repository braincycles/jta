package test;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import portfolio.Portfolio;
import portfolio.PortfolioManager;

import trade.Stock;
import trade.StockTrade;
import trade.Trade;
import trade.Transaction;

public class PortfolioTest {
	
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testBasics() throws Exception {	
		
		int initialCash = 100000;
		
		PortfolioManager pman = new PortfolioManager();
		
		Portfolio p1 = new Portfolio("Test 1", initialCash);
		Portfolio p2 = new Portfolio("Test 2", initialCash);
		p1.setAllowedConcurrentPositions(1);
		p1.setAllowedConcurrentPositions(1);
		pman.addPortfolio(p1, p2);
		
		assertEquals(2,pman.getNumberOfPortfolios(),0);
		
		try {
			Trade t1 = new StockTrade(new DateTime(),new Stock("YHOO"),100, 67.45, 10);
			double costOfTrade = t1.getCostOfTrade();
			
			// Try to add trade to a non-existent portfolio, should be unsuccessful
			Transaction tran1 = pman.registerTrade("Does not exist", t1);
			assertEquals(false, tran1.isSuccessful());
			//Check that there is a message with error code NO_PORTFOLIO
			assertEquals(true, tran1.getMessages().containsKey(PortfolioManager.NO_PORTFOLIO));
			
			// Lets add to an existing portfolio, should be successful
			Transaction tran2 = pman.registerTrade("Test 1", t1);
			assertEquals(true, tran2.isSuccessful());
			
			double cash = pman.getPortfolioCash("Test 1");
			assertEquals(100000-costOfTrade, cash, 0);
			
			//Lets add to an existing position
			pman.setAddToExisitingPositions(true);
			tran2 = pman.registerTrade("Test 1", t1);
			assertEquals(true, tran2.isSuccessful());
			
			cash = pman.getPortfolioCash("Test 1");
			assertEquals(100000-2*costOfTrade, cash, 0);
			
			//Lets try to sell a stock we do not have a position in
			Trade t2 = new StockTrade(new DateTime(),new Stock("AAPL"),-100, 67.45, 10);
			Transaction tran3 = pman.registerTrade("Test 1", t2);
			assertEquals("We managed to sell a stock we do not have a position in!", false, tran3.isSuccessful());
			
			
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	

}
