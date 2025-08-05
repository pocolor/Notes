package com.pocolor.notes.appdirs.platform;

public class LinuxXDGBasePlatform extends Platform {
    public LinuxXDGBasePlatform() {
        this.cacheDir = envOrDefault("XDG_CACHE_HOME", System.getProperty("user.home") + "/.cache").resolve(APP_NAME);
        this.configDir = envOrDefault("XDG_CONFIG_HOME", System.getProperty("user.home") + "/.config").resolve(APP_NAME);
        this.dataDir = envOrDefault("XDG_DATA_HOME", System.getProperty("user.home") + "/.local/share").resolve(APP_NAME);
        this.logsDir = this.dataDir.resolve("logs");
    }
}
