package com.pocolor.notes.appdirs;

import com.pocolor.notes.appdirs.platform.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Platform-specific implementation of {@link AppDirs} that automatically
 * detects the operating system and creates appropriate directory structures.
 *
 * <p>This class supports the following platforms:</p>
 * <ul>
 *   <li><b>Windows:</b> Uses LOCALAPPDATA and APPDATA environment variables</li>
 *   <li><b>Linux/Unix:</b> Follows XDG Base Directory specification or falls back to ~/.local/share</li>
 *   <li><b>macOS:</b> Uses standard macOS application directories</li>
 *   <li><b>Other:</b> Creates directories in the current working directory</li>
 * </ul>
 *
 * <p>All directories are automatically created during initialization if they don't exist.</p>
 *
 * @see AppDirs
 */
public class PlatformSpecificAppDirs implements AppDirs {
    private static final Logger log = LoggerFactory.getLogger(PlatformSpecificAppDirs.class);
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

        try {
            this.platform.createDirs();
        } catch (IOException e) {
            log.error("Failed to create platform directories.", e);
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
