package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import mygeom.InertialMatrix;
import mygeom.Vector2;
import oneDollarRecognizer.GestureEvent;
import oneDollarRecognizer.GestureEventListener;
import widget.MTComponent;
import widget.MTPicture;
import widget.MTSurface;
import widget.events.ChangedSideEvent;
import widget.events.ChangedSideListener;
import widget.events.DiscreteEvent;
import widget.events.DiscreteEventListener;
import widget.events.SRTEvent;
import widget.events.SRTEventListener;

public class Main {

	public static final int FRAME_WIDTH = 1000;
	public static final int FRAME_HEIGHT = 1000;
	public static final int SURFACE_WIDTH = 700;
	public static final int SURFACE_HEIGHT = 700;
	
	private static MTPicture pic;
	private static MTPicture pic2;
	
	public static final InertialMatrix inertialMatrix = new InertialMatrix();
	
	private static void createGui() {
		JFrame frame = new JFrame("appli");
		MTSurface surface = new MTSurface();
		JButton cursorVisibleButton = new JButton("Cursor Visible");
		
		pic = new MTPicture("/data/Bird.jpg", new Vector2 (0,0));
		pic2 = new MTPicture("/data/Snake_River.jpg", new Vector2 (100, 100));
		
		initPictures();

		surface.getContainer().addGestureEventListener(new MainGestureListener());
		
		pic.addDiscreteEventListener(new MainDiscreteListener());
		pic2.addDiscreteEventListener(new MainDiscreteListener());
		
		pic.addSRTEventListener(new MainSRTListener());
		pic2.addSRTEventListener(new MainSRTListener());
		
		
		surface.add(pic);
		surface.add(pic2);
		
		surface.setPreferredSize(new Dimension(SURFACE_WIDTH,SURFACE_HEIGHT));
		surface.setBackground(new Color(0, 200, 255));
		surface.setBorder(new LineBorder(new Color(255, 0, 0)));
		surface.addChangedSideListener(new ChangedSideListener() {
			@Override
			public void changedSidePerformed(ChangedSideEvent e) {
				System.out.println("curseur d'id : " + e.getCursorId() + " a chang� de cot�");
			}
		});
		
		cursorVisibleButton.setPreferredSize(new Dimension(100, 30));
		cursorVisibleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				surface.switchCursorVisible();
			}
		});
		
		
		frame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		
		frame.setLayout(new FlowLayout());
		
		frame.getContentPane().add(surface);
		frame.getContentPane().add(cursorVisibleButton);
		
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void initPictures() {
		pic.setPosition(Math.toRadians(10), new Vector2(500, 500), 200, 200);
		pic2.setPosition(Math.toRadians(30), new Vector2(400,400), 200, 200);
		
		pic.setVisible(true);
		pic2.setVisible(true);
	}
	
	public static void main(String args[]) {
		System.out.println("ok");
		SwingUtilities.invokeLater(new Runnable () {
			@Override
			public void run() {
				createGui();
			}
		});
	}	
	
	
}

class MainDiscreteListener implements DiscreteEventListener {
	
	@Override
	public void gesturePerformed(DiscreteEvent ev) {
		((MTComponent) ev.getSource()).click();
	}
	
}

class MainGestureListener implements GestureEventListener {
	
	@Override
	public void gesturePerformed(GestureEvent ev) {
		System.out.println(ev.getTemplateName() + ", score : " + ev.getScore());
	}
	
}

class MainSRTListener implements SRTEventListener {

	@Override
	public void gesturePerformed(SRTEvent ev) {
		((MTComponent) ev.getSource()).updatePosition(ev.getTranslation(), ev.getAngle(), ev.getScale());
	}
	
}