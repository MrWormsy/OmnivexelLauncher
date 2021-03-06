package fr.mrwormsy.omnivexel.launcher;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import fr.mrwormsy.omnivexel.launcher.game.LaunchGame;
import fr.theshark34.supdate.BarAPI;
import fr.theshark34.supdate.SUpdate;

public class LauncherFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	
	
	private static LauncherFrame instance;
	private static LauncherPanel launcherPanel;
	
	private static SUpdate su;
	
	// Static things
	private static String versionId;
	private static String launchClass;
	private static String assetIndex;
	private static String arguments;
	private static String launcherDirectory = "omnivexelproject/game";


	@SuppressWarnings("static-access")
	public static void main(String[] args) {		
		new LauncherFrame();
				
		if (!netIsAvailable()) {
			// If we are here that means we are not connected to the internet... Wssssssse tell him that he needs internet
			JOptionPane.showMessageDialog(null, "ERROR : You are not connected to the internet, and you don't have the game installed.", "No internet connection", JOptionPane.ERROR_MESSAGE);				
			System.exit(0);
		}
		
		setVersionId("1.9.4"); 
		setAssetIndex("1.9"); 
		setLaunchClass("net.minecraft.client.main.Main"); 
		setArguments("--username ${auth_player_name} --version ${version_name} --gameDir ${game_directory} --assetsDir ${assets_root} --assetIndex ${assets_index_name} --uuid ${auth_uuid} --accessToken ${auth_access_token} --userType ${user_type} --versionType ${version_type}");  

		PropertiesSaver.loadUserProps();
	}
	

	private LauncherFrame() {	
				
		instance = this;
		
		this.setTitle("Omnivexel Launcher");
		this.setSize(690, 540);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setUndecorated(true);
		this.setIconImage(new ImageIcon(this.getClass().getResource("/fr/mrwormsy/omnivexel/launcher/resources/logo.png")).getImage());
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
		
		try {
			launcherPanel = new LauncherPanel();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		this.getContentPane().add(launcherPanel);
		
		this.getRootPane().setDefaultButton(launcherPanel.getLaunchButton());
		
		this.setVisible(true);
		
		launcherPanel.getUsernamField().requestFocus();
		launcherPanel.getUsernamField().requestFocusInWindow();
		
	}

	private static Thread thread;
	
	public static void update() throws Exception {
		setSu(new SUpdate("http://51.75.254.98/omnivexel/", OSUtil.getDirectory()));
				
		getInstance().rootPane.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		
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
					
					if (max != 0) {
						launcherPanel.getProgressBarInfos().setText((int) (((double) val / (double) max) * 100) + "% (" + val/1000 + "/" + max/1000 + "MB)");
					}
					
				}
				
				if (max - val == 0) {
					launcherPanel.getProgressBarInfos().setText("Launching...");
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
		
		//Save props
		PropertiesSaver.saveProps();
		
        LaunchGame gameLaunch = new LaunchGame();
        gameLaunch.launchGame();
        System.exit(0);
		
	}
	
	// http://51.75.254.98/update.html
	public static String getHTML(String urlstr) {
		BufferedReader br = null;

        try {

            URL url = new URL(urlstr);
            br = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;

            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {

                sb.append(line);
                sb.append(System.lineSeparator());
            }

            if (br != null) {
                br.close();
            }
            
            return sb.toString();
        } catch (IOException e) {
			e.printStackTrace();
		}
        
        return "<p>Unable to connect to the internet</p>";
	}
	
	private static boolean netIsAvailable() {
	    try {
	        final URL url = new URL("http://www.google.com");
	        final URLConnection conn = url.openConnection();
	        conn.connect();
	        conn.getInputStream().close();
	        return true;
	    } catch (MalformedURLException e) {
	        throw new RuntimeException(e);
	    } catch (IOException e) {
	        return false;
	    }
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


	public static String getVersionId() {
		return versionId;
	}


	public static void setVersionId(String versionId) {
		LauncherFrame.versionId = versionId;
	}


	public static String getLaunchClass() {
		return launchClass;
	}


	public static void setLaunchClass(String launchClass) {
		LauncherFrame.launchClass = launchClass;
	}


	public static String getAssetIndex() {
		return assetIndex;
	}


	public static void setAssetIndex(String assetIndex) {
		LauncherFrame.assetIndex = assetIndex;
	}


	public static String getArguments() {
		return arguments;
	}


	public static void setArguments(String arguments) {
		LauncherFrame.arguments = arguments;
	}


	public static String getLauncherDirectory() {
		return launcherDirectory;
	}


	public static void setLauncherDirectory(String launcherDirectory) {
		LauncherFrame.launcherDirectory = launcherDirectory;
	}
}
