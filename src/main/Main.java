package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

import widget.*;
import widget.events.ChangedSideEvent;
import widget.events.ChangedSideListener;
import tuio.*;

public class Main {

	public static final int FRAME_WIDTH = 500;
	public static final int FRAME_HEIGHT = 500;
	public static final int SURFACE_WIDTH = 250;
	public static final int SURFACE_HEIGHT = 250;
	
	
	private static void createGui() {
		JFrame frame = new JFrame("appli");
		MTSurface surface = new MTSurface();
		JButton cursorVisibleButton = new JButton("Cursor Visible");
		
		surface.setPreferredSize(new Dimension(SURFACE_WIDTH,SURFACE_HEIGHT));
		surface.setBackground(new Color(0, 200, 255));
		surface.setBorder(new LineBorder(new Color(255, 0, 0)));
		surface.addChangedSideListener(new ChangedSideListener() {
			@Override
			public void changedSidePerformed(ChangedSideEvent e) {
				System.out.println("curseur d'id : " + e.getCursorId() + " a changé de coté");
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
