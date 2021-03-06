package fr.mrwormsy.omnivexel.launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class PropertiesSaver {
    public static String username;
    public static String ram;
    public static String gameWidth;
    public static String gameHeight;
    public static boolean rememberMe;
    private static Properties prop;
    private static OutputStream output;
    private static InputStream input;

    public static void loadUserProps() {
        File f = new File(OSUtil.getDirectory() + "/config.cfg");
        if (f.exists()) {
            PropertiesSaver.loadProps();
        } else {
            try {
                f.getParentFile().mkdirs();
                f.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            PropertiesSaver.saveDefaultProps();
            PropertiesSaver.loadProps();
        }
    }

    private static void saveDefaultProps() {
        block12 : {
            prop = new Properties();
            try {
                try {
                    output = new FileOutputStream(OSUtil.getDirectory() + "/config.cfg");
                    prop.setProperty("accountName", "Pseudo");
                    prop.setProperty("ramAllowed", "1024M");
                    prop.setProperty("gameWidth", "854");
                    prop.setProperty("gameHeight", "480");
                    prop.setProperty("rememberMe", "false");
                    prop.setProperty("password", "");
                    prop.store(output, null);
                }
                catch (IOException io) {
                    io.printStackTrace();
                    if (output == null) break block12;
                    try {
                        output.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            finally {
                if (output != null) {
                    try {
                        output.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
	public static void saveProps() {
        block12 : {
            prop = new Properties();
            try {
                try {
                    output = new FileOutputStream(OSUtil.getDirectory() + "/config.cfg");
                    prop.setProperty("accountName", LauncherFrame.getLauncherPanel().getUsernamField().getText());
                    username = LauncherFrame.getLauncherPanel().getUsernamField().getText();
                    prop.setProperty("ramAllowed", String.valueOf(LauncherFrame.getLauncherPanel().getRamSelector().getValue() * 512) + "M");
                    ram = String.valueOf(LauncherFrame.getLauncherPanel().getRamSelector().getValue() * 512) + "M";
                    prop.setProperty("rememberMe", "" + LauncherFrame.getLauncherPanel().getChckbxNewCheckBox().isSelected());
                    prop.setProperty("password", LauncherFrame.getLauncherPanel().getPassInput().getText());
                    prop.store(output, null);
                }
                catch (IOException io) {
                    io.printStackTrace();
                    if (output == null) break block12;
                    try {
                        output.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            finally {
                if (output != null) {
                    try {
                        output.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void loadProps() {
        block12 : {
            prop = new Properties();
            try {
                try {
                    input = new FileInputStream(OSUtil.getDirectory() + "/config.cfg");
                    prop.load(input);
                    username = prop.getProperty("accountName");
                    
                    LauncherFrame.getLauncherPanel().getRamSelector().setValue(Integer.parseInt(prop.getProperty("ramAllowed").replaceAll("M", "")) / 512);
                    ram = prop.getProperty("ramAllowed");
                    
                    LauncherFrame.getLauncherPanel().getUsernamField().setText(prop.getProperty("accountName"));
                    
                    if (prop.getProperty("rememberMe").equalsIgnoreCase("true")) {
                    	LauncherFrame.getLauncherPanel().getChckbxNewCheckBox().setSelected(true);
                    	LauncherFrame.getLauncherPanel().getPassInput().setText(prop.getProperty("password"));
					} else {
						LauncherFrame.getLauncherPanel().getChckbxNewCheckBox().setSelected(false);
					}
                    
                    
                }
                catch (IOException io) {
                    io.printStackTrace();
                    if (input == null) break block12;
                    try {
                        input.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            finally {
                if (input != null) {
                    try {
                        input.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        PropertiesSaver.username = username;
    }

    public static String getRam() {
        return ram;
    }

    public static void setRam(String ram) {
        PropertiesSaver.ram = ram;
    }
}
