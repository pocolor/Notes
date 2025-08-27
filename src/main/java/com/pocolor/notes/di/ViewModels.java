package com.pocolor.notes.di;

import com.pocolor.notes.gui.editor.EditorViewModel;
import com.pocolor.notes.gui.startup.StartupViewModel;

public final class ViewModels {
    private final StartupViewModel startupViewModel;
    private final EditorViewModel editorViewModel;

    ViewModels() {
        this.startupViewModel = new StartupViewModel();
        this.editorViewModel = new EditorViewModel();
    }

    public StartupViewModel startupViewModel() { return this.startupViewModel; }
    public EditorViewModel editorViewModel() { return this.editorViewModel; }
}
