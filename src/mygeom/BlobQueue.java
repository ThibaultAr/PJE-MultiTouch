package mygeom;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	
	public boolean checkid (int id) {
		return this.cursor.containsKey(id);
	}
	
	public int getNbCursor() {
		return this.cursor.size();
	}
	
	public Point2 getCursor(int nbCursor, int id) {
		Path[] path = new Path[nbCursor];
		this.cursor.values().toArray(path);
		return path[id].points.get(path[id].points.size() - 1);
	}
	
	public Path get(int id) {
		Path[] path = new Path[this.cursor.values().size()];
		this.cursor.values().toArray(path);
		return path[id];
	}
	
	public List<Path> getPath() {
		return new ArrayList<Path>(this.cursor.values());
	}
}
