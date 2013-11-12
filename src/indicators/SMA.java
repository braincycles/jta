package indicators;

import java.util.ArrayList;
import java.util.List;

public class SMA extends Indicator {
	
	
	private int window;
	private List<IndicatorValue> values;
	
	public SMA(int window) {
		values = new ArrayList<IndicatorValue>();
		this.window = window;
	}
	
	public SMA(int window, boolean save) {
		values = new ArrayList<IndicatorValue>();
		this.window = window;
		this.save = save;
	}

	@Override
	public double calculate(int upto) {
		
		if(save);
		return 0;
	}

	@Override
	public boolean isValid(int upto) {
		return (values.size()>=window) ? true : false;
	}
	
	

}
