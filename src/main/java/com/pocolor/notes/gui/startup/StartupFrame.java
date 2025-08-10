package com.pocolor.notes.gui.startup;

import com.pocolor.notes.gui.startup.views.MainContent;
import com.pocolor.notes.gui.startup.views.sidebar.SideBar;
import com.pocolor.notes.utils.ImageIcons;

import javax.swing.*;
import java.awt.*;

public class StartupFrame extends JFrame {
    public StartupFrame() {
        this.setTitle("Notes");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setIconImage(ImageIcons.book.getImage());

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new SideBar(), BorderLayout.WEST);
        contentPane.add(new MainContent(), BorderLayout.CENTER);
        this.setContentPane(contentPane);
        this.setVisible(true);
    }
}
