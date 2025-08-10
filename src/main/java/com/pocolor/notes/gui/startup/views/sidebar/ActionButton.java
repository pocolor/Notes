package com.pocolor.notes.gui.startup.views.sidebar;

import com.pocolor.notes.utils.ImageIcons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ActionButton extends JPanel {
    public ActionButton(ImageIcon icon, String text, Runnable onClick) {
        this.setOpaque(true);
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.setMaximumSize(new Dimension(200, 50));

        this.add(new JLabel(ImageIcons.scaleHeight(icon, 40)));
        this.add(Box.createHorizontalStrut(10));
        this.add(new JLabel(text));

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onClick.run();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                ActionButton.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ActionButton.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }
}
