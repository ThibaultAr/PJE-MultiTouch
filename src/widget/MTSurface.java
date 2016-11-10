package widget;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import mygeom.Path;
import mygeom.Point2;
import tuio.MTedt;

public class MTSurface extends JPanel {
	
	private MTedt edt;
	private Path path = new Path();
	
	public MTSurface() {
		super();
		this.edt = new MTedt(this);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		// drawing instructions with g2.
		this.path.draw(g2);
	}
	
	public synchronized void addCursor(int id, Point2 p) {
		System.out.println("add cursor id: " + id + ", (x,y) : (" + p.getX() + "," + p.getY() + ")");
		this.path.clear();
		this.path.setCursorId(id);
		this.path.add(p);
		this.repaint();
	}
	
	public synchronized void removeCursor(int id, Point2 p) {
		System.out.println("remove cursor id: " + id + ", (x,y) : (" + p.getX() + "," + p.getY() + ")");
		this.path.clear();
		this.repaint();
	}
	
	public synchronized void updateCursor(int id, Point2 p) {
		System.out.println("update cursor id: " + id + ", (x,y) : (" + p.getX() + "," + p.getY() + ")");
		this.path.add(p);
		this.repaint();
	}

}
