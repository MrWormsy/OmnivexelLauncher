/*
 * Decompiled with CFR 0.139.
 */
package fr.mrwormsy.omnivexel.launcher.game;

import java.util.List;

import com.google.common.base.Predicate;

public abstract class AbstractGameProcess
implements GameProcess {
    private final List<String> arguments;
    private final Predicate<String> sysOutFilter;
    private GameProcessRunnable onExit;

    protected AbstractGameProcess(List<String> arguments, Predicate<String> sysOutFilter) {
        this.arguments = arguments;
        this.sysOutFilter = sysOutFilter;
    }

    public Predicate<String> getSysOutFilter() {
        return this.sysOutFilter;
    }

    @Override
    public List<String> getStartupArguments() {
        return this.arguments;
    }

    @Override
    public GameProcessRunnable getExitRunnable() {
        return this.onExit;
    }

    @Override
    public void setExitRunnable(GameProcessRunnable runnable) {
        this.onExit = runnable;
        if (!this.isRunning() && runnable != null) {
            runnable.onGameProcessEnded(this);
        }
    }
}

