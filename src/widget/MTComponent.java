package widget;

import java.awt.Graphics2D;

import javax.swing.JComponent;

import mygeom.OBB;

public abstract class MTComponent extends JComponent {
	protected OBB obb;
	
	public abstract void draw(Graphics2D g);
}
