package com.pocolor.notes.gui.startup.views.sidebar;

import javax.swing.*;
import java.awt.*;

public class SideBar extends JPanel {
    public SideBar() {
        this.setPreferredSize(new Dimension(200, 0));
        this.setBackground(Color.BLUE);  // TMP
        this.setLayout(new BorderLayout());

        this.add(new ActionList(), BorderLayout.CENTER);
    }
}
