package widget;

import java.util.ArrayList;
import java.util.List;

import mygeom.BlobQueue;
import mygeom.Path;
import mygeom.Point2;
import mygeom.Vector2;
import oneDollarRecognizer.GestureEvent;
import widget.events.DiscreteEvent;
import widget.events.SRTEvent;

public class GestureAnalyzer {
	
	protected Path gesture;
	
	public void analyze(MTComponent comp, BlobQueue bq, String state, int id, Point2 point) {
		switch(state) {
		case "Add" :
			this.add(comp, point, bq);
			comp.fireDiscretePerformed(new DiscreteEvent(comp));
			break;
		case "Update" :
			this.update(comp, point, bq);
			break;
		case "Remove" :
			this.remove(comp, point, bq);
		}
	}
	
	public void add(MTComponent comp, Point2 point, BlobQueue bq) {
		int nbCursors = bq.getNbCursor();
		if(nbCursors == 1) {
			comp.gestureState.motionTranslateBegin(new Vector2(point.getX(), point.getY()));
		}
		if(nbCursors == 2) {
			Point2 cursorA = bq.getCursor(nbCursors, 0);
			Point2 cursorB = bq.getCursor(nbCursors, 1);
			comp.gestureState.motionTRSBegin(new Vector2(cursorA.getX(), cursorA.getY()), new Vector2(cursorB.getX(), cursorB.getY()));
		}
		if(nbCursors > 2) {
			List<Point2> cursors = new ArrayList<Point2>();
			for(int i = 0; i < nbCursors; i++) {
				cursors.add(new Point2(bq.getCursor(nbCursors, i)));
			}
			
			comp.gestureState.motionLastTRSBegin(cursors);
		}
		this.gesture = new Path();
		this.gesture.add(point);
	}
	
	public void update(MTComponent comp, Point2 point, BlobQueue bq) {
		int nbCursors = bq.getNbCursor();
		if(nbCursors == 1) {
			comp.gestureState.motionTranslateUpdate(new Vector2(point.getX(), point.getY()));
			Vector2 translation = comp.gestureState.computeTranslation();
			comp.fireSRTPerformed(new SRTEvent(comp, translation, 0, 1));
		}
		if(nbCursors == 2) {
			Point2 cursorA = bq.getCursor(nbCursors, 0);
			Point2 cursorB = bq.getCursor(nbCursors, 1);
			comp.gestureState.motionTRSUpdate(new Vector2(cursorA.getX(), cursorA.getY()), new Vector2(cursorB.getX(), cursorB.getY()));
			double scale = comp.gestureState.computeTRSScale();
			double angle = comp.gestureState.computeTRSRotation();
			Vector2 translation = comp.gestureState.computeTRSTranslate();
			
			comp.fireSRTPerformed(new SRTEvent(comp, translation, angle, scale));
		}
		if(nbCursors > 2) {
			List<Point2> cursors = new ArrayList<Point2>();
			for(int i = 0; i < nbCursors; i++) {
				cursors.add(new Point2(bq.getCursor(nbCursors, i)));
			}
			comp.gestureState.motionLastTRSUpdate(cursors);
			double scale = comp.gestureState.computeLastTRSScale();
			double angle = comp.gestureState.computeLastTRSAngle();
			Vector2 translation = comp.gestureState.computeLastTRSTranslate();
			
			comp.fireSRTPerformed(new SRTEvent(comp, translation, angle, scale));
		}
		this.gesture.add(point);
		
	}
	
	public void remove(MTComponent comp, Point2 point, BlobQueue bq) {
		int nbCursors = bq.getNbCursor();
		if(nbCursors == 1) {
			Point2 realPoint = bq.getCursor(1, 0);
			comp.gestureState.motionTranslateBegin(new Vector2(realPoint.getX(), realPoint.getY()));
		}
		if(nbCursors == 2) {
			Point2 cursorA = bq.getCursor(nbCursors, 0);
			Point2 cursorB = bq.getCursor(nbCursors, 1);
			comp.gestureState.motionTRSBegin(new Vector2(cursorA.getX(), cursorA.getY()), new Vector2(cursorB.getX(), cursorB.getY()));
		}
		if(nbCursors > 2) {
			List<Point2> cursors = new ArrayList<Point2>();
			for(int i = 0; i < nbCursors; i++) {
				cursors.add(new Point2(bq.getCursor(nbCursors, i)));
			}
			
			comp.gestureState.motionLastTRSBegin(cursors);
		}
		
		Path path = new Path(gesture);
		
		this.gesture = comp.gestureState.getOneDRecognizer().resample(this.gesture);
		this.gesture = comp.gestureState.getOneDRecognizer().rotateToZero(this.gesture);
		this.gesture = comp.gestureState.getOneDRecognizer().scaleToSquare(this.gesture);
		this.gesture = comp.gestureState.getOneDRecognizer().translateToOrigin(this.gesture);
		GestureEvent ev = comp.gestureState.getOneDRecognizer().recognize(this.gesture);
		ev.setNbDoigts(bq.getNbCursor() + 1);
		ev.setSource(comp);
		ev.setPath(path);
		comp.fireGesturePerformed(ev);
	}
	
	
}
