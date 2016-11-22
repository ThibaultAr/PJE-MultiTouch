package widget.events;

import java.util.EventObject;

import mygeom.Vector2;


public class SRTEvent extends EventObject {

	protected Vector2 translation;
	
	public SRTEvent(Object source, Vector2 translation) {
		super(source);
		this.translation = translation;
	}

	public Vector2 getTranslation() {
		return translation;
	}
}
