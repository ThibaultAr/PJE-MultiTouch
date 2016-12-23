package oneDollarRecognizer;

import java.util.Vector;

import mygeom.Path;
import mygeom.Point2;
import mygeom.Tuple2;

public class OneDollarRecognizer {
	
	int size;
	Vector<Template> templates;
	
	public OneDollarRecognizer() {
		this.size = 64;
		this.templates = new TemplateManager("data/gestures.xml").getTemplates();
		
		for(Template t : this.templates) {
			Path p = new Path();
			p.fromTuples(t.getPoints());
			p = this.resample(p);
			p = this.rotateToZero(p);
			p = this.scaleToSquare(p);
			p = this.translateToOrigin(p);
			
			t.setPoints(p.toTuples());
		}
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

	public Path rotateToZero(Path points) {
		Point2 c = points.isoBarycentre();
		double teta = Math.atan2(c.getY() - points.getPoints().get(0).getY(),
				c.getX() - points.getPoints().get(0).getX());

		return this.rotateBy(points, -teta);
	}

	public Path rotateBy(Path points, double angle) {
		Point2 c = points.isoBarycentre();
		Path newPoints = new Path();
		for (Point2 p : points.getPoints()) {
			double qx = (p.getX() - c.getX()) * Math.cos(angle) - (p.getY() - c.getY()) * Math.sin(angle) + c.getX();
			double qy = (p.getX() - c.getX()) * Math.sin(angle) + (p.getY() - c.getY()) * Math.cos(angle) + c.getX();

			newPoints.add(new Point2(qx, qy));
		}
		return newPoints;
	}

	public Path scaleToSquare(Path points) {
		double bWidth = this.boundingWidth(points);
		double bHeight = this.boundingHeight(points);
		Path newPoints = new Path();
		for (Point2 p : points.getPoints()) {
			double qx = p.getX() * (this.size / bWidth);
			double qy = p.getY() * (this.size / bHeight);
			newPoints.add(new Point2(qx, qy));
		}
		return newPoints;
	}

	private double boundingWidth(Path points) {
		double minX = points.getPoints().get(0).getX();
		double maxX = points.getPoints().get(0).getX();
		for (Point2 p : points.getPoints()) {
			if (p.getX() > maxX)
				maxX = p.getX();
			if (p.getX() < minX)
				minX = p.getX();
		}
		return maxX - minX;
	}

	private double boundingHeight(Path points) {
		double minY = points.getPoints().get(0).getY();
		double maxY = points.getPoints().get(0).getY();
		for (Point2 p : points.getPoints()) {
			if (p.getY() > maxY)
				maxY = p.getY();
			if (p.getY() < minY)
				minY = p.getY();
		}
		return maxY - minY;
	}

	public Path translateToOrigin(Path points) {
		Point2 c = points.isoBarycentre();
		Path newPoints = new Path();
		for (Point2 p : points.getPoints()) {
			double qx = p.getX() - c.getX();
			double qy = p.getY() - c.getY();
			newPoints.add(new Point2(qx, qy));
		}
		return newPoints;
	}

	public GestureEvent recognize(Path points) {
		double b = Double.MAX_VALUE;
		Template t2 = this.templates.get(0);
		Point2 c = points.isoBarycentre();
		double teta = Math.atan2(c.getY() - points.getPoints().get(0).getY(),
				c.getX() - points.getPoints().get(0).getX());
		for (Template t : this.templates) {
			double d = this.distanceAtAngle(points, t);
			if (d < b) {
				b = d;
				t2 = t;
			}
		}
		double score = 1 - b / (0.5 * Math.sqrt(this.size * this.size + this.size * this.size));
		return new GestureEvent(this, t2.getName(), score);
	}

	public double distanceAtAngle(Path points, Template t) {
		Path newPoints = this.rotateToZero(points);
		double d = this.pathDistance(newPoints, t.getPoints());
		return d;
	}

	public double pathDistance(Path points, Vector<Tuple2> tpoints) {
		double d = 0;
		for (int i = 0; i < points.getPoints().size(); i++) {
			d += this.distance(points.getPoints().get(i), new Point2(tpoints.get(i).getX(), tpoints.get(i).getY()));
		}
		return d / points.getPoints().size();
	}
}
