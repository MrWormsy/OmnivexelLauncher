package fr.mrwormsy.omnivexel.launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import fr.trxyy.launcherlib.LauncherConstants;
import fr.trxyy.launcherlib.OSUtil;
import fr.trxyy.launcherlib.accounts.Account;
import fr.trxyy.launcherlib.utils.Logger;

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
                    prop.setProperty("ramAllowed", "1024");
                    prop.setProperty("gameWidth", "854");
                    prop.setProperty("gameHeight", "480");
                    prop.setProperty("rememberMe", "false");
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

    public static void saveProps() {
        block12 : {
            prop = new Properties();
            try {
                try {
                    output = new FileOutputStream(OSUtil.getDirectory() + "/config.cfg");
                    Logger.write("AccountNameSave: " + Account.getUsername());
                    prop.setProperty("accountName", Account.getUsername());
                    prop.setProperty("ramAllowed", Account.getRam());
                    prop.setProperty("gameWidth", LauncherConstants.getGameWidth());
                    prop.setProperty("gameHeight", LauncherConstants.getGameHeight());
                    prop.setProperty("rememberMe", "" + LauncherFrame.getLauncherPanel().getChckbxNewCheckBox().isSelected());
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
                    Account.setUsername(prop.getProperty("accountName"));
                    
                    LauncherFrame.getLauncherPanel().getRamSelector().setValue(Integer.parseInt(prop.getProperty("ramAllowed").replaceAll("M", "")) / 512);
                    ram = prop.getProperty("ramAllowed");
                    Account.setRam(prop.getProperty("ramAllowed"));
                    gameWidth = prop.getProperty("gameWidth");
                    LauncherConstants.setGameWidth(prop.getProperty("gameWidth"));
                    gameHeight = prop.getProperty("gameHeight");
                    LauncherConstants.setGameHeight(prop.getProperty("gameHeight"));
                    
                    if (prop.getProperty("rememberMe").equalsIgnoreCase("true")) {
                    	LauncherFrame.getLauncherPanel().getChckbxNewCheckBox().setSelected(true);
                    	LauncherFrame.getLauncherPanel().getUsernamField().setText(prop.getProperty("accountName"));
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
