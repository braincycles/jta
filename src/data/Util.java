package data;

import java.util.Collections;
import java.util.Vector;

public class Util {
	
	public static void pad(PriceHistory[] ph) {
		
		for(int i = 0;i<ph.length-1;i++) {
			pad(ph[i],ph[i+1]);
		}
		
	}
	


	public static void pad(PriceHistory ph1, PriceHistory ph2) {
		Vector <PriceBar> pb1 = ph1.getPriceHistory();
		Vector <PriceBar> pb2 = ph2.getPriceHistory();
		
		Vector <PriceBar> newPb1 = new Vector<PriceBar>(pb1);
		Vector <PriceBar> newPb2 = new Vector<PriceBar>(pb2);

		newPb1.removeAll(pb2);
		newPb2.removeAll(pb1);
		
		for(PriceBar pb : newPb2)
			pb1.add(new PriceBar(pb.getSymbol(), pb.getDate(),-1,-1,-1,-1,-1));
		
		for(PriceBar pb : newPb1) 
			pb2.add(new PriceBar(pb.getSymbol(), pb.getDate(),-1,-1,-1,-1,-1));

		Collections.sort(pb1);
		Collections.sort(pb2);
		
		if(pb1.size() != pb2.size()) System.out.println("Error");
		
		/* Set the -1 values to that of the previous pricebar */
		for(PriceBar pb : pb1) 
			if(pb.getClose()==-1) {
				if (pb1.indexOf(pb)-1 >=0) //Start of data, leave 0
					pb.setClose(pb1.get(pb1.indexOf(pb)-1).getClose());
			}
		
		for(PriceBar pb : pb2) 
			if(pb.getClose()==-1){
				if (pb2.indexOf(pb)-1 >=0)
					pb.setClose(pb2.get(pb2.indexOf(pb)-1).getClose());
			}
		
			
		
	}
}
