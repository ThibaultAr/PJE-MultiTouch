package widget;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import main.Main;
import mygeom.OBB;
import mygeom.Path;
import mygeom.Point2;
import mygeom.Vector2;
import oneDollarRecognizer.OneDollarRecognizer;

public class InternalGestureState {
	protected OBB oldOBB, currentOBB;
	protected Vector2 oldPos, currentPos, A, B, Ap, Bp;
	protected OneDollarRecognizer oneDRecognizer;
	protected Vector2 oldEigenVector, currentEigenVector;
	
	public InternalGestureState(MTComponent c) {
		this.oldPos = new Vector2();
		this.currentPos = new Vector2();
		this.A = new Vector2();
		this.B = new Vector2();
		this.Ap = new Vector2();
		this.Bp = new Vector2();
		this.oldOBB = new OBB(); 
		this.currentOBB = c.getOBB();
		this.oneDRecognizer = new OneDollarRecognizer();
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
	
	public Vector2 computeTRSTranslate() {
		AffineTransform transform = new AffineTransform();
		double angle = this.computeTRSRotation();
		double scale = this.computeTRSScale();
		
		
		transform.setToTranslation(A.getX(), A.getY());
		transform.translate(Ap.getX() - A.getX(), Ap.getY() - A.getY());
		transform.rotate(angle);
		transform.scale(scale, scale);
		transform.translate(-A.getX(), -A.getY());
		
		Point2D P0 = new Point2D.Double(this.currentOBB.getNormalizeX(), this.currentOBB.getNormalizeY());
		Point2D Pp0 = new Point2D.Double();
		
		transform.transform(P0, Pp0);
		
		Vector2 vPp0 = new Vector2(Pp0.getX(), Pp0.getY());
		Vector2 vP0 = new Vector2(P0.getX(), P0.getY());
		
		return (Vector2) vPp0.sub(vP0);
	}
	
	public void motionLastTRSBegin(List<Point2> cursors) {
		Main.inertialMatrix.setPath(new Path(cursors));
		this.currentEigenVector = Main.inertialMatrix.getTRSVector();
	}
	
	public void motionLastTRSUpdate(List<Point2> cursors) {
		Main.inertialMatrix.setPath(new Path(cursors));
		this.oldEigenVector = new Vector2(this.currentEigenVector);
		
		this.currentEigenVector = Main.inertialMatrix.getTRSVector();
		if(this.oldEigenVector.dot(currentEigenVector) < 0) this.currentEigenVector.mul(-1);
	}
	
	public double computeLastTRSAngle() {
		double dot = this.oldEigenVector.dot(this.currentEigenVector);
		double det = this.oldEigenVector.determinant(this.currentEigenVector);
		
		if(dot > 1) dot = 1;
		
		return Math.signum(det) * Math.acos(dot);
	}
	
	public double computeLastTRSScale() {
		return 1;
	}
	
	public Vector2 computeLastTRSTranslate() {
//		Main.inertialMatrix.setPath(new Path(this.currentCursors));
//		Main.inertialMatrix.getTRSVector();
		return new Vector2(0, 0);
	}

	public OneDollarRecognizer getOneDRecognizer() {
		return oneDRecognizer;
	}
}
