package mygeom;

public class OBB {
	protected double angle;
	protected Vector2 origin;
	protected double width;
	protected double height;
	
	public OBB(double angle, Vector2 origin, double width, double height) {
		this.angle = angle;
		this.origin = origin;
		this.width = width;
		this.height = height;
	}
	
	public double getX() {
		return this.origin.getX();
	}
	
	public double getY() {
		return this.origin.getY();
	}
	
	public double getAngle() {
		return angle;
	}
	public Vector2 getOrigin() {
		return origin;
	}
	public double getWidth() {
		return width;
	}
	public double getHeight() {
		return height;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public void setOrigin(Vector2 origin) {
		this.origin = origin;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public void setHeight(double height) {
		this.height = height;
	}
}
