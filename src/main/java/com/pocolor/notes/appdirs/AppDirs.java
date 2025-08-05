package com.pocolor.notes.appdirs;

import java.nio.file.Path;

public interface AppDirs {
    Path getCacheDir();
    Path getConfigDir();
    Path getDataDir();
    Path getLogsDir();
}
