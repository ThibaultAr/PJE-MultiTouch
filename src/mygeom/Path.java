package mygeom;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;

import main.Main;

public class Path {

	protected List<Point2> points = new ArrayList<Point2>();
	protected int cursorId;

	public void add(Point2 p) {
		this.points.add(p); 
	}
	
	public void setCursorId(int id) {
		this.cursorId = id;
	}
	
	public void clear() {
		this.points.clear();
	}
	
	protected Point2 isoBarycentre() {
		double sumX = 0;
		double sumY = 0;

		for(Point2 p : this.points) {
			sumX += p.getX();
			sumY += p.getY();
		}
		
		return new Point2(sumX / this.points.size(), sumY / this.points.size());
	}
	
	public void draw(Graphics2D g2) {
		GeneralPath p = new GeneralPath();
		if(this.points.isEmpty())
			return;
		p.moveTo(this.points.get(0).getX() * Main.SURFACE_WIDTH, this.points.get(0).getY() * Main.SURFACE_HEIGHT);
		for(Point2 each : this.points) {
			p.lineTo(each.getX()*Main.SURFACE_WIDTH, each.getY()*Main.SURFACE_HEIGHT);
		}
		
		Point2 lastPoint = this.points.get(this.points.size()-1);
		Point2 isoBarycentre = this.isoBarycentre();
		
		g2.draw(new Ellipse2D.Double(lastPoint.getX()* Main.SURFACE_WIDTH -10, lastPoint.getY() * Main.SURFACE_HEIGHT -10, 20, 20));
		g2.drawString("" + cursorId, (float) (lastPoint.getX()* Main.SURFACE_WIDTH - 3), (float) (lastPoint.getY() * Main.SURFACE_HEIGHT -2));
		
		g2.fill(new Ellipse2D.Double(isoBarycentre.getX() * Main.SURFACE_WIDTH - 5, isoBarycentre.getY() * Main.SURFACE_HEIGHT - 5, 10, 10));
		
		g2.draw(p);
	}

	public List<Point2> getPoints() {
		return points;
	}
}
