package mygeom;

import java.awt.Graphics2D;
import java.util.HashMap;

public class BlobQueue {
	
	protected HashMap<Integer, Path> cursor;
	
	public BlobQueue() {
		this.cursor = new HashMap<Integer, Path>();
	}
	
	public void addCursor(int id, Point2 point) {
		Path path = new Path();
		path.add(point);
		path.setCursorId(id);
		this.cursor.put(id, path);
	}
	
	public void removeCursor(int id) {
		this.cursor.remove(id);
	}
	
	public void updateCursor(int id, Point2 point) {
		this.cursor.get(id).add(point);
	}
	
	public void draw(Graphics2D g2) {
		for(Path path : this.cursor.values()) {
			path.draw(g2);
		}
	}
}
