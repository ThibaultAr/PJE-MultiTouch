package widget;

import java.awt.Graphics2D;
import java.util.EventObject;

import javax.swing.JComponent;

import mygeom.OBB;
import mygeom.Point2;
import widget.events.DiscreteEvent;
import widget.events.DiscreteEventListener;
import widget.events.SRTEvent;
import widget.events.SRTEventListener;

public abstract class MTComponent extends JComponent {
	protected OBB obb;
	
	public abstract void draw(Graphics2D g);
	
	public boolean isInside(Point2 point) {
		return (point.getX() >= this.obb.getOrigin().getX())
		&& (point.getY() >= this.obb.getOrigin().getY())
		&& (point.getX() <= this.obb.getWidth() + this.obb.getOrigin().getX())
		&& (point.getY() <= this.obb.getHeight() + this.obb.getOrigin().getY());
	}

	public void addDiscreteEventListener(DiscreteEventListener listener) {
		this.listenerList.add(DiscreteEventListener.class, listener);
	}
	
	public void addSRTEventListener(SRTEventListener listener) {
		this.listenerList.add(SRTEventListener.class, listener);
	}
	
	public void fireDiscretePerformed(EventObject ev) {
		Object[] listeners = this.listenerList.getListenerList();
		for(int i = listeners.length - 2; i >= 0; i -= 2){
			if(listeners[i] == DiscreteEventListener.class)
				((DiscreteEventListener) listeners[i+1]).gesturePerformed((DiscreteEvent) ev);
		}
	}
	
	public void fireSRTPerformed(EventObject ev) {
		Object[] listeners = this.listenerList.getListenerList();
		for(int i = listeners.length - 2; i >= 0; i -= 2){
			if(listeners[i] == SRTEventListener.class)
				((SRTEventListener) listeners[i+1]).gesturePerformed((SRTEvent) ev);
		}
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
