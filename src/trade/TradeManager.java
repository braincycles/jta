package trade;




public class TradeManager {


	public static int getAmountByPercentage(double totalCash, double price, double percent) {
		return (int) ((int)(totalCash*percent)/price);
	}



	/* Constants */

	public final static int PERCENTAGE_TRADE = 300;
	public final static int ALLIN_TRADE = 301;
	public final static int ABSOLUTE_TRADE = 302;

}
