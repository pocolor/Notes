package com.pocolor.notes.appdirs.platform;

import java.nio.file.Paths;

public class LinuxPlatform extends Platform {
    public LinuxPlatform() {
        this.cacheDir = Paths.get(System.getProperty("user.home"), ".cache", APP_NAME);
        this.configDir = Paths.get(System.getProperty("user.home"), ".config", APP_NAME);
        this.dataDir = Paths.get(System.getProperty("user.home"), ".local", "share", APP_NAME);
        this.logsDir = this.dataDir.resolve("logs");
    }
}
