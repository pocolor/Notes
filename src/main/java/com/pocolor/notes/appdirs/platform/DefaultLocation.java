package com.pocolor.notes.appdirs.platform;

import java.nio.file.Paths;

public class DefaultLocation extends Platform {
    public DefaultLocation() {
        this.cacheDir = Paths.get(APP_DEV_NAME, "cache");
        this.configDir = Paths.get(APP_DEV_NAME, "config");
        this.dataDir = Paths.get(APP_DEV_NAME, "data");
        this.logsDir = Paths.get(APP_DEV_NAME, "logs");
    }
}
