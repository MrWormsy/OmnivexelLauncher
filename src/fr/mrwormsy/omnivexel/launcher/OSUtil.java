/*
 * Decompiled with CFR 0.139.
 */
package fr.mrwormsy.omnivexel.launcher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.URI;
import java.util.ArrayList;

public class OSUtil {
    public static OperatingSystem getOS() {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {
            return OperatingSystem.windows;
        }
        if (osName.contains("mac")) {
            return OperatingSystem.macos;
        }
        if (osName.contains("linux")) {
            return OperatingSystem.linux;
        }
        if (osName.contains("unix")) {
            return OperatingSystem.linux;
        }
        if (osName.contains("solaris")) {
            return OperatingSystem.linux;
        }
        if (osName.contains("sunos")) {
            return OperatingSystem.linux;
        }
        return OperatingSystem.unknown;
    }

    public static File getLocalStorage(String dir) {
        String userHome = System.getProperty("user.home");
        if (OSUtil.getOS() == OperatingSystem.windows) {
            return new File(System.getenv("ALLUSERSPROFILE"), "." + dir);
        }
        if (OSUtil.getOS() == OperatingSystem.macos) {
            return new File(userHome, "Library/" + dir);
        }
        return new File(userHome, dir);
    }

    
    public static File getDirectory() {
        return OSUtil.getLocalStorage(LauncherFrame.getLauncherDirectory());
    }

    public static String getPath() {
        return OSUtil.getDirectory().getAbsolutePath();
    }

    public static File writeFile(String filename, String toWrite) {
        try {
            if (!OSUtil.getDirectory().exists()) {
                OSUtil.getDirectory().mkdir();
            }
            File file = new File(OSUtil.getDirectory(), filename);
            PrintStream out = new PrintStream(file.getAbsolutePath());
            out.print(toWrite);
            out.close();
            return file;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<File> list(File folder) {
        ArrayList<File> files = new ArrayList<File>();
        if (!folder.isDirectory()) {
            return files;
        }
        File[] folderFiles = folder.listFiles();
        if (folderFiles != null) {
            for (File f : folderFiles) {
                if (f.isDirectory()) {
                    files.addAll(OSUtil.list(f));
                    continue;
                }
                files.add(f);
            }
        }
        return files;
    }

    public static void openLink(URI uri) {
        try {
            Object o = Class.forName("java.awt.Desktop").getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
            o.getClass().getMethod("browse", URI.class).invoke(o, uri);
        }
        catch (Throwable e) {
            System.out.println("Failed to open link " + uri.toString());
        }
    }

    public static enum OperatingSystem {
        windows,
        macos,
        linux,
        unknown;
        
    }

}

