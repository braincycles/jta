package data;

import java.util.List;
import java.util.Vector;

import org.joda.time.DateTime;

public class Util {
	
	
	
	public static void pad(PriceHistory ph1, PriceHistory ph2) {
		Vector<PriceBar> pb1 = ph1.getPriceHistory();
		Vector<PriceBar> pb2 = ph2.getPriceHistory();
		
		boolean earliest = pb1.get(pb1.size()-1).getDate().isBefore(pb2.get(pb2.size()-1).getDate());
		DateTime earliestDate = earliest ? pb1.get(pb1.size()-1).getDate() : pb2.get(pb2.size()-1).getDate();
		
		Vector<PriceBar> start = earliest?pb1:pb2;
		Vector<PriceBar> other = !earliest?pb1:pb2;
		
		
		
		for(int i=0;i<start.size();i++) {
			
			
			
			//pb1.get(i).getDate().
			
		}
		
		
	}
	
	
	

}
