package fr.mrwormsy.omnivexel.launcher;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class OmnivexelLauncher {

	private JFrame frame;
	private JTextField textField;
	private JPasswordField passInput;
	private JCheckBox chckbxNewCheckBox;
	private JButton launchButton;
	private JSlider ramSelector;
	private JTextField ramIndicator;
	private JButton closeButton;
	private JButton reduceButton;
	private JButton connectionToServerOn;
	private JButton connectionToServerOff;
	private JPanel panel;
	private JLabel backgroundLabel;
	
	private boolean connectionToOmnivexelServer;

	//TODO CHANGE Authentification to Authentication
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OmnivexelLauncher window = new OmnivexelLauncher();

					window.frame.setVisible(true);
					
					//window.refreshTextures();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public OmnivexelLauncher() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		ImageIcon authMethodImage1 = new ImageIcon(OmnivexelLauncher.class.getResource("/resources/OmnixexelAuth.png"));
		ImageIcon authMethodImage2 = new ImageIcon(OmnivexelLauncher.class.getResource("/resources/NoPassAuth.png"));
		
		ImageIcon backgroundWithPass = new ImageIcon(OmnivexelLauncher.class.getResource("/resources/backgroundWithPass.png"));
		ImageIcon backgroundWithoutPass = new ImageIcon(OmnivexelLauncher.class.getResource("/resources/backgroundWithoutPass.png"));
		
		frame = new JFrame();
		frame.setUndecorated(true);
		frame.getContentPane().setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		frame.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		frame.setResizable(false);
		frame.setBounds(100, 100, 960, 540);
		frame.setPreferredSize(new Dimension(960, 540));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		Image logo = new ImageIcon(OmnivexelLauncher.class.getResource("/resources/logo.png")).getImage();
		frame.setIconImage(logo);
		frame.setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(0, 0, 960, 540);
		panel.setLayout(null);
		panel.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		
		backgroundLabel = new JLabel(backgroundWithPass);
		backgroundLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
		backgroundLabel.setBounds(0, 0, 960, 540);
		panel.add(backgroundLabel);
		
		chckbxNewCheckBox = new JCheckBox();
		//chckbxNewCheckBox.setBackground(new Color(53, 43, 49));
		//chckbxNewCheckBox.setForeground(new Color(245, 245, 212));
		//chckbxNewCheckBox.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		chckbxNewCheckBox.setBackground(new Color(205, 214, 156));
		chckbxNewCheckBox.setBounds(570, 346, 20, 20);
		chckbxNewCheckBox.setBorder(null);
		ImageIcon rememberMeUnselected = new ImageIcon(OmnivexelLauncher.class.getResource("/resources/rememberMeUnselected.png"));
		chckbxNewCheckBox.setIcon(rememberMeUnselected);
		ImageIcon rememberMeSelected = new ImageIcon(OmnivexelLauncher.class.getResource("/resources/rememberMeSelected.png"));
		chckbxNewCheckBox.setSelectedIcon(rememberMeSelected);
		panel.add(chckbxNewCheckBox);
		
		
		textField = new JTextField();
		textField.setFont(new Font("SansSerif", Font.PLAIN, 28));
		textField.setBounds(728, 193, 188, 36);
		textField.setBackground(new Color(89,84,74));
		textField.setCaretColor(new Color(245, 245, 212));
		textField.setForeground(new Color(245, 245, 212));
		textField.setBorder(null);
		panel.add(textField);
		
		passInput = new JPasswordField();
		passInput.setFont(new Font("SansSerif", Font.PLAIN, 28));
		passInput.setBounds(728, 257, 188, 36);
		passInput.setBackground(new Color(89,84,74));
		passInput.setCaretColor(new Color(245, 245, 212));
		passInput.setForeground(new Color(245, 245, 212));
		passInput.setBorder(null);
		panel.add(passInput);
		
		launchButton = new JButton();
		ImageIcon launch = new ImageIcon(OmnivexelLauncher.class.getResource("/resources/launch.png"));
		launchButton.setIcon(launch);
		launchButton.setBounds(777, 346, 130, 50);
		launchButton.setBorder(null);
		launchButton.setBackground(new Color(205, 214, 156));
		panel.add(launchButton);
		
		ramSelector = new JSlider(0, 1, 8, 1);
		ramSelector.setBounds(571, 376, 110, 20);
		ramSelector.setBackground(new Color(197, 205, 150));
		ramSelector.setForeground(new Color(53,43,49));	
		panel.add(ramSelector);
		
		ramSelector.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				ramIndicator.setText(0.5f * ramSelector.getValue() + " GB");
			}
		});
		
		ramIndicator = new JTextField("0.5 GB");
		ramIndicator.setFont(new Font("SansSerif", Font.PLAIN, 12));
		ramIndicator.setBounds(688, 378, 48, 16);
		ramIndicator.setBackground(new Color(53,43,49));	
		ramIndicator.setForeground(new Color(245, 245, 212));
		ramIndicator.setBorder(null);
		ramIndicator.setEditable(false);
		panel.add(ramIndicator);
		
		closeButton = new JButton();
		ImageIcon close = new ImageIcon(OmnivexelLauncher.class.getResource("/resources/close.png"));
		closeButton.setIcon(close);
		closeButton.setBounds(920, 11, 16, 16);
		closeButton.setBorder(null);
		closeButton.setBackground(new Color(53,43,49));
		panel.add(closeButton);
		
		closeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				System.exit(0);
			}
		});
		
		reduceButton = new JButton();
		ImageIcon reduce = new ImageIcon(OmnivexelLauncher.class.getResource("/resources/reduce.png"));
		reduceButton.setIcon(reduce);
		reduceButton.setBounds(890, 11, 16, 16);
		reduceButton.setBorder(null);
		reduceButton.setBackground(new Color(53,43,49));
		panel.add(reduceButton);
		
		reduceButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setState(Frame.ICONIFIED);
				
			}
		});
		
		connectionToOmnivexelServer = true;
		
		connectionToServerOn = new JButton();
		ImageIcon omnixelxelConnectionOnButton = new ImageIcon(OmnivexelLauncher.class.getResource("/resources/OmnixelxelConnectionOnButton.png"));
		connectionToServerOn.setIcon(omnixelxelConnectionOnButton);
		connectionToServerOn.setBounds(870, 308, 40, 25);
		connectionToServerOn.setBorder(null);
		connectionToServerOn.setBackground(new Color(197, 205, 150));
		connectionToServerOn.setForeground(new Color(197, 205, 150));
		panel.add(connectionToServerOn);
		
		connectionToServerOn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.remove(connectionToServerOn);
				//connectionToServerOn.repaint();
				//connectionToServerOn.setVisible(false);
				panel.add(connectionToServerOff);
				//connectionToServerOff.repaint();
				//connectionToServerOff.setVisible(true);
				
				panel.remove(passInput);
				backgroundLabel.setIcon(backgroundWithoutPass);
				
				//frame.setVisible(true);
				
				refreshTextures();
				
				reduceButton.repaint();
				
				connectionToOmnivexelServer = false;
			}
		});
		
		connectionToServerOff = new JButton();
		ImageIcon omnixelxelConnectionOffButton = new ImageIcon(OmnivexelLauncher.class.getResource("/resources/OmnixelxelConnectionOffButton.png"));
		connectionToServerOff.setIcon(omnixelxelConnectionOffButton);
		connectionToServerOff.setBounds(870, 308, 40, 25);
		connectionToServerOff.setBorder(null);
		connectionToServerOff.setBackground(new Color(197, 205, 150));
		connectionToServerOff.setForeground(new Color(197, 205, 150));
		//panel.add(connectionToServerOff);
		
		connectionToServerOff.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//connectionToServerOff.setVisible(false);
				panel.remove(connectionToServerOff);
				//connectionToServerOff.repaint();
				//connectionToServerOn.setVisible(true);
				panel.add(connectionToServerOn);
				//connectionToServerOn.repaint();
				
				backgroundLabel.setIcon(backgroundWithPass);
				panel.add(passInput);
				
				refreshTextures();
				
				reduceButton.repaint();
				
				connectionToOmnivexelServer = true;
			}
		});
		
	
		
		//565 308
		
		// Enable to move the frame with the mouse
		
		
		Point point = new Point();
		frame.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (!e.isMetaDown()) {
					point.x = e.getX();
					point.y = e.getY();
				}
			}
		});
		frame.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (!e.isMetaDown()) {
					Point p = frame.getLocation();
					frame.setLocation(p.x + e.getX() - point.x, p.y + e.getY() - point.y);
				}
			}
		});		
		
		frame.getContentPane().add(panel);
		frame.setVisible(true);
		
		refreshTextures();
	}
	
	public void refreshTextures() {
		/*
		
		chckbxNewCheckBox.repaint();
		closeButton.repaint();
		reduceButton.repaint();
		connectionToServerOn.repaint();
		connectionToServerOff.repaint();
		ramSelector.repaint();
		*/
		
		//First we need to paint the backgroud
		backgroundLabel.repaint();
		
		for(Component c : panel.getComponents()) {
			
				//if (!panel.getComponentAt(0, 0).equals(c)) {				
				//Connection On
				if (panel.getComponentAt(885, 308).equals(c) && connectionToOmnivexelServer) {
					//c.repaint();
				} else if ((panel.getComponentAt(870, 308).equals(c) || panel.getComponentAt(728, 257).equals(c)) && !connectionToOmnivexelServer) {
					//c.repaint();
				} else {
					c.repaint();
				}
				
			//}
			
			if (connectionToOmnivexelServer) {
				connectionToServerOn.repaint();
				passInput.repaint();
			} else {
				connectionToServerOff.repaint();
			}
			
			
			
			
		}
	}
}
