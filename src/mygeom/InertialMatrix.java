package mygeom;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

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
		
		Vector2 u = new Vector2(-f, -a + r1).normalize();
		Vector2 v = new Vector2(-f, -a + r2).normalize();
		
		
		Point2 c = new Point2(centroid);
		c.x *= Main.SURFACE_WIDTH;
		c.y *= Main.SURFACE_HEIGHT;
		
		DebugDraw.add(new Segment2(c, new Vector2(u).mul(50)), 3, Color.red);
		DebugDraw.add(new Segment2(c, new Vector2(v).mul(50)), 3, Color.green);
		
		AffineTransform transform = new AffineTransform();
		transform.setToTranslation(centroid.x, centroid.y);
				
		double dot = new Vector2(1,0).dot(v);
		double det = new Vector2(1,0).determinant(v);
		
		if(dot > 1) dot = 1;
		
		double angle = Math.signum(det) * Math.acos(dot);
		transform.rotate(angle);
		
		double scale = u.length() / new Vector2(1,0).length();
		transform.scale(scale, scale);
		
		double minX = Double.MAX_VALUE;
		double maxX = Double.MIN_VALUE;
		double minY = Double.MAX_VALUE;
		double maxY = Double.MIN_VALUE;

		for(Point2 p : this.path.getPoints()) {
			if (p.getX() > maxX)
				maxX = p.getX();
			if (p.getX() < minX)
				minX = p.getX();
			if (p.getY() > maxY)
				maxY = p.getY();
			if (p.getY() < minY)
				minY = p.getY();
		}
		
		double width = maxX - minX;
		double height = maxY - minY;
		
		Point2D x1 = new Point2D.Double(-width / 2, -height / 2);
		Point2D x2 = new Point2D.Double(width / 2, -height / 2);
		Point2D y1 = new Point2D.Double(-width / 2, height / 2);
		Point2D y2 = new Point2D.Double(width / 2, height / 2);
		
		Point2D x1P = new Point2D.Double();
		Point2D x2P = new Point2D.Double();
		Point2D y1P = new Point2D.Double();
		Point2D y2P = new Point2D.Double();
		
		transform.transform(x1, x1P);
		transform.transform(x2, x2P);
		transform.transform(y1, y1P);
		transform.transform(y2, y2P);
		
		Point2 p1 = new Point2(x1P.getX() * Main.SURFACE_WIDTH, x1P.getY() * Main.SURFACE_HEIGHT);
		Point2 p2 = new Point2(x2P.getX() * Main.SURFACE_WIDTH, x2P.getY() * Main.SURFACE_HEIGHT);
		Point2 p3 = new Point2(y1P.getX() * Main.SURFACE_WIDTH, y1P.getY() * Main.SURFACE_HEIGHT);
		Point2 p4 = new Point2(y2P.getX() * Main.SURFACE_WIDTH, y2P.getY() * Main.SURFACE_HEIGHT);
		
		DebugDraw.add(new Segment2(p1, p2), 3, Color.black);
		DebugDraw.add(new Segment2(p1, p3), 3, Color.black);
		DebugDraw.add(new Segment2(p2, p4), 3, Color.black);
		DebugDraw.add(new Segment2(p3, p4), 3, Color.black);
		
		return obb;
	}
}
