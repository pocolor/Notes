package com.pocolor.notes.appdirs.platform;

import com.pocolor.notes.di.DI;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class Platform {
    public static final String APP_DEV_NAME = DI.values.appDevName();

    protected Path cacheDir;
    protected Path configDir;
    protected Path dataDir;
    protected Path logsDir;

    public Path getCacheDir() {
        return this.cacheDir;
    }

    public Path getConfigDir() {
        return this.configDir;
    }

    public Path getDataDir() {
        return this.dataDir;
    }

    public Path getLogsDir() {
        return this.logsDir;
    }

    public void createDirs() throws IOException {
        Files.createDirectories(this.cacheDir);
        Files.createDirectories(this.configDir);
        Files.createDirectories(this.dataDir);
        Files.createDirectories(this.logsDir);
    }

    protected static Path envOrDefault(String envVar, String defaultValue) {
        String value = System.getenv(envVar);
        if (value == null || value.isEmpty()) {
            return Paths.get(defaultValue);
        }
        return Paths.get(value);
    }
}
