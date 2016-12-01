package widget;

import main.Main;
import mygeom.BlobQueue;
import mygeom.Point2;
import mygeom.Vector2;
import widget.events.DiscreteEvent;
import widget.events.SRTEvent;

public class GestureAnalyzer {
	
	public void analyze(MTComponent comp, BlobQueue bq, String state, int id, Point2 point) {
		switch(state) {
		case "Add" :
			this.add(comp, point, bq);
			comp.fireDiscretePerformed(new DiscreteEvent(comp));
			break;
		case "Update" :
//			Point2 updatePoint = new Point2(point.getX(), point.getY());
//			updatePoint.sub(previousPoint);
//			Vector2 translate = new Vector2(updatePoint.getX() * Main.SURFACE_WIDTH, updatePoint.getY() * Main.SURFACE_HEIGHT);
			this.update(comp, point, bq);
			break;
		case "Remove" :
			this.remove(comp, point, bq);
		}
//		point.copy(this.previousPoint);
	}
	
	public void add(MTComponent comp, Point2 point, BlobQueue bq) {
		if(bq.getNbCursor() == 1)
			comp.gestureState.motionTranslateBegin(new Vector2(point.getX(), point.getY()));
	}
	
	public void update(MTComponent comp, Point2 point, BlobQueue bq) {
		if(bq.getNbCursor() == 1)
			comp.gestureState.motionTranslateUpdate(new Vector2(point.getX(), point.getY()));
		
		Vector2 translation = comp.gestureState.computeTranslation();
		comp.fireSRTPerformed(new SRTEvent(comp, translation, 0, 1));
	}
	
	public void remove(MTComponent comp, Point2 point, BlobQueue bq) {
		if(bq.getNbCursor() >= 1) {
			comp.gestureState.motionTranslateBegin(new Vector2(point.getX(), point.getY()));
		}
	}
	
	
}
