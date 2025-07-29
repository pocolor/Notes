package com.pocolor.notes.gui.viewmodel.startup;

public class StartupViewModelFactory {
    private static class InstanceHolder {
        private static final StartupViewModelFactory INSTANCE = new StartupViewModelFactory();
    }

    public static StartupViewModelFactory getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private final StartupViewModel startupViewModel;
    private final TopBarViewModel topBarViewModel;
    private final TemplateViewModel templateViewModel;
    private final RecentViewModel recentViewModel;

    private StartupViewModelFactory() {
        this.startupViewModel = new StartupViewModel();
        this.topBarViewModel = new TopBarViewModel();
        this.templateViewModel = new TemplateViewModel();
        this.recentViewModel = new RecentViewModel();
    }

    public StartupViewModel getStartupViewModel() {
        return this.startupViewModel;
    }

    public TopBarViewModel getTopBarViewModel() {
        return this.topBarViewModel;
    }

    public TemplateViewModel getTemplateViewModel() {
        return this.templateViewModel;
    }

    public RecentViewModel getRecentViewModel() {
        return this.recentViewModel;
    }
}
