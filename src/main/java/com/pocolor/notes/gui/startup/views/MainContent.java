package com.pocolor.notes.gui.startup.views;

import javax.swing.*;
import java.awt.*;

public class MainContent extends JScrollPane {
    private final JPanel contentPane;

    public MainContent() {
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        this.contentPane = new JPanel();
        this.contentPane.setLayout(new BoxLayout(this.contentPane, BoxLayout.Y_AXIS));
        this.contentPane.setBorder(BorderFactory.createEmptyBorder());
        this.contentPane.setOpaque(true);
        this.contentPane.setBackground(Color.cyan);  // TMP

        this.setViewportView(this.contentPane);
    }
}
