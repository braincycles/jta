package indicators;

import java.text.DecimalFormat;

import org.joda.time.DateTime;

public class IndicatorValue {
	private DecimalFormat df = new DecimalFormat("+0.000;-0.000");
	
	private String message;
	private final DateTime date;
	private final double[] value;
	private String name;

	public IndicatorValue(String name, DateTime date, double[] value) {
		this.name = name;
		this.date = date;
		this.value = value;
		this.message = "";
	}
	
	public IndicatorValue(String name, DateTime date, double[] value, String message) {
		this.name = name;
		this.date = date;
		this.value = value;
		this.message = message;
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


	public void setDecimalFormat(DecimalFormat df) {
		this.df = df;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Indicator: " + name + " : " + value[0];
	}

	
	
}

