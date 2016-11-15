package widget;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class MTContainer extends MTComponent {
	protected List<MTComponent> components = new ArrayList<MTComponent>();

	
	public void add(MTComponent component) {
		this.components.add(component);
	}
	
	@Override
	public void draw(Graphics2D g) {
		for(MTComponent component : this.components)
			component.draw(g);
	}
}
