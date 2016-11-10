package widget;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import mygeom.BlobQueue;
import mygeom.Point2;
import tuio.MTedt;

public class MTSurface extends JPanel {
	
	private MTedt edt;
	private BlobQueue blobQueue = new BlobQueue();
	private boolean cursorVisible = true;
	
	public MTSurface() {
		super();
		this.edt = new MTedt(this);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		// drawing instructions with g2.
		if(this.cursorVisible) this.blobQueue.draw(g2);
	}
	
	public void switchCursorVisible() {
		this.cursorVisible = !this.cursorVisible;
	}
	
	public synchronized void addCursor(int id, Point2 p) {
		System.out.println("add cursor id: " + id + ", (x,y) : (" + p.getX() + "," + p.getY() + ")");
		this.blobQueue.addCursor(id, p);
		this.repaint();
	}
	
	public synchronized void removeCursor(int id, Point2 p) {
		System.out.println("remove cursor id: " + id + ", (x,y) : (" + p.getX() + "," + p.getY() + ")");
		this.blobQueue.removeCursor(id);
		this.repaint();
	}
	
	public synchronized void updateCursor(int id, Point2 p) {
		System.out.println("update cursor id: " + id + ", (x,y) : (" + p.getX() + "," + p.getY() + ")");
		this.blobQueue.updateCursor(id, p);
		this.repaint();
	}

}
