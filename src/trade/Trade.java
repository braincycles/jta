package trade;

import org.joda.time.DateTime;

public abstract class Trade {
	
	protected int type = 0;
	protected int status = PENDING;
	protected int amount = 0;
	protected double price=0;
	
	/**
	 * 
	 * */
	protected Stock stock;
	
	protected DateTime date;
	
	protected boolean existingPosition = false;
	
	
	public Trade(DateTime date, Stock stock, int amount, double price) throws Exception {
		this(date, stock, amount, price, false);
	}
	
	
	public Trade(DateTime date, Stock stock, int amount, double price, boolean existingPosition) 
	 																throws Exception {
		this.status = PENDING;
		setAmount(amount);
		setPrice(price);
		setStock(stock);
		setDate(date);
		this.existingPosition = existingPosition;
	}

	
	
	public int getType() {
		return amount>0 ? BUY : SELL;
	}


	public double getPrice() {
		return price;
	}

	public void setPrice(double price) throws Exception {
		if(price<0) throw new IllegalArgumentException("Trade price is negative.");
		this.price = price;
	}

	public int getAmount() {
		return amount;
	}

	private void setAmount(int amount) {
		this.amount = amount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}



	public abstract double getCostOfTrade();



	public DateTime getDate() {
		return date;
	}




	private void setDate(DateTime date) {
		this.date = date;
	}


	public boolean isFromExistingPosition() {
		return existingPosition;
	}


	public void setFromExistingPosition(boolean existingPosition) {
		this.existingPosition = existingPosition;
	}


	public Stock getStock() {
		return stock;
	}


	private void setStock(Stock stock) {
		this.stock = stock;
	}

	public int getId() {
		return hashCode();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + (existingPosition ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + status;
		result = prime * result + ((stock == null) ? 0 : stock.hashCode());
		result = prime * result + type;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trade other = (Trade) obj;
		if (amount != other.amount)
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (existingPosition != other.existingPosition)
			return false;
		if (Double.doubleToLongBits(price) != Double
				.doubleToLongBits(other.price))
			return false;
		if (status != other.status)
			return false;
		if (stock == null) {
			if (other.stock != null)
				return false;
		} else if (!stock.equals(other.stock))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	
	
	/* Constants */
	public final static int BUY = 1;
	public final static int SELL = -1;
	
	public static int PENDING=0;
	public static int ACTIVE=1;
	public static int OLD=2;
	
	
}
