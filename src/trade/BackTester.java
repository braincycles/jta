package trade;

import indicators.Indicator;
import indicators.IndicatorValue;

import java.util.ArrayList;
import java.util.List;

import data.PriceBar;
import data.PriceHistory;

public class BackTester {
	List<PriceHistory> priceHistories;
	List<Indicator> indicators;
	
	private int counter = 0;


	public BackTester() {
		priceHistories = new ArrayList<PriceHistory>();
		indicators = new ArrayList<Indicator>();
	}

	
	private void setUp() {
		
		
		
	}

	public void run() {


		for(int i=0;i<10;i++) {

			for(PriceBar pb : getBars(counter++)) {
				System.out.print(pb.getClose() + " ");
			}
			System.out.print("\n");


		}



	}

	
	private PriceBar[] getBars(int index) {
		int i = 0;
		PriceBar[] bars = new PriceBar[priceHistories.size()];
		for(PriceHistory ph : priceHistories) {
			bars[i++]=ph.getPriceBar(index);
		}
		return bars;
	}
	
	
	private IndicatorValue[] getIndicators(int index) {
		int i = 0;
		IndicatorValue[] bars = new IndicatorValue[indicators.size()];
		for(Indicator ph : indicators) {
			bars[i++]=ph.getPriceBar(index);
		}
		return bars;
	}



	public void addPriceHistories(PriceHistory ... ph){
		for(PriceHistory phin : ph)
			priceHistories.add(phin);
	}
	
	
	public void addIndicators(Indicator ... ind){
		for(Indicator indin : ind)
			indicators.add(indin);
	}



}
