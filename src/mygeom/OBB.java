package mygeom;

public class OBB {
	protected int angle;
	protected Vector2 origin;
	protected int width;
	protected int height;
	
	public OBB(int angle, Vector2 origin, int width, int height) {
		this.angle = angle;
		this.origin = origin;
		this.width = width;
		this.height = height;
	}
	
	public int getAngle() {
		return angle;
	}
	public Vector2 getOrigin() {
		return origin;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}

	public void setOrigin(Vector2 origin) {
		this.origin = origin;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
