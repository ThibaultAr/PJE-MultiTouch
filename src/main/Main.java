package main;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

import widget.*;
import tuio.*;

public class Main {

	public static final int FRAME_WIDTH = 500;
	public static final int FRAME_HEIGHT = 500;
	public static final int SURFACE_WIDTH = 250;
	public static final int SURFACE_HEIGHT = 250;
	
	
	private static void createGui() {
		JFrame frame = new JFrame("appli");
		MTSurface surface = new MTSurface();
		surface.setPreferredSize(new Dimension(SURFACE_WIDTH,SURFACE_HEIGHT));
		surface.setBackground(new Color(0, 200, 255));
		surface.setBorder(new LineBorder(new Color(255, 0, 0)));
		
		frame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		
		frame.setLayout(new FlowLayout());
		
		frame.getContentPane().add(surface);
		
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
