package widget;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import main.Main;
import mygeom.OBB;
import mygeom.Point2;
import mygeom.Vector2;
import oneDollarRecognizer.GestureEvent;
import oneDollarRecognizer.GestureEventListener;

public class MTContainer extends MTComponent {
	protected List<MTComponent> components = new ArrayList<MTComponent>();
	
	public MTContainer() {
		super();
		this.addGestureEventListener(new ContainerGestureEventListener());
	}
	
	public void add(MTComponent component) {
		this.components.add(component);
	}

	@Override
	public void draw(Graphics2D g) {
		AffineTransform transform = g.getTransform();
		for (MTComponent component : this.components) {
			g.translate(component.obb.getX(), component.obb.getY());
			g.rotate(component.obb.getAngle());
			
			component.draw(g);			
			g.setTransform(transform);
			
		}
	}

	public MTComponent whichIs(Point2 point) {
		List<MTComponent> reverse = new ArrayList<MTComponent>();
		reverse.addAll(this.components);
		Collections.reverse(reverse);
		for (MTComponent comp : reverse) {			
			if (comp.isInside(point)) {
				comp.registerContainer(this);
				return comp;
			}
		}
		return this;
	}

	public void select(MTComponent comp) {
		this.components.remove(comp);
		this.components.add(comp);
	}
}

class ContainerGestureEventListener implements GestureEventListener {

	@Override
	public void gesturePerformed(GestureEvent e) {
		if(e.getTemplateName().equals("circle"))
			Main.initPictures();
//		else if(e.getTemplateName().equals("rectangle")) {
			Main.inertialMatrix.setPath(e.getPath());
			Main.inertialMatrix.getOBB();
//		}
	}
	
}
