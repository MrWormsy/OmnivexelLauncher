/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  javafx.application.Platform
 */
package fr.mrwormsy.omnivexel.launcher.game;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import com.google.common.base.MoreObjects;

import fr.mrwormsy.omnivexel.launcher.LauncherFrame;
import fr.mrwormsy.omnivexel.launcher.OSUtil;
import fr.mrwormsy.omnivexel.launcher.PropertiesSaver;
import javafx.application.Platform;

public class LaunchGame
implements GameProcessRunnable {
    private static final String CRASH_IDENTIFIER_MAGIC = "#@!@#";
    private final GameProcessFactory processFactory = new DirectGameProcessFactory();
    public static File workingDirectory = OSUtil.getDirectory();
    public static String gameDir = new File(workingDirectory, "bin").getAbsolutePath();
    public static String gameFile = new File(gameDir, String.valueOf(LauncherFrame.getVersionId()) + ".jar").getAbsolutePath();
    public static String nativesDir = new File(workingDirectory, "natives").getAbsolutePath();
    public static String assetsDir = new File(workingDirectory, "assets").getAbsolutePath();
    public static String librariesDir = new File(workingDirectory, "libraries").getAbsolutePath();
    public static String mainClass = LauncherFrame.getLaunchClass();
    public static String versionId;
    public static String assetIndex;
    public static String minecraftArguments;

    static {
        versionId = LauncherFrame.getVersionId();
        assetIndex = LauncherFrame.getAssetIndex();
        minecraftArguments = LauncherFrame.getArguments();
    }

    public void launchGame() {
        GameProcessBuilder processBuilder = new GameProcessBuilder(MoreObjects.firstNonNull(null, OperatingSystem.getCurrentPlatform().getJavaDir()));
        processBuilder.withSysOutFilter(input -> input.contains(CRASH_IDENTIFIER_MAGIC));
        processBuilder.directory(new File(workingDirectory, "bin"));
        String defaultArgument = "-Xmx" + PropertiesSaver.getRam() + " -XX:+UseConcMarkSweepGC -XX:+CMSIncrementalMode -XX:-UseAdaptiveSizePolicy -Xmn128M";
        processBuilder.withArguments(defaultArgument.split(" "));
        if (OperatingSystem.getCurrentPlatform() == OperatingSystem.WINDOWS) {
            processBuilder.withArguments("-XX:HeapDumpPath=MojangTricksIntelDriversForPerformance_javaw.exe_minecraft.exe.heapdump");
            if (System.getProperty("os.version").contains("10")) {
                processBuilder.withArguments("-Dos.name=Windows 10");
                processBuilder.withArguments("-Dos.version=10.0");
            }
        } else if (OperatingSystem.getCurrentPlatform() == OperatingSystem.OSX) {
            processBuilder.withArguments("-Xdock:icon=${asset=icons/minecraft.icns}", "-Xdock:name=Minecraft");
        }
        processBuilder.withArguments("-Djava.library.path=" + nativesDir);
        processBuilder.withArguments("-Dminecraft.launcher.brand=java-minecraft-launcher");
        processBuilder.withArguments("-Dminecraft.launcher.version=106");
        processBuilder.withArguments("-cp", this.constructClasspath());
        processBuilder.withArguments("net.minecraft.client.main.Main");
        processBuilder.withArguments("--username=", PropertiesSaver.getUsername());
        processBuilder.withArguments("--version=", versionId);
        processBuilder.withArguments("--gameDir=", gameDir);
        processBuilder.withArguments("--assetsDir=", assetsDir);
        processBuilder.withArguments("--assetIndex=", assetIndex);
        processBuilder.withArguments("--uuid=", "account_offline-" + (new Random()).nextInt(10000));
        processBuilder.withArguments("--accessToken=", "account_offline-" + (new Random()).nextInt(10000));
        processBuilder.withArguments("--userProperties=", "{}");
        processBuilder.withArguments("--userType=", "legacy");
        processBuilder.withArguments("--versionType=release");
        processBuilder.withArguments("--width=", "850");
        processBuilder.withArguments("--height=", "480");
        try {
            try {
                GameProcess process = this.processFactory.startGame(processBuilder);
                process.setExitRunnable(this);
            }
            catch (IOException e) {
                Platform.exit();
                return;
            }
        }
        finally {
            Platform.exit();
        }
    }

    private String constructClasspath() {
        String result = "";
        ArrayList<File> libs = OSUtil.list(new File(librariesDir));
        String separator = System.getProperty("path.separator");
        for (File lib : libs) {
            result = String.valueOf(result) + lib.getAbsolutePath() + separator;
        }
        result = String.valueOf(result) + gameFile;
        return result;
    }

    @Override
    public void onGameProcessEnded(GameProcess process) {
        int exitCode = process.getExitCode();
        if (exitCode == 0) {
        } else {
            String errorText = null;
            Collection<String> sysOutLines = process.getSysOutLines();
            String[] sysOut = sysOutLines.toArray(new String[sysOutLines.size()]);
            for (int i = sysOut.length - 1; i >= 0; --i) {
                String line = sysOut[i];
                int pos = line.lastIndexOf(CRASH_IDENTIFIER_MAGIC);
                if (pos < 0 || pos >= line.length() - CRASH_IDENTIFIER_MAGIC.length() - 1) continue;
                errorText = line.substring(pos + CRASH_IDENTIFIER_MAGIC.length()).trim();
                break;
            }
            if (errorText != null) {
                File file = new File(errorText);
                if (file.isFile()) {
                    FileInputStream inputStream = null;
                    try {
                        String line;
                        inputStream = new FileInputStream(file);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder result = new StringBuilder();
                        while ((line = reader.readLine()) != null) {
                            if (result.length() > 0) {
                                result.append("\n");
                            }
                            result.append(line);
                        }
                        reader.close();
                        if (!Desktop.isDesktopSupported()) {
                            System.out.println("Desktop is not supported");
                            return;
                        }
                        Desktop desktop = Desktop.getDesktop();
                        if (file.exists()) {
                            desktop.open(file);
                        }
                    }
                    catch (IOException e) {
                    }
                } else {
                }
            }
        }
    }
}

