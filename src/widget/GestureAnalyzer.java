package widget;

import mygeom.BlobQueue;
import mygeom.Point2;
import widget.events.DiscreteEvent;

public class GestureAnalyzer {
	
	public void analyze(MTComponent comp, BlobQueue bq, String state, int id, Point2 point) {
		switch(state) {
		case "Add" :
			comp.fireDiscretePerformed(new DiscreteEvent(comp));
			break;
		}
	}
}
