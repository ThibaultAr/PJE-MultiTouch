package widget;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import mygeom.OBB;
import mygeom.Vector2;
import oneDollarRecognizer.GestureEvent;
import oneDollarRecognizer.GestureEventListener;

public class MTPicture extends MTComponent {

	private BufferedImage image;

	public MTPicture(String name) {
		super();
		try {
			this.image = ImageIO.read(new File(new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getParent() + name));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.addGestureEventListener(new PicGestureListener());
	}
	
	public MTPicture() {
		super();
		
		File file = new File(new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getParent() + "/data");
		String[] imageNames = file.list();
		Random random = new Random();
		String name = imageNames[random.nextInt(imageNames.length)];
		
		try {
			this.image = ImageIO.read(new File(file.getAbsolutePath() + "/" + name));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.addGestureEventListener(new PicGestureListener());
	}

	@Override
	public void draw(Graphics2D g) {
		if(this.isVisible()) g.drawImage(image, 0, 0, (int) this.obb.getWidth(), (int) this.obb.getHeight(), null);
	}

}

class PicGestureListener implements GestureEventListener {
	
	@Override
	public void gesturePerformed(GestureEvent ev) {
		System.out.println("test");
		if(ev.getNbDoigts() == 3) {
			System.out.println(ev.getTemplateName() + ", score : " + ev.getScore());
			if(ev.getTemplateName().equals("delete")) {
				((MTPicture) ev.getSource()).setVisible(false);
			}
		}
	}
	
}
