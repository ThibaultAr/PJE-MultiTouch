package widget;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.EventObject;

import javax.swing.JComponent;

import main.Main;
import mygeom.OBB;
import mygeom.Point2;
import mygeom.Vector2;
import widget.events.DiscreteEvent;
import widget.events.DiscreteEventListener;
import widget.events.SRTEvent;
import widget.events.SRTEventListener;

public abstract class MTComponent extends JComponent {
	protected OBB obb;
	protected MTContainer container;

	public abstract void draw(Graphics2D g);

	public boolean isInside(Point2 point) {
		return (point.getX() >= this.obb.getOrigin().getX() / Main.SURFACE_WIDTH)
				&& (point.getY() >= this.obb.getOrigin().getY() / Main.SURFACE_HEIGHT)
				&& (point.getX() <= (this.obb.getWidth() + this.obb.getX()) / Main.SURFACE_WIDTH)
				&& (point.getY() <= (this.obb.getHeight() + this.obb.getY()) / Main.SURFACE_HEIGHT);
	}

	public void addDiscreteEventListener(DiscreteEventListener listener) {
		this.listenerList.add(DiscreteEventListener.class, listener);
	}

	public void addSRTEventListener(SRTEventListener listener) {
		this.listenerList.add(SRTEventListener.class, listener);
	}

	public void fireDiscretePerformed(EventObject ev) {
		Object[] listeners = this.listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == DiscreteEventListener.class)
				((DiscreteEventListener) listeners[i + 1]).gesturePerformed((DiscreteEvent) ev);
		}
	}

	public void fireSRTPerformed(EventObject ev) {
		Object[] listeners = this.listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == SRTEventListener.class)
				((SRTEventListener) listeners[i + 1]).gesturePerformed((SRTEvent) ev);
		}
	}

	public void registerContainer(MTContainer container) {
		this.container = container;
	}

	public void click() {
		this.container.select(this);
	}

	public OBB getOBB() {
		return this.obb;
	}

	public void setPosition(double angle, Vector2 origin, int width, int height) {
		this.obb.setAngle(angle);
		this.obb.setOrigin(origin);
		this.obb.setHeight(height);
		this.obb.setWidth(width);
	}
	
	public void updateposition(Vector2 t, double angle, double k) {
		this.obb.setAngle(this.obb.getAngle() + angle);
		this.obb.setHeight(this.obb.getHeight() * k);
		this.obb.setWidth(this.obb.getWidth() * k);
		this.obb.getOrigin().add(t);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((obb == null) ? 0 : obb.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MTComponent other = (MTComponent) obj;
		if (obb == null) {
			if (other.obb != null)
				return false;
		} else if (!obb.equals(other.obb))
			return false;
		return true;
	}
}