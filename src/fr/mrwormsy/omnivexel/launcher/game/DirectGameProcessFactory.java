/*
 * Decompiled with CFR 0.139.
 */
package fr.mrwormsy.omnivexel.launcher.game;

import java.io.IOException;
import java.util.List;

public class DirectGameProcessFactory
implements GameProcessFactory {
    @Override
    public GameProcess startGame(GameProcessBuilder builder) throws IOException {
        List<String> full = builder.getFullCommands();
        return new DirectGameProcess(full, new ProcessBuilder(full).directory(builder.getDirectory()).redirectErrorStream(true).start(), builder.getSysOutFilter(), builder.getLogProcessor());
    }
}

