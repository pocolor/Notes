package com.pocolor.notes.di;

import com.pocolor.notes.appdirs.PlatformSpecificAppDirs;

/**
 * Dependency Injection container providing singleton instances of application components.
 *
 * <p><b>Available services:</b></p>
 * <ul>
 *   <li>{@link ImageIcons} - Application icon resources</li>
 *   <li>{@link Values} - Application configuration values</li>
 *   <li>{@link PlatformSpecificAppDirs} - Platform-specific directories</li>
 *   <li>{@link ViewModels} - ViewModels</li>
 * </ul>
 *
 * <p><b>Usage:</b></p>
 * <pre>{@code
 * String appTitle = DI.values.appTitle();
 * ImageIcon bookIcon = DI.imageIcons.book();
 * StartupViewModel viewModel = DI.viewModels.startupViewModel();
 * }</pre>
 */
public final class DI {
    private DI() throws Exception { throw new Exception("no instances of this class"); }

    public static final ImageIcons imageIcons = new ImageIcons();
    public static final Values values = new Values();
    public static final PlatformSpecificAppDirs platformSpecificAppDirs = new PlatformSpecificAppDirs();
    public static final ViewModels viewModels = new ViewModels();
}
