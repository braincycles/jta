package trade;

import org.joda.time.DateTime;

public class TradeIntegrity {
	
	
	public static boolean checkTradeBasics(Trade t) {
		
		return checkAmount(t.getAmount()) &&
			checkDate(t.getDate());
		
		
	}
	
	
	private static boolean checkAmount(int amount) {
		return amount>0;
	}
	
	private static boolean checkDate(DateTime date) {
		return true;
		//return date.isBefore(new DateTime());
	}

}
