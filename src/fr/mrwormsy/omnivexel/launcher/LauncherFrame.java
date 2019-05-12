package fr.mrwormsy.omnivexel.launcher;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class LauncherFrame extends JFrame {
	private static final long serialVersionUID = 1L;


	
	private static LauncherFrame instance;
	private LauncherPanel launcherPanel;
	
	//TODO CHANGE Authentification to Authentication

	
	public static void main(String[] args) {
		
		instance = new LauncherFrame();
		
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private LauncherFrame() {	
		
		this.setTitle("Omnivexel Launcher");
		this.setSize(690, 540);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.setIconImage(new ImageIcon(LauncherFrame.class.getResource("/fr/mrwormsy/omnivexel/launcher/resources/logo.png")).getImage());
		this.setResizable(false);
		this.getContentPane().setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		this.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		this.setBounds(100, 100, 960, 540);
		this.setPreferredSize(new Dimension(960, 540));
		this.setLayout(null);
		
		//Move the window
		Point point = new Point();
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (!e.isMetaDown()) {
					point.x = e.getX();
					point.y = e.getY();
				}
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (!e.isMetaDown()) {
					Point p = getLocation();
					setLocation(p.x + e.getX() - point.x, p.y + e.getY() - point.y);
				}
			}
		});
		
		launcherPanel = new LauncherPanel();
		
		this.getContentPane().add(launcherPanel);
		
		this.setVisible(true);
		
	}

	public static LauncherFrame getInstance() {
		return instance;
	}
	

}
