package fr.mrwormsy.omnivexel.launcher;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



public class LauncherPanel extends JPanel {
	private static final long serialVersionUID = 3087434899043525718L;
	private JTextField textField;
	private JPasswordField passInput;
	private CustomCheckbox chckbxNewCheckBox;
	private JSlider ramSelector;
	private JTextField ramIndicator;
	private CustomButton connectionToServerOn;
	private CustomButton connectionToServerOff;
	
	private Image backgroundWithPass = (new ImageIcon(LauncherFrame.class.getResource("/fr/mrwormsy/omnivexel/launcher/resources/backgroundWithPass.png")).getImage());
	private Image backgroundWithoutPass = (new ImageIcon(LauncherFrame.class.getResource("/fr/mrwormsy/omnivexel/launcher/resources/backgroundWithoutPass.png")).getImage());
	
	private boolean connectionToOmnivexelServer;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (connectionToOmnivexelServer) {
			g.drawImage(backgroundWithPass, 0, 0, this.getWidth(), this.getHeight(), this);
		} else {
			g.drawImage(backgroundWithoutPass, 0, 0, this.getWidth(), this.getHeight(), this);
		}
		
		
	}
	
	@SuppressWarnings("unused")
	public LauncherPanel() {

		ImageIcon authMethodImage1 = new ImageIcon(LauncherFrame.class.getResource("/fr/mrwormsy/omnivexel/launcher/resources/OmnixexelAuth.png"));
		ImageIcon authMethodImage2 = new ImageIcon(LauncherFrame.class.getResource("/fr/mrwormsy/omnivexel/launcher/resources/NoPassAuth.png"));
		
		this.setBounds(0, 0, 960, 540);
		this.setLayout(null);
		this.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		
		chckbxNewCheckBox = new CustomCheckbox((new ImageIcon(LauncherFrame.class.getResource("/fr/mrwormsy/omnivexel/launcher/resources/rememberMeUnselected.png"))).getImage(), (new ImageIcon(LauncherFrame.class.getResource("/fr/mrwormsy/omnivexel/launcher/resources/rememberMeUnselectedPressed.png"))).getImage(), (new ImageIcon(LauncherFrame.class.getResource("/fr/mrwormsy/omnivexel/launcher/resources/rememberMeSelected.png"))).getImage(), (new ImageIcon(LauncherFrame.class.getResource("/fr/mrwormsy/omnivexel/launcher/resources/rememberMeSelectedPressed.png"))).getImage());
		chckbxNewCheckBox.setBackground(new Color(205, 214, 156));
		chckbxNewCheckBox.setBounds(570, 346, 20, 20);
		chckbxNewCheckBox.setBorder(null);

		
		textField = new JTextField();
		textField.setFont(new Font("SansSerif", Font.PLAIN, 28));
		textField.setBounds(728, 193, 188, 36);
		textField.setBackground(new Color(89,84,74));
		textField.setCaretColor(new Color(245, 245, 212));
		textField.setForeground(new Color(245, 245, 212));
		textField.setBorder(null);

		passInput = new JPasswordField();
		passInput.setFont(new Font("SansSerif", Font.PLAIN, 28));
		passInput.setBounds(728, 257, 188, 36);
		passInput.setBackground(new Color(89,84,74));
		passInput.setCaretColor(new Color(245, 245, 212));
		passInput.setForeground(new Color(245, 245, 212));
		passInput.setBorder(null);
		
		CustomButton launchButton = new CustomButton((new ImageIcon(LauncherFrame.class.getResource("/fr/mrwormsy/omnivexel/launcher/resources/launch.png"))).getImage(), (new ImageIcon(LauncherFrame.class.getResource("/fr/mrwormsy/omnivexel/launcher/resources/launchPressed.png"))).getImage());
		
		//launchButton.setIcon(launch);
		launchButton.setBounds(777, 346, 130, 50);
		launchButton.setBorder(null);
		launchButton.setBackground(new Color(205, 214, 156));
		
		launchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
								
			}
		});

		ramSelector = new JSlider(0, 1, 8, 1);
		ramSelector.setBounds(571, 376, 110, 20);
		ramSelector.setBackground(new Color(197, 205, 150));
		ramSelector.setForeground(new Color(53,43,49));	

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

		CustomButton closeButton = new CustomButton((new ImageIcon(LauncherFrame.class.getResource("/fr/mrwormsy/omnivexel/launcher/resources/close.png"))).getImage(), (new ImageIcon(LauncherFrame.class.getResource("/fr/mrwormsy/omnivexel/launcher/resources/closePressed.png"))).getImage());
		closeButton.setBounds(920, 11, 16, 16);
		closeButton.setBorder(null);
		closeButton.setBackground(new Color(53,43,49));
		
		closeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LauncherFrame.getInstance().dispose();
				System.exit(0);
			}
		});
		
		CustomButton reduceButton = new CustomButton((new ImageIcon(LauncherFrame.class.getResource("/fr/mrwormsy/omnivexel/launcher/resources/reduce.png"))).getImage(), (new ImageIcon(LauncherFrame.class.getResource("/fr/mrwormsy/omnivexel/launcher/resources/reducePressed.png"))).getImage());
		reduceButton.setBounds(890, 11, 16, 16);
		reduceButton.setBorder(null);
		reduceButton.setBackground(new Color(53,43,49));
		
		closeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LauncherFrame.getInstance().dispose();
				System.exit(0);
			}
		});
		
		reduceButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LauncherFrame.getInstance().setState(Frame.ICONIFIED);
				
			}
		});
		
		connectionToOmnivexelServer = true;
		
		connectionToServerOn = new CustomButton((new ImageIcon(LauncherFrame.class.getResource("/fr/mrwormsy/omnivexel/launcher/resources/OmnixelxelConnectionOnButton.png"))).getImage(), (new ImageIcon(LauncherFrame.class.getResource("/fr/mrwormsy/omnivexel/launcher/resources/OmnixelxelConnectionOnButtonPressed.png"))).getImage());
		connectionToServerOn.setBounds(870, 308, 40, 25);
		connectionToServerOn.setBorder(null);	
		connectionToServerOn.setBackground(new Color(197, 205, 150));
		connectionToServerOn.setForeground(new Color(197, 205, 150));

		connectionToServerOn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				remove(connectionToServerOn);
				add(connectionToServerOff);
				remove(passInput);
				refreshTextures();
				connectionToOmnivexelServer = false;
			}
		});
		
		connectionToServerOff = new CustomButton((new ImageIcon(LauncherFrame.class.getResource("/fr/mrwormsy/omnivexel/launcher/resources/OmnixelxelConnectionOffButton.png"))).getImage(), (new ImageIcon(LauncherFrame.class.getResource("/fr/mrwormsy/omnivexel/launcher/resources/OmnixelxelConnectionOffButtonPressed.png"))).getImage());
		connectionToServerOff.setBounds(870, 308, 40, 25);
		connectionToServerOff.setBorder(null);
		connectionToServerOff.setBackground(new Color(197, 205, 150));
		connectionToServerOff.setForeground(new Color(197, 205, 150));
		//this.add(connectionToServerOff);
		
		connectionToServerOff.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				remove(connectionToServerOff);
				add(connectionToServerOn);
				add(passInput);
				refreshTextures();
				connectionToOmnivexelServer = true;
			}
		});
		
		
		this.add(reduceButton);
		this.add(closeButton);
		this.add(ramIndicator);
		this.add(ramSelector);
		this.add(launchButton);
		this.add(passInput);
		this.add(textField);
		this.add(connectionToServerOn);
		this.add(chckbxNewCheckBox);
		
	}
	
	public void refreshTextures() {

		this.repaint();
		
	}
	
}