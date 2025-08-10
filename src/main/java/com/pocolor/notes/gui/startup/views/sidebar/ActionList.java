package com.pocolor.notes.gui.startup.views.sidebar;

import com.pocolor.notes.gui.startup.viewmodels.ViewModels;
import com.pocolor.notes.utils.ImageIcons;

import javax.swing.*;

public class ActionList extends JPanel {
    public ActionList() {
        this.setOpaque(false);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(new ActionButton(ImageIcons.addFile, "New", ViewModels.sideBarViewModel::newButtonClicked));
        this.add(new ActionButton(ImageIcons.openFile, "Open", ViewModels.sideBarViewModel::openButtonClicked));
        this.add(new ActionButton(ImageIcons.recent, "Recent", ViewModels.sideBarViewModel::recentButtonClicked));
    }
}
