package data;

import java.util.Collections;
import java.util.Vector;

public class Util {


	public static void pad(PriceHistory ph1, PriceHistory ph2) {
		Vector <PriceBar> pb1 = ph1.getPriceHistory();
		Vector <PriceBar> pb2 = ph2.getPriceHistory();
		
		Vector <PriceBar> newPb1 = new Vector<PriceBar>(pb1);
		Vector <PriceBar> newPb2 = new Vector<PriceBar>(pb2);

		newPb1.removeAll(pb2);
		newPb2.removeAll(pb1);
		
		for(PriceBar pb : newPb2)
			pb1.add(new PriceBar(pb.getDate(),-1,-1,-1,-1,-1));
		
		for(PriceBar pb : newPb1) 
			pb2.add(new PriceBar(pb.getDate(),-1,-1,-1,-1,-1));

		Collections.sort(pb1);
		Collections.sort(pb2);
		
		/* Set the -1 values to that of the previous pricebar */
		for(PriceBar pb : pb1) 
			if(pb.getClose()==-1)
				pb.setClose(pb1.get(pb1.indexOf(pb)-1).getClose());
		
		for(PriceBar pb : pb2) 
			if(pb.getClose()==-1)
				pb.setClose(pb2.get(pb2.indexOf(pb)-1).getClose());
		
			
		
	}
}
