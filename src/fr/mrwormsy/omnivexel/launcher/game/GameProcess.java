/*
 * Decompiled with CFR 0.139.
 */
package fr.mrwormsy.omnivexel.launcher.game;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public interface GameProcess {
    public List<String> getStartupArguments();

    public Collection<String> getSysOutLines();

    public Predicate<String> getSysOutFilter();

    public boolean isRunning();

    public GameProcessRunnable getExitRunnable();

    public void setExitRunnable(GameProcessRunnable var1);

    public int getExitCode();

    public void stop();
}

