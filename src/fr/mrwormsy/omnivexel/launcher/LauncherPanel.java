package fr.mrwormsy.omnivexel.launcher;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



public class LauncherPanel extends JPanel {
	private static final long serialVersionUID = 3087434899043525718L;
	private JTextField usernamField;
	private JPasswordField passInput;

	private CustomCheckbox chckbxNewCheckBox;
	private JSlider ramSelector;
	private JTextField ramIndicator;
	private CustomButton connectionToServerOn;
	private CustomButton connectionToServerOff;
	private CustomButton launchButton;
	private CustomProgressbar progressBar;
	
	private JLabel progressBarInfos;
	
	private Image backgroundWithPass = (new ImageIcon(LauncherFrame.getInstance().getClass().getResource("/fr/mrwormsy/omnivexel/launcher/resources/backgroundWithPass.png")).getImage());
	private Image backgroundWithoutPass = (new ImageIcon(LauncherFrame.getInstance().getClass().getResource("/fr/mrwormsy/omnivexel/launcher/resources/backgroundWithoutPass.png")).getImage());
	
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
	public LauncherPanel() throws IOException {
		
		ImageIcon authMethodImage1 = new ImageIcon(LauncherFrame.getInstance().getClass().getResource("/fr/mrwormsy/omnivexel/launcher/resources/omnixexelAuth.png"));
		ImageIcon authMethodImage2 = new ImageIcon(LauncherFrame.getInstance().getClass().getResource("/fr/mrwormsy/omnivexel/launcher/resources/noPassAuth.png"));
		
		this.setBounds(0, 0, 960, 540);
		this.setLayout(null);
		this.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		
		chckbxNewCheckBox = new CustomCheckbox((new ImageIcon(LauncherFrame.getInstance().getClass().getResource("/fr/mrwormsy/omnivexel/launcher/resources/rememberMeUnselected.png"))).getImage(), (new ImageIcon(LauncherFrame.getInstance().getClass().getResource("/fr/mrwormsy/omnivexel/launcher/resources/rememberMeUnselectedPressed.png"))).getImage(), (new ImageIcon(LauncherFrame.getInstance().getClass().getResource("/fr/mrwormsy/omnivexel/launcher/resources/rememberMeSelected.png"))).getImage(), (new ImageIcon(LauncherFrame.getInstance().getClass().getResource("/fr/mrwormsy/omnivexel/launcher/resources/rememberMeSelectedPressed.png"))).getImage());
		chckbxNewCheckBox.setBackground(new Color(205, 214, 156));
		chckbxNewCheckBox.setBounds(570, 346, 20, 20);
		chckbxNewCheckBox.setBorder(null);

		usernamField = new JTextField();
		usernamField.setFont(new Font("SansSerif", Font.PLAIN, 28));
		usernamField.setBounds(728, 193, 188, 36);
		usernamField.setBackground(new Color(89,84,74));
		usernamField.setCaretColor(new Color(245, 245, 212));
		usernamField.setForeground(new Color(245, 245, 212));
		usernamField.setBorder(null);

		passInput = new JPasswordField();
		passInput.setFont(new Font("SansSerif", Font.PLAIN, 28));
		passInput.setBounds(728, 257, 188, 36);
		passInput.setBackground(new Color(89,84,74));
		passInput.setCaretColor(new Color(245, 245, 212));
		passInput.setForeground(new Color(245, 245, 212));
		passInput.setBorder(null);
		
		launchButton = new CustomButton((new ImageIcon(LauncherFrame.getInstance().getClass().getResource("/fr/mrwormsy/omnivexel/launcher/resources/launch.png"))).getImage(), (new ImageIcon(LauncherFrame.getInstance().getClass().getResource("/fr/mrwormsy/omnivexel/launcher/resources/launchPressed.png"))).getImage());
		
		//launchButton.setIcon(launch);
		launchButton.setBounds(777, 346, 130, 50);
		launchButton.setBorder(null);
		launchButton.setBackground(new Color(205, 214, 156));
		
		launchButton.addActionListener(new ActionListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				
				usernamField.setText(usernamField.getText().replaceAll(" ", ""));
				
				if (usernamField.getText().length() >= 3) {
					
					// If we do not want to use the omnivexel auth system we just launch the game
					if (!connectionToOmnivexelServer) {
						preLaunch();
					}
					
					// Else we try to connect using a php get method
					else {
						if (readAuthAnswer(usernamField.getText(), passInput.getText())) {
							preLaunch();
						} else {
							JOptionPane.showMessageDialog(LauncherFrame.getInstance(), "Your password is incorect !", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
					
					
				} else {
					JOptionPane.showMessageDialog(LauncherFrame.getInstance(), "Your username is incorect !", "Error", JOptionPane.ERROR_MESSAGE);
				}
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

		CustomButton closeButton = new CustomButton((new ImageIcon(LauncherFrame.getInstance().getClass().getResource("/fr/mrwormsy/omnivexel/launcher/resources/close.png"))).getImage(), (new ImageIcon(LauncherFrame.getInstance().getClass().getResource("/fr/mrwormsy/omnivexel/launcher/resources/closePressed.png"))).getImage());
		
		closeButton.setBounds(920, 11, 16, 16);
		closeButton.setBorder(null);
		closeButton.setBackground(new Color(53,43,49));
		
		closeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//We we close the window we save the props
				PropertiesSaver.saveProps();
				
				LauncherFrame.getInstance().dispose();
				System.exit(0);
			}
		});
		
		CustomButton reduceButton = new CustomButton((new ImageIcon(LauncherFrame.getInstance().getClass().getResource("/fr/mrwormsy/omnivexel/launcher/resources/reduce.png"))).getImage(), (new ImageIcon(LauncherFrame.getInstance().getClass().getResource("/fr/mrwormsy/omnivexel/launcher/resources/reducePressed.png"))).getImage());
		
		reduceButton.setBounds(890, 11, 16, 16);
		reduceButton.setBorder(null);
		reduceButton.setBackground(new Color(53,43,49));
		
		reduceButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LauncherFrame.getInstance().setState(Frame.ICONIFIED);
				
			}
		});
		
		connectionToOmnivexelServer = true;
		
		connectionToServerOn = new CustomButton((new ImageIcon(LauncherFrame.getInstance().getClass().getResource("/fr/mrwormsy/omnivexel/launcher/resources/omnixelxelConnectionOnButton.png"))).getImage(), (new ImageIcon(LauncherFrame.getInstance().getClass().getResource("/fr/mrwormsy/omnivexel/launcher/resources/omnixelxelConnectionOnButtonPressed.png"))).getImage());
		
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
		
		connectionToServerOff = new CustomButton((new ImageIcon(LauncherFrame.getInstance().getClass().getResource("/fr/mrwormsy/omnivexel/launcher/resources/omnixelxelConnectionOffButton.png"))).getImage(), (new ImageIcon(LauncherFrame.getInstance().getClass().getResource("/fr/mrwormsy/omnivexel/launcher/resources/omnixelxelConnectionOffButtonPressed.png"))).getImage());
		
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
		
		progressBar = new CustomProgressbar((new ImageIcon(LauncherFrame.getInstance().getClass().getResource("/fr/mrwormsy/omnivexel/launcher/resources/progressBarEmpty.png"))).getImage(), (new ImageIcon(LauncherFrame.getInstance().getClass().getResource("/fr/mrwormsy/omnivexel/launcher/resources/progressBarFull.png"))).getImage());
		
		//progressBar = new JProgressBar(5, 10);
		progressBar.setBounds(30, 500, 900, 20);
		progressBar.setBorder(null);
		progressBar.setMinimum(0);
		progressBar.setMaximum(0);
		
		progressBarInfos = new JLabel("Launch the game !");
		progressBarInfos.setHorizontalTextPosition(JLabel.CENTER);
		progressBarInfos.setVerticalAlignment(JLabel.CENTER);
		progressBarInfos.setBounds(405, 465, 150, 25);
		progressBarInfos.setFont(new Font("SansSerif", Font.BOLD, 16));
		
		
		
		progressBarInfos.setIcon(new ImageIcon(LauncherFrame.getInstance().getClass().getResource("/fr/mrwormsy/omnivexel/launcher/resources/progressBarInfos.png")));
		
		this.add(reduceButton);
		this.add(closeButton);
		this.add(ramIndicator);
		this.add(ramSelector);
		this.add(launchButton);
		this.add(passInput);
		this.add(usernamField);
		this.add(connectionToServerOn);
		this.add(chckbxNewCheckBox);
		this.add(progressBar);		
		this.add(progressBarInfos);
		
		NewsPanel newsPanel = new NewsPanel();
		newsPanel.setLocation(25, 25);
		this.add(newsPanel);
	}
	
	//Get if the password matchs according to the username entred
	protected boolean readAuthAnswer(String text, String text2) {
		
		try {
			@SuppressWarnings("deprecation")
			URL url = new URL("http://51.75.254.98/omnivexel/login.php?login=" + usernamField.getText() +"&pass=" + passInput.getText());
	        BufferedReader in = new BufferedReader(
	        new InputStreamReader(url.openStream()));

	        String inputLine;
	        while ((inputLine = in.readLine()) != null)
	        	if (inputLine.equalsIgnoreCase("true")) {
					return true;
				}
	        in.close();
	        
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return false;
	}

	public void preLaunch() {
		launchButton.setEnabled(false);
		usernamField.setEnabled(false);
		passInput.setEnabled(false);
		chckbxNewCheckBox.setEnabled(false);
		connectionToServerOn.setEnabled(false);
		connectionToServerOff.setEnabled(false);
		ramSelector.setEnabled(false);			
		
		Thread t = new Thread() {
			
			@Override
			public void run() {
				
				try {
					LauncherFrame.update();
				} catch (Exception e) {
					LauncherFrame.interruptThread();
					
					//TODO SET THE FIELDS ENABLE
					
					e.printStackTrace();
					
					return;
				}
				
			}
			
		};
		
		t.start();
	}

	public void refreshTextures() {
		this.repaint();	
	}

	public JTextField getUsernamField() {
		return usernamField;
	}

	public CustomCheckbox getChckbxNewCheckBox() {
		return chckbxNewCheckBox;
	}

	public JSlider getRamSelector() {
		return ramSelector;
	}
	
	public JButton getLaunchButton() {
		return this.launchButton;
	}
	
	
	public JLabel getProgressBarInfos() {
		return progressBarInfos;
	}

	public CustomProgressbar getProgressBar() {
		return progressBar;
	}
	
	public boolean isConnectionToOmnivexelServer() {
		return connectionToOmnivexelServer;
	}

	public void setConnectionToOmnivexelServer(boolean connectionToOmnivexelServer) {
		this.connectionToOmnivexelServer = connectionToOmnivexelServer;
	}
	
	public JPasswordField getPassInput() {
		return passInput;
	}

	public void setPassInput(JPasswordField passInput) {
		this.passInput = passInput;
	}
}
