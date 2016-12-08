package oneDollarRecognizer;

import mygeom.Path;
import mygeom.Point2;

public class OneDollarRecognizer {
	public GestureEvent recognize(Path points) {
		return null;
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

	public Path resample(Path points, int n) {
		double I = this.pathLength(points) / (n - 1);
		double D = 0;
		Path newPoints = new Path();
		newPoints.add(points.getPoints().get(0));
		for (int i = 1; i < points.getPoints().size(); i++) { // SI PROBLEME
																// C'EST ICI
																// !!!!
			Point2 pi1 = points.getPoints().get(i - 1);
			Point2 pi = points.getPoints().get(i);
			double d = this.distance(pi1, pi);
			if (D + d >= I) {
				double qx = pi1.getX() + ((I - D) / d) * (pi.getX() - pi1.getX());
				double qy = pi1.getY() + ((I - D) / d) * (pi.getY() - pi1.getY());
				Point2 q = new Point2(qx, qy);
				newPoints.add(q);
				points.getPoints().add(i + 1, q); // OU ICI !!!!
				D = 0;
			} else {
				D += d;
			}
		}
		return newPoints;
	}

	public Path rotateToZero(Path points) {
		Point2 c = points.isoBarycentre();
		double teta = Math.atan2(c.getY() - points.getPoints().get(0).getY(),
				c.getX() - points.getPoints().get(0).getX());

		return this.rotateBy(points, -teta);
	}
	
	public Path rotateBy(Path points, double angle) {
		Point2 c = points.isoBarycentre();
		Path newPoints = new Path();
		for(Point2 p : points.getPoints()) {
			double qx = (p.getX() - c.getX()) * Math.cos(angle) - (p.getY() - c.getY()) * Math.sin(angle) + c.getX();
			double qy = (p.getX() - c.getX()) * Math.cos(angle) + (p.getY() - c.getY()) * Math.sin(angle) + c.getX();
			
			newPoints.add(new Point2(qx, qy));
		}
		return newPoints;
	}
}
