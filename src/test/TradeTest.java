package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import trade.TradeManager;

public class TradeTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testTradeManager() {
		double cash = 5435;
		double price = 45;
		double percentage = 0.5;
		int number = TradeManager.getAmountByPercentage(cash, price, percentage);
		//5435*0.5/45 = 60.3888 so should be 60
		assertEquals("", 60,number,0 );
	}

}
