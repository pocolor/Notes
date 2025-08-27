package com.pocolor.notes.di;

import com.pocolor.notes.appdirs.PlatformSpecificAppDirs;

public final class DI {
    private DI() throws Exception { throw new Exception("no instances of this class"); }

    public static final ImageIcons imageIcons = new ImageIcons();
    public static final Values values = new Values();
    public static final PlatformSpecificAppDirs platformSpecificAppDirs = new PlatformSpecificAppDirs();
    public static final ViewModels viewModels = new ViewModels();
}
