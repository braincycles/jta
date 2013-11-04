package trade;

public class SimpleSpread extends Spread {
	public SimpleSpread(int spread) {
		this.setLongSpread(spread);
		this.setShortSpread(spread);
	}
}
