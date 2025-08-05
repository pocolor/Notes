package com.pocolor.notes.appdirs;

import com.pocolor.notes.appdirs.platform.*;

import java.nio.file.Path;

class PlatformSpecificAppDirs implements AppDirs {
    private final Platform platform;

    public PlatformSpecificAppDirs() {
        String osName = System.getProperty("os.name").toLowerCase();

        if (osName.contains("win")) {
            this.platform = new WindowsPlatform();

        } else if (osName.contains("linux") || osName.contains("mac")) {
            String s = System.getenv("XDG_CONFIG_HOME");

            if (s == null || s.isEmpty()) {
                this.platform = new LinuxPlatform();
            } else {
                this.platform = new LinuxXDGBasePlatform();
            }

        } else {
            this.platform = new DefaultLocation();
        }
    }

    @Override
    public Path getCacheDir() {
        return this.platform.getCacheDir();
    }

    @Override
    public Path getConfigDir() {
        return this.platform.getConfigDir();
    }

    @Override
    public Path getDataDir() {
        return this.platform.getDataDir();
    }

    @Override
    public Path getLogsDir() {
        return this.platform.getLogsDir();
    }
}
