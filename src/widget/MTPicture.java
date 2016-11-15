package widget;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import mygeom.OBB;

public class MTPicture extends MTComponent {

	private BufferedImage image;

	public MTPicture(String name) {
		try {
			this.image = ImageIO.read(new File(name));
			this.obb = new OBB();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(image, (int) this.obb.getOrigin().getX(), (int) this.obb.getOrigin().getY(), (int) this.obb.getWidth(),
				(int) this.obb.getHeight(), null);
	}

}
