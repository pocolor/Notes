package com.pocolor.notes.startup.viewmodel;

public class ViewModelFactory {
    private static class InstanceHolder {
        private static final ViewModelFactory INSTANCE = new ViewModelFactory();
    }

    public static ViewModelFactory getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private final StartupViewModel startupViewModel;
    private final TopBarViewModel topBarViewModel;
    private final TemplateViewModel templateViewModel;
    private final RecentViewModel recentViewModel;

    private ViewModelFactory() {
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
