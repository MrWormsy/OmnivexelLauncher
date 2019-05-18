/*
 * Decompiled with CFR 0.139.
 */
package fr.mrwormsy.omnivexel.launcher.game;

import java.io.File;
import java.io.IOException;
import java.net.URI;

public enum OperatingSystem {
    LINUX("linux", new String[]{"linux", "unix"}),
    WINDOWS("windows", new String[]{"win"}),
    OSX("osx", new String[]{"mac"}),
    UNKNOWN("unknown", new String[0]);
    
    private final String name;
    private final String[] aliases;

    private OperatingSystem(String name, String[] aliases) {
        this.name = name;
        this.aliases = aliases == null ? new String[0] : aliases;
    }

    public String getName() {
        return this.name;
    }

    public String[] getAliases() {
        return this.aliases;
    }

    public boolean isSupported() {
        return this != UNKNOWN;
    }

    public String getJavaDir() {
        String separator = System.getProperty("file.separator");
        String path = String.valueOf(System.getProperty("java.home")) + separator + "bin" + separator;
        if (OperatingSystem.getCurrentPlatform() == WINDOWS && new File(String.valueOf(path) + "javaw.exe").isFile()) {
            return String.valueOf(path) + "javaw.exe";
        }
        return String.valueOf(path) + "java";
    }

    public static OperatingSystem getCurrentPlatform() {
        String osName = System.getProperty("os.name").toLowerCase();
        for (OperatingSystem os : OperatingSystem.values()) {
            for (String alias : os.getAliases()) {
                if (!osName.contains(alias)) continue;
                return os;
            }
        }
        return UNKNOWN;
    }

    public static void openLink(URI link) {
        try {
            Class<?> desktopClass = Class.forName("java.awt.Desktop");
            Object o = desktopClass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
            desktopClass.getMethod("browse", URI.class).invoke(o, link);
        }
        catch (Throwable e2) {
            if (OperatingSystem.getCurrentPlatform() == OSX) {
                try {
                    Runtime.getRuntime().exec(new String[]{"/usr/bin/open", link.toString()});
                }
                catch (IOException e1) {
                }
            }
        }
    }

    public static void openFolder(File path) {
        String absolutePath = path.getAbsolutePath();
        OperatingSystem os = OperatingSystem.getCurrentPlatform();
        if (os == OSX) {
            try {
                Runtime.getRuntime().exec(new String[]{"/usr/bin/open", absolutePath});
                return;
            }
            catch (IOException e) {
            }
        }
        if (os == WINDOWS) {
            String cmd = String.format("cmd.exe /C start \"Open file\" \"%s\"", absolutePath);
            try {
                Runtime.getRuntime().exec(cmd);
                return;
            }
            catch (IOException e2) {
            }
        }
        try {
            Class<?> desktopClass = Class.forName("java.awt.Desktop");
            Object desktop = desktopClass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
            desktopClass.getMethod("browse", URI.class).invoke(desktop, path.toURI());
        }
        catch (Throwable e3) {
        }
    }
}

