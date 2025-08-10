package com.pocolor.notes;

import com.pocolor.notes.appdirs.PlatformSpecificAppDirs;
import com.pocolor.notes.gui.startup.StartupFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        System.setProperty("LOGS_DIR", new PlatformSpecificAppDirs().getLogsDir().toString());

        SwingUtilities.invokeLater(StartupFrame::new);
    }
}