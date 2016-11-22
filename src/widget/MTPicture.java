package widget;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import mygeom.OBB;
import mygeom.Vector2;

public class MTPicture extends MTComponent {

	private BufferedImage image;

	public MTPicture(String name, Vector2 origin) {
		try {
			this.image = ImageIO.read(new File(name));
			this.obb = new OBB(0, origin, this.image.getWidth()/5, this.image.getHeight()/5);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(image, (int) this.obb.getOrigin().getX(), (int) this.obb.getOrigin().getY(), this.obb.getWidth(),
				this.obb.getHeight(), null);
	}

}
