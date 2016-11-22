package widget;

import java.awt.Graphics2D;
import java.util.HashMap;

import mygeom.BlobQueue;
import mygeom.Point2;

public class ComponentMap {
	protected HashMap<MTComponent, BlobQueue> Cmap = new HashMap<MTComponent, BlobQueue>();
	protected GestureAnalyzer analyzer = new GestureAnalyzer(); 
	
	public void addBlob (MTComponent component, int id, Point2 point) {
		if(!this.Cmap.containsKey(component))
			this.Cmap.put(component, new BlobQueue());
		this.Cmap.get(component).addCursor(id, point);
		analyzer.analyze(component, this.Cmap.get(component), "Add", id, point);
	}
	
	public void updateBlob (int id, Point2 point) {
		for(MTComponent comp : this.Cmap.keySet())
			if(this.Cmap.get(comp).checkid(id)) {
				this.Cmap.get(comp).updateCursor(id, point);
				analyzer.analyze(comp, this.Cmap.get(comp), "Update", id, point);
			}
	}
	
	public void removeBlob (int id, Point2 point) {
		for(MTComponent comp : this.Cmap.keySet())
			if(this.Cmap.get(comp).checkid(id)) {
				this.Cmap.get(comp).removeCursor(id);
				analyzer.analyze(comp, this.Cmap.get(comp), "Remove", id, point);
			}
	}
	
	public void draw (Graphics2D g2){
		for(BlobQueue bq : this.Cmap.values())
			bq.draw(g2);
	}
}
