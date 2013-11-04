package trade;

public abstract class Spread {
	
	private int shortSpread = 0;
	private int longSpread = 0;

	public int getSpread(int type){
		if(SpreadBet.LONG == type)
			return longSpread;
		else
			return shortSpread;
	}
	
	public int getShortSpread() {
		return shortSpread;
	}
	public void setShortSpread(int shortSpread) {
		this.shortSpread = shortSpread;
	}
	public int getLongSpread() {
		return longSpread;
	}
	public void setLongSpread(int longSpread) {
		this.longSpread = longSpread;
	}

	

}
