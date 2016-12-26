package mygeom;

import main.Main;

public class Point2 extends Tuple2 {

		public Point2() {
			super();
		}
	
		public Point2(Point2 a) {
			super(a);
		}
		
		public Point2(Point2 a,Point2 b) {
			super(b);
			this.sub(a);
		}
		
		public Point2(double x,double y) {
			super(x,y);
		}
		
		public Point2 toPixels() {
			this.x *= Main.SURFACE_WIDTH;
			this.y *= Main.SURFACE_HEIGHT;
			return this;
		}
		
		public Point2 toUnit() {
			this.x /= Main.SURFACE_WIDTH;
			this.y /= Main.SURFACE_HEIGHT;
			return this;
		}
		
		public double dist(Point2 p) {
			double a = (p.x - this.x) * (p.x - this.x);
			double b = (p.y - this.y) * (p.y - this.y);
			return Math.sqrt(a + b);
		}
				
}
