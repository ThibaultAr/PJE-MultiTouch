package widget;

import mygeom.OBB;
import mygeom.Vector2;

public class InternalGestureState {
	protected OBB oldOBB, currentOBB;
	protected Vector2 oldPos, currentPos;
	
	public InternalGestureState(MTComponent c) {
		this.oldPos = new Vector2();
		this.currentPos = new Vector2();
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
}
