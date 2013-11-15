package indicators;

import java.text.DecimalFormat;

import org.joda.time.DateTime;

public class IndicatorValue {
	DecimalFormat df = new DecimalFormat("+0.000;-0.000");
	
	private final DateTime date;
	private final double[] value;

	public IndicatorValue(DateTime date, double[] value) {
		this.date = date;
		this.value = value;
	}

	public DateTime getDate() {
		return date;
	}

	public double getValue() {
		return value[0];
	}
	
	public double getValue(int index) {
		return value[index];
	}

	public double getFormattedValue(int index) {
		return Double.parseDouble(df.format(value[index]));
	}

	
}

