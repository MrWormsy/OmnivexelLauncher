package fr.mrwormsy.omnivexel.launcher;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import fr.trxyy.launcherlib.accounts.Account;
import fr.trxyy.launcherlib.update.GameUpdater;



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
	private JProgressBar progressBar;
	
	private Image backgroundWithPass = (new ImageIcon(LauncherFrame.class.getResource("/fr/mrwormsy/omnivexel/launcher/resources/backgroundWithPass.png")).getImage());
	private Image backgroundWithoutPass = (new ImageIcon(LauncherFrame.class.getResource("/fr/mrwormsy/omnivexel/launcher/resources/backgroundWithoutPass.png")).getImage());
	
	private boolean connectionToOmnivexelServer;
	
	public boolean isConnectionToOmnivexelServer() {
		return connectionToOmnivexelServer;
	}

	public void setConnectionToOmnivexelServer(boolean connectionToOmnivexelServer) {
		this.connectionToOmnivexelServer = connectionToOmnivexelServer;
	}

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
		
		launchButton = new CustomButton((new ImageIcon(LauncherFrame.class.getResource("/fr/mrwormsy/omnivexel/launcher/resources/launch.png"))).getImage(), (new ImageIcon(LauncherFrame.class.getResource("/fr/mrwormsy/omnivexel/launcher/resources/launchPressed.png"))).getImage());
		
		//launchButton.setIcon(launch);
		launchButton.setBounds(777, 346, 130, 50);
		launchButton.setBorder(null);
		launchButton.setBackground(new Color(205, 214, 156));
		
		launchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				launchGame();
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
		
		progressBar = new JProgressBar(5, 10);
		progressBar.setBounds(400, 400, 100, 20);
		progressBar.setValue(5);
		
		
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

	protected void launchGame() {
		Account.setUsername(usernamField.getText());
		Account.setRam(String.valueOf(ramSelector.getValue() * 512) + "M");
			
		
		launchButton.setEnabled(false);
		usernamField.setEnabled(false);
		passInput.setEnabled(false);
		chckbxNewCheckBox.setEnabled(false);
		connectionToServerOn.setEnabled(false);
		connectionToServerOff.setEnabled(false);
		ramSelector.setEnabled(false);
		
		GameUpdater gameUpdater = new GameUpdater();
        
        
            	Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                    	
                    	
                        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
                        exec.scheduleAtFixedRate(new Runnable() {
                          @Override
                          public void run() {
                        	  
                        	  if (progressBar.getMinimum() != 0) {
                        		  progressBar.setMaximum(gameUpdater.downloadTask.needToDownload);
                        		  progressBar.setMinimum(0);
                        	  }
                        	
                        	
    						progressBar.setValue(gameUpdater.downloadTask.downloadedFiles);
    						//System.out.println(gameUpdater.downloadTask.downloadedFiles);
                        	  
                        	  
                          }
                        }, 2000, 100, TimeUnit.MILLISECONDS);                    	
                    }
                });
            	
            	thread.start();
		
		gameUpdater.checkForUpdateLauncher();
		
		PropertiesSaver.saveProps();
		
		/*
		
        LaunchGame gameLaunch = new LaunchGame();
        gameLaunch.launchGame();
        
        LauncherFrame.getInstance().dispose();
        System.exit(0);
		
		
	
		*/
	}

	public void refreshTextures() {

		this.repaint();
		
	}	
	
	 public long fileCount(Path dir) { 
		    try {
				return Files.walk(dir)
				            .parallel()
				            .filter(p -> !p.toFile().isDirectory())
				            .count();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 0;
	 }
}
