package widget.events;

import java.util.EventObject;

import mygeom.Vector2;


public class SRTEvent extends EventObject {

	protected Vector2 translation;
	protected double angle, scale;
	
	public SRTEvent(Object source, Vector2 translation, double angle, double scale) {
		super(source);
		this.translation = translation;
		this.angle = angle;
		this.scale = scale;
	}

	public Vector2 getTranslation() {
		return translation;
	}
	
	public double getAngle() {
		return this.angle;
	}
	
	public double getScale() {
		return this.scale;
	}
}
