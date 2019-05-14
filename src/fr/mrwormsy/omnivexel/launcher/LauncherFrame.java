package fr.mrwormsy.omnivexel.launcher;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import org.omg.CORBA.PRIVATE_MEMBER;

import com.launcher.game.process.direct.LaunchGame;

import fr.theshark34.supdate.BarAPI;
import fr.theshark34.supdate.SUpdate;
import fr.theshark34.supdate.exception.BadServerResponseException;
import fr.theshark34.supdate.exception.BadServerVersionException;
import fr.theshark34.supdate.exception.ServerDisabledException;
import fr.theshark34.supdate.exception.ServerMissingSomethingException;
import fr.trxyy.launcherlib.Init;
import fr.trxyy.launcherlib.OSUtil;
import fr.trxyy.launcherlib.utils.LauncherConfiguration;

public class LauncherFrame extends JFrame {
	private static final long serialVersionUID = 1L;


	
	private static LauncherFrame instance;
	private static LauncherPanel launcherPanel;
	
	private static SUpdate su;
	
	//TODO CHANGE Authentification to Authentication

	



	@SuppressWarnings("static-access")
	public static void main(String[] args) {
				
		LauncherConfiguration config = new LauncherConfiguration(); 
		config.setVersionId("1.9.4"); 
		config.setAssetIndex("1.9"); 
		config.setLaunchClass("net.minecraft.client.main.Main"); 
		config.setDownloadUrl("http://51.75.254.98/downloads/"); 
		config.setCurrentlyMaintenance(false); 
		config.setMaintenanceUrl(""); 
		config.setLaunchArguments("--username ${auth_player_name} --version ${version_name} --gameDir ${game_directory} --assetsDir ${assets_root} --assetIndex ${assets_index_name} --uuid ${auth_uuid} --accessToken ${auth_access_token} --userType ${user_type} --versionType ${version_type}"); 
		Init.setConfiguration(config);
		Init.registerLauncherConfiguration("Omnivexel Launcher", 850, 540, "omnivexelprojec", "/resources/"); 
		
		instance = new LauncherFrame();
		
		

		PropertiesSaver.loadUserProps();
	}
	

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
		
		this.getRootPane().setDefaultButton(launcherPanel.getLaunchButton());
		
		this.setVisible(true);
		
		launcherPanel.getUsernamField().requestFocus();
		launcherPanel.getUsernamField().requestFocusInWindow();
		
	}

	private static Thread thread;
	
	public static void update() throws Exception {
		setSu(new SUpdate("http://51.75.254.98/omnivexel/", OSUtil.getDirectory()));
		
		thread = new Thread() {
			
			private int val;
			private	int max;
			 
			@Override
			public void run() {

				while (!this.isInterrupted()) {
					
					val = (int) (BarAPI.getNumberOfTotalDownloadedBytes() / 1000);
					max = (int) (BarAPI.getNumberOfTotalBytesToDownload() / 1000);
					
					
					LauncherFrame.getLauncherPanel().getProgressBar().setMaximum(max);
					LauncherFrame.getLauncherPanel().getProgressBar().setValue(val);
					
				}
				
				if (max - val == 0) {
					launchGame();
				}
				
			}
			
		};
		
		thread.start();
	
		su.start();
		
		thread.interrupt();		
	}
	
	public static void interruptThread() {
		thread.interrupt();
	}
	
	public static void launchGame() {
		
        LaunchGame gameLaunch = new LaunchGame();
        gameLaunch.launchGame();
        System.exit(0);
		
	}
	
	public static LauncherFrame getInstance() {
		return instance;
	}
	
	public static LauncherPanel getLauncherPanel() {
		return launcherPanel;
	}


	public static SUpdate getSu() {
		return su;
	}


	public static void setSu(SUpdate su) {
		LauncherFrame.su = su;
	}
}
