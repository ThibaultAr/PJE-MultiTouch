package mygeom;

import java.awt.Color;

import main.Main;

public class InertialMatrix {
	
	public Path path;
	private final int size = 64;
	
	public void setPath(Path path) {
		this.path = path;
	}
	
	public double distance(Point2 p1, Point2 p2) {
		double distX = p2.getX() - p1.getX();
		double distY = p2.getY() - p1.getY();
		double distCarre = distX * distX + distY * distY;
		return Math.sqrt(distCarre);
	}

	public double pathLength(Path points) {
		double d = 0;
		for (int i = 1; i < points.getPoints().size(); i++)
			d += this.distance(points.getPoints().get(i - 1), points.getPoints().get(i));
		return d;
	}
	
	public Path resample(Path points) {
		double I = this.pathLength(points) / (this.size - 1);
		double D = 0;
		Path newPoints = new Path();
		newPoints.add(points.getPoints().get(0));
		for (int i = 1; i < points.getPoints().size(); i++) {
			Point2 pi1 = points.getPoints().get(i - 1);
			Point2 pi = points.getPoints().get(i);
			double d = this.distance(pi1, pi);
			if (D + d >= I) {
				double qx = pi1.getX() + ((I - D) / d) * (pi.getX() - pi1.getX());
				double qy = pi1.getY() + ((I - D) / d) * (pi.getY() - pi1.getY());
				Point2 q = new Point2(qx, qy);
				newPoints.add(q);
				points.getPoints().add(i, q);
				D = 0;
			} else {
				D += d;
			}
		}
		
		if (newPoints.getPoints().size() == this.size - 1)
		{
			newPoints.add(points.getPoints().get(points.getPoints().size() - 1));
		}
		return newPoints;
	}
	
	public OBB getOBB() {
		OBB obb = new OBB();
		
		double a = 0, b = 0, f = 0;
		
		this.path = this.resample(path);
		Point2 centroid = new Point2(path.isoBarycentre());
		
		for(Point2 p : path.getPoints()) {
			a += (p.y - centroid.y) * (p.y - centroid.y);
			b += (p.x - centroid.x) * (p.x - centroid.x);
			f += (p.x - centroid.x) * (p.y - centroid.y);
		}
		
		double r1 = (a + b - Math.sqrt((a - b) * (a - b) + 4 * (f * f))) / 2;
		double r2 = (a + b + Math.sqrt((a - b) * (a - b) + 4 * (f * f))) / 2;
		
		Vector2 u = new Vector2(-f, -a + r1).normalize().mul(50);
		Vector2 v = new Vector2(-f, -a + r2).normalize().mul(50);
		
		centroid.x *= Main.SURFACE_WIDTH;
		centroid.y *= Main.SURFACE_HEIGHT;
		
		DebugDraw.add(new Segment2(centroid, u), 3, Color.red);
		DebugDraw.add(new Segment2(centroid, v), 3, Color.green);
		  
		return obb;
	}
}
