package com.pocolor.notes.utils;

import javax.swing.*;
import java.awt.*;

public final class ImageIconUtils {
    private ImageIconUtils() throws Exception { throw new Exception("no instances of this class"); }

    public static ImageIcon scaleWidth(ImageIcon icon, int width) {
        int height = (icon.getIconHeight() * width) / icon.getIconWidth();
        return new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }

    public static ImageIcon scaleHeight(ImageIcon icon, int height) {
        int width = (icon.getIconWidth() * height) / icon.getIconHeight();
        return new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }
}
