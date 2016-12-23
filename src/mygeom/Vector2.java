package mygeom;

public class Vector2 extends Tuple2 {
	
	public Vector2() {
		super();
	}
	
	public Vector2(Vector2 a) {
		super(a);
	}
	
	public Vector2(Segment2 s) {
		super(new Point2((Point2)s.end.sub(s.init)));
	}
	
	public Vector2(double x,double y) {
		super(x,y);
	}
	
	public double dot(Vector2 v) {
		return this.x * v.x + this.y * v.y;
	}
	
	public double determinant(Vector2 v) {
		return this.x * v.y - this.y * v.x;
	}
	
	public double det(Vector2 v) {
		return this.determinant(v);
	}
	
	public double length() {
		return Math.sqrt(x * x + y * y);
	}
	
	public Vector2 mul(double scalar) {
		this.x *= scalar;
		this.y *= scalar;
		return this;
	}
	
	public Vector2 normalize() {
		double longueur = this.length();
		
		this.x = this.x / longueur;			
		this.y = this.y / longueur;
		
		return this;
	}
}
