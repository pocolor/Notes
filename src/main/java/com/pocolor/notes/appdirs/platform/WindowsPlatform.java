package com.pocolor.notes.appdirs.platform;

import java.nio.file.Path;

public class WindowsPlatform extends Platform {
    public WindowsPlatform() {
        Path local = envOrDefault("LOCALAPPDATA", System.getProperty("user.home") + "\\AppData\\Local");
        Path roaming = envOrDefault("APPDATA", System.getProperty("user.home") + "\\AppData\\Roaming");

        this.cacheDir = local.resolve(APP_NAME).resolve("Cache");
        this.configDir = roaming;
        this.dataDir = local;
        this.logsDir = local.resolve(APP_NAME).resolve("Logs");
    }
}
