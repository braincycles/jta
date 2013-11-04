package trade;

public abstract class Trade {

	public static int LONG=1;
	public static int SHORT = -1;
	
	public static int STOCK=0;
	public static int SPREADBET=1;
	
	public static int INACTIVE=0;
	public static int ACTIVE=1;
	public static int OLD=2;
	
	protected int tradeVehicle = 0;
	protected int tradeType = 0;
	protected double currentValue=0;
	protected double initialPrice=0;
	protected int status;
	
	
	public Trade(int tradeVehicle){
		this.tradeVehicle = tradeVehicle;
		this.status = INACTIVE;
	}
	
	public int getTradeVehicle() {
		return tradeVehicle;
	}
	
	
	public int getTradeType() {
		return tradeType;
	}

	public void setTradeType(int tradeType) {
		this.tradeType = tradeType;
	}

	public double getCurrentValue(double price) {
		return currentValue;
	}

	public double getInitialPrice() {
		return initialPrice;
	}

	public void setInitialPrice(double initialPrice) {
		this.initialPrice = initialPrice;
	}

	public abstract double close(double price);

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
	
	
	
}
