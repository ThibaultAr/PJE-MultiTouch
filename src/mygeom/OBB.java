package mygeom;

import main.Main;

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
	public double getNormalizeX() {
		return this.origin.getX() / Main.SURFACE_WIDTH;
	}
	
	public double getY() {
		return this.origin.getY();
	}
	public double getNormalizeY() {
		return this.origin.getY() / Main.SURFACE_HEIGHT;
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
	public double getNormalizeWidth() {
		return width / Main.SURFACE_WIDTH;
	}
	public double getHeight() {
		return height;
	}
	public double getNormalizeHeight() {
		return height / Main.SURFACE_HEIGHT;
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
