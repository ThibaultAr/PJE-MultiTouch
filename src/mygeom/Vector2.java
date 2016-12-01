package mygeom;

public class Vector2 extends Tuple2 {
	
	public Vector2() {
		super();
	}
	
	public Vector2(Vector2 a) {
		super(a);
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
	
	public Vector2 normalize() {
		double longueur = Math.sqrt(x * x + y * y);
		
		this.x = this.x / longueur;			
		this.y = this.y / longueur;
		
		return this;
	}
}
