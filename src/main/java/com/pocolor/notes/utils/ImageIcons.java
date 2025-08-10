package com.pocolor.notes.utils;

import javax.swing.ImageIcon;
import java.awt.*;

public final class ImageIcons {
    private ImageIcons() {}

    public static final ImageIcon addFile = loadImageIcon("add-file.png");
    public static final ImageIcon book = loadImageIcon("book.png");
    public static final ImageIcon openFile = loadImageIcon("open-file.png");
    public static final ImageIcon recent = loadImageIcon("recent.png");

    public static ImageIcon scaleWidth(ImageIcon icon, int width) {
        int height = (icon.getIconHeight() * width) / icon.getIconWidth();
        return new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }

    public static ImageIcon scaleHeight(ImageIcon icon, int height) {
        int width = (icon.getIconWidth() * height) / icon.getIconHeight();
        return new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }

    private static ImageIcon loadImageIcon(String name) {
        //noinspection DataFlowIssue
        return new ImageIcon(ImageIcons.class.getResource("/icons/" + name));
    }
}
