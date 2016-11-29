package widget;

import main.Main;
import mygeom.BlobQueue;
import mygeom.Point2;
import mygeom.Vector2;
import widget.events.DiscreteEvent;
import widget.events.SRTEvent;

public class GestureAnalyzer {
	
	protected Point2 previousPoint = new Point2();
	
	public void analyze(MTComponent comp, BlobQueue bq, String state, int id, Point2 point) {
		switch(state) {
		case "Add" :
			comp.fireDiscretePerformed(new DiscreteEvent(comp));
			break;
		case "Update" :
			Point2 updatePoint = new Point2(point.getX(), point.getY());
			updatePoint.sub(previousPoint);
			Vector2 translate = new Vector2(updatePoint.getX() * Main.SURFACE_WIDTH, updatePoint.getY() * Main.SURFACE_HEIGHT);
			
			comp.fireSRTPerformed(new SRTEvent(comp, translate));
			break;
		}
		point.copy(this.previousPoint);
	}
}
