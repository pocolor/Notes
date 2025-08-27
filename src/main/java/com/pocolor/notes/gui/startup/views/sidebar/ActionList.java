package com.pocolor.notes.gui.startup.views.sidebar;

import com.pocolor.notes.di.DI;

import javax.swing.*;

public class ActionList extends JPanel {
    public ActionList() {
        this.setOpaque(false);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(new ActionButton(DI.imageIcons.addFile(), "New", DI.viewModels.startupViewModel()::newButtonClicked));
        this.add(new ActionButton(DI.imageIcons.openFile(), "Open", DI.viewModels.startupViewModel()::openButtonClicked));
    }
}
