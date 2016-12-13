package widget;

import java.awt.AWTEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

import mygeom.BlobQueue;
import mygeom.Point2;
import tuio.MTedt;
import widget.events.ChangedSideEvent;
import widget.events.ChangedSideListener;

public class MTSurface extends JPanel {
	
	private MTedt edt;
	private ComponentMap Cmap;
	private boolean cursorVisible = true;
	private Point2 previousPos;
	private MTContainer container;
	
	private EventListenerList listenerList = null;
	
	public MTSurface() {
		super();
		this.Cmap = new ComponentMap();
		this.edt = new MTedt(this);
		this.listenerList = new EventListenerList();
		this.container = new MTContainer();
	}
	
	public MTContainer getContainer() {
		return this.container;
	}
	
	public void add(MTComponent component) {
		this.container.add(component);
	}
	
	public void addChangedSideListener(ChangedSideListener l) {
		this.listenerList.add(ChangedSideListener.class, l);
	}
	
	public void fireChangedListenerPerformed(AWTEvent e) {
		Object[] listeners = this.listenerList.getListenerList();
		for(int i = listeners.length - 1; i >= 0; i--) {
			if(listeners[i] == ChangedSideListener.class) {
				((ChangedSideListener)listeners[i + 1]).changedSidePerformed((ChangedSideEvent) e);
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		// drawing instructions with g2.
		this.container.draw(g2);
		if(this.cursorVisible) this.Cmap.draw(g2);
	}
	
	public void switchCursorVisible() {
		this.cursorVisible = !this.cursorVisible;
	}
	
	public synchronized void addCursor(int id, Point2 p) {
		//System.out.println("add cursor id: " + id + ", (x,y) : (" + p.getX() + "," + p.getY() + ")");
		this.Cmap.addBlob(this.container.whichIs(p), id, p);
		this.repaint();
		
		this.previousPos = p;
	}
	
	public synchronized void removeCursor(int id, Point2 p) {
		//System.out.println("remove cursor id: " + id + ", (x,y) : (" + p.getX() + "," + p.getY() + ")");
		this.Cmap.removeBlob(id, p);
		this.repaint();
	}
	
	public synchronized void updateCursor(int id, Point2 p) {
		//System.out.println("update cursor id: " + id + ", (x,y) : (" + p.getX() + "," + p.getY() + ")");
		this.Cmap.updateBlob(id, p);
		this.repaint();
		
		double middleX = 0.5;

		if(this.previousPos.getX() <= middleX && p.getX() > middleX || this.previousPos.getX() > middleX && p.getX() <= middleX)
			this.fireChangedListenerPerformed(new ChangedSideEvent(this, 0, id));
		
		this.previousPos = p;
	}

}
