/*
 * Decompiled with CFR 0.139.
 */
package fr.mrwormsy.omnivexel.launcher.game;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.MoreObjects;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

public class GameProcessBuilder {
    private final String processPath;
    private final List<String> arguments = Lists.newArrayList();
    private Predicate<String> sysOutFilter = s2 -> true;
    private GameOutputLogProcessor logProcessor = (process, logLine) -> {};
    private File directory;

    public GameProcessBuilder(String processPath) {
        if (processPath == null) {
            processPath = OperatingSystem.getCurrentPlatform().getJavaDir();
        }
        this.processPath = processPath;
    }

    public List<String> getFullCommands() {
        ArrayList<String> result = new ArrayList<String>(this.arguments);
        result.add(0, this.getProcessPath());
        return result;
    }

    public /* varargs */ void withArguments(String ... commands) {
        this.arguments.addAll(Arrays.asList(commands));
    }

    public List<String> getArguments() {
        return this.arguments;
    }

    public void directory(File directory) {
        this.directory = directory;
    }

    public File getDirectory() {
        return this.directory;
    }

    public void withSysOutFilter(Predicate<String> predicate) {
        this.sysOutFilter = predicate;
    }

    public void withLogProcessor(GameOutputLogProcessor logProcessor) {
        this.logProcessor = logProcessor;
    }

    public Predicate<String> getSysOutFilter() {
        return this.sysOutFilter;
    }

    private String getProcessPath() {
        return this.processPath;
    }

    public GameOutputLogProcessor getLogProcessor() {
        return this.logProcessor;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("processPath", this.processPath).add("arguments", this.arguments).add("sysOutFilter", this.sysOutFilter).add("directory", this.directory).add("logProcessor", this.logProcessor).toString();
    }
}

