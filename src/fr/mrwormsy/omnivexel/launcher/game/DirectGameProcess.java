/*
 * Decompiled with CFR 0.139.
 */
package fr.mrwormsy.omnivexel.launcher.game;

import java.util.Collection;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.EvictingQueue;

public class DirectGameProcess
extends AbstractGameProcess {
    private final Process process;
    //protected final DirectProcessInputMonitor monitor;
    private final Collection<String> sysOutLines = EvictingQueue.create(5);

    public DirectGameProcess(List<String> commands, Process process, Predicate<String> sysOutFilter, GameOutputLogProcessor logProcessor) {
        super(commands, sysOutFilter);
        this.process = process;
        //this.monitor = new DirectProcessInputMonitor(this, logProcessor);
        //this.monitor.start();
    }

    public Process getRawProcess() {
        return this.process;
    }

    @Override
    public Collection<String> getSysOutLines() {
        return this.sysOutLines;
    }

    @Override
    public boolean isRunning() {
        try {
            this.process.exitValue();
        }
        catch (IllegalThreadStateException ex) {
            return true;
        }
        return false;
    }

    @Override
    public int getExitCode() {
        try {
            return this.process.exitValue();
        }
        catch (IllegalThreadStateException ex) {
            ex.fillInStackTrace();
            throw ex;
        }
    }

    public String toString() {
        //return "Process: " + this.process + ", Monitor: " + this.monitor;
    	return "Process: " + this.process;
    }

    @Override
    public void stop() {
        this.process.destroy();
    }
}

