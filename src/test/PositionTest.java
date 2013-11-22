package test;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import portfolio.Position;

import trade.Stock;
import trade.StockTrade;
import trade.Trade;

public class PositionTest {

	
	
	Position position = new Position();
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testBasics() throws Exception {	
		int amount = 1000;
		double price = 15;
		double fee = 12.5;
		
		Stock stock = new Stock("YHOO");
		Trade t1 = new StockTrade(new DateTime(),stock,amount, price, fee);
		
		position = new Position(t1);
		
		assertEquals("Stock objects are not equal.", position.getStock(), stock);
		assertEquals("Amount in position not equal.", position.getAmount(), amount);
		assertEquals("Initial price of position not equal.", price, position.getInitialPrice(), 0.0);
		assertEquals("Initial value of position not equal.", (price*amount), position.getInitialValue(), 0.0);
		assertEquals("Current value of position not equal.", (price*amount), position.getCurrentValue(), 0.0);
		
		
		double newPrice = 2000;
		position.setCurrentPrice(newPrice);
		assertEquals("Current value of position not equal.", (newPrice*amount), position.getCurrentValue(), 0.0);
		
		/* Lets close the position */
		double finalPrice = 4000;
		assertEquals("Closed value of position not equal.", (finalPrice*amount), position.closePosition(finalPrice), 0.0);
		
	}
	
	@Test
	public void testPositionMechanics() throws Exception {	
		int amount = 1034;
		double price = 1234.3;
		double fee = 12.5;
		
		Stock stock1 = new Stock("YHOO");
		Trade t1 = new StockTrade(new DateTime(),stock1,amount, price, fee);
		
		position = new Position(t1);
		
		/* Lets try to add a different stock to this position */
		Stock stock2 = new Stock("AAPL");
		Trade t2 = new StockTrade(new DateTime(), stock2,amount, price, fee);
		
		/* addToPosition should return false */
		assertEquals(false, position.adjust(t2).isSuccessful());
		/* Amount should be unaltered */
		assertEquals("Amount in position not equal.", position.getAmount(), amount);
		
		Trade t3 = new StockTrade(new DateTime(),stock1,amount, price, fee);
		/* addToPosition should return true */
		assertEquals(true, position.adjust(t3).isSuccessful());
		/* Amount should be double */
		assertEquals("Amount in position not equal.", position.getAmount(), amount*2);
		
	}

}
