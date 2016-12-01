package widget;

import mygeom.OBB;
import mygeom.Vector2;

public class InternalGestureState {
	protected OBB oldOBB, currentOBB;
	protected Vector2 oldPos, currentPos, A, B, Ap, Bp;
	
	public InternalGestureState(MTComponent c) {
		this.oldPos = new Vector2();
		this.currentPos = new Vector2();
		this.A = new Vector2();
		this.B = new Vector2();
		this.Ap = new Vector2();
		this.Bp = new Vector2();
		this.oldOBB = new OBB(); 
		this.currentOBB = c.getOBB();
	}
	
	public void motionTranslateBegin(Vector2 cursor) {
		cursor.copy(currentPos);
	}
	
	public void motionTranslateUpdate(Vector2 cursor) {
		this.currentPos.copy(oldPos);
		cursor.copy(currentPos);
	}
	
	public Vector2 computeTranslation() {
		double x = currentPos.getX() - oldPos.getX();
		double y = currentPos.getY() - oldPos.getY();
		
		return new Vector2(x, y);
	}
	
	public void motionTRSBegin(Vector2 cursorA, Vector2 cursorB) {
		cursorA.copy(Ap);
		cursorB.copy(Bp);
	}
	
	public void motionTRSUpdate(Vector2 cursorA, Vector2 cursorB) {
		this.Ap.copy(A);
		this.Bp.copy(B);
		cursorA.copy(Ap);
		cursorB.copy(Bp);
	}
	
	public double computeTRSScale() {
		double xBxA = (B.getX() - A.getX());
		double yByA = (B.getY() - A.getY());
		double xBpxAp = (Bp.getX() - Ap.getX());
		double yBpyAp = (Bp.getY() - Ap.getY());
		
		double normeAB = Math.sqrt(xBxA * xBxA + yByA * yByA);
		double normeApBp = Math.sqrt(xBpxAp * xBpxAp + yBpyAp * yBpyAp);
		
		return normeApBp / normeAB;
	}
	
	public double computeTRSRotation() {
		Vector2 u = new Vector2(B.getX() - A.getX(), B.getY() - A.getY()).normalize();
		Vector2 v = new Vector2(Bp.getX() - Ap.getX(), Bp.getY() - Ap.getY()).normalize();
		
		double dot = u.dot(v);
		double det = u.determinant(v);
		
		if(dot > 1) dot = 1;
		
		return Math.signum(det) * Math.acos(dot);
	}
}
